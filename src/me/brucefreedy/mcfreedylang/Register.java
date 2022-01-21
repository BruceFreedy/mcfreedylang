package me.brucefreedy.mcfreedylang;

import lombok.Getter;
import lombok.SneakyThrows;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.registry.ProcessRegister;
import me.brucefreedy.freedylang.registry.ProcessUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Register {

    @Getter
    private ProcessRegister processRegister;
    @Getter
    private Scope scope;

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
        Bukkit.getScheduler().cancelTasks(API.getPlugin());
        File src = new File(API.getPlugin().getDataFolder(), "src");
        if (!src.exists()) {
            src.mkdirs();
            File defaultFile = new File(src, "freedy.java");
            Files.write(defaultFile.toPath(), "log 'Hello, world!'".getBytes());
        }
        File[] files = src.listFiles(File::isFile);
        if (files == null) throw new IOException();
        Arrays.stream(files).forEach(file -> {
            Process<?> process = parsing(processRegister, file.toPath());
            ((AbstractFront) process).setScopeSupplier(() -> scope);
            process.run(new ProcessUnit(processRegister.getVariableRegister()));
        });
        Arrays.stream(MethodDefs.values()).forEach(m -> scope.register(m.name().toLowerCase(), m.getMethod()));
    }

    public static Process<?> parsing(ProcessRegister processRegister, Path path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String source = ColorEdit.toColor(stringBuilder.toString());
        return ProcessUtils.parsing(processRegister, ProcessUtils.withoutExtension(path.toFile().getName()), source);
    }

}
