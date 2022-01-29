package me.brucefreedy.mcfreedylang.command;

import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.variable.VariableImpl;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public abstract class CustomCommand extends Command implements PluginIdentifiableCommand {

    protected CustomCommand(String name) {
        super(name);
    }

    @Override
    public Plugin getPlugin() {
        return API.getPlugin();
    }

    public abstract void execute(CommandSender commandSender, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!Bukkit.isPrimaryThread()) return false;
        execute(sender, args);
        return true;
    }
}
