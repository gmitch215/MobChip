package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Raider;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Raider to move to a Raid
 */
public final class PathfinderMoveToRaid extends Pathfinder {

    /**
     * Constructs a PathfinderMoveToRaid.
     * @param m Raider to use
     */
    public PathfinderMoveToRaid(@NotNull Raider m) {
        super(m);
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalRaid";
    }
}
