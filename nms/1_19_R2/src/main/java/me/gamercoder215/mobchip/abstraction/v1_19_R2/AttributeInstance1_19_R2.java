package me.gamercoder215.mobchip.abstraction.v1_19_R2;

import com.google.common.base.Preconditions;
import me.gamercoder215.mobchip.ai.attribute.Attribute;
import me.gamercoder215.mobchip.ai.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_19_R2.attribute.CraftAttributeInstance;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.bukkit.craftbukkit.v1_19_R2.attribute.CraftAttributeInstance.convert;

final class AttributeInstance1_19_R2 implements AttributeInstance {

    private final net.minecraft.world.entity.ai.attributes.AttributeInstance handle;
    private final Attribute a;

    public AttributeInstance1_19_R2(Attribute a, net.minecraft.world.entity.ai.attributes.AttributeInstance handle) {
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
        handle.setBaseValue(v);
    }

    @NotNull
    @Override
    public Collection<AttributeModifier> getModifiers() {
        return handle.getModifiers().stream().map(CraftAttributeInstance::convert).collect(Collectors.toSet());
    }

    @Override
    public void addModifier(@NotNull AttributeModifier mod) {
        Preconditions.checkArgument(mod != null, "modifier");
        handle.addPermanentModifier(convert(mod));
    }

    @Override
    public void removeModifier(@NotNull AttributeModifier mod) {
        Preconditions.checkArgument(mod != null, "modifier");
        handle.removeModifier(convert(mod));
    }

    @Override
    public double getValue() {
        return handle.getValue();
    }

    @Override
    public double getDefaultValue() {
        return handle.getAttribute().getDefaultValue();
    }
}
