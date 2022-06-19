package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to randomly swim
 */
public final class PathfinderRandomSwim extends PathfinderRandomStroll {

    /**
     * The Default Interval to swim, in ticks
     */
    public static final int DEFAULT_INTERVAL = 120;
    
    private int interval;

    /**
     * Constructs a PathfinderRandomSwim with the {@link #DEFAULT_INTERVAL}.
     * @param c Creature to use
     */
    public PathfinderRandomSwim(@NotNull Creature c) {
        this(c, DEFAULT_INTERVAL);
    }

    /**
     * Constructs a PathfinderRandomSwim with no speed modifier.
     * @param c Creature to use
     * @param interval Interval, in ticks, of how often to swim
     */
    public PathfinderRandomSwim(@NotNull Creature c, int interval) {
        super(c);
        this.interval = interval;
    }

    /**
     * Constructs a PathfinderRandomSwim.
     * @param c Creature to use
     * @param speedMod Speed Modifier while swimming
     * @param interval Interval, in ticks, of how often to swim
     */
    public PathfinderRandomSwim(@NotNull Creature c, double speedMod, int interval) {
        super(c, speedMod);
        this.interval = interval;
    }

    /**
     * Gets the interval, in ticks, of how often this Mob should swim.
     * @return Interval in ticks
     */
    public int getInterval() {
        return this.interval;
    }

    /**
     * Sets the interval, in ticks, of how often this Mob should swim.
     * @param interval interval in ticks
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public RandomSwimmingGoal getHandle() {
        return new RandomSwimmingGoal((PathfinderMob) nmsEntity, this.getSpeedModifier(), interval);
    }

}
