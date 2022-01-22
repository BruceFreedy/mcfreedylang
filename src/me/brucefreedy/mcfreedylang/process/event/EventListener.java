package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.mcfreedylang.variable.*;
import me.brucefreedy.mcfreedylang.variable.enumvar.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

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
            register(scope, "hand", new VEquipmentSlot(event.getHand()));
            register(scope, "blockFace", new VBlockFace(event.getBlockFace()));
            register(scope, "clickedBlock", event.getClickedBlock() == null ? null :
                    new VBlock(event.getClickedBlock().getState()));
            register(scope, "item", new VItem(event.getItem()));
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
            register(scope, "message", event.getMessage());
        }
        @Override
        protected void map(PlayerCommandPreprocessEvent event, Scope scope) {
            super.map(event, scope);
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
            register(scope, "inventory", event.getClickedInventory());
            register(scope, "currentItem", new VItem(event.getCurrentItem()));
            register(scope, "cursor", new VItem(event.getCursor()));
            register(scope, "slot", new SimpleNumber(event.getSlot()));
            register(scope, "hotbar", new SimpleNumber(event.getHotbarButton()));
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

}
