package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.IronGolem;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for an Iron Golem to offer a Villager a flower
 */
public final class PathfinderOfferFlower extends Pathfinder {

    /**
     * Constructs a PathfinderOfferFlower.
     * @param m Iron Golem to use
     */
    public PathfinderOfferFlower(@NotNull IronGolem m) {
        super(m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalOfferFlower";
    }
}
