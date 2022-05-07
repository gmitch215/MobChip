package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

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
			e.printStackTrace();
			return null;
		}
	}

	static double getDouble(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getDouble(g);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	static float getFloat(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getFloat(g);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	static int getInt(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getInt(g);
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	static boolean getBoolean(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.getBoolean(g);
		} catch (Exception e) {
			e.printStackTrace();
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
		this.nmsEntity = ChipConversions.convertType(entity);
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
	public abstract Goal getHandle();
	
	/**
	 * Get the entity involved in this Pathfinder.
	 * @return Creature involved
	 */
	public final Mob getEntity() {
		return this.entity;
	}

	public final String getInternalName() {
		return getHandle().getClass().getSimpleName();
	}
	
}
