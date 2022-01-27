package me.brucefreedy.mcfreedylang.variable.enumvar;

import org.bukkit.Material;

public class VMaterial extends AbstractEnumVar<Material> {
    public VMaterial(Material m) {
        super(m);
        register("isSolid", func(o -> object.isSolid()));
        register("hasGravity", func(o -> object.hasGravity()));
        register("isBlock", func(o -> object.isBlock()));
        register("isItem", func(o -> object.isItem()));
        register("isBurnable", func(o -> object.isBurnable()));
        register("isEdible", func(o -> object.isEdible()));
        register("isFlammable", func(o -> object.isFlammable()));
        register("isFuel", func(o -> object.isFuel()));
        register("isInteractable", func(o -> object.isInteractable()));
        register("isOccluding", func(o -> object.isOccluding()));
        register("isRecord", func(o -> object.isRecord()));
    }
}
