package me.gamercoder215.mobchip.ai.sensing;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a mob's senses and groups of {@link Sensor}s.
 */
public interface EntitySenses {

    /**
     * Fetches a list of all sensors that this mob has enabled.
     * @return List of Sensors
     */
    @NotNull
    List<Sensor<?>> getSensors();

    /**
     * Adds a Sensor to this mob's senses.
     * @param sensor Sensor to Add
     * @throws IllegalArgumentException if sensor is not registered
     */
    void addSensor(@NotNull Sensor<?> sensor) throws IllegalArgumentException;

    /**
     * Removes a Sensor from this mob's senses.
     * @param sensor Sensor to Remove
     */
    void removeSensor(@NotNull Sensor<?> sensor);

    /**
     * Removes a Sensor by its NamespacedKey from this mob's senses.
     * @param key NamespacedKey to Remove
     */
    void removeSensor(@NotNull NamespacedKey key);

    /**
     * Whether this mob has a Sensor by its NamespacedKey.
     * @param key NamespacedKey to Check
     * @return true if sensor exists, false otherwise
     */
    boolean hasSensor(@NotNull NamespacedKey key);

}
