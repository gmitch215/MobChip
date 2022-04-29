package me.gamercoder215.mobchip.ai.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.pathfinder.Path;

/**
 * Represents an Immutable List of Nodes
 */
public abstract class NavigationPath implements Cloneable, Iterable<NavigationNode> {

    private final Path handle;
    private final List<NavigationNode> nodes;

    protected NavigationPath(@NotNull Path nms) {
       this.handle = nms; 
       this.nodes = new ArrayList<>();
    }

    public final Path getHandle() {
        return this.handle;
    }

    public void advance() {
        this.handle.advance();
    }

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
     * Whether or not this NavigationPath is empty.
     * @return true if empty, else false
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Whether or not this Path contains this Navigation Node.
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
     * Whether or not this NavigationPath contains all of these Navigation Nodes.
     * @param c Collection to test
     * @return true if contains, else false
     */
    public boolean containsAll(@Nullable Collection<NavigationNode> c) {
        return nodes.containsAll(c);
    }

    /**
     * Whether or not this NavigationPath contains all of these Navigation Nodes.
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
