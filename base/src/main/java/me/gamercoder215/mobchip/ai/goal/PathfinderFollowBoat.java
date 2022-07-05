package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to follow a Boat
 */
public final class PathfinderFollowBoat extends Pathfinder {

    /**
     * Constructs a PathfinderFollowBoat.
     * @param c Creature to use
     */
    public PathfinderFollowBoat(@NotNull Creature c) {
        super(c);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalFollowBoat";
    }
}
