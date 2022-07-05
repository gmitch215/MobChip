package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.behavior.EntityBehavior;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.bukkit.events.RestrictionSetEvent;
import me.gamercoder215.mobchip.bukkit.events.memory.MemoryChangeEvent;
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
public final class BukkitBrain implements EntityBrain {
	
	private final Mob m;

	private BukkitBrain(@NotNull Mob m) {
		this.m = m;
	}

	private static final ChipUtil wrapper = ChipUtil.getWrapper();

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
		return new BukkitAI(m, false);
	}

	/**
	 * Get the Entity Target AI associated with this Brain.
	 * @return Entity Target AI
	 */
	@Override
	public @NotNull EntityAI getTargetAI() {
		return new BukkitAI(m, true);
	}

	/**
	 * Creates new Entity Navigation associated with this Brain.
	 * @return Created Entity Navigation
	 */
	@Override
	public @NotNull EntityNavigation createNavigation() {
		return wrapper.getNavigation(m);
	}

	/**
	 * Get the Entity Controller associated with this Brain.
	 * @return Entity Controller
	 */
	@Override
	public @NotNull EntityController getController() {
		return wrapper.getController(m);
	}

	/**
	 * Get the Entity's Behavior associated with this Brain.
	 * @return Entity Behavior
	 */
	@Override
	public EntityBehavior getBehaviors() {
		try {
			Class<?> b = Class.forName(BukkitBrain.class.getPackage().getName() + ".Bukkit" + Character.toUpperCase(m.getType().name().charAt(0)) + m.getType().name().substring(1).toLowerCase() + "Behavior");
			return (EntityBehavior) b.getConstructor(m.getType().getEntityClass()).newInstance(m);
		} catch (ClassNotFoundException | NoSuchMethodException e) {
			return new BukkitEntityBehavior(m);
		} catch (Exception e) {
			Bukkit.getLogger().severe(e.getMessage());
			for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
			return new BukkitEntityBehavior(m);
		}
	}

	/**
	 * Fetches the Entity Body Editor.
	 * @return Entity Body Editor
	 */
	@Override
	public @NotNull EntityBody getBody() {
		return wrapper.getBody(m);
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
		Object old = getMemory(memory);
		wrapper.setMemory(m, memory, value);

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
		Object old = getMemory(memory);
		wrapper.setMemory(m, memory, value, expire);

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
		return wrapper.getMemory(m, memory);
	}

	/**
	 * Get the expiration date of this Memory.
	 * @param memory Memory to fetch
	 * @return Found expiration date, or 0 if no expiration or not found
	 */
	@Override
	public long getExpiration(@NotNull Memory<?> memory) {
		return wrapper.getExpiry(m, memory);
	}

	/**
	 * Whether this Brain contains this memory.
	 * @param memory Memory to fetch
	 * @return true if contains, else false
	 */
	@Override
	public boolean containsMemory(@NotNull Memory<?> memory) {
		return wrapper.contains(m, memory);
	}

	@Override
	public void removeMemory(@NotNull Memory<?> memory) {
		wrapper.removeMemory(m, memory);
	}

	/**
	 * Whether this Entity is in its restriction area.
	 * @return true if inside, else false
	 */
	@Override
	public boolean isInRestriction() {
		return wrapper.isRestricted(m);
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

		wrapper.restrictTo(m, newCenter.getX(), newCenter.getY(), newCenter.getZ(), newRadius);
	}

	/**
	 * Clears this Entity's Restriction Area.
	 */
	@Override
	public void clearRestrictionArea() {
		wrapper.clearRestriction(m);
	}

	/**
	 * Gets the current restriction area.
	 * @return Restriction Area
	 */
	@Override
	public Location getRestrictionArea() {
		return wrapper.getRestriction(m);
	}

	/**
	 * Whether this entity has a restriction area.
	 * @return true if it has a restriction area, else false
	 */
	@Override
	public boolean hasRestriction() {
		return wrapper.hasRestriction(m);
	}

	/**
	 * Fetch the current restriction radius for this entity.
	 * @return Restriction Radius
	 */
	@Override
	public int getRestrictionRadius() {
		return wrapper.getRestrictionRadius(m);
	}

	/**
	 * Whether this Mob can see another Entity.
	 * @param en Entity to test
	 * @return true if entity can see, else false
	 */
	@Override
	public boolean canSee(@Nullable Entity en) {
		return wrapper.canSee(m, en);
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
