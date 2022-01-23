package me.brucefreedy.mcfreedylang.variable.enumvar;

import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;

public abstract class AbstractEnumVar<T extends Enum<?>> extends SimpleVar<T> {
    public AbstractEnumVar(T object) {
        super(object);
    }

    @Override
    public String toString() {
        return object == null ? new Null().toString() : object.name();
    }
}
