package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.behavior.EntityBehavior;
import me.gamercoder215.mobchip.ai.behavior.FrogBehavior;
import me.gamercoder215.mobchip.ai.behavior.WardenBehavior;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.bukkit.events.RestrictionSetEvent;
import me.gamercoder215.mobchip.bukkit.events.memory.MemoryChangeEvent;
import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.npc.Villager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Bukkit Implementation of EntityBrain
 * @see EntityBrain
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class BukkitBrain implements EntityBrain {
	
	private final Mob m;
	private final net.minecraft.world.entity.Mob nmsMob;

	private BukkitBrain(@NotNull Mob m) {
		this.m = m;
		this.nmsMob = MobChipUtil.convert(m);
	}

	private static <T> Object convertMemory(Memory<T> t, T value) {
		try {
			Field f = t.getClass().getDeclaredField("convert");
			f.setAccessible(true);

			Function<T, ?> convert = Function.class.cast(f.get(t));

			return convert.apply(value);
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return null;
		}
	}
	
	private static MemoryModuleType getHandle(Memory<?> memory) {
		return ((EntityMemory<?>) memory).getHandle();
	}

	/**
	 * Get the EntityBrain of this Mob.
	 * @param m Mob to get
	 * @return EntityBrain
	 */
	public static EntityBrain getBrain(@NotNull Mob m) {
		return new BukkitBrain(m);
	}

	/**
	 * Get the Entity AI associated with this Brain.
	 * @return Entity AI
	 */
	@Override
	public @NotNull EntityAI getGoalAI() {
		return new BukkitAI(nmsMob.goalSelector, false);
	}

	/**
	 * Get the Entity Target AI associated with this Brain.
	 * @return Entity Target AI
	 */
	@Override
	public @NotNull EntityAI getTargetAI() {
		return new BukkitAI(nmsMob.targetSelector, true);
	}

	/**
	 * Creates new Entity Navigation associated with this Brain.
	 * @return Created Entity Navigation
	 */
	@Override
	public @NotNull EntityNavigation createNavigation() {
		return new BukkitNavigation(nmsMob.getNavigation(), m);
	}

	/**
	 * Get the Entity Controller associated with this Brain.
	 * @return Entity Controller
	 */
	@Override
	public @NotNull EntityController getController() {
		return new BukkitController(m, m.getWorld(), nmsMob.getJumpControl(), nmsMob.getLookControl(), nmsMob.getMoveControl());
	}

	/**
	 * Get the Entity's Behavior associated with this Brain.
	 * @return Entity Behavior
	 */
	@Override
	public EntityBehavior getBehaviors() {
		if (nmsMob instanceof PathfinderMob) return new BukkitCreatureBehavior((PathfinderMob) nmsMob);
		else if (nmsMob instanceof Villager) return new BukkitVillagerBehavior((Villager) nmsMob);
		else if (nmsMob instanceof Axolotl) return new BukkitAxolotlBehavior((Axolotl) nmsMob);

		else switch (m.getType().name().toLowerCase()) {
				case "warden": try {
						Class<?> wardenClass = Class.forName("net.minecraft.world.entity.monster.Warden");
						return (WardenBehavior) Class.forName("me.gamercoder215.mobchip.bukkit.BukkitWardenBehavior").getConstructor(wardenClass).newInstance(nmsMob);
					} catch (Exception ignored) { return null; }
				case "frog": try {
					Class<?> frogClass = Class.forName("net.minecraft.world.entity.animal.frog.Frog");
					return (FrogBehavior) Class.forName("me.gamercoder215.mobchip.bukkit.BukkitFrogBehavior").getConstructor(frogClass).newInstance(nmsMob);
				} catch (Exception ignored) { return null; }
		}

		return new BukkitEntityBehavior(nmsMob);
	}

	/**
	 * Fetches the Entity Body Editor.
	 * @return Entity Body Editor
	 */
	@Override
	public @NotNull EntityBody getBody() {
		return new BukkitBody(nmsMob);
	}

	/**
	 * Sets a permanent memory into this entity's brain.
	 * @param memory Memory to change
	 * @param value Value of new memory, null to remove
	 * @throws IllegalArgumentException if the value is not suitable for this memory
	 * @param <T> Memory Type
	 */
	@Override
	public <T> void setMemory(@NotNull Memory<T> memory, @NotNull T value) throws IllegalArgumentException {
		if (value == null) {
			nmsMob.getBrain().eraseMemory(getHandle(memory));
			return;
		}

		if (convertMemory(memory, value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		Object old = getMemory(memory);

		nmsMob.getBrain().setMemory(getHandle(memory), convertMemory(memory, value));

		MemoryChangeEvent event = new MemoryChangeEvent(this, (EntityMemory<?>) memory, old, value);
		Bukkit.getPluginManager().callEvent(event);
	}

	/**
	 * Sets a temporary memory into this entity's brain.
	 * <p>
	 * Removing ANY memory should be using {@link #setMemory(Memory, Object)} with null as the second parameter.
	 * @param memory Memory to change
	 * @param value Value of new memory
	 * @param expire How many ticks until this memory will be forgotten/removed
	 * @throws IllegalArgumentException if the value is not suitable for this memory / ticks amount is invalid
	 * @param <T> Memory Type
	 */
	@Override
	public <T> void setMemory(@NotNull Memory<T> memory, @NotNull T value, long expire) throws IllegalArgumentException {
		if (expire < 0) throw new IllegalArgumentException("Invalid ticks number " + expire);
		if (convertMemory(memory, value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		nmsMob.getBrain().setMemoryWithExpiry(getHandle(memory), convertMemory(memory, value), expire);
		Object old = getMemory(memory);

		MemoryChangeEvent event = new MemoryChangeEvent(this, (EntityMemory<?>) memory, old, value);
		Bukkit.getPluginManager().callEvent(event);
	}

	/**
	 * Fetch the Memory that is stored in this Entity's Brain.
	 * @param memory Memory to fetch
	 * @return Found value as an object, null if not present
	 * @param <T> Memory Type
	 */
	@Override
	public <T> @Nullable T getMemory(@NotNull Memory<T> memory) {
		Optional<?> mem = nmsMob.getBrain().getMemory(getHandle(memory));
		if (mem.isEmpty()) return null;

		Class<T> clazz = memory.getBukkitClass();
		Object o = mem.get();

		try {
			Field f = memory.getClass().getDeclaredField("backConvert");
			Function back = Function.class.cast(f.get(memory));
			return clazz.cast(back.apply(o));
		} catch (ClassCastException e) {
			return null;
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return null;
		}
	}

	/**
	 * Get the expiration date of this Memory.
	 * @param memory Memory to fetch
	 * @return Found expiration date, or 0 if no expiration or not found
	 */
	@Override
	public long getExpiration(@NotNull Memory<?> memory) {
		return nmsMob.getBrain().getTimeUntilExpiry(getHandle(memory));
	}

	/**
	 * Whether this Brain contains this memory.
	 * @param memory Memory to fetch
	 * @return true if contains, else false
	 */
	@Override
	public boolean containsMemory(@NotNull Memory<?> memory) {
		return nmsMob.getBrain().hasMemoryValue(getHandle(memory));
	}

	@Override
	public void removeMemory(@NotNull Memory<?> memory) {
		nmsMob.getBrain().eraseMemory(getHandle(memory));
	}

	/**
	 * Whether this Entity is in its restriction area.
	 * @return true if inside, else false
	 */
	@Override
	public boolean isInRestriction() {
		return nmsMob.isWithinRestriction();
	}

	/**
	 * Sets the Restriction Area for this Entity.
	 * @param center Location center
	 * @param radius Radius of restriction center
	 */
	@Override
	public void setRestrictionArea(Location center, int radius) {
		RestrictionSetEvent event = new RestrictionSetEvent(this, getRestrictionArea(), center, getRestrictionRadius(), radius);
		Bukkit.getPluginManager().callEvent(event);

		Location newCenter = event.getNewCenter();
		int newRadius = event.getNewRadius();

		nmsMob.restrictTo(new BlockPos(newCenter.getX(), newCenter.getY(), newCenter.getZ()), newRadius);
	}

	/**
	 * Clears this Entity's Restriction Area.
	 */
	@Override
	public void clearRestrictionArea() {
		nmsMob.clearRestriction();
	}

	/**
	 * Gets the current restriction area.
	 * @return Restriction Area
	 */
	@Override
	public Location getRestrictionArea() {
		BlockPos center = nmsMob.getRestrictCenter();
		if (center == null) return null;
		return new Location(m.getWorld(), center.getX(), center.getY(), center.getZ());
	}

	/**
	 * Whether this entity has a restriction area.
	 * @return true if it has a restriction area, else false
	 */
	@Override
	public boolean hasRestriction() {
		return nmsMob.hasRestriction();
	}

	/**
	 * Fetch the current restriction radius for this entity.
	 * @return Restriction Radius
	 */
	@Override
	public int getRestrictionRadius() {
		return Math.min((int) Math.floor(nmsMob.getRestrictRadius()), Integer.MAX_VALUE);
	}

	/**
	 * Whether this Mob can see another Entity.
	 * @param en Entity to test
	 * @return true if entity can see, else false
	 */
	@Override
	public boolean canSee(@Nullable Entity en) {
		return nmsMob.getSensing().hasLineOfSight(MobChipUtil.convert(en));
	}

	/**
	 * Get the Entity that this Brain relates to.
	 * @return Entity of this brain
	 */
	@Override
	public @NotNull Mob getEntity() {
		return this.m;
	}
}
