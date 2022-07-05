package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Fish;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Cod / Salmon / Tropical Fish to follow its school leader
 */
public final class PathfinderFollowFishLeader extends Pathfinder {
    /**
     * Constructs a PathfinderFollowFishLeader.
     * @param f Fish to use
     */
    public PathfinderFollowFishLeader(@NotNull Fish f) {
        super(f);
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "FollowFlockLeaderGoal";
    }
}
