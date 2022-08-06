package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.schedule.Updatable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Behavior for an Axolotl
 */
public interface AxolotlBehavior extends CreatureBehavior, Updatable {

    /**
     * Makes this Axolotl Play Dead.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult playDead();

}
