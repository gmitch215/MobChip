package me.gamercoder215.mobchip.ai.memories;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for Entity Memories
 */
public final class EntityMemories {

    private static final ChipUtil w = ChipUtil.getWrapper();

    /**
     * Registers a new memory in the registry.
     * @param memory Memory to register
     * @return true if memory was registered, false if memory already exists
     * @throws IllegalArgumentException if memory is null
     */
    public static boolean registerMemory(@NotNull Memory<?> memory) throws IllegalArgumentException {
        if (memory instanceof EntityMemory<?>) return false;
        if (w.existsMemory(memory)) return false;

        w.registerMemory(memory);
        return true;
    }

    /**
     * Whether this entity has the given memory.
     * @param memory Memory to check
     * @return true if memory exists, false if memory does not exist
     */
    public static boolean doesMemoryExist(@NotNull Memory<?> memory) {
        return w.existsMemory(memory);
    }

}
