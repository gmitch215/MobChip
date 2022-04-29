package me.gamercoder215.mobchip.ai;

/**
 * Represents a Pathfinder that has a speed modifier
 * <p>
 * The number from {@link #getSpeedModifier()} will be multiplied by the original speed.
 * <p>
 * Ex: A Sheep with a Avoid Goal with a movement speed of 0.5, if the speed modifier is 2 then the sheep will move twice as fast or at a movement speed attribute of 1.
 */
public interface SpeedModifier {
	
	/**
	 * Default Speed Modifier
	 */
	float DEFAULT_SPEED_MODIFIER = 1.5F;
	
	/**
	 * Get the Speed Modifier of this Pathfinder.
	 * @return Speed Modifier
	 */
	double getSpeedModifier();
	
	/**
	 * Sets the Speed Modifier of this Pathfinder.
	 * @param mod Modifier to set
	 */
	void setSpeedModifier(double mod);
}
