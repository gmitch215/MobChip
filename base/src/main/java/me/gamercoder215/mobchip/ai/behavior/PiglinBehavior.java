package me.gamercoder215.mobchip.ai.behavior;

import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents Behavior for a Bukkit Piglin
 */
public interface PiglinBehavior extends CreatureBehavior {

    /**
     * Makes this Piglin stop hunting for Piglins.
     * <p>This behavior requires {@link EntityMemory#HAS_HUNTED_RECENTLY} to be registered, and {@link EntityMemory#ATTACK_TARGET} to be present.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult stopHunting();

    /**
     * Makes this Piglin start admiring if a wanted item is near.
     * <p>This method will set {@link EntityMemory#IS_ADMIRING_ITEM} and {@link EntityMemory#NEAREST_WANTED_ITEM} for you with the provided arguments.</p>
     * <p>This behavior requires {@link EntityMemory#ADMIRING_DISABLED} and {@link EntityMemory#IS_ADMIRING_ITEM} to be absent previously.</p>
     * @param want Item Entity to admire
     * @param duration How long to admire the item
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult startAdmiring(@Nullable Item want, int duration);

    /**
     * Makes this Piglin start hunting if a Hoglin is near.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult startHunting();

    /**
     * Sets the hunting radius used to look for a new Hoglin in {@link #startHunting()}.
     * @param radius Radius to look for a Hoglin
     * @throws IllegalArgumentException if radius is not positive
     */
    void setHuntingRadius(int radius) throws IllegalArgumentException;

    /**
     * Resets the hunting radius used in {@link #startHunting()} to its default value (100).
     */
    default void resetHuntingRadius() {
        setHuntingRadius(100);
    }

    /**
     * Makes this Piglin stop admiring an item if it is admiring an item.
     * <p>This method removes {@link EntityMemory#IS_ADMIRING_ITEM} for you.</p>
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult stopAdmiring();

}
