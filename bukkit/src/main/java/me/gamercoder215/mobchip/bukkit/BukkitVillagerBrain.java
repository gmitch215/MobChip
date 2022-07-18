package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.VillagerBrain;
import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

class BukkitVillagerBrain extends BukkitBrain implements VillagerBrain {

    final Villager v;

    BukkitVillagerBrain(@NotNull Villager v) {
        super(v);
        this.v = v;
    }

    @Override
    public EntityGossipContainer getGossipContainer() {
        return w.getGossipContainer(v);
    }
}
