package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.animal.horse.SkeletonTrapGoal;
import org.bukkit.entity.SkeletonHorse;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder that will cause a trap when players get close
 */
public final class PathfinderSkeletonTrap extends Pathfinder {

    /**
     * Constructs a PathfinderClimbPowderedSnow from a NMS Goal.
     * @param g NMS Goal
     */
    public PathfinderSkeletonTrap(@NotNull SkeletonTrapGoal g) {
        super(Pathfinder.getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderSkeletonTraP.
     * @param horse SkeletonHorse to use
     */
    public PathfinderSkeletonTrap(@NotNull SkeletonHorse horse) {
        super(horse);
    }

    @Override
    public @NotNull SkeletonTrapGoal getHandle() {
        return null;
    }
}

