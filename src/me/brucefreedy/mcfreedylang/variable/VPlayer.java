package me.brucefreedy.mcfreedylang.variable;

import lombok.Getter;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.entity.Player;

@Getter
public class VPlayer extends AbstractVar<Player> {
    public VPlayer(Player player) {
        super(player);
        registerMethod("name", (processUnit, params) -> player.getName());
        registerMethod("displayname", (processUnit, params) -> player.getDisplayName());
        registerMethod("print", (processUnit, params) -> {
            params.stream().map(Object::toString).forEach(player::sendMessage);
            return params;
        });

    }
}
