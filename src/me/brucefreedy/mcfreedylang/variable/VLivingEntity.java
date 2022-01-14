package me.brucefreedy.mcfreedylang.variable;

import org.bukkit.entity.LivingEntity;

public class VLivingEntity<T extends LivingEntity> extends VEntity<T> {
    public VLivingEntity(T object) {
        super(object);
        register("health", doubleValue(object::setHealth, object::getHealth));
        register("eyeLocation", new VLocation(object.getEyeLocation()));
    }
}
