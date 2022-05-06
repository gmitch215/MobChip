package me.gamercoder215.mobchip.bukkit.events.memory;

import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.bukkit.events.BrainEvent;

/**
 * Represents an Event involving a Memory
 */
public abstract class MemoryEvent extends BrainEvent implements Cancellable {
    
    private EntityMemory<?> memory;
    private boolean cancel;

    public MemoryEvent(@NotNull EntityBrain brain, EntityMemory<?> memory) {
        super(brain);

        this.memory = memory;
        this.cancel = false;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public EntityMemory<?> getMemory() {
        return this.memory;
    }

    public void setMemory(EntityMemory<?> memory) {
        this.memory = memory;
    }



}
