package me.brucefreedy.mcfreedylang.variable;

import lombok.Getter;
import me.brucefreedy.freedylang.lang.abst.Method;
import org.bukkit.entity.Player;

@Getter
public class VPlayer extends VLivingEntity<Player> {
    public VPlayer(Player player) {
        super(player);
        register("displayname", (Method) (processUnit, params) -> player.getDisplayName());
        register("print", (Method) (processUnit, params) -> {
            params.stream().map(Object::toString).forEach(player::sendMessage);
            return this;
        });
        register("food", intValue(object::setFoodLevel, object::getFoodLevel));
    }
}
