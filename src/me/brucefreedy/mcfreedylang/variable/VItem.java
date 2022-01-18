package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import org.bukkit.inventory.ItemStack;

public class VItem extends AbstractVar<ItemStack> {
    public VItem(ItemStack object) {
        super(object);
    }
}
