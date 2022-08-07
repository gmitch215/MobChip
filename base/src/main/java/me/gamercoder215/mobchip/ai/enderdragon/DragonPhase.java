package me.gamercoder215.mobchip.ai.enderdragon;

import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Dragon Phase
 */
public interface DragonPhase extends Keyed {

    /**
     * Fetches the Dragon associated with this Custom Phase.
     * @return EnderDragon used in this phase
     */
    @NotNull
    EnderDragon getDragon();

    /**
     * Fetches the target location of the Ender Dragon during this Phase.
     * @return Target Location during Phase
     */
    @NotNull
    Location getTargetLocation();

    /**
     * Called when the Phase starts on the first tick.
     */
    void start();

    /**
     * Called when the Phase ends on the last tick.
     */
    void stop();

    /**
     * Called every tick during the phase between the first and last.
     * <br><br>
     * Use this method to handle client logic (e.g. packets, particles).
     */
    void clientTick();

    /**
     * Called every tick during the phase between the first and last.
     * <br><br>
     * Use this method to handle server logic (e.g. movements).
     */
    void serverTick();

    /**
     * Whether the Ender Dragon is currently sitting.
     * @return true if sitting, else false
     */
    boolean isSitting();

    /**
     * Fetches the current flying speed of the dragon. Defaults to 0.6F.
     * @return Flying Speed
     */
    float getFlyingSpeed();

    /**
     * Called when an End Crystal is destroyed during this phase.
     * @param c Crystal Destroyed
     * @param cause Damage Cause that destroyed the Crystal
     * @param p Player that destroyed the crystal, can be null
     */
    default void onCrystalDestroyed(EnderCrystal c, EntityDamageEvent.DamageCause cause, @Nullable Player p) {}

    /**
     * Called when the Dragon is damaged during this phase.
     * @param cause Damage Cause that damaged the Dragon
     * @param amount Amount of damage that was dealt
     * @return the new damage amount
     */
    default float onDamage(EntityDamageEvent.DamageCause cause, float amount) { return amount; }
}
