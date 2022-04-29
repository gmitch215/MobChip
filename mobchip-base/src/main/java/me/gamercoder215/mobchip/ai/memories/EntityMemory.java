package me.gamercoder215.mobchip.ai.memories;

import java.util.function.Function;

import org.bukkit.Location;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

/**
 * Represents a Memory of an entity
 */
public enum EntityMemory {

    NOT_ADMIRING(Boolean.class, Boolean.class, MemoryModuleType.ADMIRING_DISABLED, BOOLEAN()),
    CELEBRATING_LOCATION(Location.class, BlockPos.class, MemoryModuleType.CELEBRATE_LOCATION, LOCATION()),

    ;

    private static final Function<Object, BlockPos> LOCATION() {
        return o -> {
            if (!(o instanceof Location l)) return null;
            return new BlockPos(l.getX(), l.getY(), l.getZ());
        };
    }


    private static final Function<Object, Boolean> BOOLEAN() {
        return o -> {
            if (!(o instanceof Boolean b)) return null;
            return b;
        };
    }

    private final Class<?> bukkit;
    private final Class<?> nms;
    private final MemoryModuleType<?> handle;
    private final Function<Object, ?> convert;

    private <N> EntityMemory(Class<?> bukkit, Class<N> nms, MemoryModuleType<N> handle, Function<Object, N> convert) {
        this.bukkit = bukkit;
        this.nms = nms;
        this.handle = handle;
        this.convert = convert;
    }

    @SuppressWarnings("rawtypes")
    public final MemoryModuleType getHandle() {
        return this.handle;
    }

    public final Class<?> getNMSClass() {
        return this.nms;
    }

    public final Class<?> getBukkitClass() {
        return this.bukkit;
    }

    @SuppressWarnings("unchecked")
    public <T> T convert(Object obj, Class<T> clazz) {
        if (!(clazz.isInstance(obj))) return null;

        return (T) convert.apply(obj);
    }

    public Object convert(Object obj) {
        return convert.apply(obj);
    }

    
}
