package me.gamercoder215.mobchip.abstraction.v1_18_R2;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_18_R2;
import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EntityGossipContainer1_18_R2 implements EntityGossipContainer {
    private final GossipContainer handle;
    private final Villager entity;

    public EntityGossipContainer1_18_R2(Villager v) {
        this.entity = v;
        this.handle = ((CraftVillager) v).getHandle().getGossips();
    }

    @Override
    public @NotNull Villager getEntity() {
        return this.entity;
    }

    @Override
    public void decay() {
        handle.decay();
    }

    @Override
    public int getReputation(@NotNull Entity en, @Nullable GossipType... types) throws IllegalArgumentException {
        return handle.getReputation(en.getUniqueId(), g -> Arrays.asList(types).contains(ChipUtil1_18_R2.fromNMS(g)));
    }

    @Override
    public void put(@NotNull Entity en, @NotNull GossipType type, int maxCap) throws IllegalArgumentException {
        handle.add(en.getUniqueId(), ChipUtil1_18_R2.toNMS(type), maxCap);
    }

    @Override
    public void remove(@NotNull Entity en, @NotNull GossipType type) throws IllegalArgumentException {
        handle.remove(en.getUniqueId(), ChipUtil1_18_R2.toNMS(type));
    }

    @Override
    public void removeAll(@NotNull GossipType type) throws IllegalArgumentException {
        handle.remove(ChipUtil1_18_R2.toNMS(type));
    }
}
