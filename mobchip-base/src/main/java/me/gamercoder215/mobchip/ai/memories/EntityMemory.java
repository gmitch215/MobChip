package me.gamercoder215.mobchip.ai.memories;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
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
    public static final EntityMemory<Boolean> NOT_ADMIRING = new EntityMemory<>(Boolean.class, MemoryModuleType.ADMIRING_DISABLED, BOOLEAN());
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
	
    public static final EntityMemory<Location[]> SECONDARY_JOB_SITE = new EntityMemory<>(Location[].class, MemoryModuleType.SECONDARY_JOB_SITE, LIST(GLOCATION()));
    public static final EntityMemory<LivingEntity[]> NEAREST_LIVING_ENTITIES = new EntityMemory<>(LivingEntity[].class, MemoryModuleType.NEAREST_LIVING_ENTITIES, LIST(ChipConversions::convertType));
    public static final EntityMemory<NearestVisibleEntities> NEAREST_VISIBLE_LIVING_ENTITIES = new EntityMemory<>(NearestVisibleEntities.class, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, o -> { return o.getHandle();});
    public static final EntityMemory<Villager[]> VISIBLE_VILLAGER_BABIES = new EntityMemory<>(Villager[].class, MemoryModuleType.VISIBLE_VILLAGER_BABIES, o -> {
    	List<net.minecraft.world.entity.LivingEntity> l = new ArrayList<>();
    	
    	for (Villager vill : o) l.add(EntityMemory.<net.minecraft.world.entity.npc.Villager>getHandle(vill));
    	return l;
    });
    public static final EntityMemory<Player[]> NEAREST_PLAYERS = new EntityMemory<>(Player[].class, MemoryModuleType.NEAREST_PLAYERS, LIST(ChipConversions::convertType));
    public static final EntityMemory<Player> NEAREST_VISIBLE_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_VISIBLE_PLAYER, ChipConversions::convertType);
    public static final EntityMemory<Player> NEAREST_VISIBLE_ATTACKING_PLAYER = new EntityMemory<>(Player.class, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, ChipConversions::convertType);
    public static final EntityMemory<WalkingTarget> WALKING_TARGET = new EntityMemory<>(WalkingTarget.class, MemoryModuleType.WALK_TARGET, o -> { return o.getHandle(); });
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
    
    public static final EntityMemory<LivingEntity> ATTACK_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.ATTACK_TARGET, ChipConversions::convertType);
    public static final EntityMemory<Boolean> ATTACK_COOLING_DOWN = new EntityMemory<>(Boolean.class, MemoryModuleType.ATTACK_COOLING_DOWN, BOOLEAN());
    public static final EntityMemory<LivingEntity> INTERACTION_TARGET = new EntityMemory<>(LivingEntity.class, MemoryModuleType.INTERACTION_TARGET, ChipConversions::convertType);
    
    
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
    	 * @param entity
    	 * @param nearest
    	 */
    	public NearestVisibleEntities(@NotNull LivingEntity entity, @NotNull List<LivingEntity> nearest) {
    		this.entity = entity;
    		this.nearest = nearest;
    	}
    	
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
    
    private static final <T> Function<T, GlobalPos> GLOCATION() {
        return o -> {
            if (!(o instanceof Location l)) return null;
            return GlobalPos.of(ChipConversions.convertType(l.getWorld()).dimension(), ChipConversions.convertType(l));
        };
    }


    private static final <T> Function<T, Boolean> BOOLEAN() {
        return o -> {
            if (!(o instanceof Boolean b)) return null;
            return b;
        };
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
