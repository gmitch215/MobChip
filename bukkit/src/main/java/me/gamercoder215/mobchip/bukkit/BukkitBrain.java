package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.DragonBrain;
import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.attribute.Attribute;
import me.gamercoder215.mobchip.ai.attribute.AttributeInstance;
import me.gamercoder215.mobchip.ai.behavior.*;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.memories.MemoryStatus;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.sensing.EntitySenses;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.bukkit.events.RestrictionSetEvent;
import me.gamercoder215.mobchip.bukkit.events.memory.MemoryChangeEvent;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.nbt.EntityNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;

/**
 * Bukkit Implementation of EntityBrain
 * @see EntityBrain
 */
public class BukkitBrain implements EntityBrain {

	private static final String BUKKIT_PACKAGE = BukkitBrain.class.getPackage().getName() + ".";
	final Mob m;

	BukkitBrain(@NotNull Mob m) {
		this.m = m;
	}

	static final ChipUtil w = ChipUtil.getWrapper();

	/**
	 * Get the EntityBrain of this Mob.
	 * @param m Mob to get
	 * @return EntityBrain
	 */
	@Nullable
	public static EntityBrain getBrain(@Nullable Mob m) {
		if (m == null) return null;
		if (m instanceof Villager) return new BukkitVillagerBrain((Villager) m);
		return new BukkitBrain(m);
	}

	/**
	 * Gets the DragonBrain of this EnderDragon.
	 * @param d EnderDragon to get
	 * @return DragonBrain
	 */
	@Nullable
	public static DragonBrain getBrain(@Nullable EnderDragon d) {
		if (d == null) return null;
		return new BukkitDragonBrain(d);
	}

	/**
	 * Registers a Sensor.
	 * @param sensor Sensor to Register
	 * @throws IllegalArgumentException if sensor is already registered with this key
	 */
	public static void registerSensor(@NotNull Sensor<?> sensor) throws IllegalArgumentException {
		if (isSensorRegistered(sensor.getKey())) throw new IllegalArgumentException("Sensor is already registered with key: " + sensor.getKey());
		w.registerSensor(sensor);
	}

	/**
	 * Checks whether this sensor is registered.
	 * @param sensor Sensor to check
	 * @return true if registered, false otherwise
	 */
	public static boolean isSensorRegistered(@NotNull Sensor<?> sensor) {
		return isSensorRegistered(sensor.getKey());
	}

	/**
	 * Checks whether a sensor is registered with this key.
	 * @param key NamespacedKey of the Sensor
	 * @return true if registered, false otherwise
	 */
	public static boolean isSensorRegistered(@NotNull NamespacedKey key) {
		return w.existsSensor(key);
	}

