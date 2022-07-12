package me.gamercoder215.mobchip.ai.navigation;

import me.gamercoder215.mobchip.util.Position;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a Path for an Entity
 */
public interface NavigationPath extends Iterable<Position> {

    /**
     * Whether this NavigationPath is complete.
     * @return true if complete, else false
     */
    boolean isDone();

    /**
     * Whether this NavigationPath contains all of these Navigation Nodes.
     * @param coll Collection to test
     * @return true if contains, else false
     */
    default boolean containsAll(Collection<? extends Position> coll) {
        AtomicBoolean state = new AtomicBoolean(true);
        for (Position n : coll)
            if (!contains(n)) { state.set(false); break; }

        return state.get();
    }

    /**
     * Whether this Path contains this Navigation Node.
     * @param node Position
     * @return true if contains, else false
     */
    boolean contains(@Nullable Position node);

    /**
     * Whether this NavigationPath is empty.
     * @return true if empty, else false
     */
    boolean isEmpty();

    /**
     * Advances this path.
     */
    void advance();

    /**
     * Converts this NavigationPath into an Array of Nodes.
     * @return Array of Position
     */
    Position[] toArray();

    /**
     * Returns the index of this Navigation Node.
     * @param o Position to fetch
     * @return Index found
     * @see List#indexOf(Object)
     */
    int indexOf(@Nullable Position o);

    /**
     * Returns the last index of this Navigation Node.
     * @param o Position to fetch
     * @return Index found
     * @see List#lastIndexOf(Object)
     */
    int lastIndexOf(@Nullable Position o);

    /**
     * Whether this NavigationPath contains all of these Navigation Nodes.
     * @param nodes Nodes to test
     * @return true if contains, else false
     */
    default boolean containsAll(@Nullable Position... nodes) {
        return containsAll(Arrays.asList(nodes));
    }


}
