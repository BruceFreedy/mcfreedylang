package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.*;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;

@Processable(alias = "repeat")
public class RepeatTask implements Process<Object>, Stacker<Object> {

    Object result = new Null();
    Process<?> delay;
    Process<?> period;
    Process<?> body;
    Scope parent;
    boolean hasNoParent;

    @Override
    public void parse(ParseUnit parseUnit) {
        delay = Process.parsing(parseUnit);
        parseUnit.steal(p -> delay = p, () -> delay);
        if (delay instanceof Stacker) {
            period = ((Stacker<?>) delay).getProcess();
            if (period instanceof Breaker) {
                period = Process.parsing(parseUnit);
                parseUnit.steal(p -> period = p, () -> period);
            }
        }
        else {
            period = Process.parsing(parseUnit);
            parseUnit.steal(p -> period = p, () -> period);
        }
        if (period instanceof Stacker) {
            body = ((Stacker<?>) period).getProcess();
            if (body instanceof Breaker) {
                body = Process.parsing(parseUnit);
                parseUnit.steal(p -> body = p, () -> body);
            }
        }
        else {
            body = Process.parsing(parseUnit);
            parseUnit.steal(p -> body = p, () -> body);
        }
        List<Process<?>> peek = parseUnit.getDeclaration().peek();
        if (peek != null) peek.add(this);
        else hasNoParent = true;
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (parent == null) {
            parent = processUnit.getVariableRegister().peek();
            return;
        }
        long delay, period;
        this.delay.run(processUnit);
        Object delayO = this.delay.get();
        if (delayO instanceof Number) delay = ((Number) delayO).getNumber().longValue();
        else return;
        this.period.run(processUnit);
        Object periodO = this.period.get();
        if (periodO instanceof Number) period = ((Number) periodO).getNumber().longValue();
        else return;
        VariableRegister register = new VariableRegister(processUnit.getVariableRegister());
        if (!register.isEmpty()) register.set(0, API.getRegister().getScope());
        if (!hasNoParent) register.set(processUnit.getVariableRegister().indexOf(parent), parent);
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(API.getPlugin(),
                () -> body.run(new ProcessUnit(register)), delay, period);
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

