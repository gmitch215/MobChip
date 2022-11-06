package me.gamercoder215.mobchip.ai.sensing;

import me.gamercoder215.mobchip.ai.memories.Memory;
import org.bukkit.Keyed;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <p>Represents an action performed when certain {@link Memory} objects are present.</p>
 * <p>This is typically used for updating memories when other memories are present.</p>
 * @param <T> Entity Type related with this Sensor.
 */
public interface Sensor<T extends LivingEntity> extends Keyed {

    /**
     * Represents the Default Scan Rate of 20 Ticks.
     */
    int DEFAULT_SCAN_RATE = 20;

    /**
     * Fetches all the Memories required for this Sensor to be performed.
     * @return List of Required Memories
     */
    @NotNull
    List<Memory<?>> required();

    /**
     * Fetches the scan rate of how often to check for the memories in {@link #required()}, in ticks.
     * @return Memory Scan Rate, in Ticks
     */
    int getScanRate();

    /**
     * Fetches the Class Type related to this Sensor.
     * @return Class Type
     */
    @NotNull
    Class<T> getEntityClass();

    /**
     * Performs the Sensor's action.
     * @param w World
     * @param entity Entity
     */
    void run(@NotNull World w, LivingEntity entity);

}
