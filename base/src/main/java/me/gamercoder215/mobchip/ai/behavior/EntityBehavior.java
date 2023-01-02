package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.memories.MemoryStatus;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents Entity Behavior.
 * <br><br>
 * <p>Typically, behaviors require certain {@link Memory} objects to be present, not present, or registered in the native Minecraft Registry, and they will default to nothing if they are not present.</p>
 * <p>Some behaviors will set these memories for you. Those methods will be indicated when necessary.</p>
 * @see MemoryStatus
 */
public interface EntityBehavior {

    /**
     * Makes this Mob back up if it is too close.
     * <p>This behavior requires {@link EntityMemory#WALKING_TARGET} to be absent, {@link EntityMemory#LOOKING_TARGET} to be registered, and {@link EntityMemory#ATTACK_TARGET} & {@link EntityMemory#NEAREST_VISIBLE_LIVING_ENTITIES} to be present.</p>
     * @param minDistance Minimum Distance to back up
     * @param speedModifier Speed Modifier while backing up
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult backupIfClose(int minDistance, float speedModifier);

    /**
     * Makes this Mob back up if it is too close, with no speed modifier.
     * <p>This behavior requires {@link EntityMemory#WALKING_TARGET} to be absent, {@link EntityMemory#LOOKING_TARGET} to be registered, and {@link EntityMemory#ATTACK_TARGET} & {@link EntityMemory#NEAREST_VISIBLE_LIVING_ENTITIES} to be present.</p>
     * @param minDistance Minimum Distance to back up
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult backupIfClose(int minDistance) {
        return backupIfClose(minDistance, 1);
    }

    /**
     * Makes this Entity become Passive is a Memory is present.
     * <p>This behavior does not require any other memories to run.</p>
     * @param memory Memory to use
     * @param durationTicks How long to be passive, in ticks
     * @return Result of Behavior
     * @throws IllegalArgumentException if memory is null
     */
    @NotNull BehaviorResult passiveIf(@NotNull Memory<?> memory, int durationTicks) throws IllegalArgumentException;

    /**
     * Erases a memory if function returns true.
     * <p>This behavior does not require any other memories to run.</p>
     * @param function Function to test containing Mob
     * @param memory Memory to remove if function returns true
     * @return Result of Behavior
     * @throws IllegalArgumentException if function or memory is null
     */
    @NotNull BehaviorResult eraseIf(@NotNull Predicate<Mob> function, @NotNull Memory<?> memory) throws IllegalArgumentException;

    /**
     * Makes this Entity move to its wanted item.
     * <p>This behavior requires {@link EntityMemory#LOOKING_TARGET} and {@link EntityMemory#TICKS_ITEM_PICKUP_COOLDOWN} to be registered, and {@link EntityMemory#NEAREST_VISIBLE_WANTED_ITEM} to be present.</p>
     * <p>Depending on {@code requireTarget}, {@link EntityMemory#WALKING_TARGET} needs to be present if {@code true}, or absent if {@code false}.</p>
     * @param minDist Minimum Distance from Item
     * @param speedMod Speed Modifier while moving
     * @param requireTarget Whether a walking target is required (if false, {@link EntityMemory#WALKING_TARGET} needs to be absent, defaults to true)
     * @return Result of Behavior
     */
    @NotNull BehaviorResult moveToWantedItem(int minDist, float speedMod, boolean requireTarget);

