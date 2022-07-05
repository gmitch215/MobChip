package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to randomly stroll through a village
 */
public final class PathfinderRandomStrollThroughVillage extends Pathfinder {
    
    private int interval;

    /**
     * Constructs a PathfinderRandomStrollThroughVillage with a default interval of 100 ticks.
     * @param c Creature to use
     */
    public PathfinderRandomStrollThroughVillage(@NotNull Creature c) {
        this(c, 100);
    }

    /**
     * Constructs a PathfinderRandomStrollThroughVillage.
     * @param c Creature to use
     * @param interval Interval, in ticks, to stroll
     */
    public PathfinderRandomStrollThroughVillage(@NotNull Creature c, int interval) {
        super(c);

        this.interval = interval;
    }

    /**
     * Gets the current interval of this PathfinderRandomStrollThroughVillage.
     * @return Interval to stroll, in ticks
     */
    public int getInterval() {
        return this.interval;
    }

    /**
     * Sets the current interval of this PathfinderRandomStrollThroughVillage.
     * @param interval interval to stroll, in ticks
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalNearestVillage";
    }
}
