package me.gamercoder215.mobchip.ai.memories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

/**
 * Represents a Memory of an entity
 */
public final class EntityMemory<T> {
	
	/**
	 * Whether or not this Entity is not admiring another entity.
	 */
    public static final EntityMemory<Boolean> NOT_ADMIRING = new EntityMemory<>(Boolean.class, MemoryModuleType.ADMIRING_DISABLED, SELF());
    /**
     * The Celebrating Location of this Entity.
     */
    public static final EntityMemory<Location> CELEBRATING_LOCATION = new EntityMemory<>(Location.class, MemoryModuleType.CELEBRATE_LOCATION, ChipConversions::convertType);
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
    public static final EntityMemory<LivingEntity[]> NEAREST_LIVING_ENTITIES = new EntityMemory<>(LivingEntity[].class, MemoryModuleType.NEAREST_LIVING_ENTITIES, LIST(ChipConversions::convertType));
    /**
     * An Array of Nearby Visible Living Entities.
     */
    public static final EntityMemory<NearestVisibleEntities> NEAREST_VISIBLE_LIVING_ENTITIES = new EntityMemory<>(NearestVisibleEntities.class, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, o -> { return o.getHandle();});
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
    public static final EntityMemory<Player[]> NEAREST_PLAYERS = new EntityMemory<>(Player[].class, MemoryModuleType.NEAREST_PLAYERS, LIST(ChipConversions::convertType));
    /**
     * The Nearest Visible Player.
     */
    public static final EntityMemory<Player> NEAREST_VISIBLE_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_VISIBLE_PLAYER, ChipConversions::convertType);
    /**
     * The Nearest Visible Player this entity can attack.
     */
    public static final EntityMemory<Player> NEAREST_VISIBLE_ATTACKING_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, ChipConversions::convertType);
    /**
     * The Entity's Walking Target. See {@link WalkingTarget} for more information on how to set up.
     */
    public static final EntityMemory<WalkingTarget> WALKING_TARGET = new EntityMemory<>(WalkingTarget.class, MemoryModuleType.WALK_TARGET, o -> { return o.getHandle(); });
    /**
     * The Entity's Looking Target.
     */
    public static final EntityMemory<Location> LOOKING_TARGET = new EntityMemory<>(Location.class, MemoryModuleType.LOOK_TARGET, loc -> { return new PositionTracker() {
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
	}; });
    /**
     * The Entity's Attack Target.
     */
    public static final EntityMemory<LivingEntity> ATTACK_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.ATTACK_TARGET, ChipConversions::convertType);
    /**
     * Whether or not this Entity's Attack is cooling down.
     */
    public static final EntityMemory<Boolean> ATTACK_COOLING_DOWN = new EntityMemory<>(Boolean.class, MemoryModuleType.ATTACK_COOLING_DOWN, SELF());
    /**
     * The Entity's Interaction Target.
     */
    public static final EntityMemory<LivingEntity> INTERACTION_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.INTERACTION_TARGET, ChipConversions::convertType);
    /**
     * The Entity's Breeding Target.
     */
    public static final EntityMemory<Ageable> BREEDING_TARGET = new EntityMemory<>(Ageable.class, MemoryModuleType.BREED_TARGET, ChipConversions::convertType);
    /**
     * The Entity's Riding Target.
     */
    public static final EntityMemory<Entity> RIDING_TARGET = new EntityMemory<>(Entity.class, MemoryModuleType.RIDE_TARGET, ChipConversions::convertType);
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
    public static final EntityMemory<Location> NEAREST_BED = new EntityMemory<>(Location.class, MemoryModuleType.NEAREST_BED, ChipConversions::convertType);
    /**
     * The Last DamageCause of this Entity.
     */
    public static final EntityMemory<DamageCause> LAST_HURT_CAUSE = new EntityMemory<>(DamageCause.class, MemoryModuleType.HURT_BY, ChipConversions::convertType);
    /**
     * The Last entity that caused this Entity Damage.
     */
    public static final EntityMemory<LivingEntity> LAST_HURT_ENTITY = new EntityMemory<>(LivingEntity.class, MemoryModuleType.HURT_BY_ENTITY, ChipConversions::convertType);
    /**
     * The Avoid Target of this Entity.
     */
    public static final EntityMemory<LivingEntity> AVOID_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.AVOID_TARGET, ChipConversions::convertType);
    /**
     * The Nearest Hostile Living Entity.
     */
    public static final EntityMemory<LivingEntity> NEAREST_HOSTILE = new EntityMemory<>(LivingEntity.class, MemoryModuleType.NEAREST_HOSTILE, ChipConversions::convertType);
    /**
     * The Nearest Living Entity that this Mob can attack.
     */
    public static final EntityMemory<LivingEntity> NEAREST_ATTACKABLE = new EntityMemory<>(LivingEntity.class, MemoryModuleType.NEAREST_ATTACKABLE, ChipConversions::convertType);
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
     * Whether or not a Golem has been <strong>recently</strong> detected.
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
    public static final EntityMemory<Ageable> NEAREST_VISIBLE_ADULT = new EntityMemory<>(Ageable.class, MemoryModuleType.NEAREST_VISIBLE_ADULT, ChipConversions::convertType);
    /**
     * How many ticks this Mob has played dead.
     */
    public static final EntityMemory<Integer> TICKS_PLAY_DEAD = new EntityMemory<>(Integer.class, MemoryModuleType.PLAY_DEAD_TICKS, SELF());
    /**
     * The Player this Mob is tempting.
     */
    public static final EntityMemory<Player> TEMPTING_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.TEMPTING_PLAYER, ChipConversions::convertType);
    /**
     * The cooldown, in ticks, of this Mob's tempting.
     */
    public static final EntityMemory<Integer> TEMPING_COOLDOWN = new EntityMemory<>(Integer.class, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, SELF());
    /**
     * Whether or not this Mob is being tempted.
     */
    public static final EntityMemory<Boolean> IS_TEMPTED = new EntityMemory<>(Boolean.class, MemoryModuleType.IS_TEMPTED, SELF());
    
    
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
	public static interface ComplexMemoryType {
		
		/**
		 * Get the Handle of this ComplexMemoryType.
		 * @return Handle
		 */
		@NotNull Object getHandle();
		
	}
	
    /**
     * Represents a Memory Type of NearestVisibleEntities
     */
    public static final class NearestVisibleEntities implements ComplexMemoryType {
    
    	private final LivingEntity entity;
    	private final List<LivingEntity> nearest;
    	
    	/**
    	 * Construct a NearestVisibleEntities 
    	 * @param entity The Entity to Use
    	 * @param nearest A List of Nearby Entities
    	 */
    	public NearestVisibleEntities(@NotNull LivingEntity entity, @NotNull List<LivingEntity> nearest) {
    		this.entity = entity;
    		this.nearest = nearest;
    	}
    	
    	/**
    	 * Get the Handle of this NearestVisibleEntities.
    	 */
    	@NotNull
    	public final NearestVisibleLivingEntities getHandle() {
    	 	return new NearestVisibleLivingEntities(ChipConversions.convertType(entity), ChipConversions.convertType(nearest));
    	}
    	
    }
    
    /**
     * Represents a Memory Type of a Walking Target
     *
     */
    public static final class WalkingTarget implements SpeedModifier, ComplexMemoryType {
    
    	private final Location loc;
    	
    	private float speedMod;
    	private int distance;
    	
    	public WalkingTarget(@NotNull Location loc, float speedMod, int distance) {
    		this.loc = loc;
    		this.speedMod = speedMod;
    		this.distance = distance;
    	}
    	public WalkingTarget(@NotNull Location loc, int distance) {
    		this(loc, DEFAULT_SPEED_MODIFIER, distance);
    	}
    	
    	/**
    	 * Get the Location that this WalkingTarget is looking for.
    	 * @return
    	 */
    	@NotNull
    	public final Location getLocation() {
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
		
		public WalkTarget getHandle() {
			return new WalkTarget(ChipConversions.convertType(loc), speedMod, distance);
		}
    	
    }
    
	private static final <T, E> Function<T[], List<E>> LIST(Function<T, E> converter) {
    	return o -> {
    		List<E> tList = new ArrayList<>();
    		for (T e : o) try { tList.add(converter.apply(e)); } catch (ClassCastException err) { continue; };
    		
    		return tList;
    	};
    }
    
    private static final Function<Location, GlobalPos> GLOCATION() {
        return l -> {
            return GlobalPos.of(ChipConversions.convertType(l.getWorld()).dimension(), ChipConversions.convertType(l));
        };
    }
    
    private static final <T> Function<T, T> SELF() {
    	return o -> { return o; };
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
    public final MemoryModuleType getHandle() {
        return this.handle;
    }
    
    /**
     * Get the Bukkit Class of this EntityMemory.
     * @return Bukkit Class
     */
    public final Class<T> getBukkitClass() {
    	return this.bukkit;
    }
    
    /**
     * @deprecated Internal use only
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public <E> E convert(T obj, Class<E> clazz) {
        if (!(clazz.isInstance(obj))) return null;

        return (E) convert.apply(obj);
    }
    
    /**
     * @deprecated Internal use only
     */
    public Object convert(T obj) {
        return convert.apply(obj);
    }

    
}
