package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Mob to eat a tile
 */
public final class PathfinderEatTile extends Pathfinder {

    /**
     * Constructs a PathfinderEatTile from a NMS EatBlockGoal.
     * @param g Goal to use
     */
    public PathfinderEatTile(@NotNull EatBlockGoal g) {
        super(Pathfinder.getEntity(g, "c"));
    }

    /**
     * Constructs a PathfinderEatTile.
     * @param m Mob to use
     */
    public PathfinderEatTile(@NotNull Mob m) {
        super(m);
    }

    @Override
    public EatBlockGoal getHandle() {
        return new EatBlockGoal(nmsEntity);
    }

}
