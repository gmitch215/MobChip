package me.gamercoder215.mobchip.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder to Randomly Stroll back into a Village
 */
public final class PathfinderRandomStrollToVillage extends PathfinderRandomStroll {

    /**
     * Constructs a PathfinderRandomStrollToVillage with a NMS MoveBackToVillageGoal.
     * @param g Goal to use
     */
    public PathfinderRandomStrollToVillage(@NotNull MoveBackToVillageGoal g) {
        super(g);
    }

    /**
     * Constructs a PathfinderRandomStrollToVillage with no speed modifier.
     * @param c Creature to use
     * @see PathfinderRandomStroll#PathfinderRandomStroll(Creature)
     */
    public PathfinderRandomStrollToVillage(@NotNull Creature c) {
        super(c);
    }

    /**
     * Constructs a PathfinderRandomStrollToVillage.
     * @param c Creature to use
     * @param speedMod Speed Modifier while strolling
     * {@link PathfinderRandomStroll#PathfinderRandomStroll(Creature, double)}
     */
    public PathfinderRandomStrollToVillage(@NotNull Creature c, double speedMod) {
        super(c, speedMod);
    }

    @Override
    public MoveBackToVillageGoal getHandle() {
        return new MoveBackToVillageGoal((PathfinderMob) nmsEntity, this.getSpeedModifier(), true);
    }

}
