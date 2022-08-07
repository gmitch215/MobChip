package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.schedule.Updatable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Warden Behaviors
 */
public interface WardenBehavior extends CreatureBehavior, Updatable {

    /**
     * Sets the Warden's Disturbance Location.
     * @param loc Location of Disturbance
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult setDisturbanceLocation(@NotNull Location loc);

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
