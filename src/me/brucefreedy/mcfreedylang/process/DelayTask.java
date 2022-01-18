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

@Processable(alias = "delay")
public class DelayTask extends EmptyImpl<Object> {

    Object result = new Null();
    long delay;
    Process<?> body;

    @Override
    public void parse(ParseUnit parseUnit) {
        Process<?> process = Process.parsing(parseUnit);
        Object o = process.get();
        if (o instanceof Number) delay = ((Number) o).getNumber().longValue();
        else return;
        body = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        int id = Bukkit.getScheduler().runTaskLater(API.getPlugin(), () -> {
            VariableRegister register = new VariableRegister();
            register.add(API.getRegister().getScope());
            body.run(new ProcessUnit(register));
        }, delay).getTaskId();
        result = new SimpleNumber(id);
    }

    @Override
    public Object get() {
        return result;
    }
}
