package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Raider;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.PathfindToRaidGoal;

/**
 * Represents a Pathfinder for a Raider to move to a Raid
 */
public final class PathfinderMoveToRaid extends Pathfinder {

    /**
     * Constructs a PathfinderMoveToRaid from a NMS PathfindToRaidGoal.
     * @param g Goal to use
     */
    public PathfinderMoveToRaid(@NotNull PathfindToRaidGoal<?> g) {
        super(Pathfinder.getEntity(g, "c"));
    }

    /**
     * Constructs a PathfinderMoveToRaid.
     * @param m Raider to use
     */
    public PathfinderMoveToRaid(@NotNull Raider m) {
        super(m);
    }

    @Override
    public PathfindToRaidGoal<?> getHandle() {
        return new PathfindToRaidGoal<net.minecraft.world.entity.raid.Raider>((net.minecraft.world.entity.raid.Raider) nmsEntity);
    }
    


}
