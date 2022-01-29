package me.brucefreedy.mcfreedylang.command;

import lombok.Getter;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

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
        Arrays.stream(commands).forEach(command -> {
            scm.register(API.getPlugin().getName(), command);
            API.getPlugin().getCommand(command.getName()).setTabCompleter((sender, command1, alias, args) -> null);
        });
    }

    public void unregisterCommands() {
        scm.getCommands().stream().filter(command -> command instanceof CustomCommand).forEach(command -> command.unregister(scm));
    }

}
