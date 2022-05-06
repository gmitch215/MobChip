package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;

/**
 * Represents a Pathfinder for a Tameable to attack the Target if its owner is hurt
 */
public final class PathfinderOwnerHurtByTarget extends TargetPathfinder {

    /**
     * COnstructs a PathfinderOwnerHurtByTarget from a NMS OwnerHurtByTarget.
     * @param g Goal to use
     */
    public PathfinderOwnerHurtByTarget(@NotNull OwnerHurtByTargetGoal g) {
        super(TargetPathfinder.getEntity(g, "e"));
    }
    
    /**
     * Constructs a PathfinderOwnerHurtByTarget.
     * @param m Tameable to use
     */
    public PathfinderOwnerHurtByTarget(@NotNull Tameable m) {
        super(m);
    }

    @Override
    public OwnerHurtByTargetGoal getHandle() {
        return new OwnerHurtByTargetGoal((TamableAnimal) nmsEntity);
    }


}
