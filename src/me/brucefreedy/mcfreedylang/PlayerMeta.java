package me.brucefreedy.mcfreedylang;

import me.brucefreedy.freedylang.lang.abst.Null;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataStoreBase;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class PlayerMeta extends MetadataStoreBase<Null> {

    private static final Null aNull = new Null();

    @Override
    protected String disambiguate(Null subject, String metadataKey) {
        return metadataKey;
    }

    public Object get(String key) {
        List<MetadataValue> metadata = getMetadata(aNull, key);
        if (metadata.isEmpty()) return new Null();
        MetadataValue metadataValue = metadata.get(0);
        return metadataValue.value();
    }

    public void set(String key, Object o) {
        setMetadata(aNull, key, new FixedMetadataValue(API.getPlugin(), o));
    }

}
