package me.brucefreedy.mcfreedylang;

public class API {

    public static Plugin getPlugin() {
        return Plugin.getInst();
    }

    public static Register getRegister() {
        return getPlugin().getRegister();
    }

}
