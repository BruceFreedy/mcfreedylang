package me.brucefreedy.mcfreedylang.variable.enumvar;

import org.bukkit.event.entity.EntityDamageEvent;

public class VDamageCause extends AbstractEnumVar<EntityDamageEvent.DamageCause> {
    public VDamageCause(EntityDamageEvent.DamageCause object) {
        super(object);
    }
}
