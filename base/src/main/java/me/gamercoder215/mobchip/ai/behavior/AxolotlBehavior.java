package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.schedule.Updatable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Behavior for an Axolotl
 */
public interface AxolotlBehavior extends CreatureBehavior, Updatable {

    /**
     * Makes this Axolotl Play Dead.
     * <p>This behavior requires {@link EntityMemory#TICKS_PLAY_DEAD} and {@link EntityMemory#LAST_HURT_ENTITY} to be present in the brain.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult playDead();

}
