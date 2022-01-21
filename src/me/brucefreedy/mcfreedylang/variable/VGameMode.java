package me.brucefreedy.mcfreedylang.variable;

import me.brucefreedy.mcfreedylang.abst.AbstractEnumVar;
import org.bukkit.GameMode;

public class VGameMode extends AbstractEnumVar<GameMode> {
    public VGameMode(GameMode object) {
        super(object);
    }
}
