package me.gamercoder215.mobchip.ai.navigation;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.level.pathfinder.Node;

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
    public float distance(@NotNull NavigationNode node) {
        return getHandle().distanceTo(node.getHandle());
    }
    
    /**
     * Gets the distance of this NavigationNode to another Location.
     * @param loc Location to use
     * @return distance
     */
    public float distance(@NotNull Location loc) {
        return getHandle().distanceTo(ChipConversions.convertType(loc));
    }
    
    /**
     * Gets the distance of this NavigationNode to another Entity.
     * @param en Entity to use
     * @return distance
     */
    public float distance(@NotNull Entity en) {
        return distance(en.getLocation());
    }
    
    /**
     * Gets the distance, squared, of this NavigationNode to another NavigationNode.
     * @param node NavigationNode to use
     * @return distance squared
     */
    public float distanceSquared(@NotNull NavigationNode node) {
        return getHandle().distanceToSqr(node.getHandle());
    }
    
    /**
     * Gets the distance, squared, of this NavigationNode to another Location.
     * @param loc Location to use
     * @return distance squared
     */
    public float distanceSquared(@NotNull Location loc) {
        return getHandle().distanceToSqr(ChipConversions.convertType(loc));
    }
    
    /**
     * Gets the distance, squared, of this NavigationNode to another Entity.
     * @param en Entity to use
     * @return distance squared
     */
    public float distanceSquared(@NotNull Entity en) {
        return distance(en.getLocation());
    }

    /**
     * Get the NMS Representation of this Navigation Node.
     * @return NMS Node
     */
    public Node getHandle() {
        return new Node(x, y, z);
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
    public NavigationNode remove(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
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
