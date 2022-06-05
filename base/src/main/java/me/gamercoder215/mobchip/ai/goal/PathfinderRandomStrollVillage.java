package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;

/**
 * Represents a Pathfinder for a Creature to randomly stroll throughout a village
 */
public final class PathfinderRandomStrollVillage extends PathfinderRandomStroll {

    /**
     * Constructs a PathfinderRandomStrollVillage from a NMS GolemRandomStrollInVillageGoal.
     * @param g Goal to use
     */
    public PathfinderRandomStrollVillage(@NotNull GolemRandomStrollInVillageGoal g) {
        super(g);
    }
    
    /**
     * Constructs a PathfinderRandomStrollVillage with no speed modifier.
     * @param c Creature to use
     * @see PathfinderRandomStroll#PathfinderRandomStroll(Creature)
     */
    public PathfinderRandomStrollVillage(@NotNull Creature c) {
        super(c);
    }

    /**
     * Constructs a PathfinderRandomStrollVillage.
     * @param c Creature to use
     * @param speedMod Speed Modifier to use
     * @see PathfinderRandomStroll#PathfinderRandomStroll(Creature, double)
     */
    public PathfinderRandomStrollVillage(@NotNull Creature c, double speedMod) {
        super(c, speedMod);
    }

    @Override
    public GolemRandomStrollInVillageGoal getHandle() {
        return new GolemRandomStrollInVillageGoal((PathfinderMob) nmsEntity, this.getSpeedModifier());
    }

}
