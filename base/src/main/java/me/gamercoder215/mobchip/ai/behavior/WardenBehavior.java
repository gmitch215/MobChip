package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.schedule.Updatable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Warden Behaviors
 */
public interface WardenBehavior extends CreatureBehavior, Updatable {

    /**
     * Sets the Warden's Disturbance Location.
     * <p>This behavior does not require any memories.</p>
     * @param loc Location of Disturbance
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult setDisturbanceLocation(@NotNull Location loc);

    /**
     * Makes this Warden dig into the ground.
     * <p>This method removes {@link EntityMemory#ATTACK_TARGET} and {@link EntityMemory#WALKING_TARGET} for you.</p>
     * @param duration How long the warden should dig
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult dig(int duration);

    /**
     * Makes this Warden emerge from the ground.
     * <p>This method sets {@link EntityMemory#IS_EMERGING} and removes {@link EntityMemory#WALKING_TARGET} for you.</p>
     * <p>This behavior requires {@link EntityMemory#LOOKING_TARGET} to be registered.</p>
     * @param duration How long the warden should emerge
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult emerge(int duration);

    /**
     * Makes this Warden roar.
     * <p>This behavior requires {@link EntityMemory#ROAR_TARGET} to be present, {@link EntityMemory#ATTACK_TARGET} to be absent.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult roar();

    /**
     * Makes this Warden perform a Sonic Boom.
     * <p>This behavior requires {@link EntityMemory#ATTACK_TARGET} to be present.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult sonicBoom();

    /**
     * Makes this Warden sniff for players.
     * <p>This behavior requires {@link EntityMemory#IS_SNIFFING} to be present, and the following memories to be absent:</p>
     * <ul>
     *     <li>{@link EntityMemory#ATTACK_TARGET}</li>
     *     <li>{@link EntityMemory#WALKING_TARGET}</li>
     *     <li>{@link EntityMemory#DISTURBANCE_LOCATION}</li>
     * </ul>
     * <p>This behavior also requires the following memories to be registered:</p>
     * <ul>
     *     <li>{@link EntityMemory#LOOKING_TARGET}</li>
     *     <li>{@link EntityMemory#NEAREST_ATTACKABLE}</li>
     * </ul>
     * @param duration How long to sniff, in ticks
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult sniff(int duration);

}
