package me.gamercoder215.mobchip.combat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Combat Entry stored in the Entity's {@link EntityCombatTracker}
 */
public final class CombatEntry {

    private final Mob owner;
    private final EntityDamageEvent.DamageCause cause;
    private final int ticks;
    private final float health;
    private final float damage;
    private final CombatLocation location;
    private final float lastFallDistance;

    private final Entity attacker;

    /**
     * Constructs a new CombatEntry.
     * @param owner Owner of this CombatEntry
     * @param cause DamageCause of this CombatEntry
     * @param ticks Number of ticks this CombatEntry has been in combat
     * @param health Health of the Entity at the time of this CombatEntry before taking {@link #getDamage()}
     * @param damage Damage the Entity took in this CombatEntry
     * @param combatLocation Location of this CombatEntry
     * @param fall Last amount of distance the Entity fell in this CombatEntry
     * @param attacker Entity that attacked the Entity in this CombatEntry
     * @throws IllegalArgumentException if DamageCause or Owner is null, or ticks/health/damage/fall is less than 0
     */
    public CombatEntry(@NotNull Mob owner, @NotNull EntityDamageEvent.DamageCause cause, int ticks, float health, float damage, @Nullable CombatLocation combatLocation, float fall, @Nullable Entity attacker) throws IllegalArgumentException {
        if (cause == null) throw new IllegalArgumentException("DamageCause cannot be null");
        if (owner == null) throw new IllegalArgumentException("Owner of CombatEntry cannot be null");
        if (ticks < 0 || health < 0 || damage < 0 || fall < 0) throw new IllegalArgumentException("Ticks, Health, Damage Amount, and Fall Damage cannot be less than 0");
        this.owner = owner;
        this.cause = cause;
        this.ticks = ticks;
        this.health = health;
        this.damage = damage;
        this.location = combatLocation;
        this.lastFallDistance = fall;
        this.attacker = attacker;
    }

    /**
     * Fetches the cause of the damage in this CombatEntry.
     * @return DamageCause of this CombatEntry
     */
    @NotNull
    public EntityDamageEvent.DamageCause getCause() { return this.cause; }

    /**
     * Fetches how long {@link #getOwner()} was in combat, in ticks.
     * @return Duration of Combat, in ticks
     */
    public int getCombatTime() { return this.ticks; }

    /**
     * Fetches the owner of this CombatEntry.
     * @return CombatEntry Owner
     */
    @NotNull
    public Mob getOwner() {
        return this.owner;
    }

    /**
     * Fetches how much damage this Entity took.
     * @return Damage taken before dying
     */
    public float getDamage() {
        return this.damage;
    }

    /**
     * Fetches the health of this Entity would have had before taking damage.
     * @return Health of entity before damage
     */
    public float getHealthBeforeDamage() {
        return this.health;
    }

    /**
     * Fetches how much health the entity has had after taking damage.
     * @return Health of entity after damage
     */
    public float getHealthAfterDamage() {
        return this.health - this.damage;
    }

    /**
     * Fetches the type of preparation this CombatEntry was for. May be null.
     * <br><br>
     * Developers should check {@link #hasLocation()} before calling this method.
     * @return CombatLocation found, or null if it does not exist
     */
    @Nullable
    public CombatLocation getLocation() {
        return this.location;
    }

    /**
     * Whether this CombatEntry has a CombatLocation.
     * @return true if CombatLocation exists, else false
     */
    public boolean hasLocation() {
        return this.location != null;
    }

    /**
     * Fetches the Attacker of this CombatEntry. May be null.
     * <br><br>
     * Developers should check {@link #hasAttacker()} before calling this method.
     * @return Entity that attacked the Mob that owns this Combat Entry, or null if not found
     */
    @Nullable
    public Entity getAttacker() {
        return this.attacker;
    }

    /**
     * Whether this CombatEntry contains an Entity Attacker.
     * @return true if Entity Attacker exists, else false
     */
    public boolean hasAttacker() {
        return this.attacker != null;
    }

    /**
     * Fetches the last amount of distance the Entity fell in this CombatEntry.
     * <br><br>
     * This may return {@link Float#MAX_VALUE} if the Damage Cause is {@link EntityDamageEvent.DamageCause#VOID}
     * @return Fall Distance
     */
    public float getFallDistance() {
        return this.lastFallDistance;
    }


}
