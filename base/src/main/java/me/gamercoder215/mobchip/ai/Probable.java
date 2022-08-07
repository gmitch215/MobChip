package me.gamercoder215.mobchip.ai;

/**
 * Represents a Pathfinder that has a chance of succeeding
 * <p>
 * The range must be between 0F and 1F.
 */
public interface Probable {
	
	/**
	 * Get the current probability of this Pathfinder.
	 * @return Probability of this Pathfinder happening
	 */
	float getProbability();
	
	/**
	 * Sets the current probability of this Pathfinder.
	 * @param prob Probability to set
	 */
	void setProbability(float prob);
	
}
