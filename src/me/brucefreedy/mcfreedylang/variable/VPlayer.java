package me.brucefreedy.mcfreedylang.variable;

import lombok.Getter;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.mcfreedylang.API;
import me.brucefreedy.mcfreedylang.PlayerMeta;
import me.brucefreedy.mcfreedylang.variable.enumvar.VGameMode;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Getter
public class VPlayer extends VLivingEntity<Player> {
    public VPlayer(Player player) {
        super(player);
        register("displayname", (Method) (processUnit, params) -> object.getDisplayName());
        register("print", (Method) (processUnit, params) -> {
            params.stream().map(Object::toString).forEach(object::sendMessage);
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
        register("actionbar", voidFunc(params ->
                object.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(params.first().toString()))));
        register("inventory", (Method) (unit, params) -> new VInventory(object.getInventory()));
        register("gamemode", method(o -> o instanceof VGameMode, GameMode.class, object::setGameMode, object::getGameMode));
        register("execute", stringValue(s -> Bool.get(object.performCommand(s)), () -> new Null().toString()));
        register("sneaking", boolValue(object::setSneaking, object::isSneaking));
        register("updateInventory", voidFunc(params -> object.updateInventory()));
        register("closeInventory", voidFunc(params -> object.closeInventory()));
        register("permission", voidFunc(params -> Bool.get(object.hasPermission(params.first().toString()))));
        register("set", voidFunc(a -> {
            System.out.println("alsdfjaslkdfkslfkdlfa");
            getMeta().set(a.get(0).toString(), a.get(1));
        }));
        register("get", func(a -> {
            System.out.println("lllllllllllfffffffaaaaaaa");
            return getMeta().get(a.toString());
        }));
    }

    @Override
    public String toString() {
        return object.getName();
    }

    private PlayerMeta getMeta() {
        try {
            PlayerMeta registry = API.getRegister().getPlayerMetaRegistry().getRegistry(object.getUniqueId());
            if (registry == null) throw new NullPointerException();
            return registry;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}