package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.AbstractVillager;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Villager 
 */
public final class PathfinderLookAtTradingPlayer extends Pathfinder {

    /**
     * Constructs a PathfinderLookAtTradingPlayer.
     * @param m AbstractVillager (Villager or Wandering Trader) to use
     */
    public PathfinderLookAtTradingPlayer(@NotNull AbstractVillager m) {
        super(m);
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalLookAtTradingPlayer";
    }
}