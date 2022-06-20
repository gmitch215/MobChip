package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents a Pathfinder Goal of an Entity.
 * <p>
 * <strong>For custom pathfinders, extend {@link CustomPathfinder}.</strong>
 */
public abstract class Pathfinder implements PathfinderInfo {

	static net.minecraft.world.entity.Mob getEntity(Goal g, String fieldName) {
		try {
			
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			
			Object o = f.get(g);
			if (o == null && g.getClass().getSuperclass().isAssignableFrom(Goal.class)) o = getEntity((Goal) g.getClass().getSuperclass().cast(g), fieldName);
			
			return (net.minecraft.world.entity.Mob) o;
		} catch (Exception e) {
			return null;
		}
	}
	
	static <T> T getField(Goal g, String fieldName, Class<T> clazz) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return clazz.cast(f.get(g));
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return null;
		}
	}

	static double getDouble(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getDouble(g);
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return 1;
		}
	}

	static float getFloat(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getFloat(g);
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return 1;
		}
	}

	static int getInt(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getInt(g);
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return 1;
		}
	}

	static void setFinal(Goal g, String fieldName, Object value) {
		try {
			Field a = g.getClass().getDeclaredField(fieldName);
			a.setAccessible(true);

			Field modifiers = Field.class.getDeclaredField("modifiers");
			modifiers.setAccessible(true);
			modifiers.setInt(a, a.getModifiers() & ~Modifier.FINAL);

			a.set(g, value);
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
		}
	}

	static boolean getBoolean(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getBoolean(g);
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return false;
		}
	}

	static PathfinderMob getCreature(Goal g, String fieldName) {
		try {
			
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			
			Object o = f.get(g);
			if (o == null && g.getClass().getSuperclass().isAssignableFrom(Goal.class)) o = getEntity((Goal) g.getClass().getSuperclass().cast(g), fieldName);
			
			return (PathfinderMob) o;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * This Entity.
	 */
	protected final Mob entity;
	/**
	 * The Entity's NMS Class.
	 */
	protected final net.minecraft.world.entity.Mob nmsEntity;

	/**
	 * Constructs a Pathfinder.
	 * @param entity Entity to use
	 * @throws IllegalArgumentException if entity is null
	 */
	protected Pathfinder(@NotNull Mob entity) throws IllegalArgumentException {
		if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
		this.entity = entity;
		this.nmsEntity = MobChipUtil.getWrapper().convert(entity);
	}
	
	/**
	 * Constructs a Pathfinder.
	 * @param entity NMS Entity to use
	 * @throws IllegalArgumentException if entity is null
	 */
	protected Pathfinder(@NotNull net.minecraft.world.entity.Mob entity) throws IllegalArgumentException {
		if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
		this.nmsEntity = entity;
		this.entity = (Mob) entity.getBukkitEntity();
	}
	
	/**
	 * Fetches the NMS Class that this Pathfinder is relating to.
	 * <p>
	 * <strong>For custom pathfinders, extend {@link CustomPathfinder} instead.</strong>
	 * @return NMS Class
	 */
	@NotNull
	public abstract Goal getHandle();
	
	/**
	 * Get the entity involved in this Pathfinder.
	 * @return Creature involved
	 */
	@NotNull
	public final Mob getEntity() {
		return this.entity;
	}

	@Override
	public final String getInternalName() {
		return getHandle().getClass().getSimpleName();
	}

	/**
	 * Fetches a Set of Flags that this Pathfinder has.
	 * @return Set of Pathfinder Flags
	 */
	@NotNull
	public final Set<PathfinderFlag> getFlag() {
		Set<PathfinderFlag> flags = new HashSet<>();
		for (Goal.Flag f : getHandle().getFlags()) {
			for (PathfinderFlag fl : PathfinderFlag.values())
				if (f == fl.type) flags.add(fl);
		}

		return flags;
	}

    /**
     * Pathfinder Flags for a Pathfinder
     */
    public enum PathfinderFlag {
        /**
         * Flag representing that the Pathfinder will move the entity
         */
        MOVEMENT(Goal.Flag.MOVE),
        /**
         * Flag representing that the Pathfinder will target something/someone
         */
        TARGETING(Goal.Flag.TARGET),
        /**
         * Flag representing that the Pathfinder will make the entity look at something/someone
         */
        LOOKING(Goal.Flag.LOOK),
        /**
         * Flag representing that the Pathfinder involves making the entity jump
         */
        JUMPING(Goal.Flag.JUMP)
        ;

        private final Goal.Flag type;

        PathfinderFlag(Goal.Flag type) {
            this.type = type;
        }

        /**
         * Get the NMS Enum of this PathfinderFlag.
         * @return Handle
         */
        public final Goal.Flag getHandle() {
            return this.type;
        }
    }
}
