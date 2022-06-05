package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

/**
 * Represents a Pathfinder for a Creature to panic when damaged
 */
public final class PathfinderPanic extends Pathfinder implements SpeedModifier {

    private double speedMod;

    /**
     * Constructs a PathfinderPanic from a NMS PanicGoal.
     * @param g Goal to use
     */
    public PathfinderPanic(@NotNull PanicGoal g) {
        super(Pathfinder.getEntity(g, "b"));

        this.speedMod = Pathfinder.getDouble(g, "c");
    }

    /**
     * Constructs a PathfinderPanic with {@link SpeedModifier#DEFAULT_SPEED_MODIFIER}.
     * @param m Creature to use
     */
    public PathfinderPanic(@NotNull Creature m) {
        this(m, DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Constructs a PathfinderPanic.
     * @param m Creature to use
     * @param speedMod Speed Modifier while panicking
     */
    public PathfinderPanic(@NotNull Creature m, double speedMod) {
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
    public PanicGoal getHandle() {
        return new PanicGoal((PathfinderMob) nmsEntity, speedMod);
    }
    


}
