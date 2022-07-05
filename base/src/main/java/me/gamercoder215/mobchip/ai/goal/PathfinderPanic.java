package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to panic when damaged
 */
public final class PathfinderPanic extends Pathfinder implements SpeedModifier {

    private double speedMod;

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
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalPanic";
    }
}
