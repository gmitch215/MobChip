package me.gamercoder215.mobchip.ai.goal;

import me.gamercoder215.mobchip.ai.SpeedModifier;
import me.gamercoder215.mobchip.ai.goal.target.Targeting;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to attack.
 * <p>
 * This Pathfinder does not look for entities to attack, but only attacks them.
 * <p>
 * Any entities that do not normally attack (i.e. animals) most commonly do not have attack attributes. <strong>An entity that attacks without an Attack Attribute will crash the server.</strong>
 */
public class PathfinderMeleeAttack extends Pathfinder implements SpeedModifier, Targeting {

    private double speedMod;
    private boolean mustSee;

    /**
     * Constructs a PathfinderMeleeAttack with the default Speed Modifier.
     * @param c Creature to use
     */
    public PathfinderMeleeAttack(@NotNull Creature c) {
        this(c, DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Constructs a PathfinderMeleeAttack with must see set to true.
     * @param c Creature to use
     * @param speedMod Speed Modifier while attacking
     */
    public PathfinderMeleeAttack(@NotNull Creature c, double speedMod) {
        this(c, speedMod, true);
    }

    /**
     * Constructs a PathfinderMeleeAttack.
     * @param c Creature to use
     * @param speedMod Speed Modifier while attacking
     * @param see Whether the Creature must see the target in order to attack
     */
    public PathfinderMeleeAttack(@NotNull Creature c, double speedMod, boolean see) {
        super(c);

        this.speedMod = speedMod;
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
    public boolean mustSee() {
        return this.mustSee;
    }

    @Override
    public void setSee(boolean see) {
        this.mustSee = see;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalMeleeAttack";
    }
}
