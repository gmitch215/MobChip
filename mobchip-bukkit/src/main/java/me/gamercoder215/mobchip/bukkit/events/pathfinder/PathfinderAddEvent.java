package me.gamercoder215.mobchip.bukkit.events.pathfinder;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a Pathfinder is added
 */
public class PathfinderAddEvent extends PathfinderEvent implements Cancellable {

    private boolean cancel;
    private final int priority;

    /**
     * Constructs a PathfinderAddEvent.
     * @param ai EntityAI used
     * @param p Pathfinder used
     * @param target Whether AI is Target AI
     * @param priority Priority of Pathfinder
     */
    public PathfinderAddEvent(@NotNull EntityAI ai, @NotNull Pathfinder p, boolean target, int priority) {
        super(ai, p, target);

        this.priority = priority;
    }

    /**
     * Gets the priority that this Pathfinder will be added to.
     * @return Priority added
     */
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
