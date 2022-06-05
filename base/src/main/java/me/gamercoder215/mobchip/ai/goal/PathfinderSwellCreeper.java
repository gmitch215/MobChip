package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creeper;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.SwellGoal;

/**
 * Represents the Pathfinder for the logic of a Creeper to swell before exploding
 */
public final class PathfinderSwellCreeper extends Pathfinder {

    /**
     * Constructs a PathfinderSwellCreeper from a NMS SwellGoal.
     * @param g Goal to use
     */
    public PathfinderSwellCreeper(@NotNull SwellGoal g) {
        super(Pathfinder.getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderSwellCreeper.
     * @param m Creeper to use
     */
    public PathfinderSwellCreeper(@NotNull Creeper m) {
        super(m);
    }

    @Override
    public SwellGoal getHandle() {
        return new SwellGoal((net.minecraft.world.entity.monster.Creeper) nmsEntity);
    }

}
