package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to follow another Mob
 */
public final class PathfinderFollowMob extends Pathfinder implements SpeedModifier, Ranged {

    private float range;
    private double speedMod;
    private float stopDistance;
    /**
     * Constructs a PathfinderFollowMob with a stop distance of 1.
     * @param m Mob to use
     */
    public PathfinderFollowMob(@NotNull Mob m) {
        this(m, 1);
    }

    /**
     * Constructs a PathfinderFollowMob with {@link Ranged#DEFAULT_LOOK_RANGE}.
     * @param m Mob to use
     * @param stopDistance Distance from the mob to stop following
     */
    public PathfinderFollowMob(@NotNull Mob m, float stopDistance) {
        this(m, stopDistance, DEFAULT_LOOK_RANGE);
    }

    /**
     * Constructs a PathfinderFollowMob with {@link SpeedModifier#DEFAULT_SPEED_MODIFIER}.
     * @param m Mob to use
     * @param stopDistance Distance from the mob to stop following
     * @param lookRange Range of looking for a mob to follow
     */
    public PathfinderFollowMob(@NotNull Mob m, float stopDistance, float lookRange) {
        this(m, DEFAULT_SPEED_MODIFIER, stopDistance, lookRange);
    }

    /**
     * Constructs a PathfinderFollowMob.
     * @param m Mob to use
     * @param speedMod Speed Modifier to use when following
     * @param stopDistance Distance from the mob to stop following
     * @param lookRange Range of looking for a mob to follow
     */
    public PathfinderFollowMob(@NotNull Mob m, double speedMod, float stopDistance, float lookRange) {
        super(m);

        this.range = lookRange;
        this.speedMod = speedMod;
        this.stopDistance = stopDistance;
    }

    /**
     * Gets the Distance needed to stop following.
     * @return Distance to stop following.
     */
    public float getStopDistance() {
        return this.stopDistance;
    }

    /**
     * Sets the Distance needed to stop following.
     * @param stop distance to stop following an entity
     */
    public void setStopDistance(float stop) {
        this.stopDistance = stop;
    }
    
    @Override
    public float getRange() {
        return this.range;
    }

    @Override
    public void setRange(float range) {
        this.range = range;
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
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalFollowEntity";
    }
}
