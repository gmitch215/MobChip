package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.level.Level;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * Represents a Pathfinder to climb on Powdered Snow
 */
public final class PathfinderClimbPowderedSnow extends Pathfinder implements WorldSpecific {

    private World w;

    /**
     * Constructs a PathfinderClimbPowderedSnow from a NMS Goal.
     * @param g NMS Goal
     */
    public PathfinderClimbPowderedSnow(@NotNull ClimbOnTopOfPowderSnowGoal g) {
        super(Pathfinder.getEntity(g, "a"));

        try {
            Field a = ClimbOnTopOfPowderSnowGoal.class.getDeclaredField("b");
            a.setAccessible(true);
            this.w = MobChipUtil.convert((Level) a.get(g));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a PathfinderClimbPowderedSnow.
     * @param m Mob to use
     * @param w World to use
     * @throws IllegalArgumentException if world is null
     */
    public PathfinderClimbPowderedSnow(@NotNull Mob m, @NotNull World w) throws IllegalArgumentException {
        super(m);
        if (w == null) throw WorldSpecific.WORLD_NULL;
        this.w = w;
    }

    @Override
    public ClimbOnTopOfPowderSnowGoal getHandle() {
        return new ClimbOnTopOfPowderSnowGoal(nmsEntity, MobChipUtil.convert(w));
    }

    @Override
    public @NotNull World getWorld() {
        return this.w;
    }

    @Override
    public void setWorld(@NotNull World w) throws IllegalArgumentException {
        if (w == null) throw WorldSpecific.WORLD_NULL;
        this.w = w;
    }

}