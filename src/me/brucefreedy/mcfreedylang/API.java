package me.brucefreedy.mcfreedylang;

import me.brucefreedy.freedylang.registry.ProcessRegister;

public class API {

    public static Plugin getPlugin() {
        return Plugin.getInst();
    }

    public static ProcessRegister getRegister() {
        return getPlugin().getProcessRegister();
    }

}
