package me.brucefreedy.mcfreedylang.abst;

import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;

public abstract class AbstractEnumVar<T extends Enum<?>> extends AbstractVar<T> {
    public AbstractEnumVar(T object) {
        super(object);
    }

    @Override
    public String toString() {
        return object == null ? new Null().toString() : object.name();
    }
}
