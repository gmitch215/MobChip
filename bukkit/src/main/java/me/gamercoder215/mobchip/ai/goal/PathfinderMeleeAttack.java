package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.goal.target.Targeting;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to attack.
 * <p>
 * This Pathfinder does not look for entities to attack, but only attacks them.
 * <p>
 * Any entities that do not normally attack (i.e. animals) most commonly do not have attack attributes. <strong>An entity that attacks without an Attack Attribute will crash the server.</strong>
 */
public final class PathfinderMeleeAttack extends Pathfinder implements SpeedModifier, Targeting {

    /**
     * Default Attack Interval, in ticks (20)
     */
    public static final int DEFAULT_ATTACK_INTERVAL = 20;

    private double speedMod;
    private int attackInterval;
    private boolean mustSee;

    /**
     * Constructs a PathfinderMeleeAttack from a NMS MeleeAttackGoal.
     * @param g Goal to use
     */
    public PathfinderMeleeAttack(@NotNull MeleeAttackGoal g) {
        super(Pathfinder.getCreature(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "b");
        this.attackInterval = Pathfinder.getInt(g, "j");
        this.mustSee = Pathfinder.getBoolean(g, "c");
    }

    /**
     * Constructs a PathfinderMeleeAttack with the default Speed Modifier.
     * @param c Creature to use
     */
    public PathfinderMeleeAttack(@NotNull Creature c) {
        this(c, DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Constructs a PathfinderMeleeAttack with {@link #DEFAULT_ATTACK_INTERVAL}.
     * @param c Creature to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderMeleeAttack(@NotNull Creature c, double speedMod) {
        this(c, speedMod, DEFAULT_ATTACK_INTERVAL);
    }

    /**
     * Constructs a PathfinderMeleeAttack with must see set to true.
     * @param c Creature to use
     * @param speedMod Speed Modifier while attacking
     * @param attackInterval Attack Interval, in ticks
     * @throws IllegalArgumentException if attack interval is less than 1
     */
    public PathfinderMeleeAttack(@NotNull Creature c, double speedMod, int attackInterval) throws IllegalArgumentException {
        this(c, speedMod, attackInterval, true);
    }

    /**
     * Constructs a PathfinderMeleeAttack.
     * @param c Creature to use
     * @param speedMod Speed Modifier while attacking
     * @param attackInterval Attack Interval, in ticks
     * @param see Whether the Creature must see the target in order to attack
     * @throws IllegalArgumentException if attack interval is less than 1
     */
    public PathfinderMeleeAttack(@NotNull Creature c, double speedMod, int attackInterval, boolean see) throws IllegalArgumentException {
        super(c);

        if (attackInterval < 1) throw new IllegalArgumentException("Attack Interval must be greater than 0");

        this.speedMod = speedMod;
        this.attackInterval = attackInterval;
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

    /**
     * Fetches the current attack interval.
     * @return Attacking Interval, in ticks
     */
    public int getAttackInterval() {
        return this.attackInterval;
    }

    /**
     * Sets the current attack interval.
     * @param interval Attack Interval, in ticks
     * @throws IllegalArgumentException if interval is less than 0
     */
    public void setAttackInterval(int interval) throws IllegalArgumentException {
        if (interval < 1) throw new IllegalArgumentException("Interval must be greater than 0");

        this.attackInterval = interval;
    }

    @Override
    public MeleeAttackGoal getHandle() {
        MeleeAttackGoal g = new MeleeAttackGoal((PathfinderMob) nmsEntity, speedMod, mustSee);

        Pathfinder.setFinal(g, "j", attackInterval);

        return g;
    }

    @Override
    public boolean mustSee() {
        return this.mustSee;
    }

    @Override
    public void setSee(boolean see) {
        this.mustSee = see;
    }
    
}
