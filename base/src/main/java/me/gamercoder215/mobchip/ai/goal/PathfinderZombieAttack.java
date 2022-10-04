package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Zombie;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a special Zombie Melee Attack
 */
public final class PathfinderZombieAttack extends PathfinderMeleeAttack {

    /**
     * Constructs a PathfinderZombieAttack with no speed modifier.
     * @param m Zombie to use
     */
    public PathfinderZombieAttack(@NotNull Zombie m) {
        this(m, 1);
    }

    /**
     * Constructs a PathfinderZombieAttack with see set to true.
     * @param m Zombie to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderZombieAttack(@NotNull Zombie m, double speedMod) {
        this(m, speedMod, true);
    }
    
    /**
     * Constructs a PathfinderZombieAttack.
     * @param m Zombie to use
     * @param speedMod Speed Modifier while attacking
     * @param see Whether the Zombie has to see to attack
     */
    public PathfinderZombieAttack(@NotNull Zombie m, double speedMod, boolean see) {
        super(m, speedMod, see);
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalZombieAttack";
    }
}
