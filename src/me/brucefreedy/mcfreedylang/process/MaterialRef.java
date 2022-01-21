package me.brucefreedy.mcfreedylang.process;

import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.mcfreedylang.variable.VMaterial;
import org.bukkit.Material;

@Processable(alias = "material")
public class MaterialRef extends ProcessImpl<VMaterial> {

    Material material = Material.AIR;

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
        Object o = process.get();
        material = Material.valueOf(o.toString());
    }

    @Override
    public VMaterial get() {
        return new VMaterial(material);
    }
}
