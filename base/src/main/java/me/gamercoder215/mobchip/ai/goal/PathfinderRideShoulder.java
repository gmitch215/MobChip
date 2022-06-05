package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Parrot;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;

/**
 * Pathfinder for Shoulder-Riding Entities, like Parrots, to ride on a Player's shoulder
 */
public final class PathfinderRideShoulder extends Pathfinder {

    /**
     * Constructs a PathfinderRideShoulder from a NMS LandOnOwnersShoulderGoal.
     * @param g Goal to use
     */
    public PathfinderRideShoulder(@NotNull LandOnOwnersShoulderGoal g) {
        super(Pathfinder.getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderRideShoulder.
     * @param p Parrot to use
     */
    public PathfinderRideShoulder(@NotNull Parrot p) {
        super(p);
    }

    @Override
    public LandOnOwnersShoulderGoal getHandle() {
        return new LandOnOwnersShoulderGoal((ShoulderRidingEntity) nmsEntity);
    }
    
}
