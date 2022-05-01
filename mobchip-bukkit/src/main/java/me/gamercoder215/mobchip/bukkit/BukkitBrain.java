package me.gamercoder215.mobchip.bukkit;

import java.util.NoSuchElementException;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.behavior.Behavior;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.attributes.Attribute;
import me.gamercoder215.mobchip.attributes.ChipAttributeInstance;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.core.Registry;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BukkitBrain implements EntityBrain {
	
	private final Mob m;
	private final net.minecraft.world.entity.Mob nmsMob;

	private BukkitBrain(@NotNull Mob m) {
		this.m = m;
		this.nmsMob = ChipConversions.convertType(m);
	}

	/**
	 * Get the EntityBrain of this Mob.
	 * @param m Mob to get
	 * @return EntityBrain
	 */
	public static EntityBrain getBrain(@NotNull Mob m) {
		return new BukkitBrain(m);
	}

	public EntityAI getGoalAI() {
		return new BukkitAI(nmsMob.goalSelector);
	}

	public EntityAI getTargetAI() {
		return new BukkitAI(nmsMob.targetSelector);
	}

	@Override
	public EntityNavigation getNavigation() {
		return new BukkitNavigation(nmsMob.getNavigation());
	}

	@Override
	public EntityController getController() {
		return new BukkitController(m.getWorld(), nmsMob.getJumpControl(), nmsMob.getLookControl(), nmsMob.getMoveControl());
	}

	@Override
	public <T> void setMemory(EntityMemory<T> memory, T value) {
		if (value == null) {
			nmsMob.getBrain().eraseMemory(memory.getHandle());
			return;
		}

		if (memory.convert(value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		nmsMob.getBrain().setMemory(memory.getHandle(), memory.convert(value));
	}

	@Override
	public <T> void setMemory(EntityMemory<T> memory, T value, long expire) {
		if (expire < 0) throw new IllegalArgumentException("Invalid ticks number " + expire);

		nmsMob.getBrain().setMemoryWithExpiry(memory.getHandle(), memory.convert(value), expire);
	}

	@Override
	public <T> @Nullable T getMemory(EntityMemory<T> memory) {
		try {
			return (T) nmsMob.getBrain().getMemory(memory.getHandle()).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override

	public long getExpiration(EntityMemory<?> memory) {
		return nmsMob.getBrain().getTimeUntilExpiry(memory.getHandle());
	}

	@Override
	public boolean containsMemory(EntityMemory<?> memory) {
		return nmsMob.getBrain().hasMemoryValue(memory.getHandle());
	}

	@Override
	public void addSensor(@NotNull Sensor s) {
		s.getHandle().create().tick(ChipConversions.convertType(m.getWorld()), nmsMob);	
	}

	@Override
	public boolean addBehavior(@NotNull Behavior b) {
		return b.getHandle().tryStart(ChipConversions.convertType(m.getWorld()), nmsMob, 0L);
	}

	@Override
	public ChipAttributeInstance getAttribute(@NotNull Attribute a) {
		try {
			return new BukkitAttributeInstance(a, nmsMob.getAttribute(Registry.ATTRIBUTE.getOptional(a.getId().toResourceLocation()).get()));
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
