package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.mcfreedylang.abst.AbstractEnumVar;
import org.bukkit.Material;

public class VMaterial extends AbstractEnumVar<Material> implements Process<VMaterial>, Stacker<VMaterial> {
    public VMaterial(Material object) {
        super(object);
    }

    Process<?> process;

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        process.run(processUnit);
        object = Material.valueOf(process.get().toString());
    }

    @Override
    public Process<?> getProcess() {
        return process;
    }

    @Override
    public VMaterial get() {
        return this;
    }

}
