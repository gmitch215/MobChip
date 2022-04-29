package me.gamercoder215.mobchip.bosses;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.bosses.annotations.Repeatable;

/**
 * Represents a Boss Entity.
 */
public abstract class Boss<T extends Mob> {
    
    /**
     * This Entity, can be null.
     */
    @Nullable
    protected T mob;

    private static long idCounter = 0;
    private static Map<Long, Boss<?>> bosses = new HashMap<>();

    private Sound deathSound;
    private Sound spawnSound;

    private final EntityType type;
    private final Plugin plugin;
    private final Class<T> mobClazz;
    private final BossHandler handler;

    /**
     * The ID of this Boss.
     */
    protected final long id;

    /**
     * Creates a New Boss.
     * @param t EntityType of Boss
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
     * @return Foudn Death Sound
     */
    @NotNull
    public final Sound getDeathSound() {
        return this.deathSound;
    }

    /**
     * Sets the Death Sound.
     * @param s New Death Sound, or Null to reset
     */
    @NotNull
    public final void setDeathSound(@Nullable Sound s) {
        if (s == null) this.deathSound = s; else this.deathSound = Sound.ENTITY_WITHER_DEATH;
    }

    /**
     * Gets the Spawn Sound, or {@link Sound#ENTITY_WITHER_SPAWN} if not set
     * @return Foudn Spawn Sound
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
     * Fetch the Entity. Can be null if not spawned yet.
     * @return Entity found
     */
    @Nullable
    public T getMob() {
        return this.mob;
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
    public void onSpawn(CreatureSpawnEvent e) {};

    /**
     * Called when the Entity Dies
     * @param death Event called when it dies
     */
    public void onDeath(EntityDeathEvent death) {};

    /**
     * Called when this Entity receives damage
     * @param e Event called when it is damaged
     */
    public void onDamageDefensive(EntityDamageEvent e) {};

    /**
     * Called when this Entity damages another Entity
     * @param e Event called when it damages another entity
     */
    public void onDamageOffensive(EntityDamageByEntityEvent e) {};
}
