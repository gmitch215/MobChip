package me.gamercoder215.mobchip.abstraction.v1_20_R4;

import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class NavigationPath1_20_R4 implements NavigationPath {
    private String name;
    private final Mob m;
    private final Path handle;
    private double speedMod;

    NavigationPath1_20_R4(@NotNull Path nms, @NotNull Mob m, double speedMod) {
        this.m = m;
        this.name = "bukkitpath";
        this.handle = nms;
        this.speedMod = speedMod;
    }

    private final List<Position> nodes = new ArrayList<>();

    /**
     * Advances this path.
     */
    @Override
    public void advance() {
        if (isDone()) throw new IllegalArgumentException("Path is already done");

        Node n = handle.getNextNode();
        new EntityController1_20_R4(m).moveTo(n.x, n.y, n.z, speedMod);

        this.getHandle().advance();
    }

    /**
     * Get this Path's Name.
     *
     * @return this path's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets this Path's Name.
     *
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
     *
     * @return true if complete, else false
     */
    @Override
    public boolean isDone() {
        return this.handle.isDone();
    }

    /**
     * Get the size of this NavigationPath.
     *
     * @return size
     */
    public int size() {
        return nodes.size();
    }

    /**
     * Whether this NavigationPath is empty.
     *
     * @return true if empty, else false
     */
    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Whether this Path contains this Navigation Node.
     *
     * @param o Position
     * @return true if contains, else false
     */
    @Override
    public boolean contains(@Nullable Position o) {
        return nodes.contains(o);
    }

    @Override
    @NotNull
    public Iterator<Position> iterator() {
        return nodes.iterator();
    }

    /**
     * Converts this NavigationPath into an Array of Nodes.
     *
     * @return Array of Position
     */
    @NotNull
    @Override
    public Position[] toArray() {
        return nodes.toArray(new Position[0]);
    }

    /**
     * Returns the index of this Navigation Node.
     *
     * @param o Position to fetch
     * @return Index found
     * @see List#indexOf(Object)
     */
    @Override
    public int indexOf(@Nullable Position o) {
        return nodes.indexOf(o);
    }

    /**
     * Returns the last index of this Navigation Node.
     *
     * @param o Position to fetch
     * @return Index found
     * @see List#lastIndexOf(Object)
     */
    @Override
    public int lastIndexOf(@Nullable Position o) {
        return nodes.lastIndexOf(o);
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }
}
