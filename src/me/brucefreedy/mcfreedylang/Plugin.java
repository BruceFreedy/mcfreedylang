package me.brucefreedy.mcfreedylang;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Getter
    private static Plugin inst;
    @Getter
    private Register register;

    public Plugin() {
        inst = this;
    }

    @Override
    public void onEnable() {
        register = new Register();
        getCommand("fl").setExecutor(new Command());
    }


}
