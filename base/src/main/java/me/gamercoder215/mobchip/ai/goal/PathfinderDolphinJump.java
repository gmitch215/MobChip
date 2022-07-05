package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Dolphin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that makes Dolphins Jump
 */
public final class PathfinderDolphinJump extends Pathfinder {

    private int interval;

    /**
     * Constructs a PathfinderDolphinJump.
     * @param d Dolphin to use
     * @param interval Interval to use
     * @throws IllegalArgumentException if less than 0
     */
    public PathfinderDolphinJump(@NotNull Dolphin d, int interval) throws IllegalArgumentException {
        super(d);

        if (interval < 0) throw new IllegalArgumentException("Interval must be greater than 0");
        this.interval = interval;
    }

    /**
     * Get the current interval of jump time
     * @return current interval of jump time
     */
    public int getInterval() {
        return this.interval;
    }

    
    /**
     * Sets the Interval of how often Dolphins will jump.
     * @param interval Interval to set
     * @throws IllegalArgumentException if interval is less than 0
     */
    public void setInterval(int interval) throws IllegalArgumentException {
        if (interval < 0) throw new IllegalArgumentException("Must be greater than 0");
        this.interval = interval;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.JUMPING };
    }

    @Override
    public String getInternalName() { return "PathfinderGoalWaterJump"; }
}
