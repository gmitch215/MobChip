package me.gamercoder215.mobchip.ai.goal;

/**
 * Represents information about a Pathfider
 */
public interface PathfinderInfo {
    
    /**
     * Returns this Pathfinder's Name.
     * @return Pathfinder Name
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Returns the NMS Internal Name.
     * @return Internal Name
     */
    String getInternalName();

}
