package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Zombie;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.goal.target.Targeting;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;

/**
 * Represents a Pathfinder for a special Zombie Melee Attack
 */
public final class PathfinderZombieAttack extends Pathfinder implements SpeedModifier, Targeting {

    private boolean mustSee;
    private double speedMod;

    /**
     * Constructs a PathfinderZombieAttack from a NMS ZombieAttackGoal.
     * @param g Goal to use
     */
    public PathfinderZombieAttack(@NotNull ZombieAttackGoal g) {
        super(Pathfinder.getEntity(g, "b"));

        this.mustSee = Pathfinder.getBoolean(MeleeAttackGoal.class.cast(g), "c");
        this.speedMod = Pathfinder.getDouble(MeleeAttackGoal.class.cast(g), "b");
    }

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
     * @param see Whether or not the Zombie has to see to attack
     */
    public PathfinderZombieAttack(@NotNull Zombie m, double speedMod, boolean see) {
        super(m);

        this.mustSee = see;
        this.speedMod = speedMod;
    }

    @Override
    public boolean mustSee() {
        return this.mustSee;
    }

    @Override
    public void setSee(boolean see) {
        this.mustSee = see;
    }

    @Override
    public double getSpeedModifier() {
        return this.speedMod;
    }

    @Override
    public void setSpeedModifier(double mod) {
        this.speedMod = mod;
    }

    @Override
    public ZombieAttackGoal getHandle() {
        return new ZombieAttackGoal((net.minecraft.world.entity.monster.Zombie) nmsEntity, speedMod, mustSee);
    }

}
