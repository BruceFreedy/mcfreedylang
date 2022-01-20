package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract class AbstractEventListener<EV extends Event> extends EmptyImpl<AbstractEventListener<EV>>
        implements Listener {

    Process<?> body;
    Scope parent;

    @Override
    public void parse(ParseUnit parseUnit) {
        Bukkit.getPluginManager().registerEvents(this, API.getPlugin());
        body = Process.parsing(parseUnit);
        parent = parseUnit.getProcessRegister().getVariableRegister().peek();
    }

    @Override
    public AbstractEventListener<EV> get() {
        return this;
    }

    protected void wrap(EV event, Scope scope) {
        scope.register("eventName", event.getEventName());
    }

    protected void map(EV event, Scope scope) {}

    public void onEvent(EV event) {
        Scope scope = new Scope(Scope.ScopeType.METHOD);
        if (body instanceof AbstractFront) ((AbstractFront) body).setBeforeRun(() -> wrap(event, scope));
        else wrap(event, scope);
        VariableRegister register = new VariableRegister();
        register.add(API.getRegister().getScope());
        if (parent != null) register.add(parent);
        register.add(scope);
        body.run(new ProcessUnit(register));
        map(event, scope);
    }

    protected void register(Scope scope, String name, Object o) {
        scope.register(name, o == null ? new Null() : o);
    }

}
