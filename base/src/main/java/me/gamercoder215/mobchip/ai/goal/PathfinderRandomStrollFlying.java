package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to randomly fly
 */
public final class PathfinderRandomStrollFlying extends PathfinderRandomStroll {

    /**
     * Constructs a PathfinderRandomStrollFlying with no speed modifier.
     * @param c Creature to use
     */
    public PathfinderRandomStrollFlying(@NotNull Creature c) {
        super(c);
    }

    /**
     * Constructs a PathfinderRandomStrollFlying.
     * @param c Creature to use
     * @param speedMod Speed Modifier while flying
     */
    public PathfinderRandomStrollFlying(@NotNull Creature c, double speedMod) {
        super(c, speedMod);
    }

    @Override
    public String getInternalName() { return "PathfinderGoalRandomFly"; }

}
