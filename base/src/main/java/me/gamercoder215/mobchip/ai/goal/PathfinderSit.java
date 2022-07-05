package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for the logic of a Tamable Animal to sit when told to
 */
public final class PathfinderSit extends Pathfinder {

    /**
     * Constructs a PathfinderSit.
     * @param m Tamable Animal to use
     */
    public PathfinderSit(@NotNull Tameable m) {
        super((Animals) m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.JUMPING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalSit";
    }
}
