package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.schedule.Updatable;

/**
 * Represents Behavior for a Breeze
 */
public interface BreezeBehavior extends CreatureBehavior, Updatable {
    
    /**
     * Performs a Breeze Long Jump.
     * <p>This behavior requires {@link EntityMemory#ATTACK_TARGET} to be present in the brain.</p>
     */
    void longJump();

    /**
     * Performs a Breeze Shooting Attack.
     * <p>This behavior requires {@link EntityMemory#ATTACK_TARGET} to be present in the brain.</p>
     */
    void shoot();

    /**
     * Performs a Breeze Slide.
     * <p>This behavior requires {@link EntityMemory#ATTACK_TARGET} to be present in the brain.</p>
     */
    void slide();

}
