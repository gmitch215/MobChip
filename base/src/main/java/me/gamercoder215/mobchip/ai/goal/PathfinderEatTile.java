package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to eat a tile
 */
public final class PathfinderEatTile extends Pathfinder {

    /**
     * Constructs a PathfinderEatTile.
     * @param m Mob to use
     */
    public PathfinderEatTile(@NotNull Mob m) {
        super(m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.JUMPING, PathfinderFlag.LOOKING};
    }

    @Override
    public String getInternalName() { return "PathfinderGoalEatTile"; }
}
