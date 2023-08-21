package me.gamercoder215.mobchip.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a {@link Location} without a World
 */
public final class Position implements Cloneable {
    
    private int x;
    private int y;
    private int z;
    
    /**
     * Constructs a Position at this Entity.
     * @param en Entity to use
     */
    public Position(@NotNull Entity en) {
        this(en.getLocation());
    }
    
    /**
     * Constructs a Position at this Location.
     * @param loc Location
     */
    public Position(@NotNull Location loc) {
        this(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    /**
     * Constructs a Position at these coordinates.
     * @param x X coord
     * @param y Y Coord
     * @param z Z coord
     */
    public Position(double x, double y, double z) {
        this((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    /**
     * Constructs a Position at these coordinates.
     * @param x X coord
     * @param y Y Coord
     * @param z Z coord
     */
    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Gets the distance of this Position to another Position.
     * @param node Position to use
     * @return distance
     */
    public double distance(@NotNull Position node) {
        return Math.sqrt(distanceSquared(node));
    }
    
    /**
     * Gets the distance of this Position to another Location.
     * @param loc Location to use
     * @return distance
     */
    public double distance(@NotNull Location loc) {
        return distance(new Position(loc));
    }
    
    /**
     * Gets the distance of this Position to another Entity.
     * @param en Entity to use
     * @return distance
     */
    public double distance(@NotNull Entity en) {
        return distance(en.getLocation());
    }
    
    /**
     * Gets the distance, squared, of this Position to another Position.
     * @param node Position to use
     * @return distance squared
     */
    public double distanceSquared(@NotNull Position node) {
        double x = this.x - node.x;
        double y = this.y - node.y;
        double z = this.z - node.z;

        return x * x + y * y + z * z;
    }
    
    /**
     * Gets the distance, squared, of this Position to another Location.
     * @param loc Location to use
     * @return distance squared
     */
    public double distanceSquared(@NotNull Location loc) {
        return distanceSquared(new Position(loc));
    }
    
    /**
     * Gets the distance, squared, of this Position to another Entity.
     * @param en Entity to use
     * @return distance squared
     */
    public double distanceSquared(@NotNull Entity en) {
        return distance(en.getLocation());
    }

    /**
     * GEts the distance, squared, of this Position to these coordinates.
     * @param x X coord
     * @param y Y Coord
     * @param z Z coord
     * @return distance squared
     */
    public double distanceSquared(double x, double y, double z) { return new Position(x, y, z).distanceSquared(this); }

    /**
     * Fetches the Manhattan distance to another Position.
     * @param node Position to use
     * @return Manhattan distance
     */
    public double distanceManhattan(@NotNull Position node) {
        double x = Math.abs(this.x - node.x);
        double y = Math.abs(this.y - node.y);
        double z = Math.abs(this.z - node.z);

        return x + y + z;
    }

    /**
     * Fetches the Manhattan distance to another Location.
     * @param loc Location to use
     * @return Manhattan distance
     */
    public double distanceManhattan(@NotNull Location loc) {
        return distanceManhattan(new Position(loc));
    }

    /**
     * Fetches the current X value of this Position.
     * @return X coordinate
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Fetches the current Y value of this Position.
     * @return Y coordinate
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Fetches the current Z value of this Position.
     * @return Z coordinate
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Adds an amount to each coordinate.
     * @param x Amount of X to add
     * @param y Amount of Y to add
     * @param z Amount of Z to add
     * @return this class, for chaining
     */
    @NotNull
    public Position add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Removes an amount to each coordinate.
     * @param x Amount of X to remove
     * @param y Amount of Y to remove
     * @param z Amount of Z to remove
     * @return this class, for chaining
     */
    @NotNull
    public Position remove(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Adds a Position to this Position.
     * @param node Position to add
     * @return this class, for chaining
     */
    @NotNull
    public Position add(@NotNull Position node) {
        return add(node.x, node.y, node.z);
    }

    /**
     * Adds a location to this Position.
     * @param loc Location to add
     * @return this class, for chaining
     */
    @NotNull
    public Position add(@NotNull Location loc) {
        return add(new Position(loc));
    }

    /**
     * Adds an entity's location to this Position.
     * @param en Entity to add
     * @return this class, for chaining
     */
    @NotNull
    public Position add(@NotNull Entity en) {
        return add(en.getLocation());
    }

    /**
     * Removes a Location from this Position.
     * @param loc Location to remove
     * @return this class, for chaining
     */
    @NotNull
    public Position remove(@NotNull Location loc) {
        return remove(new Position(loc));
    }

    /**
     * Removes an entity's location from this Position.
     * @param en Entity to remove
     * @return this class, for chaining
     */
    @NotNull
    public Position remove(@NotNull Entity en) {
        return remove(en.getLocation());
    }

    /**
     * Removes a Position from this Position.
     * @param node Position to remove
     * @return this class, for chaining
     */
    @NotNull
    public Position remove(@NotNull Position node) {
        return remove(node.x, node.y, node.z);
    }
    
    /**
     * Converts this Position to a Bukkit Location.
     * @param w World to use
     * @return Bukkit Location created
     */
    @NotNull
    public Location toLocation(@Nullable World w) {
        return new Location(w, x, y, z);
    }

    /**
     * Converts this Position to a Bukkit Vector.
     * @return Bukkit Vector created
     */
    @NotNull
    public Vector toVector() { return new Vector(x, y, z); }

    /**
     * Sets the Position's X value.
     * @param x X value to set
     * @return this class, for chaining
     */
    @NotNull
    public Position setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the Position's Y value.
     * @param y Y value to set
     * @return this class, for chaining
     */
    @NotNull
    public Position setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the Position's Z value.
     * @param z Z value to set
     * @return this class, for chaining
     */
    @NotNull
    public Position setZ(int z) {
        this.z = z;
        return this;
    }

    @Override
    public Position clone() {
        try {
            return (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
}