    /**
     * Makes this Entity move to its wanted item, with {@code requireTarget} set to true.
     * <p>This behavior requires {@link EntityMemory#LOOKING_TARGET} and {@link EntityMemory#TICKS_ITEM_PICKUP_COOLDOWN} to be registered, and {@link EntityMemory#NEAREST_VISIBLE_WANTED_ITEM} & {@link EntityMemory#WALKING_TARGET} to be present.</p>
     * @param minDist Minimum Distance from Item
     * @param speedMod Speed Modifier while moving
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult moveToWantedItem(int minDist, float speedMod) {
        return moveToWantedItem(minDist, speedMod, true);
    }

    /**
     * Makes this Entity move to its wanted item, with no speed modifier.
     * <p>This behavior requires {@link EntityMemory#LOOKING_TARGET} and {@link EntityMemory#TICKS_ITEM_PICKUP_COOLDOWN} to be registered, and {@link EntityMemory#NEAREST_VISIBLE_WANTED_ITEM} & {@link EntityMemory#WALKING_TARGET} to be present.</p>
     * @param minDist Minimum Distance from Item
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult moveToWantedItem(int minDist) {
        return moveToWantedItem(minDist, 1);
    }

    /**
     * Makes this Mob jump on a Bed.
     * <p>This behavior requires {@link EntityMemory#NEAREST_BED} to be present, and {@link EntityMemory#WALKING_TARGET} to be absent.</p>
     * @param speedMod Speed Modifier while locating a bed
     * @return Result of Behavior
     */
    @NotNull BehaviorResult jumpOnBed(float speedMod);

    /**
     * Makes this Mob jump on a Bed, with no speed Modifier.
     * <p>This behavior requires {@link EntityMemory#NEAREST_BED} to be present, and {@link EntityMemory#WALKING_TARGET} to be absent.</p>
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult jumpOnBed() {
        return jumpOnBed(1);
    }

    /**
     * Makes this Mob perform a Melee Attack.
     * <p>This behavior requires {@link EntityMemory#LOOKING_TARGET} to be registered, {@link EntityMemory#ATTACK_COOLING_DOWN} to be absent, and {@link EntityMemory#ATTACK_TARGET} & {@link EntityMemory#NEAREST_VISIBLE_LIVING_ENTITIES} to be present.</p>
     * @param cooldown Cooldown, in ticks, before the next attack
     * @return Result of Behavior
     */
    @NotNull BehaviorResult meleeAttack(int cooldown);

    /**
     * Makes this Mob perform a Melee Attack, with a cooldown of 3 seconds (60 ticks).
     * <p>This behavior requires {@link EntityMemory#LOOKING_TARGET} to be registered, {@link EntityMemory#ATTACK_COOLING_DOWN} to be absent, and {@link EntityMemory#ATTACK_TARGET} & {@link EntityMemory#NEAREST_VISIBLE_LIVING_ENTITIES} to be present.</p>
     * @return Result of Behavior
     */
    default @NotNull BehaviorResult meleeAttack() {
        return meleeAttack(60);
    }

    /**
     * Wakes up this Mob if it is sleeping.
     * <p>This behavior does not require any memories to run.</p>
     * @return Result of Behavior
     */
    @NotNull BehaviorResult wakeUp();

    /**
     * Makes this Mob ring the nearest bell.
     * <p>This behavior requires {@link EntityMemory#MEETING_POINT} to be present.</p>
     * @return Result of Behavior
     */
    @NotNull BehaviorResult ringBell();

    /**
     * Makes this Mob react to a Bell ringing, symbolizing an incoming raid.
     * <p>This behavior requires {@link EntityMemory#HEARD_BELL_DURATION} to be present.</p>
     * @return Result of Behavior
     */
    @NotNull BehaviorResult reactToBell();

    /**
     * Makes this Mob interact with the nearest door.
     * <p>This behavior requires {@link EntityMemory#PATH} to be present, and {@link EntityMemory#DOORS_TO_CLOSE} & {@link EntityMemory#NEAREST_LIVING_ENTITIES} to be registered.</p>
     * @return Result of Behavior
     */
    @NotNull BehaviorResult interactWithDoor();

    /**
     * Makes this Mob sleep in a bed.
     * <p>This behavior requires {@link EntityMemory#HOME} to be present, and {@link EntityMemory#LAST_WOKEN} to be registered.</p>
     * @return Result of Behavior
     */
    @NotNull BehaviorResult sleep();

    /**
     * Makes this Mob socialize at the Village's Bell.
     * <p>This behavior requires {@link EntityMemory#NEAREST_VISIBLE_LIVING_ENTITIES} to be present, {@link EntityMemory#INTERACTION_TARGET} to be absent, and {@link EntityMemory#WALKING_TARGET} & {@link EntityMemory#LOOKING_TARGET} to be registered.</p>
     * @return Result of Behavior
     */
    @NotNull BehaviorResult socializeAtBell();



}
