package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class VItemContents extends SimpleVar<VItemContents.ItemsCont> {
    public static class ItemsCont extends List<VItem> {
        public ItemsCont() {
        }
        public ItemsCont(Collection<? extends VItem> c) {
            super(c);
        }
    }
    public VItemContents(ItemsCont object) {
        super(object);
    }
    public VItemContents(ItemStack[] contents) {
        this(new ItemsCont(Arrays.stream(contents).map(itemStack -> {
            if (itemStack == null) return new VItem(new ItemStack(Material.AIR));
            return new VItem(itemStack);
        }).collect(Collectors.toList())));
    }
}
