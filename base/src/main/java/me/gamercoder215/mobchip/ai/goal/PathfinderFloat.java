package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that mobs need to float on water
 */
public final class PathfinderFloat extends Pathfinder {

	/**
	 * Construct a PathfinderFloat
	 * @param entity Mob to use
	 */
	public PathfinderFloat(@NotNull Mob entity) {
		super(entity);
	}

	@Override
	public @NotNull PathfinderFlag[] getFlags() {
		return new PathfinderFlag[] { PathfinderFlag.JUMPING };
	}


	@Override
	public String getInternalName() {
		return "PathfinderGoalFloat";
	}
}
