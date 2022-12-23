package me.gamercoder215.mobchip.ai;

import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.WrappedPathfinder;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Represents Entity Pathfinder AI 
 */
public interface EntityAI extends Set<WrappedPathfinder> {

    /**
     * Fetches the Entity this EntityAI belongs to.
     * @return Entity
     */
    @NotNull
    Mob getEntity();

    // Util

    /**
     * Whether this EntityAI contains this Pathfinder.
     * @param p The Pathfinder to check for.
     * @return true if contains, else false
     */
    boolean contains(@NotNull Pathfinder p);

    /**
     * Adds a Pathfinder to this Entity AI.
     * @param p Pathfinder to add
     * @param priority Priority of Pathfinder
     * @return Pathfinder added
     */
    Pathfinder put(@NotNull Pathfinder p, int priority);

    /**
     * Adds all Pathfinders to this Entity AI.
     * @param map Map of Pathfinders to their Priorities
     */
    void putAll(@NotNull Map<? extends Pathfinder, Integer> map);

    /**
     * Removes a Pathfinder from this Entity AI.
     * @param p Pathfinder to remove
     * @return Pathfinder removed
     */
    boolean remove(@NotNull Pathfinder p);

    /**
     * Whether this Pathfinder is currently running.
     * @param p Pathfinder to use
     * @return true if running, else false
     */
    boolean isRunning(@NotNull Pathfinder p);

    // Other

    /**
     * Fetches all pathfinders that this Entity is running.
     * @return Set of running Pathfinders
     */
    @NotNull
    Set<WrappedPathfinder> getRunningGoals();

    /**
     * Disables all Pathfinders with this flag.
     * @param flag Flag to disable
     */
    void disableFlag(@Nullable Pathfinder.PathfinderFlag flag);

    /**
     * Enables all Pathfinders with this flag.
     * @param flag Flag to enable
     */
    void enableFlag(@Nullable Pathfinder.PathfinderFlag flag);

}
