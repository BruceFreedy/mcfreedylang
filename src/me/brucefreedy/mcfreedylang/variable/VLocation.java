package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import org.bukkit.Location;

public class VLocation extends SimpleVar<Location> {

    public VLocation(Location object) {
        super(object);
        register("x", doubleValue(object::setX, object::getX));
        register("y", doubleValue(object::setY, object::getY));
        register("z", doubleValue(object::setZ, object::getZ));
        register("yaw", floatValue(object::setYaw, object::getYaw));
        register("pitch", floatValue(object::setPitch, object::getPitch));
        register("world", (Method) (unit, params) -> new VWorld(object.getWorld()));
        register("block", (Method) (unit, params) -> new VBlock(object.getBlock().getState()));
        register("direction", (Method) (unit, params) ->  new VVector(object.getDirection()));
    }

}
