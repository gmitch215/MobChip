package me.gamercoder215.mobchip.combat;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a specific location in combat that a Mob can prepare for.
 */
public enum CombatLocation implements Keyed {

    /**
     * Represents when the Mob takes damage because of falling from vines.
     */
    VINES("vines"),
    /**
     * Represents when the Mob takes damage because of scaffolding.
     */
    SCAFFOLDING("scaffolding"),
    /**
     * Represents when the Mob takes damage because of another falling incident.
     */
    OTHER_CLIMBABLE("other_climbable"),
    /**
     * Represents when the Mob takes damage because of twisting vines.
     */
    TWISTING_VINES("twisting_vines"),
    /**
     * Represents when the Mob takes damage because of weeping vines.
     */
    WEEPING_VINES("weeping_vines"),
    /**
     * Represents when the Mob takes damage because of a ladder.
     */
    LADDER("ladder"),
    /**
     * Represents when the Mob takes damage because of water.
     */
    WATER("water"),
    ;

    private final String key;

    CombatLocation(String id) {
        this.key = id;
    }

    /**
     * Fetches the key of this CombatLocation.
     * @return NamespacedKey of this CombatLocation
     */
    @NotNull
    public NamespacedKey getKey() {
        return NamespacedKey.minecraft(key);
    }

    /**
     * Fetches a CombatLocation by its NamespacedKey.
     * @param key NamespacedKey of the CombatLocation to fetch
     * @return CombatLocation with the given NamespacedKey, or null if none exists
     */
    @Nullable
    public static CombatLocation getByKey(@Nullable NamespacedKey key) {
        for (CombatLocation l : values()) if (l.key.equals(key.getKey())) return l;
        return null;
    }

}
