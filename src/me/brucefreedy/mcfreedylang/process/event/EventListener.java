package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import me.brucefreedy.freedylang.lang.variable.bool.False;
import me.brucefreedy.freedylang.lang.variable.bool.True;
import me.brucefreedy.mcfreedylang.variable.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
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
            scope.register("joinMessage", event.getJoinMessage());
        }
        @Override
        protected void map(PlayerJoinEvent event, Scope scope) {
            super.map(event, scope);
            event.setJoinMessage(scope.getRegistry("joinMessage").toString());
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
            scope.register("leftMessage", event.getQuitMessage());
        }
        @Override
        protected void map(PlayerQuitEvent event, Scope scope) {
            super.map(event, scope);
            event.setQuitMessage(scope.getRegistry("leftMessage").toString());
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


    abstract class AbstractPlayer<T extends org.bukkit.event.player.PlayerEvent> extends AbstractEventListener<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            scope.register("player", new VPlayer(event.getPlayer()));
        }
    }

    abstract class AbstractPlayerCancellable<T extends org.bukkit.event.player.PlayerEvent & Cancellable> extends AbstractEventCanncellable<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            super.wrap(event, scope);
            scope.register("player", new VPlayer(event.getPlayer()));
        }
    }

    abstract class AbstractEventCanncellable<T extends Event & Cancellable> extends AbstractEventListener<T> {
        @Override
        protected void wrap(T event, Scope scope) {
            register(scope, "cancelled", Bool.get(event.isCancelled()));
        }
        @Override
        protected void map(T event, Scope scope) {
            super.map(event, scope);
            Object cancelled = scope.getRegistry("cancelled");
            if (cancelled instanceof True) event.setCancelled(true);
            else if (cancelled instanceof False) event.setCancelled(false);
        }
    }

}
