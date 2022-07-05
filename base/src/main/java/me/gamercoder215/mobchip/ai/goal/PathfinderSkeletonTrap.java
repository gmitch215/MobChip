package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.SkeletonHorse;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that will cause a trap when players get close
 */
public final class PathfinderSkeletonTrap extends Pathfinder {

    /**
     * Constructs a PathfinderSkeletonTraP.
     * @param horse SkeletonHorse to use
     */
    public PathfinderSkeletonTrap(@NotNull SkeletonHorse horse) {
        super(horse);
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalHorseTrap";
    }
}

