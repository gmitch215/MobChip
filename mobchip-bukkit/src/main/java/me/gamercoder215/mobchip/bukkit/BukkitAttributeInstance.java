package me.gamercoder215.mobchip.bukkit;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.attributes.Attribute;
import me.gamercoder215.mobchip.attributes.ChipAttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

final class BukkitAttributeInstance implements ChipAttributeInstance {

	private final net.minecraft.world.entity.ai.attributes.AttributeInstance nms;
	private final Attribute a;
	
	BukkitAttributeInstance(Attribute a, net.minecraft.world.entity.ai.attributes.AttributeInstance nms) {
		this.nms = nms;
		this.a = a;
	}

	@Override
	public double getBaseValue() {
		return nms.getBaseValue();
	}

	@Override
	public void setBaseValue(double value) {
		nms.setBaseValue(value);
	}
	
	private static AttributeModifier convert(net.minecraft.world.entity.ai.attributes.AttributeModifier m) {
		return new AttributeModifier(m.getId(), m.getName(), m.getAmount(), AttributeModifier.Operation.values()[m.getOperation().ordinal()]);
	}
	
	private static net.minecraft.world.entity.ai.attributes.AttributeModifier convert(AttributeModifier m) {
		return new net.minecraft.world.entity.ai.attributes.AttributeModifier(m.getUniqueId(), m.getName(), m.getAmount(), Operation.values()[m.getOperation().ordinal()]);
	}
	
	@Override
	@NotNull
	public Collection<AttributeModifier> getModifiers() {
		Collection<net.minecraft.world.entity.ai.attributes.AttributeModifier> mods = nms.getModifiers();
	
		Collection<AttributeModifier> bukkitmods = new ArrayList<>();
		
		for (net.minecraft.world.entity.ai.attributes.AttributeModifier m : mods) {
			AttributeModifier mod = convert(m);
			bukkitmods.add(mod);
		}
		
		return bukkitmods;
	}

	@Override
	public void addModifier(@NotNull AttributeModifier modifier) {
		nms.addPermanentModifier(convert(modifier));
	}

	@Override
	public void removeModifier(@NotNull AttributeModifier modifier) {
		nms.removeModifier(convert(modifier));
	}

	@Override
	public double getValue() {
		return nms.getValue();
	}

	@Override
	public double getDefaultValue() {
		return nms.getAttribute().getDefaultValue();
	}

	@Override
	public Attribute getEntityAttribute() {
		return this.a;
	}

}
