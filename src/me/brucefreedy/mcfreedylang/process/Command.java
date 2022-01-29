package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Breaker;
import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.ScopeChild;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableImpl;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.mcfreedylang.API;
import me.brucefreedy.mcfreedylang.command.CustomCommand;
import me.brucefreedy.mcfreedylang.variable.VCommandBlock;
import me.brucefreedy.mcfreedylang.variable.VPlayer;
import org.bukkit.Bukkit;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@Processable(alias = "@/", regex = true)
public class Command implements ScopeChild, Process<Null> {

    Scope parent;
    Process<?> body;
    String name;

    @Override
    public void setParent(ProcessUnit processUnit, Scope scope) {
        parent = scope;
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        Process<?> p = Process.parsing(parseUnit);
        if (p instanceof VariableImpl) name = String.join(".", ((VariableImpl) p).getNodes());
        else return;
        body = Process.parsing(parseUnit);
        if (body instanceof Breaker) body = Process.parsing(parseUnit);
        List<ScopeChild> peek = parseUnit.getDeclaration().peek();
        if (peek != null) peek.add(this);
        API.getRegister().getCommandRegister().registerCommands(new CustomCommand(name) {
            @Override
            public void execute(CommandSender sender, String[] args) {
                VariableRegister scopes = new VariableRegister();
                scopes.add(API.getRegister().getScope());
                if (parent != null) scopes.add(parent);
                Scope scope = new Scope(Scope.ScopeType.METHOD);
                if (sender instanceof Player) scope.register("sender", new VPlayer((Player) sender));
                if (sender instanceof CommandBlock) scope.register("sender", new VCommandBlock(((CommandBlock) sender)));
                if (sender instanceof ConsoleCommandSender) scope.register("sender", (Method) (unit, params) -> new Null());
                else return;
                if (body instanceof AbstractFront) ((AbstractFront) body).setScopeSupplier(() -> scope);
                else scopes.add(scope);
                body.run(new ProcessUnit(scopes));
                System.out.println("debug-----");
            }
        });
    }

    @Override
    public void run(ProcessUnit processUnit) {}

    @Override
    public Null get() {
        return new Null();
    }

}
