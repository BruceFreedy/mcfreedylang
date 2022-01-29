package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.*;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.ScopeChild;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;

@Processable(alias = "delay")
public class DelayTask implements Process<Object>, Stacker<Object>, ScopeChild {

    Object result = new Null();
    Process<?> delay;
    Process<?> body;
    Scope parent;
    boolean hasNoParent;

    @Override
    public void setParent(ProcessUnit processUnit, Scope scope) {
        parent = scope;
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        delay = Process.parsing(parseUnit);
        parseUnit.steal(p -> delay = p, () -> delay);
        if (delay instanceof Stacker) {
            body = ((Stacker<?>) delay).getProcess();
            if (body instanceof Breaker) {
                body = Process.parsing(parseUnit);
                parseUnit.steal(p -> body = p, () -> body);
            }
        } else {
            body = Process.parsing(parseUnit);
            parseUnit.steal(p -> body = p, () -> body);
        }
        List<ScopeChild> peek = parseUnit.getDeclaration().peek();
        if (peek != null) peek.add(this);
        else hasNoParent = true;
    }

    @Override
    public void run(ProcessUnit processUnit) {
        long delay;
        this.delay.run(processUnit);
        Object delayO = this.delay.get();
        if (delayO instanceof Number) delay = ((Number) delayO).getNumber().longValue();
        else return;
        VariableRegister register;
        if (hasNoParent) {
          register = processUnit.getVariableRegister();
        } else {
            register = new VariableRegister(processUnit.getVariableRegister());
            if (!register.isEmpty()) register.set(0, API.getRegister().getScope());
            if (!hasNoParent) register.set(processUnit.getVariableRegister().indexOf(parent), parent);
        }
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
