package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Tamable to attack the Target if its owner is hurt
 */
public final class PathfinderOwnerHurtByTarget extends TargetPathfinder {
    
    /**
     * Constructs a PathfinderOwnerHurtByTarget.
     * @param m Tamable to use
     */
    public PathfinderOwnerHurtByTarget(@NotNull Tameable m) {
        super((Animals) m, false, false);
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.TARGETING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalOwnerHurtByTarget";
    }
}
