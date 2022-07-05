package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder where a Tamable Animal attacks the owner's target.
 */
public final class PathfinderOwnerHurtTarget extends TargetPathfinder {

    /**
     * Constructs a PathfinderOwnerHurtTarget.
     * @param t Tamable Entity to use
     */
    public PathfinderOwnerHurtTarget(@NotNull Tameable t) {
        super((Animals) t, false, false);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.TARGETING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalOwnerHurtTarget";
    }
}
