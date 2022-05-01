package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents a Pathfinder to Breath Air
 */
public final class PathfinderBreathAir extends Pathfinder {
	
	@SuppressWarnings("unused")
	private PathfinderBreathAir(Goal g) {
		this((BreathAirGoal) g);
	}
	
	/**
	 * Constructs a PathfinderBreathAir with a NMS Goal.
	 * @param g NMS Goal to use
	 */
	public PathfinderBreathAir(@NotNull BreathAirGoal g) {
		super(Pathfinder.getCreature(g, "a"));
	}
	
	/**
	 * Constructs a PathfinderBreathAir.
	 * @param c Creature to use
	 */
	public PathfinderBreathAir(@NotNull Creature c) {
		super(c);
	}

	@Override
	public BreathAirGoal getHandle() {
		return new BreathAirGoal((PathfinderMob) nmsEntity);
	}

}
