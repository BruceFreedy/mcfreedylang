package me.brucefreedy.mcfreedylang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.mcfreedylang.process.event.EventListener;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public enum ProcessDefs {
    PLAYER_JOIN(EventListener.PlayerJoin::new),
    ;
    private Supplier<Process<?>> supplier;
}
