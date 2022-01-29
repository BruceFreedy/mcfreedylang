package me.brucefreedy.mcfreedylang.command;

import lombok.Getter;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandRegister {

    private SimpleCommandMap scm;
    @Getter
    private final SimplePluginManager spm;

    public CommandRegister() {
        spm = (SimplePluginManager) API.getPlugin().getServer().getPluginManager();
        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        try {
            scm = (SimpleCommandMap) f.get(spm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerCommands(CustomCommand... commands) {
        Arrays.stream(commands).forEach(command -> scm.register("pluginname", command));//Register the plugin
    }

}
