package me.gamercoder215.mobchip.ai.navigation;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Navigation Point, also known as a Node.
 */
public final class NavigationNode {
    
    private int x;
    private int y;
    private int z;
    
    /**
     * Constructs a NavigationNode at this Entity.
     * @param en Entity to use
     */
    public NavigationNode(@NotNull Entity en) {
        this(en.getLocation());
    }
    
    /**
     * Constructs a NavigationNode at this Location.
     * @param loc Location
     */
    public NavigationNode(@NotNull Location loc) {
        this(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
    
    /**
     * Constructs a NavigationNode at these coordinates.
     * @param x X coord
     * @param y Y Coord
     * @param z Z coord
     */
    public NavigationNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Gets the distance of this NavigationNode to another NavigationNode.
     * @param node NavigationNode to use
     * @return distance
     */
    public double distance(@NotNull NavigationNode node) {
        return Math.sqrt(distanceSquared(node));
    }
    
    /**
     * Gets the distance of this NavigationNode to another Location.
     * @param loc Location to use
     * @return distance
     */
    public double distance(@NotNull Location loc) {
        return distance(new NavigationNode(loc));
    }
    
    /**
     * Gets the distance of this NavigationNode to another Entity.
     * @param en Entity to use
     * @return distance
     */
    public double distance(@NotNull Entity en) {
        return distance(en.getLocation());
    }
    
    /**
     * Gets the distance, squared, of this NavigationNode to another NavigationNode.
     * @param node NavigationNode to use
     * @return distance squared
     */
    public double distanceSquared(@NotNull NavigationNode node) {
        double x = this.x - node.x;
        double y = this.y - node.y;
        double z = this.z - node.z;

        return x * x + y * y + z * z;
    }
    
    /**
     * Gets the distance, squared, of this NavigationNode to another Location.
     * @param loc Location to use
     * @return distance squared
     */
    public double distanceSquared(@NotNull Location loc) {
        return distanceSquared(new NavigationNode(loc));
    }
    
    /**
     * Gets the distance, squared, of this NavigationNode to another Entity.
     * @param en Entity to use
     * @return distance squared
     */
    public double distanceSquared(@NotNull Entity en) {
        return distance(en.getLocation());
    }

    /**
     * Fetches the Manhattan distance to another NavigationNode.
     * @param node NavigationNode to use
     * @return Manhattan distance
     */
    public double distanceManhattan(@NotNull NavigationNode node) {
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
        return distanceManhattan(new NavigationNode(loc));
    }

    /**
     * Fetches the current X value of this NavigationNode.
     * @return X coordinate
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Fetches the current Y value of this NavigationNode.
     * @return Y coordinate
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Fetches the current Z value of this NavigationNode.
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
    public NavigationNode add(int x, int y, int z) {
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
    public NavigationNode  remove(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Adds a NavigationNode to this NavigationNode.
     * @param node NavigationNode to add
     * @return this class, for chaining
     */
    @NotNull
    public NavigationNode add(@NotNull NavigationNode node) {
        return add(node.x, node.y, node.z);
    }

    /**
     * Adds a location to this NavigationNode.
     * @param loc Location to add
     * @return this class, for chaining
     */
    @NotNull
    public NavigationNode add(@NotNull Location loc) {
        return add(new NavigationNode(loc));
    }

    /**
     * Adds an entity's location to this NavigationNode.
     * @param en Entity to add
     * @return this class, for chaining
     */
    @NotNull
    public NavigationNode add(@NotNull Entity en) {
        return add(en.getLocation());
    }

    /**
     * Removes a Location from this NavigationNode.
     * @param loc Location to remove
     * @return this class, for chaining
     */
    @NotNull
    public NavigationNode remove(@NotNull Location loc) {
        return remove(new NavigationNode(loc));
    }

    /**
     * Removes an entity's location from this NavigationNode.
     * @param en Entity to remove
     * @return this class, for chaining
     */
    @NotNull
    public NavigationNode remove(@NotNull Entity en) {
        return remove(en.getLocation());
    }

    /**
     * Removes a NavigationNode from this NavigationNode.
     * @param node NavigationNode to remove
     * @return this class, for chaining
     */
    @NotNull
    public NavigationNode remove(@NotNull NavigationNode node) {
        return remove(node.x, node.y, node.z);
    }
    
    /**
     * Converts this NavigationNode to a Bukkit Location.
     * @param w World to use
     * @return Bukkit Location created
     */
    @NotNull
    public Location toLocation(@Nullable World w) {
        return new Location(w, x, y, z);
    }

}
