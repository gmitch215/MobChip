package me.gamercoder215.mobchip.ai.behavior;

import org.bukkit.entity.Item;
import org.bukkit.entity.Piglin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents Behavior for a Bukkit {@link Piglin}
 */
public interface PiglinBehavior extends CreatureBehavior {

    /**
     * Makes this Piglin stop hunting for Piglins.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult stopHunting();

    /**
     * Makes this Piglin start admiring if a wanted item is near.
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
     * Makes this Piglin stop admiring an item if it is admiring an item.
     * @return Result of Behavior
     */
    @NotNull
    BehaviorResult stopAdmiring();

}
