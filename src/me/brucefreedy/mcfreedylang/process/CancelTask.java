package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import org.bukkit.Bukkit;

@Processable(alias = "canceltask")
public class CancelTask extends ProcessImpl<CancelTask> {

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
        Object o = process.get();
        if (o instanceof Number) {
            int id = ((Number) o).getNumber().intValue();
            Bukkit.getScheduler().cancelTask(id);
        }
    }

    @Override
    public CancelTask get() {
        return this;
    }
}
