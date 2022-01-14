package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.World;

public class VWorld extends AbstractVar<World> {
    public VWorld(World object) {
        super(object);
        register("name", object.getName());
    }
}
