package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that is world specific
 */
public interface WorldSpecific {

    /**
     * Exception called when World is null
     */
    IllegalArgumentException WORLD_NULL = new IllegalArgumentException("World cannot be null");

    /**
     * Get the world of this WorldSpecific.
     * @return World
     */
    @NotNull
    World getWorld();

    /**
     * Sets the world of this WorldSpecific.
     * @param w World to set
     * @throws IllegalArgumentException if world is null
     */
    void setWorld(@NotNull World w) throws IllegalArgumentException;
    
}
