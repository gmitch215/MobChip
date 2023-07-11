package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to remove a block
 */
public final class PathfinderRemoveBlock extends Pathfinder implements SpeedModifier {

    private double speedMod;
    private Material toRemove;
    private int verticalSearchRange;

    /**
     * Constructs a PathfinderRemoveBlock.
     * @param c Creature to use
     * @param remove Block to remove
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Block remove) {
        this(c, remove, 1);
    }

    /**
     * Constructs a PathfinderRemoveBlock with a vertical search range of 1.
     * @param c Creature to use
     * @param remove Block to remove
     * @param speedMod Speed Modifier while moving
     * @throws IllegalArgumentException if block data is null
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Block remove, double speedMod) {
        this(c, remove, speedMod, 1);
    }

    /**
     * Constructs a PathfinderRemoveBlock.
     * @param c Creature to use
     * @param remove Block to remove
     * @param speedMod Speed Modifier while moving
     * @param verticalSearchRange Vertical search range
     * @throws IllegalArgumentException if block is null, differing worlds, or range is not positive
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Block remove, double speedMod, int verticalSearchRange) {
        super(c);
        if (remove == null) throw new IllegalArgumentException("Block cannot be null");
        if (!(remove.getWorld().getUID().equals(c.getWorld().getUID()))) throw new IllegalArgumentException("Differing worlds: Creature[" + c.getWorld().getName() + "] not matching Block[" + remove.getWorld().getName() + "]");
        if (verticalSearchRange < 1) throw new IllegalArgumentException("Vertical search range must be positive");

        this.toRemove = remove.getType();
        this.speedMod = speedMod;
    }

    /**
     * Constructs a PathfinderRemoveBlock
     * @param c Creature to use
     * @param remove Material to remove
     * @throws IllegalArgumentException if block is null or differing worlds
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Material remove) throws IllegalArgumentException {
        this(c, remove, 1);
    }

    /**
     * Constructs a PathfinderRemoveBlock with a vertical search range of 1.
     * @param c Creature to use
     * @param remove Material to remove
     * @param speedMod Speed Modifier while moving
     * @throws IllegalArgumentException if material is null
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Material remove, double speedMod) throws IllegalArgumentException {
        this(c, remove, speedMod, 1);
    }

    /**
     * Constructs a PathfinderRemoveBlock.
     * @param c Creature to use
     * @param remove Material to remove
     * @param speedMod Speed Modifier while moving
     * @param verticalSearchRange Vertical search range
     * @throws IllegalArgumentException if material is null or range is not positive
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Material remove, double speedMod, int verticalSearchRange) throws IllegalArgumentException {
        super(c);
        if (remove == null) throw new IllegalArgumentException("Material cannot be null");
        if (verticalSearchRange < 1) throw new IllegalArgumentException("Vertical search range must be positive");

        this.toRemove = remove;
        this.speedMod = speedMod;
        this.verticalSearchRange = verticalSearchRange;
    }

    /**
     * Gets the Material to remove.
     * @return Material to remove
     */
    @NotNull
    public Material getBlock() {
        return this.toRemove;
    }

    /**
     * Sets the Block to remove.
     * @param remove Block to remove
     * @throws IllegalArgumentException if block is null or differing worlds
     */
    public void setBlock(@NotNull Block remove) throws IllegalArgumentException {
        if (remove == null) throw new IllegalArgumentException("Block cannot be null");
        if (!(remove.getWorld().getName().equals(entity.getWorld().getName()))) throw new IllegalArgumentException("Differing worlds: Creature[" + entity.getWorld().getName() + "] not matching Block[" + remove.getWorld().getName() + "]");

        setBlock(remove.getType());
    }

    /**
     * Sets the Material to remove.
     * @param remove Material to remove
     * @throws IllegalArgumentException if block data is null
     * @since 1.9.1
     */
    public void setBlock(@NotNull Material remove) throws IllegalArgumentException {
        if (remove == null) throw new IllegalArgumentException("Block cannot be null");
        this.toRemove = remove;
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
     * Gets the vertical search range for looking for the specified Material.
     * @return Vertical search range
     * @since 1.9.1
     */
    public int getVerticalSearchRange() {
        return verticalSearchRange;
    }

    /**
     * Sets the vertical search range for looking for the specified Material.
     * @param verticalSearchRange Vertical search range
     * @since 1.9.1
     */
    public void setVerticalSearchRange(int verticalSearchRange) {
        this.verticalSearchRange = verticalSearchRange;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalRemoveBlock";
    }
}
