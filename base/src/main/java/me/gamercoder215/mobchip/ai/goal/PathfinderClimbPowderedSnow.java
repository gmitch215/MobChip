package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder to climb on Powdered Snow
 */
public final class PathfinderClimbPowderedSnow extends Pathfinder implements WorldSpecific {

    private World w;

    /**
     * Constructs a PathfinderClimbPowderedSnow with the current world.
     * @param m Mob to use
     */
    public PathfinderClimbPowderedSnow(@NotNull Mob m) {
        this(m, m.getWorld());
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
    public @NotNull World getWorld() {
        return this.w;
    }

    @Override
    public void setWorld(@NotNull World w) throws IllegalArgumentException {
        if (w == null) throw WorldSpecific.WORLD_NULL;
        this.w = w;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.JUMPING};
    }

    @Override
    public String getInternalName() { return "ClimbOnTopOfPowderSnowGoal"; }
}