package me.gamercoder215.mobchip.bukkit.events.memory;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.bukkit.events.BrainEvent;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an Event involving a Memory
 */
public abstract class MemoryEvent extends BrainEvent implements Cancellable {
    
    private EntityMemory<?> memory;
    private boolean cancel;

    /**
     * Constructs a MemoryEvent.
     * @param brain EntityBrain Involved
     * @param memory EntityMemory involved
     */
    public MemoryEvent(@NotNull EntityBrain brain, EntityMemory<?> memory) {
        super(brain);

        this.memory = memory;
        this.cancel = false;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * Gets the memory involved in this MemoryEvent.
     * @return memory involved
     */
    public EntityMemory<?> getMemory() {
        return this.memory;
    }

    /**
     * Sets the memory involved in this MemoryEvent.
     * @param memory memory involved
     */
    public void setMemory(EntityMemory<?> memory) {
        this.memory = memory;
    }



}
