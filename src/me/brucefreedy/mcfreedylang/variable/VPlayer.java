package me.brucefreedy.mcfreedylang.variable;

import lombok.Getter;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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
        register("title", (Method) (processUnit, params) -> {
            try {
                String title = params.get(0).toString();
                String subTitle = params.get(1).toString();
                int fadeIn = ((Number) params.get(2)).getNumber().intValue();
                int stay = ((Number) params.get(3)).getNumber().intValue();
                int fadeOut = ((Number) params.get(4)).getNumber().intValue();
                object.sendTitle(title, subTitle.isEmpty() ? null : subTitle, fadeIn, stay, fadeOut);
            } catch (Exception ignored) {}
            return new Null();
        });
        register("actionbar", (Method) (processUnit, params) -> {
            if (!params.isEmpty()) player.spigot()
                    .sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(params.first().toString()));
            return new Null();
        });
    }
}