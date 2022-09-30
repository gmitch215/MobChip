package me.gamercoder215.mobchip.abstraction.v1_13_R1;

import me.gamercoder215.mobchip.ai.attribute.Attribute;
import net.minecraft.server.v1_13_R1.AttributeRanged;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Attribute1_13_R1 extends AttributeRanged implements Attribute {

    private final NamespacedKey key;
    private final double defaultV;
    private final double min;
    private final double max;

    public static double getDouble(AttributeRanged r, String s) {
        try {
            Field f = r.getClass().getDeclaredField(s);
            f.setAccessible(true);
            return f.getDouble(r);
        } catch (Exception e) {
            return 0;
        }
    }

    public Attribute1_13_R1(NamespacedKey key, double defaultV, double min, double max, boolean clientSide) {
        super(null, "attribute.name." + key.getKey().toLowerCase(), defaultV, min, max);
        this.key = key;
        this.min = min;
        this.defaultV = defaultV;
        this.max = max;
        this.a(clientSide);
    }

    public double getMinValue() {
        return this.min;
    }

    public double getDefaultValue() {
        return this.defaultV;
    }

    public double getMaxValue() {
        return this.max;
    }

    @Override
    public boolean isClientSide() {
        return c();
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
