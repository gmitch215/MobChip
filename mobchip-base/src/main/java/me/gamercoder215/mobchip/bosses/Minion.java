package me.gamercoder215.mobchip.bosses;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.attributes.ChipAttributeInstance;

/**
 * Represents a Boss Minion
 * <p>
 * Any methods that calls a class update (i.e. {@link #setHealth(double)}) will not update the actual entity if spawned. Call updates directly from the entity received at {@link #spawn(Location)}.
 */
public final class Minion<T extends Mob> {
	
	private final Class<T> entityClazz;
	
	private double health;
	private Map<EquipmentSlot, ItemStack> equipment;
	
	private Map<ChipAttributeInstance, Double> attributes;

	/**
	 * Creates a Minion.
	 * @param clazz Entity Class
	 * @param health Health of Entity
	 * @param attributes Entity Attribute Map of Attribute Instance to Value (<strong>Max Health will be automatically applied</strong>)
	 * @param equipment Entity Equipment Map of EquipmentSlot to ItemStack
	 * @throws IllegalArgumentException if health is less than 0 or if class is null
	 */
	public Minion(@NotNull Class<T> clazz, double health, @Nullable Map<EquipmentSlot, ItemStack> equipment, @Nullable Map<ChipAttributeInstance, Double> attributes) throws IllegalArgumentException {
		if (clazz == null) throw new IllegalArgumentException("Class type cannot be null");
		if (health <= 0) throw new IllegalArgumentException("Health cannot be less than or equal to 0");
		
		this.entityClazz = clazz;
		this.health = health;
		
		if (attributes != null) this.attributes = attributes;
		else this.attributes = new HashMap<>();
		
		if (equipment != null) this.equipment = equipment;
		else this.equipment = new HashMap<>();
	}
	
	/**
	 * Creates a Minion with no equipment.
	 * @param clazz Entity Class
	 * @param health Health of Entity
	 * @param equipment Entity Equipment Map of EquipmentSlot to ItemStack
	 * @throws IllegalArgumentException if health is less than 0 or if class is null
	 */
	public Minion(@NotNull Class<T> clazz, double health, @Nullable Map<EquipmentSlot, ItemStack> equipment) throws IllegalArgumentException {
		this(clazz, health, equipment, null);
	}
	
	/**
	 * Creates a Minion with no equipment or attributes.
	 * @param clazz Entity Class
	 * @param health Health of Entity
	 * @throws IllegalArgumentException if health is less than 0 or if class is null
	 */
	public Minion(@NotNull Class<T> clazz, double health) throws IllegalArgumentException {
		this(clazz, health, null);
	}
	
	/**
	 * Creates a Minion with a health of 20.
	 * @param clazz
	 * @throws IllegalArgumentException
	 */
	public Minion(@NotNull Class<T> clazz) throws IllegalArgumentException {
		this(clazz, 20);
	}
	
	/**
	 * Get the Entity Class of this Minion.
	 * @return Entity Class
	 */
	public final Class<T> getEntityClass() {
		return this.entityClazz;
	}
	
	/**
	 * Spawns this Minion at a Location.
	 * @param loc Location to spawn
	 * @return Spawned minion, or null if location is null
	 */
	@Nullable
	public final T spawn(@Nullable Location loc) {
		if (loc == null) return null;
		
		T entity = loc.getWorld().spawn(loc, entityClazz);
		entity.setHealth(health);
		entity.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
		for (EquipmentSlot s : equipment.keySet()) entity.getEquipment().setItem(s, equipment.get(s), true);
		
		for (ChipAttributeInstance a : attributes.keySet()) a.setBaseValue(attributes.get(a));
		
		return entity;
	}
	
	/**
	 * Spawns this Minion at a Boss.
	 * @param b Boss to spawn on
	 * @return Spawned minion, or null if boss is null / not spawned yet
	 */
	@Nullable
	public final T spawn(@Nullable Boss<?> b) {
		if (b == null) return null;
		if (b.getMob() == null) return null;
		
		return spawn(b.getMob().getLocation());
	}
	
	/**
	 * Spawns this Minion at another Boss.
	 * @param en Entity to spawn on
	 * @return Spawned minion, or null if entity is null
	 */
	@Nullable
	public final T spawn(@Nullable Entity en) {
		if (en == null) return null;
		return spawn(en.getLocation());
	}
	
	/**
	 * Fetch this Entity's Attributes.
	 * @return Entity Attributes
	 */
	@NotNull
	public final Map<ChipAttributeInstance, Double> getAttributes() {
		return this.attributes;
	}
	
	/**
	 * Get the health that this Minion will spawn with.
	 * @return Health
	 */
	public final double getHealth() {
		return this.health;
	}
	
	/**
	 * Sets the health that this Minion will spawn with.
	 * @param health Health to spawn with
	 */
	public final void setHealth(double health) {
		this.health = health;
	}
	
	/**
	 * Adds an Attribute that this Minion will spawn with.
	 * @param inst Attribute Instance
	 * @param value Value to set
	 */
	public final void addAttribute(@NotNull ChipAttributeInstance inst, double value) {
		this.attributes.put(inst, value);
	}
	
	/**
	 * Removes an Attribute that this Minion will spawn with.
	 * @param inst Attribute Instance
	 */
	public final void removeAttribute(@NotNull ChipAttributeInstance inst) {
		this.attributes.remove(inst);
	}
	
	/**
	 * Fetch the Minion's Equipment.
	 * @return Equipment that the Minion will spawn with
	 */
	@NotNull
	public final Map<EquipmentSlot, ItemStack> getEquipment() {
		return this.equipment;
	}
	
	/**
	 * Sets an item on this Minion's Equipment that it will spawn with.
	 * @param slot EquipmentSlot to spawn
	 * @param item ItemStack to set, can be null
	 */
	public final void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item) {
		if (item == null) return;
		this.equipment.put(slot, item);
	}
	
	/**
	 * Sets this Minion's Helmet.
	 * @param item Helmet Item to set, can be null
	 */
	public final void setHelmet(@Nullable ItemStack item) {
		setItem(EquipmentSlot.HEAD, item);
	}
	
	/**
	 * Sets this Minion's Chestplate.
	 * @param item Chestplate Item to set, can be null
	 */
	public final void setChestplate(@Nullable ItemStack item) {
		setItem(EquipmentSlot.CHEST, item);
	}
	
	/**
	 * Sets this Minion's Leggings.
	 * @param item Leggings Item to set, can be null
	 */
	public final void setLeggings(@Nullable ItemStack item) {
		setItem(EquipmentSlot.LEGS, item);
	}
	
	/**
	 * Sets this Minion's Boots.
	 * @param item Boots Item to set, can be null
	 */
	public final void setBoots(@Nullable ItemStack item) {
		setItem(EquipmentSlot.FEET, item);
	}
	
	/**
	 * Sets this Minion's Mainhand.
	 * @param item Mainhand Item to change, can be null
	 */
	public final void setMainhand(@Nullable ItemStack item) {
		setItem(EquipmentSlot.HAND, item);
	}
	
	/**
	 * Sets this Minion's Offhand.
	 * @param item Offhand Item to change, can be null
	 */
	public final void setOffhand(@Nullable ItemStack item) {
		setItem(EquipmentSlot.OFF_HAND, item);
	}

}
