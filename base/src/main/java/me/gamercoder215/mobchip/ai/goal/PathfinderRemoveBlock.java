package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to remove a block
 */
public final class PathfinderRemoveBlock extends Pathfinder implements SpeedModifier {

    private double speedMod;
    private Block toRemove;

    /**
     * Constructs a PathfinderRemoveBlock
     * @param c Creature to use
     * @param remove Block to remove
     * @throws IllegalArgumentException if block is null or differing worlds
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Block remove) throws IllegalArgumentException {
        this(c, remove, 1);
    } 

    /**
     * Constructs a PathfinderRemoveBlock.
     * @param c Creature to use
     * @param remove Block to remove
     * @param speedMod Speed Modifier while moving
     * @throws IllegalArgumentException if block is null or differing worlds
     */
    public PathfinderRemoveBlock(@NotNull Creature c, @NotNull Block remove, double speedMod) throws IllegalArgumentException {
        super(c);
        if (remove == null) throw new IllegalArgumentException("Block cannot be null");
        if (!(remove.getWorld().getName().equals(c.getWorld().getName()))) throw new IllegalArgumentException("Differing worlds: Creature[" + c.getWorld().getName() + "] not matching Block[" + remove.getWorld().getName() + "]");

        this.toRemove = remove;
        this.speedMod = speedMod;
    }

    /**
     * Gets the Block to remove.
     * @return Block to remove
     */
    @NotNull
    public Block getBlock() {
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

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalRemoveBlock";
    }
}
