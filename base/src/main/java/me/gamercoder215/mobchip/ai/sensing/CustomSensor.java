package me.gamercoder215.mobchip.ai.sensing;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Custom Sensor.
 * @param <T> Entity Type related with this Sensor.
 */
public abstract class CustomSensor<T extends LivingEntity> implements Sensor<T> {

    private final int scanRate;
    private final Class<T> entityClass;
    private final NamespacedKey key;

    /**
     * Creates a new Custom Sensor with the default Scan Rate.
     * @param entityClass Entity Class used
     * @param key NamespacedKey of this Sensor
     */
    public CustomSensor(@NotNull Class<T> entityClass, @NotNull NamespacedKey key) {
        this(entityClass, key, DEFAULT_SCAN_RATE);
    }

    /**
     * Constructs a Custom Sensor.
     * @param entityClass Entity Class used
     * @param key NamespacedKey of this Sensor
     * @param scanRate Scan Rate, in Ticks
     * @throws IllegalArgumentException if scan rate is less than 1
     */
    public CustomSensor(@NotNull Class<T> entityClass, @NotNull NamespacedKey key, int scanRate) throws IllegalArgumentException {
        if (scanRate < 1) throw new IllegalArgumentException("Scan Rate must be positive!");
        this.scanRate = scanRate;
        this.entityClass = entityClass;
        this.key = key;
    }

    @Override
    public final int getScanRate() {
        return scanRate;
    }

    @Override
    public final Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public final NamespacedKey getKey() {
        return key;
    }

}
