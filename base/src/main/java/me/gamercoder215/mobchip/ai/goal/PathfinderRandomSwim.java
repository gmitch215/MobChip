package me.gamercoder215.mobchip.ai.goal;

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
        super(c, 1, interval);
    }

    /**
     * Constructs a PathfinderRandomSwim with the {@link #DEFAULT_INTERVAL}
     * @param c Creature to use
     * @param speedMod Speed Modifier while swimming
     */
    public PathfinderRandomSwim(@NotNull Creature c, double speedMod) {
        this(c, speedMod, DEFAULT_INTERVAL);
    }

    /**
     * Constructs a PathfinderRandomSwim.
     * @param c Creature to use
     * @param speedMod Speed Modifier while swimming
     * @param interval Interval, in ticks, of how often to swim
     */
    public PathfinderRandomSwim(@NotNull Creature c, double speedMod, int interval) {
        super(c, speedMod, interval);
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalRandomSwim";
    }

}
