package me.gamercoder215.mobchip.ai;

import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.WrappedPathfinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Represents Entity Pathfinder AI 
 */
public interface EntityAI extends Set<WrappedPathfinder> {

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
