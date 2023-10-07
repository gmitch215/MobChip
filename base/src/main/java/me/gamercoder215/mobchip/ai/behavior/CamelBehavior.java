package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.schedule.Updatable;

/**
 * Represents Entity Behaviors for a Camel
 */
public interface CamelBehavior extends CreatureBehavior, Updatable {

    /**
     * Makes this Camel sit.
     * <p>This behavior does not require any memories.</p>
     * @param minimalPoseTicks The minimum amount of ticks this camel should sit for
     */
    void sit(int minimalPoseTicks);

}
