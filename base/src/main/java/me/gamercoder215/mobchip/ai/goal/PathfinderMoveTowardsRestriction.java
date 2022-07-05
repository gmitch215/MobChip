package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to move towards its restriction center (if a mob has restriction)
 * @see EntityBrain#setRestrictionArea(org.bukkit.Location, int)
 */
public final class PathfinderMoveTowardsRestriction extends Pathfinder implements SpeedModifier {
    
    private double speedMod;
    /**
     * Constructs a PathfinderMoveTowardsRestriction with no speed modifier.
     * @param c Creature to use
     */
    public PathfinderMoveTowardsRestriction(@NotNull Creature c) {
        this(c, 1);
    }

    /**
     * Constructs a PathfinderMoveTowardsRestriction.
     * @param c Creature to use
     * @param speedMod Speed Modifier while moving
     */
    public PathfinderMoveTowardsRestriction(@NotNull Creature c, double speedMod) {
        super(c);

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
        return "PathfinderGoalMoveTowardsRestriction";
    }
}
