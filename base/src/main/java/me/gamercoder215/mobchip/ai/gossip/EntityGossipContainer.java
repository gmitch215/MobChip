package me.gamercoder215.mobchip.ai.gossip;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Gossip Container Manager for a Villager
 */
public interface EntityGossipContainer {

    /**
     * Fetches this Gossip Container's Villager.
     * @return Villager
     */
    @NotNull
    Villager getEntity();

    /**
     * Forcefully decays all the gossip types by their daily decay rate.
     */
    void decay();

    /**
     * Gets the reputation of this entity.
     * <br><br>
     * The reputation is evaluated by filtering the {@link GossipType} array passed, and then adding all of their {@link GossipType#getPriority()} methods together (negatives have negative priority, positives have positive priority).
     * @param en Entity to get the reputation of
     * @param types Gossip types to filter by, null for all gossip types
     * @return Reputation of the entity
     * @throws IllegalArgumentException if entity is null
     */
    int getReputation(@NotNull Entity en, @Nullable GossipType... types) throws IllegalArgumentException;

    /**
     * Puts this Entity to a GossipType.
     * @param en Entity to put to the GossipType
     * @param type GossipType to put to
     * @throws IllegalArgumentException if entity or gossip type is null
     */
    default void put(@NotNull Entity en, @NotNull GossipType type) throws IllegalArgumentException {
        put(en, type, type.getDefaultMaxDecay());
    }

    /**
     * Puts this Entity to a GossipType with a custom decay max.
     * @param en Entity to put to the GossipType
     * @param type GossipType to put to
     * @param maxCap Maximum decay of this GossipType
     * @throws IllegalArgumentException if entity or gossip type is null
     */
    void put(@NotNull Entity en, @NotNull GossipType type, int maxCap) throws IllegalArgumentException;

    /**
     * Removes this Entity from a GossipType. If the entity does not have this gossip type, it will do nothing.
     * @param en Entity to remove
     * @param type Gossip Type to remove from
     * @throws IllegalArgumentException if entity or gossip type is null
     */
    void remove(@NotNull Entity en, @NotNull GossipType type) throws IllegalArgumentException;

    /**
     * Removes this Entity from all GossipTypes. If the entity has no gossip type, it will do nothing.
     * @param en Entity to remove
     * @throws IllegalArgumentException if entity is null
     */
    default void removeAll(@NotNull Entity en) throws IllegalArgumentException {
        for (GossipType type : GossipType.values()) remove(en, type);
    }

    /**
     * Removes all Entities with this Gossip Type from this Gossip Container.
     * @param type Gossip Type to remove
     * @throws IllegalArgumentException if gossip type is null
     */
    void removeAll(@NotNull GossipType type) throws IllegalArgumentException;


}
