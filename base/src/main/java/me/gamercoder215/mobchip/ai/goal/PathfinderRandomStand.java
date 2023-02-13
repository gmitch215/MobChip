package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.AbstractHorse;
import org.jetbrains.annotations.NotNull;

/**
 * <p>Represents a Pathfinder for a Horse-like Mob to randomly stand.</p>
 * <strong>Only Available in 1.19.3+ Versions</strong>
 */
public final class PathfinderRandomStand extends Pathfinder {

    /**
     * Constructs a PathfinderRandomStand.
     * @param m AbstractHorse to use
     */
    public PathfinderRandomStand(@NotNull AbstractHorse m) {
        super(m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "RandomStandGoal";
    }
}
