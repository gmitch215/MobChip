package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Pillager;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Stray;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;

/**
 * Represents a Pathfinder for Any Ranged Monster to shoot arrows from a bow
 */
public final class PathfinderRangedBowAttack extends Pathfinder implements SpeedModifier, Ranged {
    
    private double speedMod;
    private float range;
    private int aInv;

    /**
     * Constructs a PathfinderRangedBowAttack from a NMS RangedBowAttackGoal.
     * @param g Goal to use
     */
    public PathfinderRangedBowAttack(@NotNull RangedBowAttackGoal<?> g) {
        super(Pathfinder.getEntity(g, "a"));

        this.speedMod = Pathfinder.getDouble(g, "b");
        this.range = (float) Math.sqrt(Pathfinder.getFloat(g, "d"));
        this.aInv = Pathfinder.getInt(g, "c");
    }

    /**
     * Constructs a PathfinderRangedBowAttack with the default attack range.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedBowAttack(@NotNull Skeleton m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedBowAttack with the default attack range.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedBowAttack(@NotNull Stray m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedBowAttack with the default attack range.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderRangedBowAttack(@NotNull Pillager m, double speedMod) {
        this(m, speedMod, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Constructs a PathfinderRangedBowAttack with the default interval.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedBowAttack(@NotNull Skeleton m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }

    /**
     * Constructs a PathfinderRangedBowAttack with the default interval.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedBowAttack(@NotNull Stray m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }

    /**
     * Constructs a PathfinderRangedBowAttack with the default interval.
     * @param m Pillager to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     */
    public PathfinderRangedBowAttack(@NotNull Pillager m, double speedMod, float range) {
        this(m, speedMod, range, DEFAULT_ATTACK_INTERVAL);
    }
    
    /**
     * Constructs a PathfinderRangedBowAttack.
     * @param m Skeleton to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedBowAttack(@NotNull Skeleton m, double speedMod, float range, int interval) {
        super(m);

        this.speedMod = speedMod;
        this.range = range;
        this.aInv = interval;
    }

    /**
     * Constructs a PathfinderRangedBowAttack.
     * @param m Stray to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedBowAttack(@NotNull Stray m, double speedMod, float range, int interval) {
        super(m);

        this.speedMod = speedMod;
        this.range = range;
        this.aInv = interval;
    }

    /**
     * Constructs a PathfinderRangedBowAttack.
     * @param m Pillager to use
     * @param speedMod Speed Modifier while attacking
     * @param range Range of attack
     * @param interval Interval of attack, in ticks
     */
    public PathfinderRangedBowAttack(@NotNull Pillager m, double speedMod, float range, int interval) {
        super(m);

        this.speedMod = speedMod;
        this.range = range;
        this.aInv = interval;
    }

    @Override
    public float getRange() {
        return this.range;
    }

    @Override
    public void setRange(float range) {
        this.range = range;
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
     * Gets the current interval of attack, in ticks.
     * @return Interval of attack, in ticks
     */
    public int getInterval() {
        return this.aInv;
    }

    /**
     * Sets the current interval of attack, in ticks
     * @param interval Interval of attack
     */
    public void setInterval(int interval) {
        this.aInv = interval;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public RangedBowAttackGoal<?> getHandle() {
        return new RangedBowAttackGoal((net.minecraft.world.entity.monster.Monster) nmsEntity, speedMod, aInv, range);
    }
    
    
}
