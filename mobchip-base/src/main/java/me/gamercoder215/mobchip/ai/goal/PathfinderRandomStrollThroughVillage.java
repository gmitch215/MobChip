package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.StrollThroughVillageGoal;

/**
 * Represents a Pathfinder for a Creature to randomly stroll through a village
 */
public final class PathfinderRandomStrollThroughVillage extends Pathfinder {
    
    private int interval;

    public PathfinderRandomStrollThroughVillage(@NotNull StrollThroughVillageGoal g) {
        super(Pathfinder.getEntity(g, "b"));

        this.interval = Pathfinder.getInt(g, "c");
    }

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
    public StrollThroughVillageGoal getHandle() {
        return new StrollThroughVillageGoal((PathfinderMob) nmsEntity, interval);
    }

}
