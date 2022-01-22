package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.util.Vector;

public class VVector extends AbstractVar<Vector> {
    public VVector(Vector object) {
        super(object);
        register("x", doubleValue(object::setX, object::getX));
        register("y", doubleValue(object::setY, object::getY));
        register("z", doubleValue(object::setZ, object::getZ));
        register("add", method(o -> o instanceof VVector, Vector.class, object::add, Null::new));
        register("multiply", method(o -> o instanceof VVector, Vector.class, object::multiply, Null::new));
    }
}
