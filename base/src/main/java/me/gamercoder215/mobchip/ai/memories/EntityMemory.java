package me.gamercoder215.mobchip.ai.memories;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Memory of an entity
 * @param <T> Type of Memory
 */
public final class EntityMemory<T> implements Memory<T> {
	
	/**
	 * Whether this Entity is not admiring another entity.
	 */
    public static final EntityMemory<Boolean> NOT_ADMIRING = new EntityMemory<>(Boolean.class, "admiring_disabled");
    /**
     * The Celebrating Location of this Entity.
     */
    public static final EntityMemory<Location> CELEBRATING_LOCATION = new EntityMemory<>(Location.class, "celebrate_location");
    /**
     * This Entity's Home.
     */
    public static final EntityMemory<Location> HOME = new EntityMemory<>(Location.class, "home");
    /**
     * The Job Site of this Entity.
     */
    public static final EntityMemory<Location> JOB_SITE = new EntityMemory<>(Location.class, "job_site");
    /**
     * A Possible Job Site of this Entity.
     */
    public static final EntityMemory<Location> POSSIBLE_JOB_SITE = new EntityMemory<>(Location.class, "potential_job_site");
    /**
     * An Entity's Meeting point.
     */
    public static final EntityMemory<Location> MEETING_POINT = new EntityMemory<>(Location.class, "meeting_point");
	/**
	 * An Array of Locations for a possible Secondary Job Site.
	 */
    public static final EntityMemory<Location[]> SECONDARY_JOB_SITE = new EntityMemory<>(Location[].class, "secondary_job_site");
    /**
     * An Array of Nearby Living Entities.
     */
    public static final EntityMemory<LivingEntity[]> NEAREST_LIVING_ENTITIES = new EntityMemory<>(LivingEntity[].class,"mobs");
    /**
     * An Array of Nearby and Visible Living Entities.
     */
    public static final EntityMemory<LivingEntity[]> NEAREST_VISIBLE_LIVING_ENTITIES = new EntityMemory<>(LivingEntity[].class, "visible_mobs");
    /**
     * An Array of Visible Villager Babies.
     */
    public static final EntityMemory<Villager[]> VISIBLE_VILLAGER_BABIES = new EntityMemory<>(Villager[].class, "visible_villager_babies");
    /**
     * An Array of Nearby Players.
     */
    public static final EntityMemory<Player[]> NEAREST_PLAYERS = new EntityMemory<>(Player[].class, "nearest_players");
    /**
     * The Nearest Visible Player.
     */
    public static final EntityMemory<Player> NEAREST_VISIBLE_PLAYER = new EntityMemory<>(Player.class, "nearest_visible_player");
    /**
     * The Nearest Visible Player this entity can attack.
     */
    public static final EntityMemory<Player> NEAREST_VISIBLE_ATTACKING_PLAYER = new EntityMemory<>(Player.class, "nearest_visible_targetable_player");
    /**
     * The Entity's Walking Target. See {@link WalkingTarget} for more information on how to set up.
     */
    public static final EntityMemory<WalkingTarget> WALKING_TARGET = new EntityMemory<>(WalkingTarget.class, "walk_target");
    /**
     * The Entity's Looking Target.
     */
    public static final EntityMemory<Location> LOOKING_TARGET = new EntityMemory<>(Location.class, "look_target");
    /**
     * The Entity's Attack Target.
     */
    public static final EntityMemory<LivingEntity> ATTACK_TARGET = new EntityMemory<>(LivingEntity.class, "attack_target");
    /**
     * Whether this Entity's Attack is cooling down.
     */
    public static final EntityMemory<Boolean> ATTACK_COOLING_DOWN = new EntityMemory<>(Boolean.class, "attack_cooling_down");
    /**
     * The Entity's Interaction Target.
     */
    public static final EntityMemory<LivingEntity> INTERACTION_TARGET = new EntityMemory<>(LivingEntity.class, "interaction_target");
    /**
     * The Entity's Breeding Target.
     */
    public static final EntityMemory<Ageable> BREEDING_TARGET = new EntityMemory<>(Ageable.class, "breed_target");
    /**
     * The Entity's Riding Target.
     */
    public static final EntityMemory<Entity> RIDING_TARGET = new EntityMemory<>(Entity.class, "ride_target");
    /**
     * An Array of Doors that are interactable (will ignore blocks without BlockData {@link Door})
     */
    public static final EntityMemory<Block[]> INTERACTABLE_DOORS = new EntityMemory<>(Block[].class, "interactable_doors");
    /**
     * An Array of Doors to close (will ignore blocks without BlockData {@link Door})
     */
    public static final EntityMemory<Block[]> DOORS_TO_CLOSE = new EntityMemory<>(Block[].class, "doors_to_close");

