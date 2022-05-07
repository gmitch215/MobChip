package me.gamercoder215.mobchip.bukkit;

import net.minecraft.world.level.pathfinder.Node;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import net.minecraft.world.level.pathfinder.Path;

/**
 * Represents a Bukkit Navigation Path
 */
public final class BukkitPath extends NavigationPath {
    
    private String name;
    private final Mob m;
    private final Path nms;

    BukkitPath(@NotNull Path nms, @NotNull Mob m) {
        super(nms);

        this.m = m;
        this.name = "bukkitpath";
        this.nms = nms;
    }

    /**
     * Advances this path.
     */
    @Override
    public void advance() {
        this.getHandle().advance();
        Node n = nms.getNextNode();
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

}
