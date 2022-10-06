package me.gamercoder215.mobchip.abstraction.v1_16_R1;

import me.gamercoder215.mobchip.ai.attribute.Attribute;
import net.minecraft.server.v1_16_R1.AttributeRanged;
import net.minecraft.server.v1_16_R1.IRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R1.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Attribute1_16_R1 extends AttributeRanged implements Attribute {

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

    public Attribute1_16_R1(AttributeRanged a) {
        super(a.getName(), a.getDefault(), getDouble(a, "a"), a.maximum);
        this.key = IRegistry.ATTRIBUTE.getKey(a) == null ? NamespacedKey.minecraft(a.getName()) : CraftNamespacedKey.fromMinecraft(IRegistry.ATTRIBUTE.getKey(a));
        this.defaultV = a.getDefault();
        this.min = getDouble(a, "a");
        this.max = a.maximum;
    }

    public Attribute1_16_R1(NamespacedKey key, double defaultV, double min, double max, boolean clientSide) {
        super("attribute.name." + key.getKey().toLowerCase(), defaultV, min, max);
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
        return b();
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
