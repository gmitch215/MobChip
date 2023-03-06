package me.gamercoder215.mobchip.abstraction.v1_16_R3;

import com.google.common.base.Preconditions;
import me.gamercoder215.mobchip.ai.attribute.Attribute;
import me.gamercoder215.mobchip.ai.attribute.AttributeInstance;
import net.minecraft.server.v1_16_R3.AttributeModifiable;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_16_R3.attribute.CraftAttributeInstance;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

final class AttributeInstance1_16_R3 implements AttributeInstance {

    private final AttributeModifiable handle;
    private final Attribute a;

    public AttributeInstance1_16_R3(Attribute a, AttributeModifiable handle) {
        this.a = a;
        this.handle = handle;
    }

    @Override
    public @NotNull Attribute getGenericAttribute() {
        return this.a;
    }

    @Override
    public double getBaseValue() {
        return handle.getBaseValue();
    }

    @Override
    public void setBaseValue(double v) {
        handle.setValue(v);
    }

    @NotNull
    @Override
    public Collection<AttributeModifier> getModifiers() {
        return handle.getModifiers().stream().map(CraftAttributeInstance::convert).collect(Collectors.toSet());
    }

    @Override
    public void addModifier(@NotNull AttributeModifier mod) {
        Preconditions.checkArgument(mod != null, "modifier");
        handle.addModifier(CraftAttributeInstance.convert(mod));
    }

    @Override
    public void removeModifier(@NotNull AttributeModifier mod) {
        Preconditions.checkArgument(mod != null, "modifier");
        handle.removeModifier(CraftAttributeInstance.convert(mod));
    }

    @Override
    public double getValue() {
        return handle.getValue();
    }

    @Override
    public double getDefaultValue() {
        return handle.getAttribute().getDefault();
    }
}
