package me.brucefreedy.mcfreedylang;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        String arg = String.join(" ", args);
        if (arg.equalsIgnoreCase("reload")) {
            API.getRegister().load();
            System.out.println("Reloaded");
            return true;
        }
        Process.parsing(new ParseUnit(API.getRegister().getProcessRegister(), "{" + arg + "}"))
                .run(new ProcessUnit(API.getRegister().getProcessRegister().getVariableRegister()));
        return true;
    }
}
