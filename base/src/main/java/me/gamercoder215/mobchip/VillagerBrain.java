package me.gamercoder215.mobchip;

import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Special Brain for a Villager
 */
public interface VillagerBrain extends EntityBrain {

    /**
     * Fetches this Villager's Gossip Container
     * @return Gossip Container
     */
    @NotNull
    EntityGossipContainer getGossipContainer();

    @Override
    Villager getEntity();

}
