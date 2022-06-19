package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import org.bukkit.entity.IronGolem;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for an Iron Golem to offer a Villager a flower
 */
public final class PathfinderOfferFlower extends Pathfinder {

    /**
     * Constructs a PathfinderOfferFlower from a NMS OfferFlowerGoal.
     * @param g Goal to use 
     */
    public PathfinderOfferFlower(@NotNull OfferFlowerGoal g) {
        super(Pathfinder.getEntity(g, "c"));
    }

    /**
     * Constructs a PathfinderOfferFlower.
     * @param m Iron Golem to use
     */
    public PathfinderOfferFlower(@NotNull IronGolem m) {
        super(m);
    }

    @Override
    public OfferFlowerGoal getHandle() {
        return new OfferFlowerGoal((net.minecraft.world.entity.animal.IronGolem) nmsEntity);
    }
    
}
