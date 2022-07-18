package me.gamercoder215.mobchip.ai.gossip;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Gossip Type that influences an Entity's popularity
 */
public enum GossipType implements Keyed {

    /**
     * Represents the most influential gossip type, a major negative. This will decay daily the most.
     */
    MAJOR_NEGATIVE("major_negative", -5, 100, 10, 10),
    /**
     * Represents a minor negative gossip type.
     */
    MINOR_NEGATIVE("minor_negative", -1, 200, 20, 20),
    /**
     * Represents a minor positive gossip type.
     */
    MINOR_POSITIVE("minor_positive", 1, 200, 1, 5),
    /**
     * Represents the least influential gossip type, a major positive. This will not decay daily.
     */
    MAJOR_POSITIVE("major_positive", 5, 100, 0, 100),
    /**
     * Represents a Gossip Type when trading with a villager.
     */
    TRADING("trading", 1, 25, 2, 20),
    ;

    private final String type;
    private final int priority;
    private final int max;
    private final int dailyDecay;
    private final int transferDecay;

    GossipType(String id, int priority, int max, int dailyDecay, int transferDecay) {
        this.type = id;
        this.priority = priority;
        this.max = max;
        this.dailyDecay = dailyDecay;
        this.transferDecay = transferDecay;
    }

    /**
     * Fetches the priority of this GossipType. Those with a lower priority will override those with a higher priority (i.e. Major Negative over Minor Positive)
     * @return Numerical Priority of this GossipType
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Fetches the maximum amount of decay that can be applied to this GossipType
     * <br><br>
     * When the gossip is applied, a number is set to how long it will stay there, and it will increase daily by {@link #getDailyDecay()} until it reaches 0, when the gossip type will be removed.
     * @return Maximum Decay of this GossipType
     */
    public int getDefaultMaxDecay() {
        return max;
    }

    /**
     * Fetches the amount of decay that will be removed from the current amount every minecraft day.
     * @return Daily Decay of this GossipType
     * @see #getDefaultMaxDecay()
     */
    public int getDailyDecay() {
        return dailyDecay;
    }

    /**
     * Fetches the amount of decay that will be removed when trading with this Villager.
     * @return Transfer Decay of this GossipType
     * @see #getDefaultMaxDecay()
     */
    public int getTransferDecay() {
        return transferDecay;
    }

    /**
     * Fetches the unique key of this GossipType.
     * @return unique NamespacedKey for this GossipType
     */
    @Override
    public NamespacedKey getKey() {
        return NamespacedKey.minecraft(type);
    }

    /**
     * Fetches a Gossip Type by its unique Namespaced Key.
     * @param key Namespaced Key to lookup
     * @return found Gossip Type or null if not found
     */
    @Nullable
    public static GossipType getByKey(@Nullable NamespacedKey key) {
        if (key == null) return null;
        for (GossipType type : values()) if (type.getKey().equals(key)) return type;
        return null;
    }
}
