package me.gamercoder215.mobchip.bukkit.events.pathfinder;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event involving a Pathfinder
 */
public abstract class PathfinderEvent extends Event {

    private final EntityAI ai;
    private final Pathfinder pathfinder;
    private final boolean isTarget;
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Construct a PathfinderEvent.
     * @param ai EntityAI involved
     * @param pathfinder Pathfinder involved
     * @param target true if AI is target, else false
     */
    public PathfinderEvent(@NotNull EntityAI ai, @NotNull Pathfinder pathfinder, boolean target) {
        this.ai = ai;
        this.pathfinder = pathfinder;
        this.isTarget = target;
    }

    /**
     * Gets the Handlers of this PathfinderEvent.
     * @return Handlers
     */
    @Override
    public HandlerList getHandlers() { return HANDLERS; }

    /**
     * Gets the Handlers of this PathfinderEvent.
     * @return Handlers
     */
    public static HandlerList getHandlerList() { return HANDLERS; }

    /**
     * Gets the current EntityAI involved in this Event.
     * @return EntityAI involved
     */
    public EntityAI getAI()  {
        return this.ai;
    }

    /**
     * Whether this EntityAI is the Target AI.
     * @return true if EntityAI is target, else false
     */
    public boolean isTarget() {
        return this.isTarget;
    }

    /**
     * Gets the Pathfinder involved in this event.
     * @return Pathfinder involved
     */
    public Pathfinder getPathfinder() {
        return this.pathfinder;
    }



}
