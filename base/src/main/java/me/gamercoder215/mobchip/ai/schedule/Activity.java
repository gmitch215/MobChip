package me.gamercoder215.mobchip.ai.schedule;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an Activity assignable to a Mob.
 */
public enum Activity implements Keyed {

    /**
     * Represents the Main Activity of the Mob.
     */
    CORE("core"),
    /**
     * Represents the Idle Activity of the Mob.
     */
    IDLE("idle"),
    /**
     * Represents the Working Activity of the Mob.
     */
    WORK("work"),
    /**
     * Represents the Playing Activity of the Mob.
     */
    PLAY("play"),
    /**
     * Represents the Sleeping/Resting Activity of the Mob.
     */
    REST("rest"),
    /**
     * Represents the Activity while hiding.
     */
    HIDE("hide"),
    /**
     * Represents the Activity during a raid.
     */
    RAID("raid"),
    /**
     * Represents the Activity right before a raid will happen.
     */
    PRE_RAID("pre_raid"),
    ;

    private final NamespacedKey key;

    Activity(String id) {
        this.key = NamespacedKey.minecraft(id);
    }

    /**
     * Return the namespaced identifier for this object.
     * @return this object's key
     */
    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    /**
     * Fetches an Activity by its key.
     * @param key Key of the Activity
     * @return Activity with the given key, or null if no Activity with the given key exists.
     */
    @Nullable
    public static Activity getByKey(@Nullable NamespacedKey key) {
        for (Activity a : values()) if (a.getKey().equals(key)) return a;

        return null;
    }
}
