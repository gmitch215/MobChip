package me.gamercoder215.mobchip.bukkit.events;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.EntityBrain;

/**
 * Represents an Event related to the Brain
 */
public abstract class BrainEvent extends Event {

    private EntityBrain brain;

    /**
     * Construct a BrainEvent
     * @param brain EntityBrain involved
     */
    public BrainEvent(@NotNull EntityBrain brain) {
        this.brain = brain;
    }

    /**
     * Construct a BrainEvent
     * @param brain EntityBrain involved
     * @param async true if async, else false
     */
    public BrainEvent(@NotNull EntityBrain brain, boolean async) {
        super(async);

        this.brain = brain;
    }

    /**
     * Gets the EntityBrain involved in this event.
     * @return EntityBrai involved
     */
    public final EntityBrain getBrain() {
        return this.brain;
    }

}
