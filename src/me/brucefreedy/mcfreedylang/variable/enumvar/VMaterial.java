package me.brucefreedy.mcfreedylang.variable.enumvar;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import org.bukkit.Material;

import java.util.function.Function;

public class VMaterial extends AbstractEnumVar<Material> {
    public VMaterial(Material m) {
        super(m);
        register("isSolid", bool(o -> object.isSolid()));
        register("hasGravity", bool(o -> object.hasGravity()));
        register("isBlock", bool(o -> object.isBlock()));
        register("isItem", bool(o -> object.isItem()));
        register("isBurnable", bool(o -> object.isBurnable()));
        register("isEdible", bool(o -> object.isEdible()));
        register("isFlammable", bool(o -> object.isFlammable()));
        register("isFuel", bool(o -> object.isFuel()));
        register("isInteractable", bool(o -> object.isInteractable()));
        register("isOccluding", bool(o -> object.isOccluding()));
        register("isRecord", bool(o -> object.isRecord()));
    }

    protected Method bool(Function<List<?>, Boolean> function) {
        return super.func(o -> Bool.get(function.apply(o)));
    }
}
