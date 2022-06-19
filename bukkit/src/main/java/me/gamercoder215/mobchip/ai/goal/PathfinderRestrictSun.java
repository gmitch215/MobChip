package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to avoid the Sun.
 * <p>
 * This Pathfinder is a more basic version of {@link PathfinderFleeSun}. There is no speed modifier included, and it will only avoid the sun. The other pathfinder will include if the Creature is on fire and if they do not have a helmet, and will also pathfind the entity to the nearest extinguish source.
 */
public final class PathfinderRestrictSun extends Pathfinder {

    /**
     * Constructs a PathfinderRestrictSun from a NMS RestrictSunGoal.
     * @param g Goal to use
     */
    public PathfinderRestrictSun(@NotNull RestrictSunGoal g) {
        super(Pathfinder.getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderRestrictSun.
     * @param c Creature to use
     */
    public PathfinderRestrictSun(@NotNull Creature c) {
        super(c);
    }

    @Override
    public RestrictSunGoal getHandle() {
        return new RestrictSunGoal((PathfinderMob) nmsEntity);
    }

    

}
