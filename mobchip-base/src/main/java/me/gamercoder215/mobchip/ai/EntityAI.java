package me.gamercoder215.mobchip.ai;

import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Represents Entity Pathfinder AI 
 */
public interface EntityAI extends Map<Integer, Pathfinder> {

    /**
     * Fetches all pathfinders that this Entity is running.
     * @return Set of running Pathfinders
     */
    @NotNull
    Set<Pathfinder> getRunningGoals();

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
