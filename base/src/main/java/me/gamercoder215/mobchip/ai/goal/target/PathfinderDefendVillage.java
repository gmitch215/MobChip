package me.gamercoder215.mobchip.ai.goal.target;

import org.bukkit.entity.IronGolem;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for an Iron Golem to defend a Village
 */
public final class PathfinderDefendVillage extends TargetPathfinder {

    /**
     * Constructs a PathfinderDefendVillage.
     * @param m Iron Golem to use
     */
    public PathfinderDefendVillage(@NotNull IronGolem m) {
        super(m, false, true);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.TARGETING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalDefendVillage";
    }
}