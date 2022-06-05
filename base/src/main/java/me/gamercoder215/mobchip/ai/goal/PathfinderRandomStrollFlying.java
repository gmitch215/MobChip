package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;

/**
 * Represents a Pathfinder for a Creature to randomly fly
 */
public final class PathfinderRandomStrollFlying extends PathfinderRandomStroll {

    /**
     * Constructs a PathfinderRandomStrollFlying from a NMS WaterAvoidingRandomFlyingGoal.
     * @param g Goal to use
     */
    public PathfinderRandomStrollFlying(@NotNull WaterAvoidingRandomFlyingGoal g) {
        super(g);
    }

    /**
     * Constructs a PathfinderRandomStrollFlying with no speed modifier.
     * @param c Creature to use
     */
    public PathfinderRandomStrollFlying(@NotNull Creature c) {
        super(c);
    }

    /**
     * Constructs a PathfinderRandomStrollFlying.
     * @param c Creature to use
     * @param speedMod Speed Modifier while flying
     */
    public PathfinderRandomStrollFlying(@NotNull Creature c, double speedMod) {
        super(c, speedMod);
    }

    @Override
    public WaterAvoidingRandomFlyingGoal getHandle() {
        return new WaterAvoidingRandomFlyingGoal((PathfinderMob) nmsEntity, this.getSpeedModifier());
    }


}
