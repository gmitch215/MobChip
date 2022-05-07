package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

/**
 * Represents a Pathfinder for a Tamable Animal to follow its owner
 */
public final class PathfinderFollowOwner extends Pathfinder implements SpeedModifier {

    private double speedMod;
    private float stopDist;
    private float startDist;
    private boolean canFly;
    
    /**
     * Constructs a PathfinderFollowOwner from a NMS FollowOwnerGoal.
     * @param g NMS Goal to use
     */
    public PathfinderFollowOwner(@NotNull FollowOwnerGoal g) {
        super(Pathfinder.getEntity(g, "e"));

        this.speedMod = Pathfinder.getDouble(g, "h");
        this.stopDist = Pathfinder.getFloat(g, "k");
        this.startDist = Pathfinder.getFloat(g, "l");
        this.canFly = Pathfinder.getBoolean(g, "n");
    }
    
    /**
     * Creates a PathfinderFollowOwner with no speed modifier.
     * @param t Tamable Entity
     * @param startDistance How far away from owner to start walking towards
     * @param stopDistance How far away from owner to stop walking towards
     */
    public PathfinderFollowOwner(@NotNull Tameable t, float startDistance, float stopDistance) {
        this(t, 1, startDistance, stopDistance);
    }

    /**
     * Creates a PathfinderFollowOwner with flying set to true.
     * @param t Tamable to use
     * @param speedMod Speed Modifier while following
     * @param startDistance How far away from owner to start walking towards
     * @param stopDistance How far away from owner to stop walking towards
     */
    public PathfinderFollowOwner(@NotNull Tameable t, double speedMod, float startDistance, float stopDistance) {
        this(t, speedMod, startDistance, stopDistance, true);
    }

    /**
     * Creates a PathfinderFollowOwner.
     * @param t Tamable to use
     * @param speedMod Speed Modifier while following
     * @param startDistance How far away from owner to start walking towards
     * @param stopDistance How far away from owner to stop walking towards
     * @param fly Whether this Tamable can fly to its owner, if able at all
     */
    public PathfinderFollowOwner(@NotNull Tameable t, double speedMod, float startDistance, float stopDistance, boolean fly) {
        super(t);

        this.speedMod = speedMod;
        this.stopDist = stopDistance;
        this.startDist = startDistance;
        this.canFly = fly;
    }

    /**
     * Gets the distance from the owner to start moving towards the owner
     * @return distance needed to start moving
     */
    public float getStartDistance() {
        return this.startDist;
    }

    /**
     * Gets the distance from the owner to stop moving towards the owner
     * @return distance needed to stop moving
     */
    public float getStopDistance() {
        return this.stopDist;
    }

    /**
     * Sets the distance from the owner to start moving towards the owner
     * @param start distance from owner to start moving
     */
    public void setStartDistance(float start) {
        this.startDist = start;
    }

    /**
     * Sets the distance from the owner to stop moving to the owner
     * @param stop distance from owner to stop moving
     */
    public void setStopDistance(float stop) {
        this.stopDist = stop;
    }

    /**
     * Whether this Tame bale can fly to their owner.
     * @return true if tamable can fly, else false
     */
    public boolean canFly() {
        return this.canFly;
    }

    /**
     * Set whether this Tamable should fly to their owner.
     * @param fly true if tamable can fly, else false
     */
    public void setCanFly(boolean fly) {
        this.canFly = fly;
    }

    @Override
    public double getSpeedModifier() {
        return speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public FollowOwnerGoal getHandle() {
        return new FollowOwnerGoal((TamableAnimal) nmsEntity, speedMod, startDist, stopDist, canFly);
    }
    
}
