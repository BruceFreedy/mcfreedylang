package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import org.bukkit.block.CommandBlock;

public class VCommandBlock extends SimpleVar<CommandBlock> {
    public VCommandBlock(CommandBlock commandBlock) {
        super(commandBlock);
        register("command", stringValue(object::setCommand, object::getCommand));
        register("name", stringValue(object::setName, object::getName));
        register("location", func(o -> new VLocation(object.getLocation())));
        register("block", func(o -> object.getBlock()));
    }
}
