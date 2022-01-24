package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.*;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableImpl;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.mcfreedylang.API;
import me.brucefreedy.mcfreedylang.event.CustomEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Processable(alias = "@", regex = true)
public class CustomEventListener extends EmptyImpl<Null> implements Listener {

    String name;
    Process<?> body;
    Scope parent;

    @Override
    public void parse(ParseUnit parseUnit) {
        try {
            super.parse(parseUnit);
            Process<?> process = Process.parsing(parseUnit);
            if (process instanceof VariableImpl) {
                VariableImpl varImpl = (VariableImpl) process;
                name = String.join(".", varImpl.getNodes());
                body =varImpl.getProcess();
                if (body instanceof Breaker) body = null;
                else {
                    Bukkit.getPluginManager().registerEvents(this, API.getPlugin());
                    List<Process<?>> peek = parseUnit.getDeclaration().peek();
                    if (peek != null) peek.add(this);
                    parseUnit.popPeek(stealer -> {
                        stealer.setProcess(body);
                        body = stealer;
                    });
                }
            }
        } catch (Exception ignored) {}
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (body != null) {
            parent = processUnit.getVariableRegister().peek();
        } else Bukkit.getPluginManager().callEvent(new CustomEvent(name));
    }

    @EventHandler
    public void onCustomEvent(CustomEvent customEvent) {
        if (customEvent.getName().equals(name)) {
            Scope scope = new Scope(Scope.ScopeType.METHOD);
            VariableRegister register = new VariableRegister();
            register.add(API.getRegister().getScope());
            if (parent != null) register.add(parent);
            register.add(scope);
            body.run(new ProcessUnit(register));
        }
    }

    @Override
    public Null get() {
        return new Null();
    }
}
