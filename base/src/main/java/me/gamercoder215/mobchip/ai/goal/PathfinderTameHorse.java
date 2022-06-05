package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.AbstractHorse;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;

/**
 * Represents a Pathfinder for the logic of taming any horse by riding it 
 */
public final class PathfinderTameHorse extends Pathfinder implements SpeedModifier {
    
    private double speedMod;

    /**
     * Construct a PathfinderTameHorse from a NMS RunAroundLikeCrazyGoal.
     * @param g Goal to use
     */
    public PathfinderTameHorse(@NotNull RunAroundLikeCrazyGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "b");
    }
    
    /**
     * Constructs a PathfinderTameHorse with no speed modifier.
     * @param m Horse to use
     */
    public PathfinderTameHorse(@NotNull AbstractHorse m) {
        this(m, 1);
    }

    /**
     * Constructs a PathfinderTameHorse.
     * @param m Horse to use
     * @param speedMod Speed Modifier while the horse is moving, when attempting to be tamed
     */
    public PathfinderTameHorse(@NotNull AbstractHorse m, double speedMod) {
        super(m);
        this.speedMod = speedMod;
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public RunAroundLikeCrazyGoal getHandle() {
        return new RunAroundLikeCrazyGoal((net.minecraft.world.entity.animal.horse.AbstractHorse) nmsEntity, speedMod);
    }

}
