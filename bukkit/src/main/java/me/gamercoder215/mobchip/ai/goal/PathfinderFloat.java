package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that mobs need to float on water
 */
public class PathfinderFloat extends Pathfinder {

	/**
	 * Constructs a PathfinderFloat from a NMS FloatGoal
	 * @param g Goal to use
	 */
	public PathfinderFloat(@NotNull FloatGoal g) {
		super(Pathfinder.getEntity(g, "a"));
	}

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
