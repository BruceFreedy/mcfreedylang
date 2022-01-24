package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.SimpleList;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.freedylang.lang.variable.text.SimpleText;
import me.brucefreedy.mcfreedylang.variable.*;
import me.brucefreedy.mcfreedylang.variable.enumvar.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

import java.util.stream.Collectors;

public interface EventListener {

    @Processable(alias = "@join")
    class PlayerJoin extends AbstractPlayer<PlayerJoinEvent> {
        @EventHandler
        public void onEvent(PlayerJoinEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerJoinEvent event, Scope scope) {
            super.wrap(event, scope);
            scope.register("message", event.getJoinMessage());
        }
        @Override
        protected void map(PlayerJoinEvent event, Scope scope) {
            super.map(event, scope);
            event.setJoinMessage(scope.getRegistry("message").toString());
        }
    }

    @Processable(alias = "@left")
    class PlayerLeft extends AbstractPlayer<PlayerQuitEvent> {
        @EventHandler
        public void onEvent(PlayerQuitEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerQuitEvent event, Scope scope) {
            super.wrap(event, scope);
            scope.register("message", event.getQuitMessage());
        }
        @Override
        protected void map(PlayerQuitEvent event, Scope scope) {
            super.map(event, scope);
            event.setQuitMessage(scope.getRegistry("message").toString());
        }
    }

    @Processable(alias = "@interact")
    class PlayerInteract extends AbstractPlayerCancellable<PlayerInteractEvent> {
        @EventHandler
        public void onEvent(PlayerInteractEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerInteractEvent event, Scope scope) {
            super.wrap(event, scope);
            scope.register("action", new VBlockAction(event.getAction()));
            register(scope, "hand", event.getHand() == null ? new Null() : new VEquipmentSlot(event.getHand()));
            register(scope, "blockFace", new VBlockFace(event.getBlockFace()));
            register(scope, "clickedBlock", event.getClickedBlock() == null ? null :
                    new VBlock(event.getClickedBlock().getState()));
            register(scope, "item", event.getItem() == null ? new Null() : new VItem(event.getItem()));
            register(scope, "material", new VMaterial(event.getMaterial()));
        }
        @Override
        protected void map(PlayerInteractEvent event, Scope scope) {
            super.map(event, scope);
        }
    }

