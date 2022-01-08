package me.brucefreedy.mcfreedylang;

import lombok.Getter;
import me.brucefreedy.freedylang.registry.ProcessRegister;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Plugin extends JavaPlugin {

    @Getter
    private static Plugin inst;

    public Plugin() {
        inst = this;
    }

    @Getter
    private ProcessRegister processRegister;

    @Override
    public void onEnable() {
        load();
        getCommand("fl").setExecutor(new Command());
    }

    public void load() {
        if (processRegister == null) {
            processRegister = new ProcessRegister();
            processRegister.register();
            Arrays.stream(ProcessDefs.values()).forEach(pDefs -> processRegister.register(pDefs.getSupplier()));
        }
        HandlerList.unregisterAll(this);
    }

}
