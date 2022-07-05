package me.gamercoder215.mobchip.ai.schedule;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Schedule Activity
 */
public enum Activity implements Keyed {

    ;

    private final NamespacedKey key;
    private final ActivityAction action;

    Activity(NamespacedKey key, ActivityAction action) {
        this.key = key;
        this.action = action;
    }

    /**
     * Fetches the Unique Key for this Activity
     * @return Activity's Unique Key
     */
    @Override
    public @NotNull NamespacedKey getKey() {
        return this.key;
    }

    /**
     * Fetches this Activity's Action.
     * @return Action for this Activity
     */
    public @NotNull ActivityAction getAction() {
        return this.action;
    }

    /**
     * Fetches an Activity by its key.
     * @param key Key to fetch
     * @return Activity with the given key
     */
    @Nullable
    public static Activity getByKey(NamespacedKey key) {
        for (Activity activity : values()) {
            if (activity.getKey().equals(key)) {
                return activity;
            }
        }
        return null;
    }

}
