package me.gamercoder215.mobchip.ai.goal.target;

/**
 * Represents a target of a Pathfinder
 */
public interface Targeting {
    
    /**
     * Whether the entity must see the target.
     * @return true if entity must see, else false
     */
    boolean mustSee();

    /**
     * Sets whether the entity must see the target.
     * @param see true if entity must see, else false
     */
    void setSee(boolean see);

}
