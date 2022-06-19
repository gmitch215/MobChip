package me.gamercoder215.mobchip.ai.memories;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Represents a Memory of an entity
 * @param <T> Type of Memory
 */
public final class EntityMemory<T> {
	
	/**
	 * Whether this Entity is not admiring another entity.
	 */
    public static final EntityMemory<Boolean> NOT_ADMIRING = new EntityMemory<>(Boolean.class, MemoryModuleType.ADMIRING_DISABLED, SELF());
    /**
     * The Celebrating Location of this Entity.
     */
    public static final EntityMemory<Location> CELEBRATING_LOCATION = new EntityMemory<>(Location.class, MemoryModuleType.CELEBRATE_LOCATION, MobChipUtil::convert);
    /**
     * This Entity's Home.
     */
    public static final EntityMemory<Location> HOME = new EntityMemory<>(Location.class, MemoryModuleType.HOME, GLOCATION());
    /**
     * The Job Site of this Entity.
     */
    public static final EntityMemory<Location> JOB_SITE = new EntityMemory<>(Location.class, MemoryModuleType.JOB_SITE, GLOCATION());
    /**
     * A Possible Job Site of this Entity.
     */
    public static final EntityMemory<Location> POSSIBLE_JOB_SITE = new EntityMemory<>(Location.class, MemoryModuleType.POTENTIAL_JOB_SITE, GLOCATION());
    /**
     * An Entity's Meeting point.
     */
    public static final EntityMemory<Location> MEETING_POINT = new EntityMemory<>(Location.class, MemoryModuleType.MEETING_POINT, GLOCATION());
	/**
	 * An Array of Locations for a possible Secondary Job Site.
	 */
    public static final EntityMemory<Location[]> SECONDARY_JOB_SITE = new EntityMemory<>(Location[].class, MemoryModuleType.SECONDARY_JOB_SITE, LIST(GLOCATION()));
    /**
     * An Array of Nearby Living Entities.
     */
    public static final EntityMemory<LivingEntity[]> NEAREST_LIVING_ENTITIES = new EntityMemory<>(LivingEntity[].class, MemoryModuleType.NEAREST_LIVING_ENTITIES, LIST(MobChipUtil::convert));
    /**
     * An Array of Visible Villager Babies.
     */
    public static final EntityMemory<Villager[]> VISIBLE_VILLAGER_BABIES = new EntityMemory<>(Villager[].class, MemoryModuleType.VISIBLE_VILLAGER_BABIES, o -> {
    	List<net.minecraft.world.entity.LivingEntity> l = new ArrayList<>();
    	
    	for (Villager vill : o) l.add(EntityMemory.<net.minecraft.world.entity.npc.Villager>getHandle(vill));
    	return l;
    });
    /**
     * An Array of Nearby Players.
     */
    public static final EntityMemory<Player[]> NEAREST_PLAYERS = new EntityMemory<>(Player[].class, MemoryModuleType.NEAREST_PLAYERS, LIST(MobChipUtil::convert));
    /**
     * The Nearest Visible Player.
     */
    public static final EntityMemory<Player> NEAREST_VISIBLE_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MobChipUtil::convert);
    /**
     * The Nearest Visible Player this entity can attack.
     */
    public static final EntityMemory<Player> NEAREST_VISIBLE_ATTACKING_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MobChipUtil::convert);
    /**
     * The Entity's Walking Target. See {@link WalkingTarget} for more information on how to set up.
     */
    public static final EntityMemory<WalkingTarget> WALKING_TARGET = new EntityMemory<>(WalkingTarget.class, MemoryModuleType.WALK_TARGET, WalkingTarget::getHandle);
    /**
     * The Entity's Looking Target.
     */
    public static final EntityMemory<Location> LOOKING_TARGET = new EntityMemory<>(Location.class, MemoryModuleType.LOOK_TARGET, loc -> new PositionTracker() {
		@Override
		public BlockPos currentBlockPosition() {
			return new BlockPos(loc.getX(), loc.getY(), loc.getZ());
		}
		@Override
		public Vec3 currentPosition() {
			return new Vec3(loc.getX(), loc.getY(), loc.getZ());
		}
		@Override
		public boolean isVisibleBy(net.minecraft.world.entity.LivingEntity arg0) {
			return true;
		}
	});
    /**
     * The Entity's Attack Target.
     */
    public static final EntityMemory<LivingEntity> ATTACK_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.ATTACK_TARGET, MobChipUtil::convert);
    /**
     * Whether this Entity's Attack is cooling down.
     */
    public static final EntityMemory<Boolean> ATTACK_COOLING_DOWN = new EntityMemory<>(Boolean.class, MemoryModuleType.ATTACK_COOLING_DOWN, SELF());
    /**
     * The Entity's Interaction Target.
     */
    public static final EntityMemory<LivingEntity> INTERACTION_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.INTERACTION_TARGET, MobChipUtil::convert);
    /**
     * The Entity's Breeding Target.
     */
    public static final EntityMemory<Ageable> BREEDING_TARGET = new EntityMemory<>(Ageable.class, MemoryModuleType.BREED_TARGET, MobChipUtil::convert);
    /**
     * The Entity's Riding Target.
     */
    public static final EntityMemory<Entity> RIDING_TARGET = new EntityMemory<>(Entity.class, MemoryModuleType.RIDE_TARGET, MobChipUtil::convert);
    /**
     * An Array of Doors that are interactable (will ignore blocks without BlockData {@link Door})
     */
    public static final EntityMemory<Block[]> INTERACTABLE_DOORS = new EntityMemory<>(Block[].class, MemoryModuleType.INTERACTABLE_DOORS, o -> {
    	List<GlobalPos> b = new ArrayList<>();
    	for (Block bl : o) if (bl.getBlockData() instanceof Door) b.add(GLOCATION().apply(bl.getLocation()));
    	
    	return b;
    });
    /**
     * An Array of Doors to close (will ignore blocks without BlockData {@link Door})
     */
    public static final EntityMemory<Block[]> DOORS_TO_CLOSE = new EntityMemory<>(Block[].class, MemoryModuleType.DOORS_TO_CLOSE, o -> {
    	Set<GlobalPos> b = new HashSet<>();
    	for (Block bl : o) if (bl.getBlockData() instanceof Door) b.add(GLOCATION().apply(bl.getLocation()));
    	
    	return b;
    });
    /**
     * The nearest bed available for this Entity.
     */
    public static final EntityMemory<Location> NEAREST_BED = new EntityMemory<>(Location.class, MemoryModuleType.NEAREST_BED, MobChipUtil::convert);
    /**
     * The Last DamageCause of this Entity.
     */
    public static final EntityMemory<DamageCause> LAST_HURT_CAUSE = new EntityMemory<>(DamageCause.class, MemoryModuleType.HURT_BY, MobChipUtil::convert);
    /**
     * The Last entity that caused this Entity Damage.
     */
    public static final EntityMemory<LivingEntity> LAST_HURT_ENTITY = new EntityMemory<>(LivingEntity.class, MemoryModuleType.HURT_BY_ENTITY, MobChipUtil::convert);
    /**
     * The Entity to avoid.
     */
    public static final EntityMemory<LivingEntity> AVOID_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.AVOID_TARGET, MobChipUtil::convert);
    /**
     * The Nearest Hostile Living Entity.
     */
    public static final EntityMemory<LivingEntity> NEAREST_HOSTILE = new EntityMemory<>(LivingEntity.class, MemoryModuleType.NEAREST_HOSTILE, MobChipUtil::convert);
    /**
     * The Nearest Living Entity that this Mob can attack.
     */
    public static final EntityMemory<LivingEntity> NEAREST_ATTACKABLE = new EntityMemory<>(LivingEntity.class, MemoryModuleType.NEAREST_ATTACKABLE, MobChipUtil::convert);
    /**
     * A Hiding Place for this Entity during a Raid.
     */
    public static final EntityMemory<Location> HIDING_PLACE = new EntityMemory<>(Location.class, MemoryModuleType.HIDING_PLACE, GLOCATION());
    /**
     * The duration, in ticks, of how long this Mob has heard a bell.
     */
    public static final EntityMemory<Long> HEARD_BELL_DURATION = new EntityMemory<>(Long.class, MemoryModuleType.HEARD_BELL_TIME, SELF());
    /**
     * The last timestamp this Mob has failed to reach their Walking Target.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_FAILURE_WALK_TARGET = new EntityMemory<>(Long.class, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, SELF());
    /**
     * Whether a Golem has been <strong>recently</strong> detected.
     */
    public static final EntityMemory<Boolean> DETECTED_GOLEM = new EntityMemory<>(Boolean.class, MemoryModuleType.GOLEM_DETECTED_RECENTLY, SELF());
    /**
     * The last timestamp this Mob has slept.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_SLEPT = new EntityMemory<>(Long.class, MemoryModuleType.LAST_SLEPT, SELF());
    /**
     * The last timestamp this Mob has woken up.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_WOKEN = new EntityMemory<>(Long.class, MemoryModuleType.LAST_WOKEN, SELF());
    /**
     * The last timestamp this Mob has worked.
     * <p>
     * This Memory uses <strong>absolute</strong> in-game time. See {@link World#getFullTime()} for more information.
     */
    public static final EntityMemory<Long> LAST_WORKED = new EntityMemory<>(Long.class, MemoryModuleType.LAST_WORKED_AT_POI, SELF());
    /**
     * The nearest visible Adult for this Mob.
     */
    public static final EntityMemory<Ageable> NEAREST_VISIBLE_ADULT = new EntityMemory<>(Ageable.class, MemoryModuleType.NEAREST_VISIBLE_ADULT, MobChipUtil::convert);
    /**
     * How many ticks this Mob has played dead.
     */
    public static final EntityMemory<Integer> TICKS_PLAY_DEAD = new EntityMemory<>(Integer.class, MemoryModuleType.PLAY_DEAD_TICKS, SELF());
    /**
     * The Player this Mob is tempting.
     */
    public static final EntityMemory<Player> TEMPTING_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.TEMPTING_PLAYER, MobChipUtil::convert);
    /**
     * The cooldown, in ticks, of this Mob's tempting.
     */
    public static final EntityMemory<Integer> TEMPING_COOLDOWN = new EntityMemory<>(Integer.class, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, SELF());
    /**
     * Whether this Mob is being tempted.
     */
    public static final EntityMemory<Boolean> IS_TEMPTED = new EntityMemory<>(Boolean.class, MemoryModuleType.IS_TEMPTED, SELF());
    /**
     * The Entity's Long Jump Cooldown, in ticks
     */
    public static final EntityMemory<Integer> LONG_JUMP_COOLDOWN = new EntityMemory<>(Integer.class, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, SELF());
    /**
     * Whether this Entity is currently doing its Long Jump.
     */
    public static final EntityMemory<Boolean> IS_LONG_JUMP = new EntityMemory<>(Boolean.class, MemoryModuleType.LONG_JUMP_MID_JUMP, SELF());
    /**
     * Whether this Entity has a HUntying Cooldown
     */
    public static final EntityMemory<Boolean> HUNTING_COOLDOWN = new EntityMemory<>(Boolean.class, MemoryModuleType.HAS_HUNTING_COOLDOWN, SELF());
    /**
     * This entity's Ram Cooldown (Typically for Ravagers).
     */
    public static final EntityMemory<Integer> RAM_COOLDOWN = new EntityMemory<>(Integer.class, MemoryModuleType.RAM_COOLDOWN_TICKS, SELF());
    /**
     * The Entity that this Entity is angry at.
     */
    public static final EntityMemory<Entity> ANGRY_AT = new EntityMemory<>(Entity.class, MemoryModuleType.ANGRY_AT, Entity::getUniqueId);
    /**
     * Whether this Entity is universally angry.
     */
    public static final EntityMemory<Boolean> UNIVERSAL_ANGER = new EntityMemory<>(Boolean.class, MemoryModuleType.UNIVERSAL_ANGER, SELF());
    /**
     * Whether this Entity is admiring an Item.
     */
    public static final EntityMemory<Boolean> IS_ADMIRING_ITEM = new EntityMemory<>(Boolean.class, MemoryModuleType.ADMIRING_ITEM, SELF());
    /**
     * How much time, in ticks, left to reach the Admiring Item.
     */
    public static final EntityMemory<Integer> TIME_TO_REACH_ADMIRING_ITEM = new EntityMemory<>(Integer.class, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, SELF());
    /**
     * Whether this Entity should walk to its admiring item.
     */
    public static final EntityMemory<Boolean> WALKING_TO_ADMIRING_ITEM = new EntityMemory<>(Boolean.class, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, b -> !b);
    /**
     * Whether Admiring is Disabled.
     */
    public static final EntityMemory<Boolean> ADMIRING_DISABLED = new EntityMemory<>(Boolean.class, MemoryModuleType.ADMIRING_DISABLED, SELF());
    /**
     * Whether this Entity has hunted recently.
     */
    public static final EntityMemory<Boolean> HAS_HUNTED_RECENTLY = new EntityMemory<>(Boolean.class, MemoryModuleType.HUNTED_RECENTLY, SELF());
    /**
     * This Entity's Celebration Location.
     */
    public static final EntityMemory<Location> CELEBRATION_LOCATION = new EntityMemory<>(Location.class, MemoryModuleType.CELEBRATE_LOCATION, MobChipUtil::convert);
    /**
     * Whether this entity is dancing.
     */
    public static final EntityMemory<Boolean> DANCING = new EntityMemory<>(Boolean.class, MemoryModuleType.DANCING, SELF());
    /**
     * The Nearest Huntable Hoglin for this Entity (Used for Piglins).
     */
    public static final EntityMemory<Hoglin> NEAREST_HUNTABLE_HOGLIN = new EntityMemory<>(Hoglin.class, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MobChipUtil::convert);
    /**
     * The Nearest Baby Hoglin for this Entity (Used for Piglins)
     */
    public static final EntityMemory<Hoglin> NEAREST_BABY_HOGLIN = new EntityMemory<>(Hoglin.class, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MobChipUtil::convert);
    /**
     * The Nearest Player that is not wearing Golden Armor (Used for Piglins)
     */
    public static final EntityMemory<Player> NEAREST_NONGOLD_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MobChipUtil::convert);
    /**
     * An Array of Nearby adult Piglins.
     */
    public static final EntityMemory<Piglin[]> NEARBY_ADULT_PIGLINS = new EntityMemory<>(Piglin[].class, MemoryModuleType.NEARBY_ADULT_PIGLINS, LIST(MobChipUtil::convert));
    /**
     * An Array of Nearby and Visible adult Piglins.
     */
    public static final EntityMemory<Piglin[]> NEARBY_VISIBLE_ADULT_PIGLINS = new EntityMemory<>(Piglin[].class, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, LIST(MobChipUtil::convert));
    /**
     * The Nearest Adult Piglin.
     */
    public static final EntityMemory<Piglin> NEAREST_ADULT_PIGLIN = new EntityMemory<>(Piglin.class, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, MobChipUtil::convert);
    /**
     * The Nearest Zombified LivingEntity.
     */
    public static final EntityMemory<LivingEntity> NEAREST_ZOMBIFIED = new EntityMemory<>(LivingEntity.class, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MobChipUtil::convert);
    /**
     * The amount of Visible Adult Piglins.
     */
    public static final EntityMemory<Integer> ADULT_PIGLIN_COUNT = new EntityMemory<>(Integer.class, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, SELF());
    /**
     * The amount of Visible Adult Hoglins.
     */
    public static final EntityMemory<Integer> ADULT_HOGLIN_COUNT = new EntityMemory<>(Integer.class, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, SELF());
    /**
     * The amount of Visible Adult Piglins.
     */
    public static final EntityMemory<Player> NEAREST_TEMPTED_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MobChipUtil::convert);
    /**
     * The nearest Repelled for this Entity.
     */
    public static final EntityMemory<Location> NEAREST_REPELLENT = new EntityMemory<>(Location.class, MemoryModuleType.NEAREST_REPELLENT, MobChipUtil::convert);
    /**
     * Whether this Entity has Eaten Recently.
     */
    public static final EntityMemory<Boolean> ATE_RECENTLY = new EntityMemory<>(Boolean.class, MemoryModuleType.ATE_RECENTLY, SELF());
    /**
     * Whether this Entity is Pacified.
     */
    public static final EntityMemory<Boolean> PACIFIED = new EntityMemory<>(Boolean.class, MemoryModuleType.PACIFIED, SELF());

	@SuppressWarnings("unchecked")
	private static <T> T getHandle(Object o) {
		try {
			return (T) o.getClass().getMethod("getHandle").invoke(o);
		} catch (Exception e) {
			return null;
		}
	}
    
	/**
	 * Represents a Memory Type that has a complex construction
	 */
	public interface ComplexMemoryType {
		
		/**
		 * Get the Handle of this ComplexMemoryType.
		 * @return Handle
		 */
		@NotNull Object getHandle();
		
	}

    /**
     * Represents a Memory Type of Walking Target
     *
     */
    public static final class WalkingTarget implements SpeedModifier, ComplexMemoryType {
    
    	private final Location loc;
    	
    	private float speedMod;
    	private int distance;

        /**
         * Constructs a WalkingTarget.
         * @param loc Location of target
         * @param speedMod Speed Modifier
         * @param distance Distance needed to stop walking
         */
    	public WalkingTarget(@NotNull Location loc, float speedMod, int distance) {
    		this.loc = loc;
    		this.speedMod = speedMod;
    		this.distance = distance;
    	}

        /**
         * Constructs a WalkingTarget with the default speed modifier.
         * @param loc Location of target
         * @param distance Distance needed to stop walking
         */
    	public WalkingTarget(@NotNull Location loc, int distance) {
    		this(loc, DEFAULT_SPEED_MODIFIER, distance);
    	}
    	
    	/**
    	 * Get the Location that this WalkingTarget is looking for.
    	 * @return Location looking for
    	 */
    	@NotNull
    	public Location getLocation() {
    		return this.loc;
    	}
    	
		@Override
		public double getSpeedModifier() {
			return this.speedMod;
		}
		
		/**
		 * Fetches the current closest enough distance.
		 * @return Closest Enough Distance
		 */
		public int getDistance() {
			return this.distance;
		}
		
		/**
		 * Sets the current closest enough distance.
		 * @param distance Distance to set
		 */
		public void setDistance(int distance) {
			this.distance = distance;
		}
		
		/**
		 * @throws IllegalArgumentException if greater than {@link Float#MAX_VALUE}
		 */
		@Override
		public void setSpeedModifier(double mod) throws IllegalArgumentException {
			if (mod > Float.MAX_VALUE) throw new IllegalArgumentException("Float Speed Modifier");
			this.speedMod = (float) mod;
		}
		
		@Override
        public @NotNull WalkTarget getHandle() {
			return new WalkTarget(MobChipUtil.convert(loc), speedMod, distance);
		}
    	
    }
    
	private static <T, E> Function<T[], List<E>> LIST(Function<T, E> converter) {
    	return o -> {
    		List<E> tList = new ArrayList<>();
    		for (T e : o) try { tList.add(converter.apply(e)); } catch (ClassCastException ignored) {
            }

            return tList;
    	};
    }
    
    private static Function<Location, GlobalPos> GLOCATION() {
        return l -> GlobalPos.of(MobChipUtil.convert(l.getWorld()).dimension(), MobChipUtil.convert(l));
    }
    
    private static <T> Function<T, T> SELF() {
    	return o -> o;
    }

    private final Class<T> bukkit;
    private final MemoryModuleType<?> handle;
    private final Function<T, ?> convert;

    private <N> EntityMemory(Class<T> bukkit, MemoryModuleType<N> handle, Function<T, N> convert) {
        this.bukkit = bukkit;
        this.handle = handle;
        this.convert = convert;
    }

	/**
     * Get the Handle of this EntityMemory.
     * @return EntityMemory handle
     */
    @SuppressWarnings("rawtypes")
    public MemoryModuleType getHandle() {
        return this.handle;
    }
    
    /**
     * Get the Bukkit Class of this EntityMemory.
     * @return Bukkit Class
     */
    public Class<T> getBukkitClass() {
    	return this.bukkit;
    }
    
    /**
     * Converts a Bukkit type to a NMS Type.
     * @param obj Object to convert
     * @param clazz NMS Class
     * @deprecated Internal use only
     * @param <E> NMS Type
     * @return Converted NMS Object
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public <E> E convert(T obj, Class<E> clazz) {
        if (!(clazz.isInstance(obj))) return null;

        return (E) convert.apply(obj);
    }
    
    /**
     * Converts a Bukkit type to a NMS Type
     * @param obj Object to convert
     * @deprecated Internal use only
     * @return Converted Object
     */
    @Deprecated
    public Object convert(T obj) {
        return convert.apply(obj);
    }

    
}
