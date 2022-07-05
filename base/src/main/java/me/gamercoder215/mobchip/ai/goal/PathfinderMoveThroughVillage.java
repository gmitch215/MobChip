package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

/**
 * Represents a Pathfinder for a Creature to move throughout a Village
 */
public final class PathfinderMoveThroughVillage extends Pathfinder implements SpeedModifier, Ranged {

    /**
     * Default Minimum Distance from a village to start moving through a village
     */
    public static final int DEFAULT_MIN_DISTANCE = 10;
    /**
     * Default Supplier to Use Doors (true)
     */
    public static final BooleanSupplier DEFAULT_USE_DOORS = () -> true;

    private boolean atNight;
    private double speedMod;
    private int minDistance;
    private BooleanSupplier canUseDoors;

    /**
     * Constructs a PathfinderMoveThroughVillage with the {@link #DEFAULT_USE_DOORS}.
     * @param c Creature to use
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c) {
        this(c, DEFAULT_USE_DOORS);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with a boolean.
     * @param c Creature to use
     * @param useDoors true if mob can use doors, else false
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, boolean useDoors) {
        this(c, () -> useDoors);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with no speed modifier.
     * @param c Creature to use
     * @param useDoors Supplier called if the entity can use doors
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors) {
        this(c, useDoors, 1);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with {@link #DEFAULT_MIN_DISTANCE}.
     * @param c Creature to use
     * @param useDoors Supplier called if the entity can use doors
     * @param speedMod Speed Modifier while moving
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors, double speedMod) {
        this(c, useDoors, speedMod, DEFAULT_MIN_DISTANCE);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with {@link #DEFAULT_MIN_DISTANCE}.
     * @param c Creature to use
     * @param useDoors true if mob can use doors, else false
     * @param speedMod Speed Modifier while moving
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, boolean useDoors, double speedMod) {
        this(c, () -> useDoors, speedMod, DEFAULT_MIN_DISTANCE);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with onlyAtNight set to false
     * @param c Creature to use
     * @param useDoors Supplier called if the entity can use doors
     * @param speedMod Speed Modifier while moving
     * @param minDistance Minimum distance from a village
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors, double speedMod, int minDistance) {
        this(c, useDoors, speedMod, minDistance, false);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with onlyAtNight set to false
     * @param c Creature to use
     * @param useDoors true if mob can use doors, else false
     * @param speedMod Speed Modifier while moving
     * @param minDistance Minimum distance from a village
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, boolean useDoors, double speedMod, int minDistance) {
        this(c, () -> useDoors, speedMod, minDistance, false);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage.
     * @param c Creature to use
     * @param useDoors True if mob can use doors, else false
     * @param speedMod Speed Modifier while moving
     * @param minDistance Minimum distance from a village
     * @param onlyAtNight Whether Creature can only move through village at night
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, boolean useDoors, double speedMod, int minDistance, boolean onlyAtNight) {
        this(c, () -> useDoors, speedMod, minDistance, onlyAtNight);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage.
     * @param c Creature to use
     * @param useDoors Supplier called if the entity can use doors
     * @param speedMod Speed Modifier while moving
     * @param minDistance Minimum distance from a village
     * @param onlyAtNight Whether Creature can only move through village at night
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors, double speedMod, int minDistance, boolean onlyAtNight) {
        super(c);

        this.atNight = onlyAtNight;
        this.speedMod = speedMod;
        this.minDistance = minDistance;
        this.canUseDoors = useDoors;
    }


    /**
     * Fetches the Function used in whether this entity can use doors throughout the Village.
     * @return Function of using doors
     */
    public BooleanSupplier canUseDoors() {
        return this.canUseDoors;
    }

    /**
     * Sets the Function used in whether this entity can use doors throughout the Village.
     * @param b Function to use
     */
    public void setCanUseDoors(@NotNull BooleanSupplier b) {
        this.canUseDoors = b;
    }

    /**
     * Sets whether this entity can use doors throughout the Village.
     * @param use true if entity can use, else false
     */
    public void setCanUseDoors(boolean use) {
        this.canUseDoors = () -> use;
    }

    /**
     * Gets the minimum distance from a village to move through it.
     * @return Minimum distance from a village
     */
    public int getMinDistance() {
        return this.minDistance;
    }

    /**
     * Sets the minimum distance from a village to move through it.
     * @param min Minimum distance from a village
     */
    public void setMinDistance(int min) {
        this.minDistance = min;
    }

    /**
     * Whether it has to be nighttime to roam the Village.
     * @return true if time must be night, else false
     */
    public boolean mustBeNight() {
        return this.atNight;
    }

    /**
     * Sets whether it has to be nighttime to roam the Village.
     * @param night true if time must be night, else false
     */
    public void setMustBeNight(boolean night) {
        this.atNight = night;
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
     * Gets this Range as a Float.
     * @return Range as float
     */
    @Override
    public float getRange() {
        return this.minDistance;
    }

    /**
     * Sets the range needed from a village to start walking to a village.
     * @param range Range, <strong>as an int</strong>
     * @throws IllegalArgumentException if not an integer
     */
    @Override
    public void setRange(float range) throws IllegalArgumentException {
        if (range > Integer.MAX_VALUE) throw new IllegalArgumentException("Range is an integer");

        this.minDistance = (int) Math.floor(range);
    }


    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalMoveThroughVillage";
    }
}
