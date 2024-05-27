package me.gamercoder215.mobchip.util;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents registration under a specific context.
 * <br><br>
 * Minecraft 1.20.5 introduced new changes to how registering new items (e.g. attributes, sensors, etc.) work.
 * You are now required to pass a namespace, key, and version, as opposed to just a namespace and key.
 * There is no functional difference if this is not provided, so MobChip will default to "unknown" for the version.
 * You can provide custom parameters here for improved native interoperability with the registry.
 */
public final class Registration {

    private static String version = "unknown";

    private Registration() { throw new UnsupportedOperationException("This class cannot be instantiated."); }

    /**
     * Gets the version for registration.
     * @return The version.
     */
    @NotNull
    public static String getVersion() {
        return version;
    }

    /**
     * Sets the version for registration.
     * @param version The version to set.
     */
    public static void setVersion(@NotNull String version) {
        if (version == null) throw new IllegalArgumentException("Version cannot be null.");
        Registration.version = version;
    }

    /**
     * Use the plugin version for registration.
     * @param plugin The plugin to use.
     */
    public static void usePlugin(@NotNull Plugin plugin) {
        if (plugin == null) throw new IllegalArgumentException("Plugin cannot be null.");
        setVersion(plugin.getDescription().getVersion());
    }

}
