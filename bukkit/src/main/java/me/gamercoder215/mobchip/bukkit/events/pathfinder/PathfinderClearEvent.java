package me.gamercoder215.mobchip.bukkit.events.pathfinder;

import me.gamercoder215.mobchip.ai.EntityAI;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when Pathfinders are cleared
 */
public class PathfinderClearEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final EntityAI ai;
    private final boolean isTarget;

    /**
     * Construct a PathfinderClearEvent.
     * @param ai EntityAI involved
     * @param target true if AI is target, else false
     */
    public PathfinderClearEvent(@NotNull EntityAI ai, boolean target) {
        this.ai = ai;
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

}
