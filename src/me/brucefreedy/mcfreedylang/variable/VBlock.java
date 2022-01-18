package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.block.BlockState;

public class VBlock extends AbstractVar<BlockState> {
    public VBlock(BlockState object) {
        super(object);
    }
}
