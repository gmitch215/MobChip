package me.gamercoder215.mobchip.ai.enderdragon;

import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a custom Ender Dragon Phase.
 */
public abstract class CustomPhase implements Keyed {

    /**
     * The Dragon that this phase is attached to.
     */
    protected final EnderDragon dragon;
    /**
     * The unique ID of this phase.
     */
    protected final NamespacedKey key;

    /**
     * Constructs a new CustomPhase.
     * @param dragon Ender Dragon to use.
     * @param key The unique ID of this phase.
     * @throws IllegalArgumentException if dragon or key is null
     */
    public CustomPhase(@NotNull EnderDragon dragon, @NotNull NamespacedKey key) throws IllegalArgumentException {
        if (dragon == null) throw new IllegalArgumentException("Dragon cannot be null");
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        this.dragon = dragon;
        this.key = key;
    }

    /**
     * Fetches the unique ID of this phase.
     * @return NamespacedKey of this CustomPhase
     */
    @Override
    @NotNull
    public final NamespacedKey getKey() {
        return this.key;
    }

    /**
     * Fetches the Dragon associated with this Custom Phase.
     * @return EnderDragon used in this phase
     */
    @NotNull
    public final EnderDragon getDragon() {
        return this.dragon;
    }

    /**
     * Fetches the target location of the Ender Dragon during this Phase.
     * @return Target Location during Phase
     */
    @NotNull
    public abstract Location getTargetLocation();

    /**
     * Called when the Phase ends on the last tick.
     */
    public void stop() {}

    /**
     * Called when the Phase starts on the first tick.
     */
    public void start() {}

    /**
     * Called every tick during the phase between the first and last.
     * <br><br>
     * Use this method to handle server logic (e.g. movements).
     */
    public void serverTick() {}

    /**
     * Called every tick during the phase between the first and last.
     * <br><br>
     * Use this method to handle client logic (e.g. packets, particles).
     */
    public void clientTick() {}

    /**
     * Called when an End Crystal is destroyed during this phase.
     * @param c Crystal Destroyed
     * @param cause Damage Cause that destroyed the Crystal
     * @param p Player that destroyed the crystal, can be null
     */
    public void onCrystalDestroyed(EnderCrystal c, EntityDamageEvent.DamageCause cause, @Nullable Player p) {}

    /**
     * Called when the Dragon is damaged during this phase.
     * @param cause Damage Cause that damaged the Dragon
     * @param amount Amount of damage that was dealt
     * @return the new damage amount
     */
    public float onDamage(EntityDamageEvent.DamageCause cause, float amount) { return amount; }

    /**
     * Whether the Ender Dragon is currently sitting.
     * @return true if sitting, else false
     */
    public boolean isSitting() {
        return false;
    }

    /**
     * Fetches the current flying speed of the dragon. Defaults to 0.6F.
     * @return Flying Speed
     */
    public float getFlyingSpeed() { return 0.6F; }

}
