package me.gamercoder215.mobchip.ai.memories;

import org.bukkit.Keyed;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an Entity Memory
 * @param <T> Type of Memory
 */
public interface Memory<T> extends Keyed {

    /**
     * Fetches the Class of this Memory.
     * @return Bukkit Class
     */
    @NotNull
    Class<T> getBukkitClass();

}
