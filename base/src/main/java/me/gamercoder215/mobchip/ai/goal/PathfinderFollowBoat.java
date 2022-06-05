package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;

/**
 * Represents a Pathfinder for a Creature to follow a Boat
 */
public final class PathfinderFollowBoat extends Pathfinder {

    /**
     * Constructs a PathfinderFollowBoat from a NMS FollowBoatGoal.
     * @param g Goal to use
     */
    public PathfinderFollowBoat(@NotNull FollowBoatGoal g) {
        super(Pathfinder.getCreature(g, "a"));
    }

    /**
     * Constructs a PathfinderFollowBoat.
     * @param c Creature to use
     */
    public PathfinderFollowBoat(@NotNull Creature c) {
        super(c);
    }

    @Override
    public FollowBoatGoal getHandle() {
        return new FollowBoatGoal((PathfinderMob) nmsEntity);
    }
    
}
