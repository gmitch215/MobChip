package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a special Ocelot Attack
 */
public final class PathfinderOcelotAttack extends Pathfinder {

    /**
     * Constructs a PathfinderOcelotAttack.
     * @param m Ocelot to use
     */
    public PathfinderOcelotAttack(@NotNull Mob m) {
        super(m);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalOcelotAttack";
    }
}
