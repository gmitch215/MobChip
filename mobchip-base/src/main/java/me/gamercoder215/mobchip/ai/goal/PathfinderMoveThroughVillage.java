package me.gamercoder215.mobchip.ai.goal;

import java.util.function.BooleanSupplier;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;

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
     * Constructs a PathfinderMoveThroughVillage from a NMS MoveThroughVillageGoal.
     * @param g Goal to use
     */
    public PathfinderMoveThroughVillage(@NotNull MoveThroughVillageGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "b");
        this.atNight = Pathfinder.getBoolean(g, "e");
        this.minDistance = Pathfinder.getInt(g, "g");
        this.canUseDoors = Pathfinder.getField(g, "h", BooleanSupplier.class);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with the {@link #DEFAULT_USE_DOORS}.
     * @param c Creature to use
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c) {
        this(c, DEFAULT_USE_DOORS);
    }

    /**
     * Constructs a PathfinderMoveThroughVillage with no speed modifier.
     * @param c Creature to use
     * @param useDoors Suppier called if the entity can use doors
     */
    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors) {
        this(c, useDoors, 1);
    }

    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors, double speedMod) {
        this(c, useDoors, speedMod, DEFAULT_MIN_DISTANCE);
    }
    
    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors, double speedMod, int minDistance) {
        this(c, useDoors, speedMod, minDistance, false);
    }

    public PathfinderMoveThroughVillage(@NotNull Creature c, @NotNull BooleanSupplier useDoors, double speedMod, int minDistance, boolean onlyAtNight) {
        super(c);

        this.atNight = onlyAtNight;
        this.speedMod = speedMod;
        this.minDistance = minDistance;
        this.canUseDoors = useDoors;
    }

    /**
     * Fetches the Function used in whether or not this entity can use doors throughout the Village.
     * @return Function of using doors
     */
    public BooleanSupplier canUseDoors() {
        return this.canUseDoors;
    }

    /**
     * Sets the Function used in whether or not this entity can use doors throughout the Village.
     * @param b Function to use
     */
    public void setCanUseDoors(BooleanSupplier b) {
        this.canUseDoors = b;
    }

    /**
     * Sets whether or not this entity can use doors throughout the Village.
     * @param use true if can use, else false
     */
    public void setCanUseDoors(boolean use) {
        this.canUseDoors = () -> use;
    }

    /**
     * Whether or not it has to be nighttime to roam the Village.
     * @return true if must be night, else false
     */
    public boolean mustBeNight() {
        return this.atNight;
    }

    /**
     * Sets whether or not it has to be nighttime to roam the Village.
     * @param night true if must be night, else false
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

    @Override
    public MoveThroughVillageGoal getHandle() {
        return new MoveThroughVillageGoal((PathfinderMob) nmsEntity, speedMod, atNight, minDistance, canUseDoors);
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


}
