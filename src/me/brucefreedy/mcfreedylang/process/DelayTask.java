package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.*;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;

@Processable(alias = "delay")
public class DelayTask implements Process<Object>, Stacker<Object> {

    Object result = new Null();
    Process<?> delay;
    Process<?> body;

    @Override
    public void parse(ParseUnit parseUnit) {
        delay = Process.parsing(parseUnit);
        parseUnit.steal(p -> delay = p, () -> delay);
        body = Process.parsing(parseUnit);
        parseUnit.steal(p -> body = p, () -> body);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        long delay;
        this.delay.run(processUnit);
        Object delayO = this.delay.get();
        if (delayO instanceof Number) delay = ((Number) delayO).getNumber().longValue();
        else return;
        VariableRegister register = new VariableRegister(processUnit.getVariableRegister());
        if (!register.isEmpty()) register.set(0, API.getRegister().getScope());
        int id = Bukkit.getScheduler().runTaskLater(API.getPlugin(),
                () -> body.run(new ProcessUnit(register)), delay).getTaskId();
        result = new SimpleNumber(id);
    }

    @Override
    public Object get() {
        return result;
    }

    @Override
    public String toString() {
        return result.toString();
    }

    @Override
    public Process<?> getProcess() {
        return new Breaker();
    }
}
