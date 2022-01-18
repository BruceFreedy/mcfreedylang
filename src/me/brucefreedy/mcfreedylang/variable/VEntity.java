package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class VEntity<T extends Entity> extends AbstractVar<T> {
    
    public VEntity(T object) {
        super(object);
        register("location", method(o -> o instanceof VLocation, Location.class, location -> {
            object.sendMessage("debugmessage");
            object.teleport(location);
        }, () -> this));
        register("name", stringValue(object::setCustomName, object::getName));
    }

}
