package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VInventory extends SimpleVar<Inventory> {
    public VInventory(Inventory object) {
        super(object);
        register("getItem", (Method) (unit, params) -> {
            Object first = params.first();
            if (first instanceof Number) {
                ItemStack item = object.getItem(((Number) first).getNumber().intValue());
                return item == null ? new Null() : new VItem(item);
            }
            return new Null();
        });
        register("add", (Method) (unit, params) -> {
            params.stream().filter(o -> o instanceof VItem)
                    .forEach(o -> object.addItem(((VItem) o).getObject()));
            return new Null();
        });
        register("set", (Method) (unit, params) -> {
            if (params.size() != 2) return new Null();
            Object index = params.get(0);
            Object item = params.get(1);
            if (!(index instanceof Number) || !(item instanceof VItem)) return new Null();
            object.setItem(((Number) index).getNumber().intValue(), ((VItem) item).getObject());
            return new Null();
        });
        register("size", (Method) (unit, params) -> new SimpleNumber(object.getSize()));
        register("open", method(o -> o instanceof VPlayer, Player.class, player -> player.openInventory(object), Null::new));


    }
}
