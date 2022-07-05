package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creeper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the Pathfinder for the logic of a Creeper to swell before exploding
 */
public final class PathfinderSwellCreeper extends Pathfinder {

    /**
     * Constructs a PathfinderSwellCreeper.
     * @param m Creeper to use
     */
    public PathfinderSwellCreeper(@NotNull Creeper m) {
        super(m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalSwell";
    }
}
