package me.gamercoder215.mobchip.ai.enderdragon;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import org.bukkit.entity.EnderDragon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility Class for Ender Dragon Phases
 */
public final class DragonPhases {

    private final EnderDragon d;

    /**
     * Constructs a new DragonPhases object.
     * @param d Ender Dragon to use.
     * @throws IllegalArgumentException if dragon is null
     */
    public DragonPhases(@NotNull EnderDragon d) {
        if (d == null) throw new IllegalArgumentException("Dragon cannot be null");
        this.d = d;
    }

    /**
     * The dragon will attack with dragon breath at its current location.
     * @see EnderDragon.Phase#BREATH_ATTACK
     */
    public final DragonPhase BREATH_ATTACK = fromBukkit(EnderDragon.Phase.BREATH_ATTACK);

    /**
     * The dragon will charge a player.
     * @see EnderDragon.Phase#CHARGE_PLAYER
     */
    public final DragonPhase CHARGE_PLAYER = fromBukkit(EnderDragon.Phase.CHARGE_PLAYER);

    /**
     * The dragon will circle outside the ring of pillars if ender crystal remain or inside the ring if not.
     * @see EnderDragon.Phase#CIRCLING
     */
    public final DragonPhase CIRCLING = fromBukkit(EnderDragon.Phase.CIRCLING);

    /**
     * The dragon will fly to the vicinity of the portal and die.
     * @see EnderDragon.Phase#DYING
     */
    public final DragonPhase DYING = fromBukkit(EnderDragon.Phase.DYING);

    /**
     * The dragon will fly towards the empty portal (approaching from the other side, if applicable).
     * @see EnderDragon.Phase#FLY_TO_PORTAL
     */
    public final DragonPhase FLY_TO_PORTAL = fromBukkit(EnderDragon.Phase.FLY_TO_PORTAL);

    /**
     * The dragon will hover at its current location, not performing any actions.
     * @see EnderDragon.Phase#HOVER
     */
    public final DragonPhase HOVER = fromBukkit(EnderDragon.Phase.HOVER);

    /**
     * The dragon will land on the portal.
     * @see EnderDragon.Phase#LAND_ON_PORTAL
     */
    public final DragonPhase LAND_ON_PORTAL = fromBukkit(EnderDragon.Phase.LAND_ON_PORTAL);

    /**
     * The dragon will leave the portal.
     * @see EnderDragon.Phase#LEAVE_PORTAL
     */
    public final DragonPhase LEAVE_PORTAL = fromBukkit(EnderDragon.Phase.LEAVE_PORTAL);

    /**
     * The dragon will roar before performing a breath attack.
     * @see EnderDragon.Phase#ROAR_BEFORE_ATTACK
     */
    public final DragonPhase ROAR_BEFORE_ATTACK = fromBukkit(EnderDragon.Phase.ROAR_BEFORE_ATTACK);

    /**
     * The dragon will search for a player to attack with dragon breath.
     * @see EnderDragon.Phase#SEARCH_FOR_BREATH_ATTACK_TARGET
     */
    public final DragonPhase SEARCH_FOR_BREATH_ATTACK_TARGET = fromBukkit(EnderDragon.Phase.SEARCH_FOR_BREATH_ATTACK_TARGET);

    /**
     * The dragon will fly towards a targeted player and shoot a fireball when within 64 blocks.
     * @see EnderDragon.Phase#STRAFING
     */
    public final DragonPhase STRAFING = fromBukkit(EnderDragon.Phase.STRAFING);

    private static final ChipUtil w = ChipUtil.getWrapper();

    /**
     * Converts a Bukkit Phase to a MobChip Phase.
     * @param dragon The Dragon this phase is using
     * @param phase Bukkit DragonPhase to convert.
     * @return MobChip DragonPhase
     * @throws IllegalArgumentException if dragon is null
     */
    public static DragonPhase fromBukkit(@NotNull EnderDragon dragon, @Nullable EnderDragon.Phase phase) throws IllegalArgumentException {
        if (dragon == null) throw new IllegalArgumentException("dragon cannot be null");
        if (phase == null) return null;
        return w.fromBukkit(dragon, phase);
    }

    private DragonPhase fromBukkit(@Nullable EnderDragon.Phase phase) {
        if (phase == null) return null;
        return w.fromBukkit(d, phase);
    }

}
