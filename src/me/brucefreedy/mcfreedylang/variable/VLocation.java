package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.Location;

public class VLocation extends AbstractVar<Location> {
    public VLocation(Location object) {
        super(object);
        registerMethod("x", doubleValue(object::setX, object::getX));
        registerMethod("y", doubleValue(object::setY, object::getY));
        registerMethod("z", doubleValue(object::setZ, object::getZ));
        registerMethod("yaw", floatValue(object::setYaw, object::getYaw));
        registerMethod("pitch", floatValue(object::setPitch, object::getPitch));
        register("world", new VWorld(object.getWorld()));

    }



}
