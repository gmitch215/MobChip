package me.gamercoder215.mobchip.bukkit.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.EntityBrain;

/**
 * Represents an Event related to the Brain
 */
public abstract class BrainEvent extends Event {

    private final EntityBrain brain;
    private static final HandlerList HANDLERS = new HandlerList();

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
     * Gets this BrainEvent's Handlers.
     * @return Handlers
     */
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets this BrainEvent's Handlers.
     * @return Handlers
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Gets the EntityBrain involved in this event.
     * @return EntityBrain involved
     */
    public final EntityBrain getBrain() {
        return this.brain;
    }

}
