package me.gamercoder215.mobchip.paper;

import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import net.kyori.adventure.text.Component;
import net.minecraft.world.level.pathfinder.Path;

public final class PaperPath extends NavigationPath {
    
    private Component name;

    PaperPath(@NotNull Path nms) {
        super(nms);

        this.name = Component.translatable("paperpath");
    }

    /**
     * Get this Path's Name.
     * @return this path's name
     */
    public Component name() {
        return this.name;
    }

    /**
     * Sets this Path's Name.
     * @param name this path's new name
     */
    public void name(@NotNull Component name) {
        this.name = name;
    }

}
