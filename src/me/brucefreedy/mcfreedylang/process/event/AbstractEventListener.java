package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract class AbstractEventListener<EV extends Event> extends EmptyImpl<AbstractEventListener<EV>>
        implements Listener {

    Process<?> body;

    @Override
    public void parse(ParseUnit parseUnit) {
        Bukkit.getPluginManager().registerEvents(this, API.getPlugin());
        body = Process.parsing(parseUnit);
    }

    @Override
    public AbstractEventListener<EV> get() {
        return this;
    }

    protected void wrap(EV event, VariableRegister variableRegister) {
        variableRegister.setVariable("eventName", event.getEventName());
    }

    protected void map(EV event, VariableRegister variableRegister) {

    }

    public void onEvent(EV event) {
        VariableRegister variableRegister = API.getRegister().getProcessRegister().getVariableRegister();
        if (body instanceof AbstractFront) ((AbstractFront) body).setBeforeRun(() -> wrap(event, variableRegister));
        else wrap(event, variableRegister);
        body.run(new ProcessUnit(variableRegister));
    }

}
