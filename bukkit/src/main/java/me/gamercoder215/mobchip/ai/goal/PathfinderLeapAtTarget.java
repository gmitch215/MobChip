package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to leap at its target
 */
public final class PathfinderLeapAtTarget extends Pathfinder {

    private float y;

    /**
     * Constructs a PathfinderLeapAtTarget from a NMS LeapAtTargetGoal.
     * @param g Goal to use
     */
    public PathfinderLeapAtTarget(@NotNull LeapAtTargetGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        this.y = Pathfinder.getFloat(g, "c");
    }

    /**
     * Constructs a PathfinderLeapAtTarget with a Y of 3.
     * @param m Mob to use
     */
    public PathfinderLeapAtTarget(@NotNull Mob m) {
        this(m, 3);
    }  

    /**
     * Constructs a PathfinderLeapAtTarget.
     * @param m Mob to use
     * @param y Height to use while leaping
     */
    public PathfinderLeapAtTarget(@NotNull Mob m, float y) {
        super(m);
        this.y = y;
    }

    /**
     * Fetches the Y value height while leaping.
     * @return Y value height
     */
    public float getY() {
        return this.y;
    }

    /**
     * Sets the Y value height while leaping.
     * @param y New Y Value Height
     */
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public LeapAtTargetGoal getHandle() {
        return new LeapAtTargetGoal(nmsEntity, y);
    }





}