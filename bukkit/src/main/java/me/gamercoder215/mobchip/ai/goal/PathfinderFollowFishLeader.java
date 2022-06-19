package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import org.bukkit.entity.Cod;
import org.bukkit.entity.Salmon;
import org.bukkit.entity.TropicalFish;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Cod / Salmon / Tropical Fish to follow its school leader
 */
public final class PathfinderFollowFishLeader extends Pathfinder {
    
    /**
     * Constructs a PathfinderFollowFishLeader with a NMS FollowFlockLeaderGoal.
     * @param g Goal to use
     */
    public PathfinderFollowFishLeader(@NotNull FollowFlockLeaderGoal g) {
        super(Pathfinder.getEntity(g, "b"));
    }

    /**
     * Constructs a PathfinderFollowFishLeader.
     * @param f Cod fish instance
     */
    public PathfinderFollowFishLeader(@NotNull Cod f) {
        super(f);
    }

    /**
     * Constructs a PathfinderFollowFishLeader.
     * @param f Salmon fish instance
     */
    public PathfinderFollowFishLeader(@NotNull Salmon f) {
        super(f);
    }

    /**
     * Constructs a PathfinderFollowFishLeader.
     * @param f TropicalFish instance
     */
    public PathfinderFollowFishLeader(@NotNull TropicalFish f) {
        super(f);
    }
    
    @Override
    public FollowFlockLeaderGoal getHandle() {
        return new FollowFlockLeaderGoal((AbstractSchoolingFish) nmsEntity);
    }

}
