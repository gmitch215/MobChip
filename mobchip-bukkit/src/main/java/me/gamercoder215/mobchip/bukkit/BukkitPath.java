package me.gamercoder215.mobchip.bukkit;

import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import net.minecraft.world.level.pathfinder.Path;

/**
 * Represents a Bukkit Navigation Path
 */
public final class BukkitPath extends NavigationPath {
    
    private String name;

    protected BukkitPath(@NotNull Path nms) {
        super(nms);

        this.name = "bukkitpath";
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
