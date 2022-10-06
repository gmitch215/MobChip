package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Represents a Pathfinder for an Entity to move to another block
 */
public final class PathfinderMoveToBlock extends Pathfinder implements SpeedModifier, Ranged {

    /**
     * Default Range to find Block (25)
     */
    public static final int DEFAULT_RANGE = 25;

    private int range;
    private int vrange;
    private Predicate<Location> validBlock;
    private double speedMod;

    /**
     * Constructs a PathfinderMoveToBlock with the default Valid Block Predicate.
     * @param c Creature to use
     */
    public PathfinderMoveToBlock(@NotNull Creature c) {
        this(c, null);
    }

    /**
     * Constructs a PathfinderMoveToBlock with the Default Speed Modifier.
     * @param c Creature to use
     * @param validBlock Function to check if the location is valid
     */
    public PathfinderMoveToBlock(@NotNull Creature c, @Nullable Predicate<Location> validBlock) {
        this(c, validBlock, DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Constructs a PathfinderMoveToBlock with the {@link #DEFAULT_RANGE};
     * @param c Creature to use
     * @param validBlock Function to check if the location is valid
     * @param speedMod Speed Modifier while moving
     */
    public PathfinderMoveToBlock(@NotNull Creature c, @Nullable Predicate<Location> validBlock, double speedMod) {
        this(c, validBlock, speedMod, DEFAULT_RANGE);
    }

    /**
     * Constructs a PathfinderMoveToBlock with horizontal and vertical ranges identical.
     * @param c Creature to use
     * @param validBlock Function to check if the location is valid
     * @param speedMod Speed Modifier while moving
     * @param range Range of blocks to look
     */
    public PathfinderMoveToBlock(@NotNull Creature c, @Nullable Predicate<Location> validBlock, double speedMod, int range) {
        this(c, validBlock, speedMod, range, range);
    }

    /**
     * Constructs a PathfinderMoveToBlock.
     * @param c Creature to use
     * @param validBlock Function to check if the location is valid
     * @param speedMod Speed Modifier while moving
     * @param range Range of blocks to look
     * @param vrange Vertical range of blocks to look
     */
    public PathfinderMoveToBlock(@NotNull Creature c, @Nullable Predicate<Location> validBlock, double speedMod, int range, int vrange) {
        super(c);

        this.speedMod = speedMod;
        this.validBlock = (validBlock == null ? l -> true : validBlock);
        this.range = range;
        this.vrange = vrange;
    }

    @Override
    public float getRange() {
        return this.range;
    }

    /**
     * Fetches the current vertical range of this Pathfinder.
     * @return Vertical Range
     */
    public float getVerticalRange() {
        return this.vrange;
    }

    /**
     * Sets the vertical range of this Pathfinder.
     * @param vrange Vertical Range
     */
    public void setVerticalRange(int vrange) {
        this.vrange = vrange;
    }

    /**
     * @throws IllegalArgumentException if range is not an integer
     */
    @Override
    public void setRange(float range) throws IllegalArgumentException {
        if (range > Integer.MAX_VALUE) throw new IllegalArgumentException("Range must be an integer");

        this.range = (int) Math.floor(range);
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    /**
     * Fetches the function called when checking if this block is valid, or a default of true if not set.
     * @return Function called when checking if block is valid
     */
    @NotNull
    public Predicate<Location> getValidBlock() {
        return this.validBlock;
    }

    /**
     * Sets the function called when checking if this block is valid, can be null
     * @param valid Function called when checking if block is valid
     */
    public void setValidBlock(@Nullable Predicate<Location> valid) {
        this.validBlock = (valid == null ? l -> true : valid);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.JUMPING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalGotoTarget";
    }
}
