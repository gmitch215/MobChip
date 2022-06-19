package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for the logic of a Tamable Animal to sit when told to
 */
public final class PathfinderSit extends Pathfinder {
    
    /**
     * Constructs a PathfinderSit from a NMS SitWhenOrderedToGOal.
     * @param g Goal to use
     */
    public PathfinderSit(@NotNull SitWhenOrderedToGoal g) {
        super(Pathfinder.getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderSit.
     * @param m Tamable Animal to use
     */
    public PathfinderSit(@NotNull Tameable m) {
        super(m);
    }

    @Override
    public SitWhenOrderedToGoal getHandle() {
        return new SitWhenOrderedToGoal((TamableAnimal) nmsEntity);
    }
    
}
