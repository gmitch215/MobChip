package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to randomly look around
 */
public final class PathfinderRandomLook extends Pathfinder {
    /**
     * Constructs a PathfinderRandomLook.
     * @param m Mob to use
     */
    public PathfinderRandomLook(@NotNull Mob m) {
        super(m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalRandomLookaround";
    }
}
