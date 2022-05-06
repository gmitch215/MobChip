package me.gamercoder215.mobchip.ai.goal;

/**
 * Represents a Pathfinder that contains Ranged Values
 */
public interface Ranged {
	
    /**
     * Default Attack Interval, in ticks (100)
     */
    int DEFAULT_ATTACK_INTERVAL = 100;
    /**
     * Default Attack Range (6F)
     */
	float DEFAULT_ATTACK_RANGE = 6F;

	/**
	 * Default Range for Looking
	 */
	float DEFAULT_LOOK_RANGE = 5F;
	
	/**
	 * Gets the current Range.
	 * @return Current Range
	 */
	float getRange();
	
	/**
	 * Sets the current Range.
	 * @param range Range to set
	 */
	void setRange(float range);
	
}
