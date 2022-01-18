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
    long delay;
    long period;
    Process<?> body;

    private long workNumber(ParseUnit parseUnit) {
        Process<?> process = Process.parsing(parseUnit);
        Object o = process.get();
        if (o instanceof Number) return ((Number) o).getNumber().longValue();
        else return 0;
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        delay = workNumber(parseUnit);
        period = workNumber(parseUnit);
        body = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
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

