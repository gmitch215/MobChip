package me.gamercoder215.mobchip.ai.goal.target;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder where a Tamable Animal attacks the owner's target.
 */
public final class PathfinderOwnerHurtTarget extends TargetPathfinder {

    /**
     * Constructs a PathfinderOwnerHurtTarget from a NMS OwnerHurtTarget.
     * @param g Goal to use
     */
    public PathfinderOwnerHurtTarget(@NotNull OwnerHurtTargetGoal g) {
        super(TargetPathfinder.getEntity(g, "a"));
    }


    /**
     * Constructs a PathfinderOwnerHurtTarget.
     * @param t Tamable Entity to use
     */
    public PathfinderOwnerHurtTarget(@NotNull Tameable t) {
        super(t);
    }

    @Override
    public TargetGoal getHandle() {
        return new OwnerHurtTargetGoal((TamableAnimal) nmsEntity);
    }
}
