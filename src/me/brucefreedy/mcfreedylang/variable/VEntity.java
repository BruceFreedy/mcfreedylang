package me.brucefreedy.mcfreedylang.variable;

import com.sun.org.apache.bcel.internal.generic.I2F;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class VEntity<T extends Entity> extends SimpleVar<T> {
    
    public VEntity(T object) {
        super(object);
        register("location", method(o -> o instanceof VLocation, Location.class,
                object::teleport, () -> new VLocation(object.getLocation())));
        register("name", stringValue(object::setCustomName, object::getName));
        register("uuid", (Method) (unit, params) -> new VUUID(object.getUniqueId()));
        register("world", (Method) (unit, params) -> new VWorld(object.getWorld()));
        register("velocity", method(o -> o instanceof VVector, Vector.class,
                object::setVelocity, () -> new VVector(object.getVelocity())));
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) return true;
        if (o instanceof AbstractVar) {
            Object object = ((AbstractVar<?>) o).getObject();
            if (object instanceof Entity)
                return this.object.getUniqueId().equals(((Entity) object).getUniqueId());
        }
        return false;
    }
}
