package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.FloatGoal;

/**
 * Represents a Pathfinder that mobs need to float on water
 */
public class PathfinderFloat extends Pathfinder {
	
	/**
	 * Construct a PathfinderFloat
	 * @param entity Mob to use
	 */
	public PathfinderFloat(@NotNull Mob entity) {
		super(entity);
	}

	@Override
	public FloatGoal getHandle() {
		return new FloatGoal(nmsEntity);
	}

}
