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

    private CombatEntry(@NotNull Builder builder) {
        this.owner = builder.owner;
        this.cause = builder.cause;
        this.ticks = builder.ticks;
        this.health = builder.health;
        this.damage = builder.damage;
        this.location = builder.location;
        this.lastFallDistance = builder.fall;
        this.attacker = builder.attacker;
    }

    /**
     * Constructs a new CombatEntry with no fall damage.
     * @param owner Owner of this CombatEntry
     * @param cause DamageCause of this CombatEntry
     * @param ticks Number of ticks this CombatEntry has been in combat
     * @param health Health of the Entity at the time of this CombatEntry before taking {@link #getDamage()}
     * @param damage Damage the Entity took in this CombatEntry
     * @throws IllegalArgumentException if DamageCause or Owner is null, or ticks/health/damage is less than 0
     */
    public CombatEntry(@NotNull Mob owner, @NotNull EntityDamageEvent.DamageCause cause, int ticks, float health, float damage) throws IllegalArgumentException {
        this(owner, cause, ticks, health, damage, 0);
    }

    /**
     * Constructs a new CombatEntry with no Combat Location.
     * @param owner Owner of this CombatEntry
     * @param cause DamageCause of this CombatEntry
     * @param ticks Number of ticks this CombatEntry has been in combat
     * @param health Health of the Entity at the time of this CombatEntry before taking {@link #getDamage()}
     * @param damage Damage the Entity took in this CombatEntry
     * @param fall Last amount of distance the Entity fell in this CombatEntry
     * @throws IllegalArgumentException if DamageCause or Owner is null, or ticks/health/damage/fall is less than 0
     */
    public CombatEntry(@NotNull Mob owner, @NotNull EntityDamageEvent.DamageCause cause, int ticks, float health, float damage, float fall) throws IllegalArgumentException {
        this(owner, cause, ticks, health, damage, fall, null);
    }

    /**
     * Constructs a new CombatEntry with no attacker.
     * @param owner Owner of this CombatEntry
     * @param cause DamageCause of this CombatEntry
     * @param ticks Number of ticks this CombatEntry has been in combat
     * @param health Health of the Entity at the time of this CombatEntry before taking {@link #getDamage()}
     * @param damage Damage the Entity took in this CombatEntry
     * @param fall Last amount of distance the Entity fell in this CombatEntry
     * @param combatLocation Location of this CombatEntry
     * @throws IllegalArgumentException if DamageCause or Owner is null, or ticks/health/damage/fall is less than 0
     */
    public CombatEntry(@NotNull Mob owner, @NotNull EntityDamageEvent.DamageCause cause, int ticks, float health, float damage, float fall, @Nullable CombatLocation combatLocation) throws IllegalArgumentException {
        this(owner, cause, ticks, health, damage, fall, combatLocation, null);
    }

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
    public CombatEntry(@NotNull Mob owner, @NotNull EntityDamageEvent.DamageCause cause, int ticks, float health, float damage, float fall, @Nullable CombatLocation combatLocation, @Nullable Entity attacker) throws IllegalArgumentException {
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

    // Builder

    /**
     * Creates a new Builder for a {@link CombatEntry}.
     * @return Builder Class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for a {@link CombatEntry}.
     */
    public static final class Builder {

        Mob owner;
        EntityDamageEvent.DamageCause cause;
        int ticks;
        float health;
        float damage;
        CombatLocation location;
        float fall;
        Entity attacker;

        Builder() {}

        /**
         * Sets the owner of this CombatEntry.
         * @param owner Owner of this CombatEntry
         * @return this class, for chaining
         * @throws IllegalArgumentException if owner is null
         */
        @NotNull
        public Builder setOwner(@NotNull Mob owner) throws IllegalArgumentException {
            if (owner == null) throw new IllegalArgumentException("Owner of CombatEntry cannot be null");
            this.owner = owner;
            return this;
        }

        /**
         * Sets the cause of this CombatEntry.
         * @param cause Cause of this CombatEntry
         * @return this class, for chaining
         * @throws IllegalArgumentException if cause is null
         */
        @NotNull
        public Builder setCause(@NotNull EntityDamageEvent.DamageCause cause) throws IllegalArgumentException {
            if (cause == null) throw new IllegalArgumentException("DamageCause cannot be null");
            this.cause = cause;
            return this;
        }

        /**
         * Sets the duration of this CombatEntry.
         * @param ticks Duration of this CombatEntry
         * @return this class, for chaining
         * @throws IllegalArgumentException if ticks is less than 0
         */
        @NotNull
        public Builder setCombatDuration(int ticks) throws IllegalArgumentException {
            if (ticks < 0) throw new IllegalArgumentException("Combat Duration cannot be less than 0");
            this.ticks = ticks;
            return this;
        }

        /**
         * Sets the health of the Entity before taking damage.
         * @param health Health of the Entity before taking damage
         * @return this class, for chaining
         * @throws IllegalArgumentException if health is less than 0
         */
        @NotNull
        public Builder setHealthBeforeDamage(float health) throws IllegalArgumentException {
            if (health < 0) throw new IllegalArgumentException("Health cannot be less than 0");
            this.health = health;
            return this;
        }

        /**
         * Sets the damage taken by the Entity.
         * @param damage Damage taken by the Entity
         * @return this class, for chaining
         * @throws IllegalArgumentException if damage is less than 0
         */
        @NotNull
        public Builder setDamage(float damage) throws IllegalArgumentException {
            if (damage < 0) throw new IllegalArgumentException("Damage cannot be less than 0");
            this.damage = damage;
            return this;
        }

        /**
         * Sets the CombatLocation of this CombatEntry.
         * @param location CombatLocation of this CombatEntry
         * @return this class, for chaining
         */
        @NotNull
        public Builder setLocation(@Nullable CombatLocation location) {
            this.location = location;
            return this;
        }

        /**
         * Sets the fall distance of the Entity.
         * @param fall Fall distance of the Entity
         * @return this class, for chaining
         * @throws IllegalArgumentException if fall is less than 0
         */
        @NotNull
        public Builder setFallDistance(float fall) throws IllegalArgumentException {
            if (fall < 0) throw new IllegalArgumentException("Fall Distance cannot be less than 0");
            this.fall = fall;
            return this;
        }

        /**
         * Sets the Entity that attacked the Entity that owns this CombatEntry.
         * @param attacker Entity that attacked the Entity that owns this CombatEntry
         * @return this class, for chaining
         */
        @NotNull
        public Builder setAttacker(@Nullable Entity attacker) {
            this.attacker = attacker;
            return this;
        }

        /**
         * Builds a new CombatEntry.
         * @return new CombatEntry
         * @throws IllegalStateException if owner or cause is null, or ticks/health/damage/fall is less than 0
         */
        @NotNull
        public CombatEntry build() throws IllegalStateException {
            if (this.owner == null) throw new IllegalStateException("Owner of CombatEntry cannot be null");
            if (this.cause == null) throw new IllegalStateException("DamageCause cannot be null");
            if (this.ticks < 0) throw new IllegalStateException("Combat Duration cannot be less than 0");
            if (this.health < 0) throw new IllegalStateException("Health cannot be less than 0");
            if (this.damage < 0) throw new IllegalStateException("Damage cannot be less than 0");
            if (this.fall < 0) throw new IllegalStateException("Fall Distance cannot be less than 0");

            return new CombatEntry(this);
        }

    }
}
