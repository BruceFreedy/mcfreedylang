package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

public class VBlock extends AbstractVar<BlockState> {
    public VBlock(BlockState object) {
        super(object);
        register("location", (Method) (unit, params) -> new VLocation(object.getLocation()));
        register("type", method(o -> o instanceof VMaterial, Material.class, object::setType, object::getType));
    }
}
