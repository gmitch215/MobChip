package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

/**
 * Represents a Pathfinder for an Entity to move to another block
 */
public final class PathfinderMoveToBlock extends Pathfinder implements SpeedModifier, Ranged {

    /**
     * Default Range to find Block (25)
     */
    public static final int DEFAULT_RANGE = 25;

    private int range;
    private Predicate<Location> validBlock;
    private double speedMod;

    /**
     * Constructs a PathfinderMoveToBlock from a NMS MoveToBlockGoal.
     * @param g Goal to use
     */
    public PathfinderMoveToBlock(@NotNull MoveToBlockGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        this.range = Pathfinder.getInt(g, "l");
        this.validBlock = l -> {
            try {
                Method m = MoveToBlockGoal.class.getDeclaredMethod("a", LevelReader.class, BlockPos.class);
                m.setAccessible(true);
                
                return (boolean) m.invoke(g, ChipConversions.convertType(l.getWorld()), new BlockPos(l.getX(), l.getY(), l.getZ()));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
        this.speedMod = g.speedModifier;
    }

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
     * Constructs a PathfinderMoveToBlock.
     * @param c Creature to use
     * @param validBlock Function to check if the location is valid
     * @param speedMod Speed Modifier while moving
     * @param range Range of blocks to look
     */
    public PathfinderMoveToBlock(@NotNull Creature c, @Nullable Predicate<Location> validBlock, double speedMod, int range) {
        super(c);

        this.speedMod = speedMod;
        this.validBlock = (validBlock == null ? l -> true : validBlock);
        this.range = range;
    }

    @Override
    public float getRange() {
        return this.range;
    }

    @Override
    public void setRange(float range) {
        if (range > Integer.MAX_VALUE) throw new IllegalArgumentException("Range is an integer");

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
    public MoveToBlockGoal getHandle() {
        return new MoveToBlockGoal((PathfinderMob) nmsEntity, speedMod, range) {

            @Override
            protected boolean isValidTarget(LevelReader w, BlockPos p) {
                return validBlock.test(new Location(ChipConversions.convertType(w.getWorldBorder().world), p.getX(), p.getY(), p.getZ()));
            }
            
        };
    }

}
