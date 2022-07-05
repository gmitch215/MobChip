package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for Creatures to randomly stroll
 */
public class PathfinderRandomStroll extends Pathfinder implements SpeedModifier {
    
    private double speedMod;

    private int interval;

    /**
     * Constructs a PathfinderRandomStroll with no speed modifier.
     * @param c Creature to use
     */
    public PathfinderRandomStroll(@NotNull Creature c) {
        this(c, 1);
    }

    /**
     * Constructs a PathfinderRandomStroll with a default interval of 120 ticks.
     * @param c Creature to use
     * @param speedMod Speed Modifier while strolling
     */
    public PathfinderRandomStroll(@NotNull Creature c, double speedMod) {
        this(c, speedMod, 120);
    }

    /**
     * Constructs a PathfinderRandomStroll.
     * @param c Creature to use
     * @param speedMod Speed Modifier while strolling
     * @param interval Strolling Speed Interval, in ticks
     */
    public PathfinderRandomStroll(@NotNull Creature c, double speedMod, int interval) {
        super(c);

        this.speedMod = speedMod;
        this.interval = interval;
    }

    /**
     * Gets the strolling speed interval of this Pathfinder, in ticks.
     * @return Stroll Speed Interval in ticks
     */
    public int getInterval() { return this.interval; }

    /**
     * Sets the interval strolling speed of this Pathfinder, in ticks.
     * @param interval Stroll Speed Interval in ticks
     */
    public void setInterval(int interval) { this.interval = interval;}

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalRandomStroll";
    }
}
