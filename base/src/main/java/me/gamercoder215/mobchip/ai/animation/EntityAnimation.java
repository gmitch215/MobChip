package me.gamercoder215.mobchip.ai.animation;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an Entity Animation
 */
public enum EntityAnimation implements Keyed {

    /**
     * Represents an Empty Animation
     */
    EMPTY(""),
    /**
     * Represents the Damage Animation when taking damage
     */
    DAMAGE("hurt"),
    /**
     * Represents the Damage Animation when taking damage on a critical hit
     */
    CRITICAL_DAMAGE("critical_hit"),
    /**
     * Represents the Damage Animation when taking damage on a magical critical hit
     */
    MAGICAL_CRITICAL_DAMAGE("magic_critical_hit"),
    /**
     * Represents the Mob's Spawning Animation
     */
    SPAWN("spawn"),
    ;

    private final NamespacedKey key;

    EntityAnimation(String key) {
        this.key = NamespacedKey.minecraft(key);
    }


    @Override
    @NotNull
    public NamespacedKey getKey() { return this.key; }

    /**
     * Fetches an EntityAnimation by its NamespacedKey.
     * @param key Key to fetch
     * @return EntityAnimation found, or null if not found
     */
    @Nullable
    public static EntityAnimation getByKey(@Nullable NamespacedKey key) {
        if (key == null) return null;
        for (EntityAnimation a : values()) {
            if (a.getKey().equals(key)) return a;
        }

        return null;
    }
}
