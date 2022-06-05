package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.AbstractVillager;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;

/**
 * Represents a Pathfinder for a Villager 
 */
public final class PathfinderLookAtTradingPlayer extends Pathfinder {

    /**
     * Constructs a PathfinderLookAtTradingPlayer from a NMS LookAtTradingPlayerGoal.
     * @param g Goal to use
     */
    public PathfinderLookAtTradingPlayer(@NotNull LookAtTradingPlayerGoal g) {
        super(Pathfinder.getEntity(g, "h"));
    }

    /**
     * Constructs a PathfinderLookAtTradingPlayer.
     * @param m AbstractVillager (Villager or Wandering Trader) to use
     */
    public PathfinderLookAtTradingPlayer(@NotNull AbstractVillager m) {
        super(m);
    }

    @Override
    public LookAtTradingPlayerGoal getHandle() {
        return new LookAtTradingPlayerGoal((net.minecraft.world.entity.npc.AbstractVillager) nmsEntity);
    }



}