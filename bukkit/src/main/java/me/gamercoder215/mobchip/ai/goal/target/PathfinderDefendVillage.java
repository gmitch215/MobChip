package me.gamercoder215.mobchip.ai.goal.target;

import net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal;
import org.bukkit.entity.IronGolem;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for an Iron Golem to defend a Village
 */
public final class PathfinderDefendVillage extends TargetPathfinder {
    
    /**
     * Constructs a PathfinderDefendVillage from a NMS DefendVillageTargetGoal.
     * @param g Goal to use
     */
    public PathfinderDefendVillage(@NotNull DefendVillageTargetGoal g) {
        super(TargetPathfinder.getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderDefendVillage.
     * @param m Iron Golem to use
     */
    public PathfinderDefendVillage(@NotNull IronGolem m) {
        super(m);
    }

    @Override
    public DefendVillageTargetGoal getHandle() {
        return new DefendVillageTargetGoal((net.minecraft.world.entity.animal.IronGolem) nmsEntity);
    }
}