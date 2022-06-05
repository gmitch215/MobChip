package me.gamercoder215.mobchip.ai.navigation;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.controller.EntityController;

/**
 * Represents Entity Navigation.
 * <p>
 * As opposed to the {@link EntityController}'s movement methods, this type of movement is better for long distances and is easier to manage.
 * <p>
 * Navigation uses multiple "Nodes" (see {@link NavigationNode}), which are multiple locations to reach before having an end goal.
 */
public interface EntityNavigation extends SpeedModifier {
    
    /**
     * Recomputes this Entity's Path.
     * @return this class, for chaining
     */
    EntityNavigation recompute();

    /**
     * Adds a Point
     * @param point NavigationNode to add
     * @return this class, for chaining
     */
    EntityNavigation addPoint(@NotNull NavigationNode point);

    /**
     * Adds a Point from Location
     * @param point Location to add
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(@NotNull Location point) {
        return addPoint(new NavigationNode(point));
    }

    /**
     * Adds a Point from coordinates
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(int x, int y, int z) {
        return addPoint(new NavigationNode(x, y, z));
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
     * @param point NavigationNode point to add
     * @return this class, for chaining
     */
    EntityNavigation addPoint(int index, @NotNull NavigationNode point);

    /**
     * Adds a Point at the given index
     * @param index index of point
     * @param point NavigationNode point to add
     * @return this class, for chaining
     */
    default EntityNavigation addPoint(int index, @NotNull Location point) {
        return addPoint(index, new NavigationNode(point));
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
    EntityNavigation removePoint(@NotNull NavigationNode point);

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
        return removePoint(new NavigationNode(point));
    }

    /**
     * Removes a Point from coordinates
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @return this class, for chaining
     */
    default EntityNavigation removePoint(int x, int y, int z) {
        return removePoint(new NavigationNode(x, y, z));
    }

    /**
     * Sets the final point of this EntityNavigation.
     * @param node NavigationNode to set
     * @return this class, for chaining
     */
    EntityNavigation setFinalPoint(@NotNull NavigationNode node);

    /**
     * Sets the final point of this EntityNavigation.
     * @param loc Location to set
     * @return this class, for chaining
     */
    default EntityNavigation setFinalPoint(@NotNull Location loc) {
        return setFinalPoint(new NavigationNode(loc));
    }

    /**
     * Sets the final point of this EntityNavigation.
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @return this class, for chaining
     */
    default EntityNavigation setFinalPoint(int x, int y, int z) {
        return setFinalPoint(new NavigationNode(x, y, z));
    }

    /**
     * Sets the final point of this EntityNavigation.
     * @param en Entity to set
     * @return this class, for chaining
     */
    default EntityNavigation setFinalPoint(@NotNull Entity en) {
        return setFinalPoint(en.getLocation());
    }

    /**
     * Sets the maximum range of this Navigation.
     * @param range Range of Navigation
     * @return this class, for chaining
     */
    EntityNavigation setRange(int range);

    /**
     * Constructs a NavigationPath.
     * @return Constructed NavigationPath
     * @throws IllegalArgumentException if final point is missing
     */
    NavigationPath buildPath() throws IllegalArgumentException;

}
