package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.abst.ListProcess;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import me.brucefreedy.freedylang.lang.variable.text.SimpleText;
import me.brucefreedy.mcfreedylang.variable.enumvar.VMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class VItem extends SimpleVar<ItemStack> {
    public VItem(ItemStack i) {
        super(i);
        register("type", method(o -> o instanceof VMaterial, Material.class, object::setType, () -> new VMaterial(object.getType())));
        register("size", intValue(object::setAmount, object::getAmount));
        register("name", stringValue(this::setName, this::getName));
        register("lore", method(o -> o instanceof ListProcess, List.class, p -> {
            ItemMeta itemMeta = getOrNewItemMeta();
            List<String> lore = new List<>();
            p.forEach(o -> lore.add(o.toString()));
            itemMeta.setLore(lore);
            object.setItemMeta(itemMeta);
        }, () -> {
            java.util.List<String> lore = getOrNewItemMeta().getLore();
            if (lore == null) lore = new ArrayList<>();
            List<String> loreList = new List<>(lore);
            ListProcess listProcess = new ListProcess(new List<>(loreList.stream().map(SimpleText::new).collect(Collectors.toList())));
            listProcess.setSync(processes -> {
                ItemMeta itemMeta = getOrNewItemMeta();
                itemMeta.setLore(processes.stream().map(Object::toString).collect(Collectors.toList()));
                object.setItemMeta(itemMeta);
            });
            return listProcess;
        }));
    }

    private void setName(String name) {
        ItemMeta itemMeta = getOrNewItemMeta();
        if (itemMeta == null) return;
        itemMeta.setDisplayName(name);
        object.setItemMeta(itemMeta);
    }

    private String getName() {
        ItemMeta itemMeta = getOrNewItemMeta();
        if (itemMeta == null) return new Null().toString();
        return itemMeta.getDisplayName();
    }

    private ItemMeta getOrNewItemMeta() {
        ItemMeta itemMeta = object.getItemMeta();
        if (itemMeta == null) return Bukkit.getItemFactory().getItemMeta(object.getType());
        else return itemMeta;
    }

}
