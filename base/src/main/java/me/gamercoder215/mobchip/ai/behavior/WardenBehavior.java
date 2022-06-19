package me.gamercoder215.mobchip.ai.behavior;

import org.jetbrains.annotations.NotNull;

/**
 * Represents Warden Behaviors
 */
public interface WardenBehavior extends CreatureBehavior {

    /**
     * Makes this Warden dig into the ground.
     * @param duration How long the warden should dig
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult dig(int duration);

    /**
     * Makes this Warden emerge from the ground.
     * @param duration How long the warden should emerge
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult emerge(int duration);

    /**
     * Makes this Warden roar.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult roar();

    /**
     * Makes this Warden perform a Sonic Boom.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult sonicBoom();

    /**
     * Makes this Warden sniff for players.
     * @param duration How long to sniff, in ticks
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult sniff(int duration);

}
