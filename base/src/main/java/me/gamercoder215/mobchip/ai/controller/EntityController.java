package me.gamercoder215.mobchip.ai.controller;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;

/**
 * Represents an Entity Controller, used for calling direct actions for an entity.
 * <p>
 * For long-term paths in movement, it is recommended to use {@link EntityNavigation}.
 */
public interface EntityController {
    
    /**
     * Forces this Entity to jump.
     * @return this controller, for chaining
     */
    EntityController jump();
    
    /**
     * Makes this Entity look at another entity.
     * @param en Entity to look at
     * @return this controller, for chaining
     */
    default EntityController lookAt(@NotNull Entity en) {
        return lookAt(en.getLocation());
    }

    /**
     * Makes this Entity look at a Location.
     * @param loc Location to look at
     * @return this controller, for chaining
     */
    default EntityController lookAt(@NotNull Location loc) {
        return lookAt(loc.getX(), loc.getY(), loc.getZ());
    }

    /**
     * Makes this Entity look at coordinates.
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @return this controller, for chaining
     */
    EntityController lookAt(double x, double y, double z);

    /**
     * Moves an Entity to another Entity, with no speed modifier.
     * @param en Entity to move to
     * @return this controller, for chaining
     */
    default EntityController moveTo(@NotNull Entity en) {
        return moveTo(en, 1);
    }

    /**
     * Moves an Entity to another Entity.
     * @param en Entity to move to
     * @param speedMod Speed Modifier
     * @return this controller, for chaining
     */
    default EntityController moveTo(@NotNull Entity en, double speedMod) {
        return moveTo(en.getLocation(), speedMod);
    }

    /**
     * Moves an Entity to the specified Location, with no speed modifier.
     * @param loc Location to move to
     * @return this controller, for chaining
     */
    default EntityController moveTo(@NotNull Location loc) {
        return moveTo(loc, 1);
    }
    
    /**
     * Moves an Entity to the specified Location.
     * @param loc Location to move to
     * @param speedMod Speed Modifier
     * @return this controller, for chaining
     */
    default EntityController moveTo(@NotNull Location loc, double speedMod) {
        return moveTo(loc.getX(), loc.getY(), loc.getZ(), speedMod);
    }

    /**
     * Moves an Entity to the specified coordinates, with no speed modifier.
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @return this controller, for chaining
     */
    default EntityController moveTo(double x, double y, double z) {
        return moveTo(x, y, z, 1);
    }

    /**
     * Moves an Entity to the specified coordinates.
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @param speedMod Speed Modifier
     * @return this controller, for chaining
     */
    EntityController moveTo(double x, double y, double z, double speedMod);

    /**
     * <p>Performs a Natural Entity Movement.</p>
     * <p>The X, Y, and Z are relative to the entity's current location.</p>
     * <p>Example: {@code naturalMoveTo(1, 0, 3, }{@link NaturalMoveType#SELF}{@code )} is a natural movement by
     * the entity's self +1 X-Axis step and +3 Z-Axis steps.</p>
     * @param x Relative X to move by
     * @param y Relative Y to move by
     * @param z Relative Z to move by
     * @param type Natural Movement Type
     * @return this controller, for chaining
     */
    EntityController naturalMoveTo(double x, double y, double z, NaturalMoveType type);

    /**
     * <p>Performs a Natural Entity Movement.</p>
     * <p>The Vector's X, Y, and Z are relative to the entity's current location.</p>
     * <p>Example: {@code naturalMoveTo(new Vector(1, 0, 3), }{@link NaturalMoveType#SELF}{@code )} is a natural movement by
     * the entity's self +1 X-Axis step and +3 Z-Axis steps.</p>
     * @param vec Relative Vector to move by
     * @param type Natural Movement Type
     * @return this controller, for chaining
     */
    default EntityController naturalMoveTo(Vector vec, NaturalMoveType type) {
        return naturalMoveTo(vec.getX(), vec.getY(), vec.getZ(), type);
    }

    /**
     * <p>Performs a Natural Entity Movement.</p>
     * <p>The X, Y, and Z are relative to the entity's current location.</p>
     * @param add Relative Location to move by
     * @param type Natural Movement Type
     * @see #naturalMoveTo(double, double, double, NaturalMoveType)
     * @return this controller, for chaining
     */
    default EntityController naturalMoveTo(Location add, NaturalMoveType type) {
        return naturalMoveTo(add.toVector(), type);
    }

    /**
     * Makes this Entity strafe.
     * @param fwd Amount to strafe <strong>forwards</strong> (negative for backwards)
     * @param right Amount to strafe <strong>right</strong> (negative for left)
     * @return this controller, for chaining
     */
    EntityController strafe(float fwd, float right);

    /**
     * Get the current Speed Modifier that this Entity has, if it is moving.
     * @return Current Speed Modifier
     */
    double getCurrentSpeedModifier();

    /**
     * Get the Location that this Entity is trying to move towards.
     * @return Target Move Location
     */
    Location getTargetMoveLocation();

    /**
     * Get the Location that this Entity is trying to look at.
     * @return Target Look Location
     */
    Location getTargetLookLocation();

    /**
     * Whether this Mob is looking at the last Look Target.
     * @return true if looking, else false
     */
    boolean isLookingAtTarget();
    
}
