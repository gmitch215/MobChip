package me.gamercoder215.mobchip.ai.goal;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a Pathfinder with a Priority
 */
public final class WrappedPathfinder {

    private final int priority;
    private final Pathfinder pathfinder;

    /**
     * Creates a WrappedPathfinder.
     * @param p Pathfinder to use
     * @param priority Priority of pathfinder
     * @throws IllegalArgumentException if priority is negative
     */
    public WrappedPathfinder(@Nullable Pathfinder p, int priority) throws IllegalArgumentException {
        if (priority < 0) throw new IllegalArgumentException("Priority must be greater than 0");

        this.pathfinder = p;
        this.priority = priority;
    }

    /**
     * Gets the priority of the pathfinder.
     * @return Priority of this WrappedPathfinder
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the pathfinder of this WrappedPathfinder.
     * @return Pathfinder added, may be null
     */
    @Nullable
    public Pathfinder getPathfinder() {
        return pathfinder;
    }

}
