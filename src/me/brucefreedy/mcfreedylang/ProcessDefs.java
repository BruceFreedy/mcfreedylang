package me.brucefreedy.mcfreedylang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.mcfreedylang.process.*;
import me.brucefreedy.mcfreedylang.process.event.CustomEventListener;
import me.brucefreedy.mcfreedylang.process.event.EventListener;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public enum ProcessDefs {
    JOIN(EventListener.PlayerJoin::new),
    LEFT(EventListener.PlayerLeft::new),
    INTERACT(EventListener.PlayerInteract::new),
    CHAT(EventListener.PlayerChat::new),
    COMMAND(EventListener.PlayerCommand::new),
    MOVE(EventListener.PlayerMove::new),
    DELAY(DelayTask::new),
    REPEAT(RepeatTask::new),
    CANCEL_TASK(CancelTask::new),
    ASYNC(Async::new),
    INVENTORY_CLICK(EventListener.InventoryClick::new),
    INVENTORY_DRAG(EventListener.InventoryDrag::new),
    INVENTORY_CLOSE(EventListener.InventoryClose::new),
    DAMAGE_BY_ENTITY(EventListener.DamageByEntity::new),
    DAMAGE_BY_BLOCK(EventListener.DamageByBlock::new),
    ENTITY_DAMAGE(EventListener.EntityDamage::new),
    ENTITY_DEATH(EventListener.EntityDeath::new),
    PROJECTILE_HIT(EventListener.ProjectileHit::new),
    PROJECTILE_LAUNCH(EventListener.ProjectileLaunch::new),
    ;
    private final Supplier<Process<?>> supplier;
}
