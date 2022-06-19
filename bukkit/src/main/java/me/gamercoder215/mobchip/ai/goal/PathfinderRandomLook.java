package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to randomly look around
 */
public final class PathfinderRandomLook extends Pathfinder {

    /**
     * Constructs a PathfinderRandomLook from a NMS RandomLookAroundGoal.
     * @param g Goal to use
     */
    public PathfinderRandomLook(@NotNull RandomLookAroundGoal g) {
        super(getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderRandomLook.
     * @param m Mob to use
     */
    public PathfinderRandomLook(@NotNull Mob m) {
        super(m);
    }

    @Override
    public RandomLookAroundGoal getHandle() {
        return new RandomLookAroundGoal(nmsEntity);
    }

}
