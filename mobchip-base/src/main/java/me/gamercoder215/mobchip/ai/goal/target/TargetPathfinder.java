package me.gamercoder215.mobchip.ai.goal.target;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

/**
 * Represents a Pathfinder that involves targeting.
 * <p>
 * <strong>For custom Pathfinders, extend {@link CustomPathfinder}.</strong>
 */
public abstract class TargetPathfinder extends Pathfinder {


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
     * Constructs a TargetPathfinder.
     * @param m Mob to use
     */
    public TargetPathfinder(@NotNull Mob m) {
        super(m);
    } 

    /**
     * Constructs a TargetPathfinder.
     * @param m NMS Mob to use
     */
    public TargetPathfinder(@NotNull net.minecraft.world.entity.Mob m) {
        super(m);
    } 

    @Override
    public abstract TargetGoal getHandle();

}
