package me.gamercoder215.mobchip.ai.enderdragon;

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
public abstract class CustomPhase implements DragonPhase {

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

    @NotNull
    public final EnderDragon getDragon() {
        return this.dragon;
    }

    public void stop() {}

    public void start() {}

    public void serverTick() {}

    public void clientTick() {}

    public void onCrystalDestroyed(EnderCrystal c, EntityDamageEvent.DamageCause cause, @Nullable Player p) {}

    public float onDamage(EntityDamageEvent.DamageCause cause, float amount) { return amount; }

    public boolean isSitting() {
        return false;
    }

    public float getFlyingSpeed() { return 0.6F; }

}
