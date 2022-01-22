package me.brucefreedy.mcfreedylang;

import org.bukkit.ChatColor;

public class ColorEdit {

    public static String toColor(String string) {
        if (string == null) return null;
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
