package me.gamercoder215.mobchip.ai.memories;

import java.util.function.Function;

import org.bukkit.Location;

import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

/**
 * Represents a Memory of an entity
 */
public enum EntityMemory {

    NOT_ADMIRING(Boolean.class, Boolean.class, MemoryModuleType.ADMIRING_DISABLED, BOOLEAN()),
    CELEBRATING_LOCATION(Location.class, BlockPos.class, MemoryModuleType.CELEBRATE_LOCATION, LOCATION()),
    HOME(Location.class, GlobalPos.class, MemoryModuleType.HOME, GLOCATION()),
    
    
    ;
	
    private static final Function<Object, BlockPos> LOCATION() {
        return o -> {
            if (!(o instanceof Location l)) return null;
            return new BlockPos(l.getX(), l.getY(), l.getZ());
        };
    }
    
    private static final Function<Object, GlobalPos> GLOCATION() {
        return o -> {
            if (!(o instanceof Location l)) return null;
            return GlobalPos.of(ChipConversions.convertType(l.getWorld()).dimension(), LOCATION().apply(l));
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
    
    /**
     * Get the Handle of this EntityMemory.
     * @return EntityMemory handle
     */
    @SuppressWarnings("rawtypes")
    public final MemoryModuleType getHandle() {
        return this.handle;
    }
    
    /**
     * Get the NMS Class that belongs to this EntityMemory.
     * @return NMS Class
     */
    public final Class<?> getNMSClass() {
        return this.nms;
    }
    
    /**
     * Get the Bukkit Class that belongs to this EntityMemory.
     * @return Bukkit Class
     */
    public final Class<?> getBukkitClass() {
        return this.bukkit;
    }
    
    /**
     * @deprecated Internal use only
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public <T> T convert(Object obj, Class<T> clazz) {
        if (!(clazz.isInstance(obj))) return null;

        return (T) convert.apply(obj);
    }
    
    /**
     * @deprecated Internal use only
     */
    public Object convert(Object obj) {
        return convert.apply(obj);
    }

    
}
