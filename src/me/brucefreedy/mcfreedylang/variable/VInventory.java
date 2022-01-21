package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import org.bukkit.inventory.Inventory;

public class VInventory extends AbstractVar<Inventory> {
    public VInventory(Inventory object) {
        super(object);
        register("getItem", (Method) (unit, params) -> {
            Object first = params.first();
            if (first instanceof Number)
                return new VItem(object.getItem(((Number) first).getNumber().intValue()));
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
    }
}