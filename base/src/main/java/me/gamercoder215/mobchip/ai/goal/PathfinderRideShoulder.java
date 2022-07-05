package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Parrot;
import org.jetbrains.annotations.NotNull;

/**
 * Pathfinder for Shoulder-Riding Entities, like Parrots, to ride on a Player's shoulder
 */
public final class PathfinderRideShoulder extends Pathfinder {

    /**
     * Constructs a PathfinderRideShoulder.
     * @param p Parrot to use
     */
    public PathfinderRideShoulder(@NotNull Parrot p) {
        super(p);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalPerch";
    }
}
