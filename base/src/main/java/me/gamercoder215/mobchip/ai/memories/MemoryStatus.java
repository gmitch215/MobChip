package me.gamercoder215.mobchip.ai.memories;

/**
 * Represents the status of a Memory's presence in a brain.
 * <p>A memory can have three states in a brain:</p>
 * <ul>
 *   <li>Value Present</li>
 *   <li>Value Absent</li>
 *   <li>Registered</li>
 * </ul>
 *
 * <p>
 *    A Memory being present means that a value is stored in the brain with its type. For example, {@link EntityMemory#NOT_ADMIRING} would have either a {@code true}
 *    or {@code false} value present.
 * </p>
 * <p>
 *    A Memory being absent means that a value is not stored in the brain. For example, {@link EntityMemory#NOT_ADMIRING} would have no value present.
 * </p>
 * <p>
 *     A Memory being registered means that the memory is present in the Minecraft Registry. All native memories, such as the ones listed in this class, are all
 *     registered in the Minecraft Registry by default, but can be unregistered at any time. However, custom memories are not registered in the Minecraft Registry, and thus, would not be able to be present in the brain.
 * </p>
 */
public enum MemoryStatus {

    /**
     * Represents a Memory that is in the brain with an assigned value.
     */
    PRESENT,
    /**
     * Represents a Memory that is not present in the brain.
     */
    ABSENT,
    /**
     * Represents a Memory that is registered within the Minecraft Registry. In usual cases with Vanilla Memories, they are all registered.
     */
    REGISTERED

}
