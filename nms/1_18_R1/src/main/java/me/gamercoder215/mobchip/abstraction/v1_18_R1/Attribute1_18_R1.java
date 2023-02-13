package me.gamercoder215.mobchip.abstraction.v1_18_R1;

import me.gamercoder215.mobchip.ai.attribute.Attribute;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

public class Attribute1_18_R1 extends RangedAttribute implements Attribute {

    private final NamespacedKey key;
    private final double defaultV;
    private final double min;
    private final double max;

    public Attribute1_18_R1(RangedAttribute a) {
        super(a.getDescriptionId(), a.getDefaultValue(), a.getMinValue(), a.getMaxValue());
        this.key = Registry.ATTRIBUTE.getKey(a) == null ? NamespacedKey.minecraft(a.getDescriptionId()) : CraftNamespacedKey.fromMinecraft(Registry.ATTRIBUTE.getKey(a));
        this.defaultV = a.getDefaultValue();
        this.min = a.getMinValue();
        this.max = a.getMaxValue();
    }

    public Attribute1_18_R1(NamespacedKey key, double defaultV, double min, double max, boolean clientSide) {
        super("attribute.name." + key.getKey().toLowerCase(), defaultV, min, max);
        this.key = key;
        this.min = min;
        this.defaultV = defaultV;
        this.max = max;
        this.setSyncable(clientSide);
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
        return isClientSyncable();
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
