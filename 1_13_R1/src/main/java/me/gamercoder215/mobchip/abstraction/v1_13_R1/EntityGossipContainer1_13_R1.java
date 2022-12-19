package me.gamercoder215.mobchip.abstraction.v1_13_R1;

import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import me.gamercoder215.mobchip.ai.gossip.GossipType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityGossipContainer1_13_R1 implements EntityGossipContainer {

    private final Villager entity;

    public EntityGossipContainer1_13_R1(Villager v) {
        this.entity = v;
        // doesn't exist
    }


    @Override
    public @NotNull Villager getEntity() {
        return entity;
    }

    @Override
    public void decay() {
        // doesn't exist
    }

    @Override
    public int getReputation(@NotNull Entity en, @Nullable GossipType... types) throws IllegalArgumentException {
        // doesn't exist
        return 0;
    }

    @Override
    public void put(@NotNull Entity en, @NotNull GossipType type, int maxCap) throws IllegalArgumentException {
        // doesn't exist
    }

    @Override
    public void remove(@NotNull Entity en, @NotNull GossipType type) throws IllegalArgumentException {
        // doesn't exist
    }

    @Override
    public void removeAll(@NotNull GossipType type) throws IllegalArgumentException {
        // doesn't exist
    }
}
