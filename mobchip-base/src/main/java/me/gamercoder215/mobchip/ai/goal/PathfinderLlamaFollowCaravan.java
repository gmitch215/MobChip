package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Llama;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.LlamaFollowCaravanGoal;

/**
 * Represents a Pathfinder for a Llama to follow a Caravan
 */
public final class PathfinderLlamaFollowCaravan extends Pathfinder implements SpeedModifier {
    
    private double speedMod;

    /**
     * Constructs a PathfinderLLamaFollowCaravan from a NMS LlamaFollowCaravanGoal.
     * @param g Goal to use
     */
    public PathfinderLlamaFollowCaravan(@NotNull LlamaFollowCaravanGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "b");
    }

    public PathfinderLlamaFollowCaravan(@NotNull Llama m) {
        this(m, 1);
    }

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
    public LlamaFollowCaravanGoal getHandle() {
        return new LlamaFollowCaravanGoal((net.minecraft.world.entity.animal.horse.Llama) nmsEntity, speedMod);
    }

        

}
