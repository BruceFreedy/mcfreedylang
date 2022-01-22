package me.brucefreedy.mcfreedylang.variable;

import com.eatthepath.uuid.FastUUID;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;

import java.util.UUID;

public class VUUID extends AbstractVar<UUID> {
    public VUUID(UUID object) {
        super(object);
    }

    @Override
    public String toString() {
        return FastUUID.toString(object);
    }

}
