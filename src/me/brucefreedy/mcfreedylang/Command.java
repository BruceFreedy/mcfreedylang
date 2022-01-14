package me.brucefreedy.mcfreedylang;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.mcfreedylang.variable.VPlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            org.bukkit.command.Command command,
            String label,
            String[] args) {
        String arg = String.join(" ", args);
        if (arg.equalsIgnoreCase("reload")) {
            API.getRegister().load();
            System.out.println("Reloaded");
            return true;
        }
        Process<?> parsing = Process.parsing(new ParseUnit(API.getRegister().getProcessRegister(), "{" + arg + "}"));
        Scope scope = new Scope(Scope.ScopeType.METHOD);
        if (sender instanceof Player) scope.register("player", new VPlayer((Player) sender));
        ((AbstractFront) parsing).setScopeSupplier(() -> scope);
        VariableRegister register = new VariableRegister();
        register.add(API.getRegister().getScope());
        parsing.run(new ProcessUnit(register));
        return true;
    }
}
