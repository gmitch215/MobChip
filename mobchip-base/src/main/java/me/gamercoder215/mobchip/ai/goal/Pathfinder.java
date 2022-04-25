package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftMob;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents a Pathfinder Goal of an Entity
 */
public abstract class Pathfinder {

	static PathfinderMob getEntity(Goal g, String fieldName) {
		try {
			Field f = g.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return (PathfinderMob) f.get(g);
		} catch (Exception e) {
			return null;
		}
	}
	
	protected Mob entity;
	protected net.minecraft.world.entity.Mob nmsEntity;
	
	protected Pathfinder(@NotNull Mob entity) {
		this.entity = entity;
		this.nmsEntity = ((CraftMob) entity).getHandle();
	}
	
	protected Pathfinder(@NotNull net.minecraft.world.entity.Mob entity) {
		this.nmsEntity = entity;
		this.entity = (Mob) ChipConversions.getById(entity.getId());
	}
	
	/**
	 * Fetches the NMS Class that this Pathfinder is relating to.
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
	
	/**
	 * Sets the Creature that this Pathfinder will belong to.
	 * @param entity Creature to set
	 */
	public final void setEntity(@NotNull Mob entity) {
		this.entity = entity;
		this.nmsEntity = ((CraftMob) entity).getHandle();
	}
	
}
