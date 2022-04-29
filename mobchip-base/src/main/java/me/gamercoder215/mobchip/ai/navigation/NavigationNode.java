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
public final class NavigationNode implements Cloneable {
    
    private int x;
    private int y;
    private int z;

    public NavigationNode(@NotNull Entity en) {
        this(en.getLocation());
    }

    public NavigationNode(@NotNull Location loc) {
        this(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public NavigationNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float distance(@NotNull NavigationNode node) {
        return getHandle().distanceTo(node.getHandle());
    }

    public float distance(@NotNull Location loc) {
        return getHandle().distanceTo(ChipConversions.convertType(loc));
    }

    public float distance(@NotNull Entity en) {
        return distance(en.getLocation());
    }

    public float distanceSquared(@NotNull NavigationNode node) {
        return getHandle().distanceToSqr(node.getHandle());
    }

    public float distanceSquared(@NotNull Location loc) {
        return getHandle().distanceToSqr(ChipConversions.convertType(loc));
    }

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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

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
     * Removes a amount to each coordinate.
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

    public Location toLocation(@Nullable World w) {
        return new Location(w, x, y, z);
    }

}
