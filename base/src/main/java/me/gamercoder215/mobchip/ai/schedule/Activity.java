package me.gamercoder215.mobchip.ai.schedule;

import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.core.Registry;
import org.apache.commons.lang.Validate;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Schedule Activity
 */
public final class Activity implements Keyed {

    private final NamespacedKey key;
    private final ActivityAction action;

    /**
     * Creates a new Activity.
     * @param key Identifier Key to use
     * @param action Action that is executed for this activity
     * @throws IllegalArgumentException if key or action is null
     * @throws UnsupportedOperationException if activity already exists
     */
    public Activity(@NotNull NamespacedKey key, @NotNull ActivityAction action) throws IllegalArgumentException, UnsupportedOperationException {
        Validate.notNull(key, "Key cannot be null");
        Validate.notNull(action, "Action cannot be null");
        if (MobChipUtil.exists(key, Registry.ACTIVITY)) { throw new UnsupportedOperationException("Activity already exists"); }

        this.key = key;
        this.action = action;
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
