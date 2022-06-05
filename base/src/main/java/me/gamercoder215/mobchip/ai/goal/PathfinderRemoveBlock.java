package me.gamercoder215.mobchip.ai.goal;

import java.lang.reflect.Field;

import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.BlockGetter;

/**
 * Represents a Pathfinder for a Creature to remove a block
 */
public final class PathfinderRemoveBlock extends Pathfinder implements SpeedModifier {

    private double speedMod;
    private Block toRemove;

    /**
     * Constructs a PathfinderRemoveBlock from a NMS RemoveBlockGoal.
     * @param g Goal to use
     */
    public PathfinderRemoveBlock(@NotNull RemoveBlockGoal g) {
        super(Pathfinder.getCreature(g, "h"));

        try {
            Field a = MoveToBlockGoal.class.getDeclaredField("b");
            a.setAccessible(true);
            this.speedMod = a.getDouble(g);

            net.minecraft.world.level.block.Block b = Pathfinder.getField(g, "g", net.minecraft.world.level.block.Block.class);
            BlockPos p = getPosWithBlock(b, nmsEntity.blockPosition(), nmsEntity.level);
            if (p != null)
                this.toRemove = entity.getWorld().getBlockAt(p.getX(), p.getY(), p.getZ());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BlockPos getPosWithBlock(net.minecraft.world.level.block.Block block, BlockPos bp, BlockGetter g) {
        if (g.getBlockState(bp).is(block)) {
            return bp;
        } else {
            BlockPos[] bp1 = new BlockPos[]{bp.below(), bp.west(), bp.east(), bp.north(), bp.south(), bp.below().below()};
            int i = bp1.length;

            for (BlockPos bps : bp1) {
                if (g.getBlockState(bps).is(block)) {
                    return bps;
                }
            }
 
            return null;
       }
    }

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

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public RemoveBlockGoal getHandle() {
        return new RemoveBlockGoal(ChipConversions.convertType(entity.getWorld()).getBlockState(new BlockPos(toRemove.getX(), toRemove.getY(), toRemove.getZ())).getBlock(), (PathfinderMob) nmsEntity, speedMod, (int) Math.floor(entity.getLocation().distance(toRemove.getLocation())) + 1);
    }
    
}
