package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to randomly stroll throughout a village
 */
public final class PathfinderRandomStrollInVillage extends PathfinderRandomStroll {
    
    /**
     * Constructs a PathfinderRandomStrollVillage with no speed modifier.
     * @param c Creature to use
     * @see PathfinderRandomStroll#PathfinderRandomStroll(Creature)
     */
    public PathfinderRandomStrollInVillage(@NotNull Creature c) {
        super(c);
    }

    /**
     * Constructs a PathfinderRandomStrollVillage.
     * @param c Creature to use
     * @param speedMod Speed Modifier to use
     * @see PathfinderRandomStroll#PathfinderRandomStroll(Creature, double)
     */
    public PathfinderRandomStrollInVillage(@NotNull Creature c, double speedMod) {
        super(c, speedMod);
    }

    @Override
    public String getInternalName() { return "PathfinderGoalStrollVillageGolem"; }
}
