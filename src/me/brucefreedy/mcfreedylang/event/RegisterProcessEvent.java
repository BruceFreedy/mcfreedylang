package me.brucefreedy.mcfreedylang.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.registry.ProcessRegister;
import me.brucefreedy.mcfreedylang.API;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class RegisterProcessEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final ProcessRegister processRegister;

    public void register(String name, Supplier<Process<?>> supplier) {
        processRegister.register(name, supplier);
    }

    public void registerMethod(String name, Object object) {
        API.getRegister().getScope().register(name, object);
    }

}
