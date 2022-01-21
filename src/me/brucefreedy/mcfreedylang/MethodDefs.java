package me.brucefreedy.mcfreedylang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.mcfreedylang.variable.VLocation;
import me.brucefreedy.mcfreedylang.variable.VMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

@Getter
@AllArgsConstructor
public enum MethodDefs {
    MATERIAL((unit, params) -> {
        Object first = params.first();
        if (first == null) return new VMaterial();
        else {
            try {
                return new VMaterial(Material.valueOf(first.toString()));
            } catch (Exception ignored) {
                return new VMaterial();
            }
        }
    }),
    LOCATION((unit, params) -> {
        try {
            World world = Bukkit.getWorld(params.get(0).toString());
            if (world == null) return new Null();
            double x = ((Number) params.get(1)).getNumber().doubleValue();
            double y = ((Number) params.get(2)).getNumber().doubleValue();
            double z = ((Number) params.get(3)).getNumber().doubleValue();
            float yaw = params.size() == 4 ? 0 : ((Number) params.get(4)).getNumber().floatValue();
            float pitch = params.size() == 4 ? 0 : ((Number) params.get(5)).getNumber().floatValue();
            return new VLocation(new Location(world, x, y, z, yaw, pitch));
        } catch (Exception ignored) {
            return new Null();
        }
    }),
    ;
    private final Method method;

}
