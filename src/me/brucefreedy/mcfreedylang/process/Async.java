package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.Bukkit;

@Processable(alias = "async")
public class Async extends ProcessImpl<Object> {

    Number taskId;

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        parseUnit.popPeek(stealer -> {
            stealer.setProcess(process);
            process = stealer;
        });
    }

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
        Object o = process.get();
        String src = o.toString();
        VariableRegister variableRegister = new VariableRegister(processUnit.getVariableRegister());
        Bukkit.getScheduler().runTaskAsynchronously(API.getPlugin(), () -> {
            Process<?> p = Process.parsing(new ParseUnit(API.getRegister().getProcessRegister(), src));
            p.run(new ProcessUnit(variableRegister));
        });
    }

    @Override
    public Object get() {
        return taskId == null ? new Null() : taskId;
    }
}
