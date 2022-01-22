package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.World;

public class VWorld extends AbstractVar<World> {
    public VWorld(World object) {
        super(object);
        register("name", (Method) (unit, params) -> object.getName());
    }

    @Override
    public String toString() {
        if (object == null) return new Null().toString();
        return object.getName();
    }
}
