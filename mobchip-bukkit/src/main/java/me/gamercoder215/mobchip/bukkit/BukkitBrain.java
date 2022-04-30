package me.gamercoder215.mobchip.bukkit;

import java.util.NoSuchElementException;

import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftMob;
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
import me.gamercoder215.mobchip.util.ChipGetter;
import net.minecraft.core.Registry;

public final class BukkitBrain implements EntityBrain {
	
	private final Mob m;
	private final net.minecraft.world.entity.Mob nmsMob;

	private BukkitBrain(@NotNull Mob m) {
		this.m = m;
		this.nmsMob = ((CraftMob) m).getHandle();
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
	@SuppressWarnings("unchecked")
	public void setMemory(EntityMemory memory, Object value) {
		if (value == null) {
			nmsMob.getBrain().eraseMemory(memory.getHandle());
			return;
		}

		if (memory.convert(value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		nmsMob.getBrain().setMemory(memory.getHandle(), memory.convert(value));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setMemory(EntityMemory memory, Object value, long expire) {
		if (expire < 0) throw new IllegalArgumentException("Invalid ticks number " + expire);

		nmsMob.getBrain().setMemoryWithExpiry(memory.getHandle(), memory.convert(value), expire);
	}

	@Override
	@SuppressWarnings("unchecked")
	public @Nullable Object getMemory(EntityMemory memory) {
		try {
			return nmsMob.getBrain().getMemory(memory.getHandle()).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	@Nullable
	@SuppressWarnings("unchecked")
	public <T> T getMemory(EntityMemory memory, Class<T> clazz) {
		try {
			Object obj = nmsMob.getBrain().getMemory(memory.getHandle()).get();
			if (!(clazz.isInstance(obj))) return null;

			return (T) obj;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public long getExpiration(EntityMemory memory) {
		return nmsMob.getBrain().getTimeUntilExpiry(memory.getHandle());
	}

	@Override
	public boolean containsMemory(EntityMemory memory) {
		return nmsMob.getBrain().hasMemoryValue(memory.getHandle());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addSensor(@NotNull Sensor s) {
		s.getHandle().create().tick(((CraftWorld) m.getWorld()).getHandle(), nmsMob);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addBehavior(@NotNull Behavior b) {
		return b.getHandle().tryStart(ChipGetter.getLevel(m.getWorld()), nmsMob, 0L);
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
