package me.brucefreedy.mcfreedylang.process.event;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;
import me.brucefreedy.mcfreedylang.variable.VPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface EventListener {

    @Processable(alias = "@join")
    class PlayerJoin extends AbstractPlayer<PlayerJoinEvent> {
        @EventHandler
        public void onEvent(PlayerJoinEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerJoinEvent event, VariableRegister variableRegister) {
            super.wrap(event, variableRegister);
            variableRegister.setVariable("joinMessage", event.getJoinMessage());
        }
        @Override
        protected void map(PlayerJoinEvent event, VariableRegister variableRegister) {
            super.map(event, variableRegister);
            event.setJoinMessage(variableRegister.getVariable("joinMessage").toString());
        }
    }

    @Processable(alias = "@left")
    class PlayerLeft extends AbstractPlayer<PlayerQuitEvent> {
        @EventHandler
        public void onEvent(PlayerQuitEvent event) {
            super.onEvent(event);
        }
        @Override
        protected void wrap(PlayerQuitEvent event, VariableRegister variableRegister) {
            super.wrap(event, variableRegister);
            variableRegister.setVariable("leftMessage", event.getQuitMessage());
        }
        @Override
        protected void map(PlayerQuitEvent event, VariableRegister variableRegister) {
            super.map(event, variableRegister);
            event.setQuitMessage(variableRegister.getVariable("leftMessage").toString());
        }
    }

    abstract class AbstractPlayer<T extends org.bukkit.event.player.PlayerEvent> extends AbstractEventListener<T> {
        @Override
        protected void wrap(T event, VariableRegister variableRegister) {
            super.wrap(event, variableRegister);
            variableRegister.setVariable("player", new VPlayer(event.getPlayer()));
        }
    }

}
