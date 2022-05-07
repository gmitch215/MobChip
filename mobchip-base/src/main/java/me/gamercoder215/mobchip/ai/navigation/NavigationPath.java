package me.gamercoder215.mobchip.ai.navigation;

import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents an Immutable List of Nodes
 */
public abstract class NavigationPath implements Iterable<NavigationNode> {
    private final Path handle;
    private final List<NavigationNode> nodes;


    /**
     * Creates a NavigationPath from a NMS Path.
     * @param nms Path to use
     */
    protected NavigationPath(@NotNull Path nms) {
       this.handle = nms; 
       this.nodes = new ArrayList<>();
    }

    /**
     * Gets the current NMS Path represented by this NavigationPath.
     * @return Path Handle
     */
    public final Path getHandle() {
        return this.handle;
    }

    /**
     * Advances this path.
     */
    public abstract void advance();

    /**
     * Whether this NavigationPath is complete.
     * @return true if complete, else false
     */
    public boolean isDone() {
        return this.handle.isDone();
    }

    /**
     * Get the size of this NavigationPath.
     * @return size
     */
    public int size() {
        return nodes.size();
    }

    /**
     * Whether this NavigationPath is empty.
     * @return true if empty, else false
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Whether this Path contains this Navigation Node.
     * @param o NavigationNode
     * @return true if contains, else false
     */
    public boolean contains(@Nullable NavigationNode o) {
        return nodes.contains(o);
    }

    @Override
    @NotNull
    public Iterator<NavigationNode> iterator() {
        return nodes.iterator();
    }

    /**
     * Converts this NavigationPath into an Array of Nodes.
     * @return Array of NavigationNode
     */
    @NotNull
    public NavigationNode[] toArray() {
        return nodes.toArray(new NavigationNode[0]);
    }

    /**
     * Whether this NavigationPath contains all of these Navigation Nodes.
     * @param c Collection to test
     * @return true if contains, else false
     */
    public boolean containsAll(@Nullable Collection<NavigationNode> c) {
        if (c == null) return false;
        return new HashSet<>(nodes).containsAll(c);
    }

    /**
     * Whether this NavigationPath contains all of these Navigation Nodes.
     * @param nodes Nodes to test
     * @return true if contains, else false
     */
    public boolean containsAll(@Nullable NavigationNode... nodes) {
        return containsAll(Arrays.asList(nodes));
    }

    /**
     * Returns the index of this Navigation Node.
     * @param o NavigationNode to fetch
     * @return Index found
     * @see List#indexOf(Object)
     */
    public int indexOf(@Nullable NavigationNode o) {
        return nodes.indexOf(o);
    }

    /**
     * Returns the last index of this Navigation Node.
     * @param o NavigationNode to fetch
     * @return Index found
     * @see List#lastIndexOf(Object)
     */
    public int lastIndexOf(@Nullable NavigationNode o) {
        return nodes.lastIndexOf(o);
    }
    
}
