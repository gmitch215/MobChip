package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to try and find water
 */
public final class PathfinderFindWater extends Pathfinder {

    /**
     * Constructs a PathfinderFindWater.
     * @param m Creature to use
     */
    public PathfinderFindWater(@NotNull Creature m) {
        super(m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() { return "PathfinderGoalWater"; }
}
