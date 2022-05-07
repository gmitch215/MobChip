package me.gamercoder215.mobchip.bosses;

import me.gamercoder215.mobchip.bosses.annotations.Repeatable;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a Boss Entity.
 * @param <T> The entity type of this Boss.
 */
public abstract class Boss<T extends Mob> {

    /**
     * Default health for Boss (20)
     */
    public static final int DEFAULT_HEALTH = 20;
    /**
     * This Entity, can be null.
     */
    @Nullable
    protected T mob;

    private static long idCounter = 0;
    private static final Map<Long, Boss<?>> bosses = new HashMap<>();

    private Sound deathSound;
    private Sound spawnSound;
    private float volume;
    private float pitch;
    
	private double health = DEFAULT_HEALTH;
	private final Map<EquipmentSlot, ItemStack> equipment = new HashMap<>();

	private final Map<AttributeInstance, Double> attributes = new HashMap<>();

    private final EntityType type;
    private final Plugin plugin;
    private final Class<T> mobClazz;
    private final BossHandler handler;

    /**
     * The Numerical ID of this Boss.
     */
    protected final long id;

    /**
     * Creates a New Boss.
     * @param t EntityType of Boss
     * @param plugin Plugin that owns this boss
     * @throws IllegalArgumentException if not a valid Mob EntityType or if Plugin is null
     */
    @SuppressWarnings("unchecked")
    protected Boss(@NotNull EntityType t, @NotNull Plugin plugin) throws IllegalArgumentException {
        if (plugin == null) throw new IllegalArgumentException("Plugin is null");

        this.plugin = plugin;
        try {
            this.mobClazz = (Class<T>) t.getEntityClass().asSubclass(Mob.class);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid EntityType: " + t.name());
        }

        this.type = t;
        this.mob = null;
        this.id = idCounter;
        bosses.put(id, this);

        this.deathSound = Sound.ENTITY_WITHER_DEATH;
        this.spawnSound = Sound.ENTITY_WITHER_SPAWN;

        this.handler = new BossHandler(this, plugin);
        idCounter++;
    }

    // Util

    // Static
    /**
     * Fetch a Boss by its id.
     * @param id ID of boss to fetch
     * @return found boss, or null if not found
     */
    @Nullable
    public static Boss<?> valueOf(long id) {
        for (Map.Entry<Long, Boss<?>> entry : bosses.entrySet()) {
            if (entry.getKey() == id) return entry.getValue();
        }

        return null;
    }

    /**
     * Fetch a Boss by an Entity UUID. Will not work for Bosses that are not spawned.
     * @param uid UUID of Entity Boss
     * @return Found boss class, or null if not found
     */
    @Nullable
    public static Boss<?> valueOf(@NotNull UUID uid) {
        if (uid == null) return null;

        for (Boss<? extends Mob> boss : bosses.values()) {
            if (boss.getMob() != null && boss.getMob().getUniqueId().equals(uid)) return boss;
        }

        return null;
    }

    /**
     * Fetch a Boss by its Entity Instance.
     * @param <T> Type of Entity
     * @param entity Entity Instance
     * @return Found boss class, or null if not found
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends Mob> Boss<T> valueOf(@NotNull T entity) {
        try {
            return (Boss<T>) valueOf(entity.getUniqueId());
        } catch (ClassCastException e) {
            return null;
        }
    } 


    // Instance

    /**
     * Fetches the BossHandler for this Boss.
     * @return BossHandler found
     */
    @NotNull
    public final BossHandler getHandler() {
        return this.handler;
    }

    /**
     * Fetch the Entity Class of this Boss.
     * @return Entity Class Found
     */
    public final Class<T> getEntityClass() {
        return this.mobClazz;
    }

    /**
     * Fetch the Entity Type of this Boss.
     * @return Entity Type Found
     */
    public final EntityType getEntityType() {
        return this.type;
    }

    /**
     * Get the ID of this Boss Class.
     * @return ID of Boss
     */
    public final long getBossId() {
        return this.id;
    }

