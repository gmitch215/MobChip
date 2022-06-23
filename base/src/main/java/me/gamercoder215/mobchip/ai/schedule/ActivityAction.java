package me.gamercoder215.mobchip.ai.schedule;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an Action that an Activity will execute
 */
@FunctionalInterface
public interface ActivityAction {

    /**
     * Starts this Activity Action.
     * @param m Mob to use
     */
    void start(@NotNull Mob m);

    /**
     * Optional action for when this Activity is stopped.
     * @param m Mob to use
     */
    default void stop(@NotNull Mob m) {
        // do nothing
    }

}
