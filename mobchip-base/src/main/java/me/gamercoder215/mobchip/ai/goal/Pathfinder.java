package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftMob;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents a Pathfinder Goal of an Entity.
 * <p>
 * <strong>For custom pathfinders, extend {@link CustomPathfinder}.</strong>
 */
public abstract class Pathfinder implements PathfinderInfo {

	static PathfinderMob getEntity(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return (PathfinderMob) f.get(g);
		} catch (Exception e) {
			return null;
		}
	}
	
	protected final Mob entity;
	protected final net.minecraft.world.entity.Mob nmsEntity;
	
	protected Pathfinder(@NotNull Mob entity) {
		this.entity = entity;
		this.nmsEntity = ((CraftMob) entity).getHandle();
	}
	
	protected Pathfinder(@NotNull net.minecraft.world.entity.Mob entity) {
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
