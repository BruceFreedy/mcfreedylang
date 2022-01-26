package me.brucefreedy.mcfreedylang;

import me.brucefreedy.common.RegistryImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerMetaRegistry extends RegistryImpl<UUID, PlayerMeta> implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        register(event.getPlayer().getUniqueId(), new PlayerMeta());
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        register(event.getPlayer().getUniqueId(), null);
    }
}
