package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to avoid the sun or to extinguish a fire.
 * <p>
 * This is an advanced version of {@link PathfinderRestrictSun}. The other pathfinder will only have the Creature avoid the sun, with not speed modifier included.
 */
public final class PathfinderFleeSun extends Pathfinder implements SpeedModifier {

    private double speedMod;

    /**
     * Creates a PathfinderFleeSun from a NMS FleeSunGoal.
     * @param g Goal to use
     */
    public PathfinderFleeSun(@NotNull FleeSunGoal g) {
        super(Pathfinder.getCreature(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "e");
    }
    
    /**
     * Creates a PathfinderFleeSun with a default speed modifier.
     * @param m Creature to use
     */
    public PathfinderFleeSun(@NotNull Creature m) {
        this(m, DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Creates a PathfinderFleeSun.
     * @param m Creature to use
     * @param speedMod Speed Modifier of fleeing
     */
    public PathfinderFleeSun(@NotNull Creature m, double speedMod) {
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
    public FleeSunGoal getHandle() {
        return new FleeSunGoal((PathfinderMob) nmsEntity, speedMod);
    }
    
}
