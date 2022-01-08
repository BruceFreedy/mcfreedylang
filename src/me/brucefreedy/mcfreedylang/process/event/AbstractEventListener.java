package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract class AbstractEventListener<EV extends Event> extends ProcessImpl<AbstractEventListener<EV>>
        implements Listener {

    @Override
    public void parse(ParseUnit parseUnit) {
        Bukkit.getPluginManager().registerEvents(this, API.getPlugin());
            super.parse(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
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
        VariableRegister variableRegister = API.getRegister().getVariableRegister();
        if (process instanceof AbstractFront) ((AbstractFront) process).setBeforeRun(() -> wrap(event, variableRegister));
        else wrap(event, variableRegister);
        process.run(new ProcessUnit(variableRegister));
    }

}
