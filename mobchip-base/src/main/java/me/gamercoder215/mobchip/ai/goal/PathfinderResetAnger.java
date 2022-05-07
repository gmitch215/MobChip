package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;

/**
 * Represents a Pathfinder for a Neutral Mob to reset its Universal Anger
 */
public final class PathfinderResetAnger extends Pathfinder {
    
    private boolean alertOthers;

    /**
     * Constructs a PathfinderResetAnger from a NMS ResetUniversalAngerTargetGoal.
     * @param g Goal to use
     */
    public PathfinderResetAnger(@NotNull ResetUniversalAngerTargetGoal<?> g) {
        super(Pathfinder.getEntity(g, "b"));

        this.alertOthers = Pathfinder.getBoolean(g, "c");
    }

    /**
     * Constructs a PathfinderResetAnger with alert others set to true
     * @param m Mob to use 
     * @throws IllegalArgumentException if mob is not neutral
     */
    public PathfinderResetAnger(@NotNull Mob m) throws IllegalArgumentException {
        this(m, true);
    }

    /**
     * Constructs a PathfinderResetAnger.
     * @param m Mob to use
     * @param alertOthers Whether to alert other mobs of this type
     * @throws IllegalArgumentException if mob is not neutral
     */
    public PathfinderResetAnger(@NotNull Mob m, boolean alertOthers) throws IllegalArgumentException {
        super(m);

        if (!(nmsEntity instanceof NeutralMob)) throw new IllegalArgumentException("Mob is not neutral type");

        this.alertOthers = alertOthers;
    }

    /**
     * Whether the Mob will alert other entities of this type.
     * @return true if alerting others, else false
     */
    public boolean isAlertingOthers() {
        return this.alertOthers;
    }

    /**
     * Sets whether the Mob will alert other entities of this type.
     * @param alert true if alerting others, else false
     */
    public void setAlertOthers(boolean alert) {
        this.alertOthers = alert;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ResetUniversalAngerTargetGoal<?> getHandle() {
        return new ResetUniversalAngerTargetGoal(nmsEntity, alertOthers);
    }

}
