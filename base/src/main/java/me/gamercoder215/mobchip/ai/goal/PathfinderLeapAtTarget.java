package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to leap at its target
 */
public final class PathfinderLeapAtTarget extends Pathfinder {

    private float height;

    /**
     * Constructs a PathfinderLeapAtTarget with a Y of 3.
     * @param m Mob to use
     */
    public PathfinderLeapAtTarget(@NotNull Mob m) {
        this(m, 3);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.JUMPING, PathfinderFlag.MOVEMENT };
    }

    /**
     * Constructs a PathfinderLeapAtTarget.
     * @param m Mob to use
     * @param y Height to use while leaping
     */
    public PathfinderLeapAtTarget(@NotNull Mob m, float y) {
        super(m);
        this.height = y;
    }

    /**
     * Fetches the Y value height while leaping.
     * @return Y value height
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Sets the Y value height while leaping.
     * @param height New Y Value Height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalLeapAtTarget";
    }
}