	/**
	 * Fetches the Sensor registered with this key.
	 * @param key NamespacedKey of the Sensor
	 * @return Sensor Registered, or null if not found
	 */
	@Nullable
	public static Sensor<?> getRegisteredSensor(@NotNull NamespacedKey key) {
		if (!isSensorRegistered(key)) return null;
		return w.getSensor(key);
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
	 * Fetches the MobChip Attribute Instance.
	 * @param a Attribute to use
	 * @return AttributeInstance
	 */
	@Override
	public @NotNull AttributeInstance getAttributeInstance(@NotNull Attribute a) {
		return w.getAttributeInstance(m, a);
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
		return w.getNavigation(m);
	}

	/**
	 * Get the Entity Controller associated with this Brain.
	 * @return Entity Controller
	 */
	@Override
	public @NotNull EntityController getController() {
		return w.getController(m);
	}

	/**
	 * Gets the EntityScheduleManager associated with this Brain.
	 * @return EntityScheduleManager
	 */
	@Override
	public @NotNull EntityScheduleManager getScheduleManager() { return w.getManager(m); }

	/**
	 * Get the Entity's Scheduling Manager associated with this Brain.
	 * @return Schedule Manager
	 */
	@Override
	public EntityBehavior getBehaviors() {
		try {
			Constructor<? extends EntityBehavior> constr = null;
			Class<?> entityClass = m.getType().getEntityClass();

			switch (m.getType().name().toLowerCase()) {
				case "frog": {
					Class<? extends FrogBehavior> frog = Class.forName(BUKKIT_PACKAGE + "BukkitFrogBehavior")
							.asSubclass(FrogBehavior.class);
					constr = frog.getDeclaredConstructor(entityClass);
					break;
				}
				case "warden": {
					Class<? extends WardenBehavior> warden = Class.forName(BUKKIT_PACKAGE + "BukkitWardenBehavior")
							.asSubclass(WardenBehavior.class);
					constr = warden.getDeclaredConstructor(entityClass);
					break;
				}
				case "ender_dragon": {
					Class<? extends DragonBehavior> dragon = Class.forName(BUKKIT_PACKAGE + "BukkitDragonBehavior")
							.asSubclass(DragonBehavior.class);
					constr = dragon.getDeclaredConstructor(entityClass);
					break;
				}
				case "axolotl": {
					Class<? extends AxolotlBehavior> axolotl = Class.forName(BUKKIT_PACKAGE + "BukkitAxolotlBehavior")
							.asSubclass(AxolotlBehavior.class);
					constr = axolotl.getDeclaredConstructor(entityClass);
					break;
				}
				case "piglin": {
					Class<? extends PiglinBehavior> piglin = Class.forName(BUKKIT_PACKAGE + "BukkitPiglinBehavior")
							.asSubclass(PiglinBehavior.class);
					constr = piglin.getDeclaredConstructor(entityClass);
					break;
				}
				case "allay": {
					Class<? extends AllayBehavior> allay = Class.forName(BUKKIT_PACKAGE + "BukkitAllayBehavior")
							.asSubclass(AllayBehavior.class);
					constr = allay.getDeclaredConstructor(entityClass);
					break;
				}
				case "camel": {
					Class<? extends CamelBehavior> camel = Class.forName(BUKKIT_PACKAGE + "BukkitCamelBehavior")
							.asSubclass(CamelBehavior.class);
					constr = camel.getDeclaredConstructor(entityClass);
					break;
				}
			}

			constr.setAccessible(true);
			return constr.newInstance(m);
		} catch (ClassNotFoundException | NoSuchMethodException ignored) {}
		catch (Exception e) {
			ChipUtil.printStackTrace(e);
		}

		if (m instanceof Villager) return new BukkitVillagerBehavior((Villager) m);
		if (m instanceof Creature) return new BukkitCreatureBehavior((Creature) m);

		return new BukkitEntityBehavior(m);
	}

	/**
	 * Fetches the Entity Body Editor.
	 * @return Entity Body Editor
	 */
	@Override
	public @NotNull EntityBody getBody() {
		return w.getBody(m);
	}

	@Override
	public @NotNull EntityCombatTracker getCombatTracker() {
		return w.getCombatTracker(m);
	}

	@Override
	public @NotNull EntityNBT getNBTEditor() {
		return w.getNBTEditor(m);
	}

	@Override
	public @NotNull EntitySenses getSenses() {
		return w.getSenses(m);
	}

	/**
	 * Sets a permanent memory into this entity's brain.
	 * @param memory Memory to change
	 * @param value Value of new memory, null to remove
	 * @throws IllegalArgumentException if the value is not suitable for this memory
	 * @param <T> Memory Type
	 */
	@Override
	public <T> void setMemory(@NotNull Memory<T> memory, @Nullable T value) throws IllegalArgumentException {
		if (value == null) removeMemory(memory);

		Object old = getMemory(memory);
		w.setMemory(m, memory, value);

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
	public <T> void setMemory(@NotNull Memory<T> memory, @Nullable T value, long expire) throws IllegalArgumentException {
		if (value == null) removeMemory(memory);

		Object old = getMemory(memory);
		w.setMemory(m, memory, value, expire);

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
		return w.getMemory(m, memory);
	}

	/**
	 * Get the expiration date of this Memory.
	 * @param memory Memory to fetch
	 * @return Found expiration date, or 0 if no expiration or not found
	 */
	@Override
	public long getExpiration(@NotNull Memory<?> memory) {
		return w.getExpiry(m, memory);
	}

	/**
	 * Whether this Brain contains this memory.
	 * @param memory Memory to fetch
	 * @return true if contains, else false
	 */
	@Override
	public boolean containsMemory(@NotNull Memory<?> memory) {
		return w.contains(m, memory);
	}

	@Override
	public void removeMemory(@NotNull Memory<?> memory) {
		w.removeMemory(m, memory);
	}

	@Override
	public MemoryStatus getMemoryStatus(@NotNull Memory<?> memory) {
		return w.getMemoryStatus(m, memory);
	}

	/**
	 * Whether this Entity is in its restriction area.
	 * @return true if inside, else false
	 */
	@Override
	public boolean isInRestriction() {
		return w.isRestricted(m);
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

		w.restrictTo(m, newCenter.getX(), newCenter.getY(), newCenter.getZ(), newRadius);
	}

	/**
	 * Clears this Entity's Restriction Area.
	 */
	@Override
	public void clearRestrictionArea() {
		w.clearRestriction(m);
	}

	/**
	 * Gets the current restriction area.
	 * @return Restriction Area
	 */
	@Override
	public Location getRestrictionArea() {
		return w.getRestriction(m);
	}

	/**
	 * Whether this entity has a restriction area.
	 * @return true if it has a restriction area, else false
	 */
	@Override
	public boolean hasRestriction() {
		return w.hasRestriction(m);
	}

	/**
	 * Fetch the current restriction radius for this entity.
	 * @return Restriction Radius
	 */
	@Override
	public int getRestrictionRadius() {
		return w.getRestrictionRadius(m);
	}

	/**
	 * Whether this Mob can see another Entity.
	 * @param en Entity to test
	 * @return true if entity can see, else false
	 */
	@Override
	public boolean canSee(@Nullable Entity en) {
		return w.canSee(m, en);
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
