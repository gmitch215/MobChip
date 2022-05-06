package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Ocelot;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;

/**
 * Represents a Pathfinder for a special Ocelot Attack
 */
public final class PathfinderOcelotAttack extends Pathfinder {

    /**
     * Constructs a PathfinderOcelotAttack from a NMS OcelotAttackGoal.
     * @param g Goal to use
     */
    public PathfinderOcelotAttack(@NotNull OcelotAttackGoal g) {
        super(Pathfinder.getEntity(g, "a"));
    }

    /**
     * Constructs a PathfinderOcelotAttack.
     * @param m Ocelot to use
     */
    public PathfinderOcelotAttack(@NotNull Ocelot m) {
        super(m);
    }

    @Override
    public OcelotAttackGoal getHandle() {
        return new OcelotAttackGoal(nmsEntity);
    }

}
