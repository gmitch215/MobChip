package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents Entity Behavior.
 * <p>
 * Some of these may require {@link EntityMemory}(ies) to be present or not present, so calling some of these methods will not always work.
 */
public interface EntityBehavior {

    /**
     * Makes this Mob back up if it is too close.
     * @param minDistance Minimum Distance to back up
     * @param speedModifier Speed Modifier while backing up
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult backupIfClose(int minDistance, float speedModifier);

    /**
     * Makes this Mob back up if it is too close, with no speed modifier.
     * @param minDistance Minimum Distance to back up
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult backupIfClose(int minDistance) {
        return backupIfClose(minDistance, 1);
    }

    /**
     * Makes this Entity become Passive is a Memory is present.
     * @param memory Memory to use
     * @param durationTicks How long to be passive, in ticks
     * @return Result of Behavior
     * @throws IllegalArgumentException if memory is null
     */
    @NotNull BehaviorResult passiveIf(@NotNull EntityMemory<?> memory, int durationTicks) throws IllegalArgumentException;

    /**
     * Erases a memory if function returns true.
     * @param function Function to test containing Mob
     * @param memory Memory to remove if function returns true
     * @return Result of Behavior
     * @throws IllegalArgumentException if function or memory is null
     */
    @NotNull BehaviorResult eraseIf(@NotNull Predicate<Mob> function, @NotNull EntityMemory<?> memory) throws IllegalArgumentException;

    /**
     * Moves this Mob to a Village's Celebration Location for winning a raid.
     * @param minDist Minimum Distance to location
     * @param speedMod Speed Modifier while moving
     * @return Result of Behavior
     */
    @NotNull BehaviorResult moveToCelebrateLocation(int minDist, float speedMod);

    /**
     * Moves this Mob to a Village's Celebration Location for winning a raid, with no speed modifier.
     * @param minDist Minimum Distance to location
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult moveToCelebrateLocation(int minDist) {
        return moveToCelebrateLocation(minDist, 1);
    }

    /**
     * Makes this Entity move to its wanted item.
     * @param minDist Minimum Distance from Item
     * @param speedMod Speed Modifier while moving
     * @param requireTarget Whether a {@link EntityMemory#WALKING_TARGET} is required (if false, requires there to not be one, defaults to true)
     * @return Result of Behavior
     */
    @NotNull BehaviorResult moveToWantedItem(int minDist, float speedMod, boolean requireTarget);

    /**
     * Makes this Entity move to its wanted item, with {@code requireTarget} set to true.
     * @param minDist Minimum Distance from Item
     * @param speedMod Speed Modifier while moving
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult moveToWantedItem(int minDist, float speedMod) {
        return moveToWantedItem(minDist, speedMod, true);
    }

    /**
     * Makes this Entity move to its wanted item, with no speed modifier.
     * @param minDist Minimum Distance from Item
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult moveToWantedItem(int minDist) {
        return moveToWantedItem(minDist, 1);
    }

    /**
     * Makes this Mob jump on a Bed.
     * @param speedMod Speed Modifier while locating a bed
     * @return Result of Behavior
     */
    @NotNull BehaviorResult jumpOnBed(float speedMod);

    /**
     * Makes this Mob jump on a Bed, with no speed Modifier.
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult jumpOnBed() {
        return jumpOnBed(1);
    }

    /**
     * Makes this Mob perform a Melee Attack.
     * @param cooldown Cooldown, in ticks, before the next attack
     * @return Result of Behavior
     */
    @NotNull BehaviorResult meleeAttack(int cooldown);

    /**
     * Makes this Mob perform a Melee Attack, with a cooldown of 3 seconds (60 ticks).
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult meleeAttack() {
        return meleeAttack(60);
    }

    /**
     * Wakes up this Mob if it is sleeping.
     * @return Result of Behavior
     */
    @NotNull BehaviorResult wakeUp();

    /**
     * Makes this Mob ring the nearest bell.
     * @return Result of Behavior
     */
    @NotNull BehaviorResult ringBell();

    /**
     * Makes this Mob react to a Bell ringing, symbolizing an incoming raid.
     * @return Result of Behavior
     */
    @NotNull BehaviorResult reactToBell();

    /**
     * Makes this Mob interact with the nearest door.
     * @return Result of Behavior
     */
    @NotNull BehaviorResult interactWithDoor();

    /**
     * Makes this Mob sleep in a bed.
     * @return Result of Behavior
     */
    @NotNull BehaviorResult sleep();

    /**
     * Makes this Mob socialize at the Village's Bell.
     * @return Result of Behavior
     */
    @NotNull BehaviorResult socializeAtBell();



}
