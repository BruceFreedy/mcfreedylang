package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import me.brucefreedy.mcfreedylang.variable.enumvar.VMaterial;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

public class VBlock extends SimpleVar<BlockState> {
    public VBlock(BlockState object) {
        super(object);
        register("location", (Method) (unit, params) -> new VLocation(object.getLocation()));
        register("type", method(o -> o instanceof VMaterial, Material.class, m -> object.getBlock().setType(m), object::getType));
    }
}
