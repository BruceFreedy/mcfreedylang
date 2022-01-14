package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.entity.Entity;

public class VEntity<T extends Entity> extends AbstractVar<T> {
    public VEntity(T object) {
        super(object);
        register("location", new VLocation(object.getLocation()));
        register("name", stringValue(object::setCustomName, object::getName));
    }
}
