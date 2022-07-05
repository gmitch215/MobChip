package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Llama;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Llama to follow a Caravan
 */
public final class PathfinderLlamaFollowCaravan extends Pathfinder implements SpeedModifier {
    
    private double speedMod;

    /**
     * Constructs a PathfinderLlamaFollowCaravan with no speed modifier.
     * @param m Llama to use
     */
    public PathfinderLlamaFollowCaravan(@NotNull Llama m) {
        this(m, 1);
    }

    /**
     * Constructs a PathfinderLlamaFollowCaravan.
     * @param m Llama to use
     * @param speedMod Speed Modifier while following
     */
    public PathfinderLlamaFollowCaravan(@NotNull Llama m, double speedMod) {
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
        return "PathfinderGoalLlamaFollow";
    }
}