    /**
     * Spawns this Boss. The SpawnReason will always be {@link SpawnReason#CUSTOM}.
     * @param l Location to spawn
     */
    public final void spawn(@NotNull Location l) {
        T mob = l.getWorld().spawn(l, this.getEntityClass());
        this.mob = mob;
        
        for (AttributeInstance a : attributes.keySet()) a.setBaseValue(attributes.get(a));
        for (EquipmentSlot s : equipment.keySet()) mob.getEquipment().setItem(s, equipment.get(s));
        
        if (spawnSound != null) l.getWorld().playSound(l, deathSound, 3F, 1F);

        final Boss<T> inst = this;

        try {
            for (Method m : this.getClass().getDeclaredMethods()) {
                m.setAccessible(true);

                if (m.getAnnotation(Repeatable.class) != null) {
                    Repeatable r = m.getAnnotation(Repeatable.class);

                    new BukkitRunnable() {
                        public void run() {
                            if (mob.isDead()) {
                                cancel();
                                return;
                            }

                            try {
                                m.invoke(inst);
                            } catch (InvocationTargetException e) {
                                e.getCause().printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.runTaskTimer(plugin, r.delay(), r.interval());
                }
            }
        } catch (Exception e) {
            // do nothing
        }

        CreatureSpawnEvent event = new CreatureSpawnEvent(mob, SpawnReason.CUSTOM);
        onSpawn(event);
    }

    /**
     * Gets the Death Sound, or {@link Sound#ENTITY_WITHER_DEATH} if not set
     * @return Found Death Sound
     */
    @NotNull
    public final Sound getDeathSound() {
        return this.deathSound;
    }

    /**
     * Sets the Death Sound.
     * @param s New Death Sound, or Null to reset
     */
    public final void setDeathSound(@Nullable Sound s) {
        this.deathSound = s == null ? Sound.ENTITY_WITHER_DEATH : s;
    }

    /**
     * Gets the Spawn Sound, or {@link Sound#ENTITY_WITHER_SPAWN} if not set
     * @return Found Spawn Sound
     */
    @NotNull
    public final Sound getSpawnSound() {
        return this.spawnSound;
    }
    
    /**
     * Sets the Death Sound.
     * @param s New Death Sound, or Null to reset
     */
    @NotNull
    public final void setSpawnSound(@Nullable Sound s) {
        if (s == null) this.spawnSound = s; else this.spawnSound = Sound.ENTITY_WITHER_SPAWN;
    }
    
    /**
     * Fetches the current sound pitch.
     * @return Current Sound Pitch
     */
    public final float getSoundPitch() {
    	return this.pitch;
    }
    
    /**
     * Fetches the current sound volume.
     * @return Current Sound Volume
     */
    public final float getSoundVolume() {
    	return this.volume;
    }
    
    /**
     * Sets the Sound's Pitch.
     * @param pitch Pitch to set
     * @throws IllegalArgumentException if pitch is less than -2F or greater than 2F
     */
    public final void setSoundPitch(float pitch) throws IllegalArgumentException {
    	if (pitch < 2 || pitch > 2) throw new IllegalArgumentException("Pitch must be between -2F and 2F");
    	this.pitch = pitch;
    }
    
    /**
     * Sets the Sound's Volume.
     * @param volume Volume to set
     * @throws IllegalArgumentException if volume is less than 0F
     */
    public final void setSoundVolume(float volume) throws IllegalArgumentException {
    	if (volume < 0) throw new IllegalArgumentException("Volume must be more than or equal to 0");
    	this.volume = volume;
    }

    /**
     * Fetch the Entity. Can be null if not spawned yet.
     * @return Entity found
     */
    @Nullable
    public final T getMob() {
        return this.mob;
    }
    
	/**
	 * Fetch this Entity's Attributes.
	 * @return Entity Attributes
	 */
	@NotNull
	public final Map<AttributeInstance, Double> getAttributes() {
		return this.attributes;
	}
	
	/**
	 * Get the health that this Boss will spawn with.
	 * @return Health spawned with
	 */
	public final double getHealth() {
		return this.health;
	}
	
	/**
	 * Sets the health that this Boss will spawn with.
	 * @param health Health to spawn with
	 */
	public final void setHealth(double health) {
		this.health = health;
	}
	
	/**
	 * Adds an Attribute that this Boss will spawn with.
	 * @param inst Attribute Instance
	 * @param value Value to set
	 */
	public final void addAttribute(@NotNull AttributeInstance inst, double value) {
		this.attributes.put(inst, value);
	}
	
	/**
	 * Removes an Attribute that this Boss will spawn with.
	 * @param inst Attribute Instance
	 */
	public final void removeAttribute(@NotNull AttributeInstance inst) {
		this.attributes.remove(inst);
	}
	
	/**
	 * Fetch the Boss's Equipment.
	 * @return Equipment that the Boss will spawn with
	 */
	@NotNull
	public final Map<EquipmentSlot, ItemStack> getEquipment() {
		return this.equipment;
	}
	
	/**
	 * Sets an item on this Boss's Equipment that it will spawn with.
	 * @param slot EquipmentSlot to spawn
	 * @param item ItemStack to set, can be null
	 */
	public final void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item) {
		if (item == null) return;
		this.equipment.put(slot, item);
	}
	
	/**
	 * Sets this Boss's Helmet.
	 * @param item Helmet Item to set, can be null
	 */
	public final void setHelmet(@Nullable ItemStack item) {
		setItem(EquipmentSlot.HEAD, item);
	}
	
	/**
	 * Sets this Boss's Chestplate.
	 * @param item Chestplate Item to set, can be null
	 */
	public final void setChestplate(@Nullable ItemStack item) {
		setItem(EquipmentSlot.CHEST, item);
	}
	
	/**
	 * Sets this Boss's Leggings.
	 * @param item Leggings Item to set, can be null
	 */
	public final void setLeggings(@Nullable ItemStack item) {
		setItem(EquipmentSlot.LEGS, item);
	}
	
	/**
	 * Sets this Boss's Boots.
	 * @param item Boots Item to set, can be null
	 */
	public final void setBoots(@Nullable ItemStack item) {
		setItem(EquipmentSlot.FEET, item);
	}
	
	/**
	 * Sets this Boss's Mainhand.
	 * @param item Mainhand Item to change, can be null
	 */
	public final void setMainhand(@Nullable ItemStack item) {
		setItem(EquipmentSlot.HAND, item);
	}
	
	/**
	 * Sets this Boss's Offhand.
	 * @param item Offhand Item to change, can be null
	 */
	public final void setOffhand(@Nullable ItemStack item) {
		setItem(EquipmentSlot.OFF_HAND, item);
	}

    // Implementation & Other

    /**
     * Get the Drops of this Boss Entity.
     * @return List of Drops
     */
    public abstract List<ItemStack> getDrops();

    /**
     * Called when the Entity Spawns
     * @param e Event called when it spawns
     */
    public void onSpawn(CreatureSpawnEvent e) {}

    /**
     * Called when the Entity Dies
     * @param death Event called when it dies
     */
    public void onDeath(EntityDeathEvent death) {}

    /**
     * Called when this Entity receives damage
     * @param e Event called when it is damaged
     */
    public void onDamageDefensive(EntityDamageEvent e) {}

    /**
     * Called when this Entity damages another Entity
     * @param e Event called when it damages another entity
     */
    public void onDamageOffensive(EntityDamageByEntityEvent e) {}
}
