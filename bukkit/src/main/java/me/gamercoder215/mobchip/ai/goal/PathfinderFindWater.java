package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to try and find water
 */
public final class PathfinderFindWater extends Pathfinder {
    
    /**
     * Constructs a PathfinderFindWater from a NMS TryFindWaterGoal.
     * @param g Goal to use
     */
    public PathfinderFindWater(@NotNull TryFindWaterGoal g) {
        super(getCreature(g, "a"));
    }

    /**
     * Constructs a PathfinderFindWater.
     * @param m Creature to use
     */
    public PathfinderFindWater(@NotNull Creature m) {
        super(m);
    }

    @Override
    public TryFindWaterGoal getHandle() {
        return new TryFindWaterGoal((PathfinderMob) nmsEntity);
    }

}