    @Processable(alias = "@chat")
    class PlayerChat extends AbstractPlayerCancellable<PlayerChatEvent> {
        @EventHandler
        public void onEvent(PlayerChatEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerChatEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "format", event.getFormat());
            register(scope, "message", event.getMessage());
        }
        @Override
        protected void map(PlayerChatEvent event, Scope scope) {
            super.map(event, scope);
            event.setFormat(scope.getRegistry("format").toString());
            event.setMessage(scope.getRegistry("message").toString());
        }
    }

    @Processable(alias = "@command")
    class PlayerCommand extends AbstractPlayerCancellable<PlayerCommandPreprocessEvent> {
        @EventHandler
        public void onEvent(PlayerCommandPreprocessEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerCommandPreprocessEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "message", new SimpleText(event.getMessage()));
        }
        @Override
        protected void map(PlayerCommandPreprocessEvent event, Scope scope) {
            super.map(event, scope);
            event.setMessage(scope.getRegistry("message").toString());
        }
    }

    @Processable(alias = "@move")
    class PlayerMove extends AbstractPlayerCancellable<PlayerMoveEvent> {
        @EventHandler
        public void onEvent(PlayerMoveEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerMoveEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "to", new VLocation(event.getTo()));
            register(scope, "from", new VLocation(event.getFrom()));
        }
        @Override
        protected void map(PlayerMoveEvent event, Scope scope) {
            super.map(event, scope);
            VLocation to = scope.getRegistry("to", VLocation.class);
            if (to != null) event.setTo(to.getObject());
            VLocation from = scope.getRegistry("from", VLocation.class);
            if (from != null) event.setFrom(from.getObject());
        }
    }

    @Processable(alias = "@inventoryclick")
    class InventoryClick extends AbstractInventoryInteract<InventoryClickEvent> {
        @EventHandler
        public void onEvent(InventoryClickEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(InventoryClickEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "action", new VInventoryAction(event.getAction()));
            register(scope, "click", new VClickType(event.getClick()));
            register(scope, "inventory",
                    event.getClickedInventory() == null ? new Null() : event.getClickedInventory());
            register(scope, "currentItem",
                    event.getCurrentItem() == null ? new Null() : new VItem(event.getCurrentItem()));
            register(scope, "cursor",
                    event.getCursor() == null ? new Null() : new VItem(event.getCursor()));
            register(scope, "slot", new SimpleNumber(event.getSlot()));
            register(scope, "hotbar", new SimpleNumber(event.getHotbarButton()));
        }
        @Override
        protected void map(InventoryClickEvent event, Scope scope) {
            super.map(event, scope);
            VItem currentItem = scope.getRegistry("currentItem", VItem.class);
            if (currentItem != null) event.setCurrentItem(currentItem.getObject());
            VItem cursor = scope.getRegistry("cursor", VItem.class);
            if (cursor != null) event.setCursor(cursor.getObject());
        }
    }

    @Processable(alias = "@inventorydrag")
    class InventoryDrag extends AbstractInventoryInteract<InventoryDragEvent> {
        @EventHandler
        public void onEvent(InventoryDragEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(InventoryDragEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "dragType", new VDragType(event.getType()));
            register(scope, "oldCursor", new VItem(event.getOldCursor()));
            register(scope, "cursor", new VItem(event.getCursor()));
            register(scope, "inventory", new VInventory(event.getInventory()));
        }
    }

    @Processable(alias = "@inventoryclose")
    class InventoryClose extends AbstractEventListener<InventoryCloseEvent> {
        @EventHandler
        public void onEvent(InventoryCloseEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(InventoryCloseEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "player", new VPlayer((Player) event.getPlayer()));
            register(scope, "inventory", new VInventory(event.getInventory()));
        }
    }

    @Processable(alias = "@damagebyentity")
    class DamageByEntity extends AbstractDamage<EntityDamageByEntityEvent> {
        @EventHandler
        public void onEvent(EntityDamageByEntityEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(EntityDamageByEntityEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "damager", getEntityVar(event.getDamager()));
        }
    }

    @Processable(alias = "@damagebyblock")
    class DamageByBlock extends AbstractDamage<EntityDamageByBlockEvent> {
        @EventHandler
        public void onEvent(EntityDamageByBlockEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(EntityDamageByBlockEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "damager",
                    event.getDamager() == null ? new Null() : new VBlock(event.getDamager().getState()));
        }
    }

    @Processable(alias = "@damage")
    class EntityDamage extends AbstractDamage<EntityDamageEvent> {
        @EventHandler
        public void onEvent(EntityDamageEvent event) {
            super.onEvent(event);
        }
    }

    @Processable(alias = "@projectilehit")
    class ProjectileHit extends AbstractEntityCancellable<ProjectileHitEvent> {
        @EventHandler
        public void onEvent(ProjectileHitEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(ProjectileHitEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "hitBlock",
                    event.getHitBlock() == null ? new Null() : new VBlock(event.getHitBlock().getState()));
            register(scope, "hitEntity", getEntityVar(event.getHitEntity()));
            register(scope, "blockFace", new VBlockFace(event.getHitBlockFace()));
        }
        @Override
        protected void map(ProjectileHitEvent event, Scope scope) {
            super.map(event, scope);
        }
    }

    @Processable(alias = "projectilelaunch")
    class ProjectileLaunch extends EntitySpawn<ProjectileLaunchEvent> {
        @EventHandler
        public void onEvent(ProjectileLaunchEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(ProjectileLaunchEvent event, Scope scope) {
            super.wrap(event, scope);
        }
        @Override
        protected void map(ProjectileLaunchEvent event, Scope scope) {
            super.map(event, scope);
        }
    }

    @Processable(alias = "entityspawn")
    class EntitySpawn<T extends EntitySpawnEvent> extends AbstractEntityCancellable<T> {
        @EventHandler
        public void onEvent(T event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "location", new VLocation(event.getLocation()));
        }
        @Override
        protected void map(T event, Scope scope) {
            super.map(event, scope);
        }
    }

    @Processable(alias = "entitydeath")
    class EntityDeath extends AbstractEntity<EntityDeathEvent> {
        @EventHandler
        public void onEvent(EntityDeathEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(EntityDeathEvent event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "droppedExp", new SimpleNumber(event.getDroppedExp()));
            register(scope, "drops", new SimpleList(event.getDrops().stream().map(VItem::new).collect(Collectors.toList())));
        }
        @Override
        protected void map(EntityDeathEvent event, Scope scope) {
            super.map(event, scope);
            Number droppedExp = scope.getRegistry("droppedExp", Number.class);
            if (droppedExp != null) event.setDroppedExp(droppedExp.getNumber().intValue());
        }
    }

    abstract class AbstractDamage<T extends EntityDamageEvent> extends AbstractEntityCancellable<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "damage", new SimpleNumber(event.getDamage()));
            register(scope, "finalDamage", new SimpleNumber(event.getFinalDamage()));
            register(scope, "cause", new VDamageCause(event.getCause()));
        }
        @Override
        protected void map(T event, Scope scope) {
            super.map(event, scope);
            Number damage = scope.getRegistry("damage", Number.class);
            if (damage != null) event.setDamage(damage.getNumber().doubleValue());
        }
    }

    abstract class AbstractEntity<T extends EntityEvent> extends AbstractEventListener<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "entity", getEntityVar(event.getEntity()));
        }
    }

    abstract class AbstractEntityCancellable<T extends EntityEvent & Cancellable> extends AbstractEventCancellable<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "entity", getEntityVar(event.getEntity()));
        }
    }

    abstract class AbstractInventoryInteract<T extends InventoryInteractEvent> extends AbstractEventCancellable<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "player", new VPlayer((Player) event.getWhoClicked()));
        }
    }

    abstract class AbstractPlayer<T extends org.bukkit.event.player.PlayerEvent> extends AbstractEventListener<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            scope.register("player", new VPlayer(event.getPlayer()));
        }
    }

    abstract class AbstractPlayerCancellable<T extends org.bukkit.event.player.PlayerEvent & Cancellable> extends AbstractEventCancellable<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            scope.register("player", new VPlayer(event.getPlayer()));
        }
    }

    abstract class AbstractEventCancellable<T extends Event & Cancellable> extends AbstractEventListener<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            register(scope, "cancelled", Bool.get(event.isCancelled()));
        }
        @Override
        protected void map(T event, Scope scope) {
            super.map(event, scope);
            Object cancelled = scope.getRegistry("cancelled");
            if (cancelled != null) {
                String s = cancelled.toString();
                if (s.equals("true")) event.setCancelled(true);
                else if (s.equals("false")) event.setCancelled(false);
            }
        }
    }

    static SimpleVar<? extends Entity> getEntityVar(Entity entity) {
        if (entity instanceof Player) return new VPlayer(((Player) entity));
        if (entity instanceof LivingEntity) return new VLivingEntity<>(((LivingEntity) entity));
        else return new VEntity<>(entity);
    }

}
