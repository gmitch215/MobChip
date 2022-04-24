package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents a Pathfinder Goal of an Entity
 */
public abstract class Pathfinder {

	protected Mob entity;
	protected net.minecraft.world.entity.Mob nmsEntity;
	
	protected Pathfinder(@NotNull Mob entity) {
		this.entity = entity;
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
	public final void setEntity(@NotNull Creature entity) {
		this.entity = entity;
		this.nmsEntity = ((CraftCreature) entity).getHandle();
	}
	
}
