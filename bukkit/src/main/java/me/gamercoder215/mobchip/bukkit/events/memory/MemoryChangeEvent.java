package me.gamercoder215.mobchip.bukkit.events.memory;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a Memory Changes
 */
public class MemoryChangeEvent extends MemoryEvent {

    private final Object oldV;
    private final Object newV;

    /**
     * Construct a MemoryChangeEvent.
     * @param brain EntityBrain involved
     * @param memory EntityMemory involved
     * @param oldValue Old Value of Memory
     * @param newValue New Value of Memory
     */
    public MemoryChangeEvent(@NotNull EntityBrain brain, @NotNull EntityMemory<?> memory, @Nullable Object oldValue, @Nullable Object newValue) {
        super(brain, memory);

        this.oldV = oldValue;
        this.newV = newValue;
    }

    /**
     * Get the old value of this MemoryChangeEvent.
     * @return Old Value
     */
    public Object getOldValue() {
        return this.oldV;
    }

    /**
     * Get the new value of this MemoryChangeEvent.
     * @return New Value
     */
    public Object getNewValue() {
        return this.newV;
    }


}
