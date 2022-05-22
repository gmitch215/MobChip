package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.bukkit.events.RestrictionSetEvent;
import me.gamercoder215.mobchip.bukkit.events.memory.MemoryChangeEvent;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.core.BlockPos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bukkit Implementation of EntityBrain
 * @see EntityBrain
 */
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

	public @NotNull EntityAI getGoalAI() {
		return new BukkitAI(nmsMob.goalSelector, false);
	}

	public @NotNull EntityAI getTargetAI() {
		return new BukkitAI(nmsMob.targetSelector, true);
	}

	@Override
	public @NotNull EntityNavigation createNavigation() {
		return new BukkitNavigation(nmsMob.getNavigation(), m);
	}

	@Override
	public @NotNull EntityController getController() {
		return new BukkitController(m.getWorld(), nmsMob.getJumpControl(), nmsMob.getLookControl(), nmsMob.getMoveControl());
	}

	@Override
	public <T> void setMemory(@NotNull EntityMemory<T> memory, @Nullable T value) throws IllegalArgumentException {
		if (value == null) {
			nmsMob.getBrain().eraseMemory(memory.getHandle());
			return;
		}

		if (memory.convert(value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		Object old = getMemory(memory);

		nmsMob.getBrain().setMemory(memory.getHandle(), memory.convert(value));

		MemoryChangeEvent event = new MemoryChangeEvent(this, memory, old, value);
		Bukkit.getPluginManager().callEvent(event);
	}

	@Override
	public <T> void setMemory(@NotNull EntityMemory<T> memory, @Nullable T value, long expire) throws IllegalArgumentException {
		if (expire < 0) throw new IllegalArgumentException("Invalid ticks number " + expire);
		if (memory.convert(value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		nmsMob.getBrain().setMemoryWithExpiry(memory.getHandle(), memory.convert(value), expire);
		Object old = getMemory(memory);

		MemoryChangeEvent event = new MemoryChangeEvent(this, memory, old, value);
		Bukkit.getPluginManager().callEvent(event);
	}

	@Override
	public <T> @Nullable T getMemory(@NotNull EntityMemory<T> memory) {
		if (nmsMob.getBrain().getMemory(memory.getHandle()).isPresent())
		return (T) nmsMob.getBrain().getMemory(memory.getHandle()).get();
		else return null;
	}

	@Override

	public long getExpiration(@NotNull EntityMemory<?> memory) {
		return nmsMob.getBrain().getTimeUntilExpiry(memory.getHandle());
	}

	@Override
	public boolean containsMemory(@NotNull EntityMemory<?> memory) {
		return nmsMob.getBrain().hasMemoryValue(memory.getHandle());
	}

	@Override
	public boolean isInRestriction() {
		return nmsMob.isWithinRestriction();
	}

	@Override
	public void setRestrictionArea(Location c, int r) {
		RestrictionSetEvent event = new RestrictionSetEvent(this, getRestrictionArea(), c, getRestrictionRadius(), r);
		Bukkit.getPluginManager().callEvent(event);

		Location center = event.getNewCenter();
		int radius = event.getNewRadius();

		nmsMob.restrictTo(new BlockPos(center.getX(), center.getY(), center.getZ()), radius);
	}

	@Override
	public Location getRestrictionArea() {
		BlockPos center = nmsMob.getRestrictCenter();
		if (center == null) return null;
		return new Location(m.getWorld(), center.getX(), center.getY(), center.getZ());
	}

	@Override
	public boolean hasRestriction() {
		return nmsMob.hasRestriction();
	}

	@Override
	public int getRestrictionRadius() {
		return Math.min((int) Math.floor(nmsMob.getRestrictRadius()), Integer.MAX_VALUE);
	}

	@Override
	public boolean canSee(@Nullable Entity en) {
		return nmsMob.getSensing().hasLineOfSight(ChipConversions.convertType(en));
	}

	@Override
	public @NotNull Mob getEntity() {
		return this.m;
	}
}
