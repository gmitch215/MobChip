package me.gamercoder215.mobchip.bukkit.events;

import me.gamercoder215.mobchip.EntityBrain;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called before an Entity's Restriction Center Changes
 */
public class RestrictionSetEvent extends BrainEvent {

    private final Location oldLoc;
    private Location newLoc;
    private final int oldRadius;
    private int newRadius;

    /**
     * Construct a RestrictionSetEvent.
     * @param brain EntityBrain involved
     * @param oldLoc Old Location Center
     * @param newLoc New Location Center
     * @param oldRadius Old Radius
     * @param newRadius New Radius
     */
    public RestrictionSetEvent(@NotNull EntityBrain brain, @Nullable Location oldLoc, @Nullable Location newLoc, int oldRadius, int newRadius) {
        super(brain);

        this.oldLoc = oldLoc;
        this.newLoc = newLoc;
        this.oldRadius = oldRadius;
        this.newRadius = newRadius;
    }

    /**
     * Fetches the old restriction center.
     * @return Old Center, may be null
     */
    @Nullable
    public Location getOldCenter() {
        return this.oldLoc;
    }

    /**
     * Fetches the old radius.
     * @return Old Restriction Radius
     */
    public int getOldRadius() {
        return this.oldRadius;
    }

    /**
     * Fetches the new radius.
     * @return New Restriction Radius
     */
    public int getNewRadius() {
        return this.newRadius;
    }

    /**
     * Sets the new radius.
     * @param radius New Restriction Radius
     */
    public void setNewRadius(int radius) {
        this.newRadius = radius;
    }

    /**
     * Fetches the new restriction center.
     * @return New Center, may be null
     */
    @Nullable
    public Location getNewCenter() {
        return this.newLoc;
    }

    /**
     * Sets the new Location center.
     * @param loc Location to set
     */
    public void setNewCenter(@Nullable Location loc) {
        this.newLoc = loc == null ? this.newLoc : loc;
    }

}
