package me.gamercoder215.mobchip.entity.ai.goal;

/**
 * Represents a PathfinderGoal involving movement
 */
public interface MovementGoal {
	
	/**
	 * Get the Speed Modifier that will be applied from this goal.
	 * @return speed modifier
	 */
	double getSpeedModifier();
	
}
