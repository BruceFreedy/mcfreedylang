package me.brucefreedy.mcfreedylang.variable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VPlayer {
    org.bukkit.entity.Player player;
    @Override
    public String toString() {
        return player.getName();
    }
}
