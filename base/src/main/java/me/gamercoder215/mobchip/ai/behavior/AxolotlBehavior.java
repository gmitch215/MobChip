package me.gamercoder215.mobchip.ai.behavior;

import org.jetbrains.annotations.NotNull;

/**
 * Represents Behavior for an Axolotl
 */
public interface AxolotlBehavior {

    /**
     * Makes this Axolotl Play Dead.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult playDead();

}
