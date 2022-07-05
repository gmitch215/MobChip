package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.AbstractVillager;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the Pathfinder for the logic of any Villager to trade with a Player
 */
public final class PathfinderTradePlayer extends Pathfinder {

    /**
     * Constructs a PathfinderTradePlayer.
     * @param m AbstractVillager to use
     */
    public PathfinderTradePlayer(@NotNull AbstractVillager m) {
        super(m);
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.JUMPING, PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalTradeWithPlayer";
    }
}