    /**
     * The nearest bed available for this Entity.
     */
    public static final EntityMemory<Location> NEAREST_BED = new EntityMemory<>(Location.class, "nearest_ved");
    /**
     * The Last DamageCause of this Entity.
     */
    public static final EntityMemory<DamageCause> LAST_HURT_CAUSE = new EntityMemory<>(DamageCause.class, "hurt_by");
    /**
     * The Last entity that caused this Entity Damage.
     */
    public static final EntityMemory<LivingEntity> LAST_HURT_ENTITY = new EntityMemory<>(LivingEntity.class, "hurt_by_entity");
    /**
     * The Entity to avoid.
     */
    public static final EntityMemory<LivingEntity> AVOID_TARGET = new EntityMemory<>(LivingEntity.class, "avoid_target");
    /**
     * The Nearest Hostile Living Entity.
     */
    public static final EntityMemory<LivingEntity> NEAREST_HOSTILE = new EntityMemory<>(LivingEntity.class, "nearest_hostile");
    /**
     * The Nearest Living Entity that this Mob can attack.
     */
    public static final EntityMemory<LivingEntity> NEAREST_ATTACKABLE = new EntityMemory<>(LivingEntity.class, "nearest_attackable");
    /**
     * A Hiding Place for this Entity during a Raid.
     */
    public static final EntityMemory<Location> HIDING_PLACE = new EntityMemory<>(Location.class, "hiding_place");
    /**
     * The duration, in ticks, of how long this Mob has heard a bell.
     */
    public static final EntityMemory<Long> HEARD_BELL_DURATION = new EntityMemory<>(Long.class, "heard_bell_time");
    /**
     * The last timestamp this Mob has failed to reach their Walking Target.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_FAILURE_WALK_TARGET = new EntityMemory<>(Long.class, "cant_reach_walk_target_since");
    /**
     * Whether a Golem has been <strong>recently</strong> detected.
     */
    public static final EntityMemory<Boolean> DETECTED_GOLEM = new EntityMemory<>(Boolean.class, "golem_detected_recently");
    /**
     * The last timestamp this Mob has slept.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_SLEPT = new EntityMemory<>(Long.class, "last_slept");
    /**
     * The last timestamp this Mob has woken up.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_WOKEN = new EntityMemory<>(Long.class, "last_woken");
    /**
     * The last timestamp this Mob has worked.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_WORKED = new EntityMemory<>(Long.class, "last_worked_at_poi");
    /**
     * The nearest visible Adult for this Mob.
     */
    public static final EntityMemory<Ageable> NEAREST_VISIBLE_ADULT = new EntityMemory<>(Ageable.class, "nearest_visible_adult");
    /**
     * How many ticks this Mob has played dead.
     */
    public static final EntityMemory<Integer> TICKS_PLAY_DEAD = new EntityMemory<>(Integer.class, "play_dead_ticks");
    /**
     * The Player this Mob is tempting.
     */
    public static final EntityMemory<Player> TEMPTING_PLAYER = new EntityMemory<>(Player.class, "tempting_player");
    /**
     * The cooldown, in ticks, of this Mob's tempting.
     */
    public static final EntityMemory<Integer> TEMPING_COOLDOWN = new EntityMemory<>(Integer.class, "temptation_cooldown_ticks");
    /**
     * Whether this Mob is being tempted.
     */
    public static final EntityMemory<Boolean> IS_TEMPTED = new EntityMemory<>(Boolean.class, "is_tempted");
    /**
     * The Entity's Long Jump Cooldown, in ticks
     */
    public static final EntityMemory<Integer> LONG_JUMP_COOLDOWN = new EntityMemory<>(Integer.class, "long_jump_cooldown_ticks");
    /**
     * Whether this Entity is currently doing its Long Jump.
     */
    public static final EntityMemory<Boolean> IS_LONG_JUMP = new EntityMemory<>(Boolean.class, "long_jump_mid_jump");
    /**
     * Whether this Entity has a Hunting Cooldown
     */
    public static final EntityMemory<Boolean> HAS_HUNTING_COOLDOWN = new EntityMemory<>(Boolean.class, "has_hunting_cooldown");
    /**
     * This entity's Ram Cooldown (Typically for Ravagers).
     */
    public static final EntityMemory<Integer> RAM_COOLDOWN = new EntityMemory<>(Integer.class, "ram_cooldown_ticks");
    /**
     * The Entity that this Entity is angry at.
     */
    public static final EntityMemory<Entity> ANGRY_AT = new EntityMemory<>(Entity.class, "angry_at");
    /**
     * Whether this Entity is universally angry.
     */
    public static final EntityMemory<Boolean> UNIVERSAL_ANGER = new EntityMemory<>(Boolean.class, "universal_anger");
    /**
     * Whether this Entity is admiring an Item.
     */
    public static final EntityMemory<Boolean> IS_ADMIRING_ITEM = new EntityMemory<>(Boolean.class, "admiring_item");
    /**
     * How much time, in ticks, left to reach the Admiring Item.
     */
    public static final EntityMemory<Integer> TIME_TO_REACH_ADMIRING_ITEM = new EntityMemory<>(Integer.class, "time_trying_to_reach_admire_item");
    /**
     * Whether this Entity should not walk to its admiring item.
     */
    public static final EntityMemory<Boolean> DISABLE_WALKING_TO_ADMIRING_ITEM = new EntityMemory<>(Boolean.class, "disable_walk_to_admire_item");
    /**
     * Whether Admiring is Disabled.
     */
    public static final EntityMemory<Boolean> ADMIRING_DISABLED = new EntityMemory<>(Boolean.class, "admiring_disabled");
    /**
     * Whether this Entity has hunted recently.
     */
    public static final EntityMemory<Boolean> HAS_HUNTED_RECENTLY = new EntityMemory<>(Boolean.class, "hunted_recently");
    /**
     * This Entity's Celebration Location.
     */
    public static final EntityMemory<Location> CELEBRATION_LOCATION = new EntityMemory<>(Location.class, "celebration_location");
    /**
     * Whether this entity is dancing.
     */
    public static final EntityMemory<Boolean> DANCING = new EntityMemory<>(Boolean.class, "dancing");
    /**
     * The Nearest Player that is not wearing Golden Armor (Used for Piglins)
     */
    public static final EntityMemory<Player> NEAREST_NONGOLD_PLAYER = new EntityMemory<>(Player.class, "nearest_visible_baby_hoglin");
    /**
     * The Nearest Zombified Piglin/Hoglin.
     */
    public static final EntityMemory<LivingEntity> NEAREST_ZOMBIFIED = new EntityMemory<>(LivingEntity.class, "nearest_visible_zombified");
    /**
     * The amount of Visible Adult Piglins.
     */
    public static final EntityMemory<Integer> ADULT_PIGLIN_COUNT = new EntityMemory<>(Integer.class, "visible_adult_piglin_count");
    /**
     * The amount of Visible Adult Hoglins.
     */
    public static final EntityMemory<Integer> ADULT_HOGLIN_COUNT = new EntityMemory<>(Integer.class, "visible_adult_hoglin_count");
    /**
     * The nearest player that is tempting this Mob.
     */
    public static final EntityMemory<Player> NEAREST_TEMPTED_PLAYER = new EntityMemory<>(Player.class, "nearest_player_holding_wanted_item");
    /**
     * The nearest Repelled for this Entity.
     */
    public static final EntityMemory<Location> NEAREST_REPELLENT = new EntityMemory<>(Location.class, "nearest_repellent");
    /**
     * Whether this Entity has Eaten Recently.
     */
    public static final EntityMemory<Boolean> ATE_RECENTLY = new EntityMemory<>(Boolean.class, "ate_recently");
    /**
     * Whether this Entity is Pacified.
     */
    public static final EntityMemory<Boolean> PACIFIED = new EntityMemory<>(Boolean.class, "pacified");
    /**
     * Represents a Mob's wanted item.
     */
    public static final EntityMemory<Item> NEAREST_WANTED_ITEM = new EntityMemory<>(Item.class, "nearest_visible_wanted_item");
    /**
     * The player that this Mob likes.
     */
    public static final EntityMemory<Player> LIKED_PLAYER = new EntityMemory<>(Player.class, "liked_player");
    /**
     * Whether this Warden is sniffing.
     */
    public static final EntityMemory<Boolean> IS_SNIFFING = new EntityMemory<>(Boolean.class, "is_sniffing");
    /**
     * Whether this Warden is emerging.
     */
    public static final EntityMemory<Boolean> IS_EMERGING = new EntityMemory<>(Boolean.class, "is_emerging");
    /**
     * This Warden's Roaring Target.
     */
    public static final EntityMemory<LivingEntity> ROAR_TARGET = new EntityMemory<>(LivingEntity.class, "roar_target");
    /**
     * This Mob's Liked Noteblock Position.
     */
    public static final EntityMemory<Location> LIKED_NOTEBLOCK = new EntityMemory<>(Location.class, "liked_noteblock");

