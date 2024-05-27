package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder to Breath Air
 */
public final class PathfinderBreathAir extends Pathfinder {
	
	/**
	 * Constructs a PathfinderBreathAir.
	 * @param c Creature to use
	 */
	public PathfinderBreathAir(@NotNull Creature c) {
		super(c);
	}

	@Override
	public @NotNull Creature getEntity() { return (Creature) entity; }

	@Override
	public @NotNull PathfinderFlag[] getFlags() {
		return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
	}

	@Override
	public String getInternalName() { return "PathfinderGoalBreath"; }
}
