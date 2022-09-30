package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import me.gamercoder215.mobchip.ai.attribute.Attribute;
import net.minecraft.core.IRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeRanged;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

public class Attribute1_17_R1 extends AttributeRanged implements Attribute {

    private final NamespacedKey key;
    private final double defaultV;
    private final double min;
    private final double max;

    public Attribute1_17_R1(AttributeRanged a) {
        super(a.getName(), a.getDefault(), a.d(), a.e());
        this.key = IRegistry.al.getKey(a) == null ? NamespacedKey.minecraft(a.getName()) : CraftNamespacedKey.fromMinecraft(IRegistry.al.getKey(a));
        this.defaultV = a.getDefault();
        this.min = a.d();
        this.max = a.e();
    }

    public Attribute1_17_R1(NamespacedKey key, double defaultV, double min, double max, boolean clientSide) {
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