    private final Class<T> bukkit;

    private final String key;

    private EntityMemory(Class<T> bukkit, String key) {
        this.bukkit = bukkit;
        this.key = key;
    }
    
    /**
     * Get the Bukkit Class of this EntityMemory.
     * @return Bukkit Class
     */
    @Override
    public Class<T> getBukkitClass() {
    	return this.bukkit;
    }

    /**
     * Fetches the NamespacedKey for this Memory.
     * @return NamespacedKey
     */
    @NotNull
    @Override
    public NamespacedKey getKey() {
        return NamespacedKey.minecraft(this.key);
    }

    /**
     * Fetches an EntityMemory by its NamespacedKey.
     * @param key NamespacedKey
     * @return EntityMemory found, or null if not found
     */
    @Nullable
    public static EntityMemory<?> getByKey(@NotNull NamespacedKey key) {
        for (EntityMemory<?> mem : values()) if (mem.getKey().equals(key)) return mem;

        return null;
    }

    /**
     * Fetches all the EntityMemory values.
     * @return EntityMemory values
     */
    @NotNull
    public static EntityMemory<?>[] values() {
        return Arrays.stream(EntityMemory.class.getDeclaredFields())
                .filter(f -> EntityMemory.class.isAssignableFrom(f.getType()))
                .filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))
                .map(f -> {
                    f.setAccessible(true);
                    try {
                        return (EntityMemory<?>) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(EntityMemory[]::new);
    }


    
}
