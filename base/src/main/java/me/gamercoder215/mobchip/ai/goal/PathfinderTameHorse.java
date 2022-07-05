package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.AbstractHorse;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for the logic of taming any horse by riding it 
 */
public final class PathfinderTameHorse extends Pathfinder implements SpeedModifier {
    
    private double speedMod;
    
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
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalTame";
    }
}
