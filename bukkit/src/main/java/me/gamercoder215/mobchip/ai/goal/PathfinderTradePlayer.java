package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import org.bukkit.entity.AbstractVillager;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the Pathfinder for the logic of any Villager to trade with a Player
 */
public final class PathfinderTradePlayer extends Pathfinder {

    /**
     * Construct a PathfinderTradePlayer from a NMS TradeWithPlayerGoal.
     * @param g Goal to use
     */
    public PathfinderTradePlayer(@NotNull TradeWithPlayerGoal g) {
        super(getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderTradePlayer.
     * @param m AbstractVillager to use
     */
    public PathfinderTradePlayer(@NotNull AbstractVillager m) {
        super(m);
    }

    @Override
    public TradeWithPlayerGoal getHandle() {
        return new TradeWithPlayerGoal((net.minecraft.world.entity.npc.AbstractVillager) nmsEntity);
    }

    

}
