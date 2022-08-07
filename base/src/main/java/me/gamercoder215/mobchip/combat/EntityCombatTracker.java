package me.gamercoder215.mobchip.combat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents an Entity's Combat Tracker
 */
public interface EntityCombatTracker {

    /**
     * Fetches the current death message of the Entity according to {@link #getLatestEntry()}.
     * @return Death message of the Entity
     */
    @NotNull
    String getCurrentDeathMessage();

    /**
     * Fetches the latest CombatEntry of this CombatTracker.
     * @return Latest CombatEntry of this CombatTracker
     */
    @Nullable
    CombatEntry getLatestEntry();

    /**
     * Creates an immutable list of the CombatEntries of this CombatTracker.
     * @return All CombatEntries for this CombatTracker
     */
    @NotNull
    List<CombatEntry> getCombatEntries();

    /**
     * Adds a CombatEntry to {@link #getCombatEntries()}.
     * @param entry CombatEntry to add
     */
    void recordEntry(@NotNull CombatEntry entry);

    /**
     * Fetches how long this Mob has been in combat, in ticks
     * @return Combat Duration in ticks
     */
    int getCombatDuration();

    /**
     * Whether this Mob is currently taking damage.
     * @return true if taking damage, else false
     */
    boolean isTakingDamage();

    /**
     * Whether this Mob is currently in combat.
     * @return true if in combat, else false
     */
    boolean isInCombat();
}
