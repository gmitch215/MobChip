package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.navigation.NavigationNode;
import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a Bukkit Navigation Path
 */
final class BukkitPath implements NavigationPath {
    
    private String name;
    private final Mob m;
    private final Path handle;

    BukkitPath(@NotNull Path nms, @NotNull Mob m) {
        this.m = m;
        this.name = "bukkitpath";
        this.handle = nms;
    }

    private final List<NavigationNode> nodes = new ArrayList<>();

    /**
     * Advances this path.
     */
    @Override
    public void advance() {
        this.getHandle().advance();
        Node n = handle.getNextNode();
        BukkitBrain.getBrain(m).getController().moveTo(n.x, n.y, n.z);
    }

    /**
     * Get this Path's Name.
     * @return this path's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets this Path's Name.
     * @param name this path's new name
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Path getHandle() {
        return this.handle;
    }

    /**
     * Whether this NavigationPath is complete.
     * @return true if complete, else false
     */
    @Override
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
    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Whether this Path contains this Navigation Node.
     * @param o NavigationNode
     * @return true if contains, else false
     */
    @Override
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
    @Override
    public NavigationNode[] toArray() {
        return nodes.toArray(new NavigationNode[0]);
    }

    /**
     * Returns the index of this Navigation Node.
     * @param o NavigationNode to fetch
     * @return Index found
     * @see List#indexOf(Object)
     */
    @Override
    public int indexOf(@Nullable NavigationNode o) {
        return nodes.indexOf(o);
    }

    /**
     * Returns the last index of this Navigation Node.
     * @param o NavigationNode to fetch
     * @return Index found
     * @see List#lastIndexOf(Object)
     */
    @Override
    public int lastIndexOf(@Nullable NavigationNode o) {
        return nodes.lastIndexOf(o);
    }

}
