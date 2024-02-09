package me.gamercoder215.mobchip.ai.navigation;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.util.Position;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents Entity Navigation.
 * <p>
 * As opposed to the {@link EntityController}'s movement methods, this type of movement is better for long distances and is easier to manage.
 * <p>
 * Navigation uses multiple "Nodes" (see {@link Position}), which are multiple locations to reach before having an end goal.
 */
public interface EntityNavigation extends SpeedModifier {
    
    /**
     * Recomputes this Entity's Path.
     * @return this class, for chaining
     * @deprecated Called automatically by the internal EntityController
     */
    @Deprecated
    EntityNavigation recompute();

    /**
     * Adds a Point
     * @param point Position to add
     * @return this class, for chaining
     */
    EntityNavigation addPoint(@NotNull Position point);

    /**
     * Adds a Point from Location
     * @param point Location to add
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(@NotNull Location point) {
        return addPoint(new Position(point));
    }

    /**
     * Adds a Point from coordinates
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(int x, int y, int z) {
        return addPoint(new Position(x, y, z));
    }

    /**
     * Adds a Point from an Entity
     * @param en Entity to add
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(@NotNull Entity en) {
        return addPoint(en.getLocation());
    }

    /**
     * Adds a Point at the given index
     * @param index index of point
     * @param point Position point to add
     * @return this class, for chaining
     */
    EntityNavigation addPoint(int index, @NotNull Position point);

    /**
     * Adds a Point at the given index
     * @param index index of point
     * @param point Position point to add
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(int index, @NotNull Location point) {
        return addPoint(index, new Position(point));
    }

    /**
     * Adds a Point at the given index
     * @param index index of point
     * @param en Entity to use as a point
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(int index, @NotNull Entity en) {
        return addPoint(index, en.getLocation());
    }


    /**
     * Removes a Point
     * @param point NavigationPoint to remove
     * @return this class, for chaining
     */
    EntityNavigation removePoint(@NotNull Position point);

    /**
     * Removes a Point
     * @param index Index of Point
     * @return this class, for chaining
     */
    EntityNavigation removePoint(int index);

    /**
     * Removes a Point from Location
     * @param point Location to remove
     * @return this class, for chaining
     */
    default EntityNavigation removePoint(@NotNull Location point) {
        return removePoint(new Position(point));
    }

    /**
     * Removes a Point from coordinates
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @return this class, for chaining
     */
    default EntityNavigation removePoint(int x, int y, int z) {
        return removePoint(new Position(x, y, z));
    }

    /**
     * Sets the maximum range of this Navigation.
     * @param range Range of Navigation
     * @return this class, for chaining
     */
    EntityNavigation setRange(int range);

    /**
     * Fetches the maximum range of this Navigation.
     * @return Range of Navigation
     */
    int getRange();

    /**
     * Constructs a NavigationPath.
     * @return Constructed NavigationPath
     * @throws IllegalArgumentException if the Path is empty
     */
    NavigationPath buildPath() throws IllegalArgumentException;

}
