package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;

@Processable(alias = "repeat")
public class RepeatTask extends EmptyImpl<Object> {

    Object result = new Null();
    Process<?> delay;
    Process<?> period;
    Process<?> body;

    @Override
    public void parse(ParseUnit parseUnit) {
        delay = Process.parsing(parseUnit);
        period = Process.parsing(parseUnit);
        body = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        long delay, period;
        this.delay.run(processUnit);
        Object delayO = this.delay.get();
        if (delayO instanceof Number) delay = ((Number) delayO).getNumber().longValue();
        else return;
        this.period.run(processUnit);
        Object periodO = this.period.get();
        if (periodO instanceof Number) period = ((Number) periodO).getNumber().longValue();
        else return;
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(API.getPlugin(), () -> {
            VariableRegister register = new VariableRegister();
            register.add(API.getRegister().getScope());
            body.run(new ProcessUnit(register));
        }, delay, period);
        result = new SimpleNumber(id);
    }

    @Override
    public Object get() {
        return result;
    }
}

