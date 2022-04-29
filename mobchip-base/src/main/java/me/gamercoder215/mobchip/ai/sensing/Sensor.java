package me.gamercoder215.mobchip.ai.sensing;

import net.minecraft.world.entity.ai.sensing.SensorType;

/**
 * Represents an Entity Sensor.
 * <p>
 * Sensors are similar to Entity Memories, but are preferred in more complex situations. 
 */
@SuppressWarnings("rawtypes")
public enum Sensor {
    
    ;

    private final SensorType<?> handle;

    Sensor(SensorType handle) {
        this.handle = handle;
    }

    public final SensorType getHandle() {
        return this.handle;
    }

}
