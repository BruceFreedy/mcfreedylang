package me.brucefreedy.mcfreedylang;

import lombok.Getter;
import lombok.SneakyThrows;
import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableImpl;
import me.brucefreedy.freedylang.registry.ProcessRegister;
import me.brucefreedy.freedylang.registry.ProcessUtils;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Register {

    @Getter
    private ProcessRegister processRegister;
    @Getter
    private Scope scope;
    private List<VariableImpl> classes = new List<>();

    public Register() {
        load();
    }

    @SneakyThrows
    public void load() {
        processRegister = new ProcessRegister();
        processRegister.register();
        Arrays.stream(ProcessDefs.values()).forEach(pDefs -> processRegister.register(pDefs.getSupplier()));
        scope = new Scope();
        HandlerList.unregisterAll(API.getPlugin());
        File src = new File(API.getPlugin().getDataFolder(), "src");
        if (!src.exists()) {
            src.mkdirs();
            File defaultFile = new File(src, "freedy.java");
            Files.write(defaultFile.toPath(), "log 'Hello, world!'".getBytes());
        }
        File[] files = src.listFiles(File::isFile);
        if (files == null) throw new IOException();
        Arrays.stream(files).forEach(file -> {
            Process<?> process = ProcessUtils.parsing(processRegister, file.toPath());
            ((AbstractFront) process).setScopeSupplier(() -> scope);
            process.run(new ProcessUnit(processRegister.getVariableRegister()));
        });
        scope.getRegistry().forEach(o -> classes.add(((VariableImpl) o)));
    }


}
