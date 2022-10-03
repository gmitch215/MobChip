package me.gamercoder215.mobchip;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.mobchip.ai.animation.EntityAnimation;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.util.Position;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Represents an Entity's Body and Server Attributes
 */
public interface EntityBody {

    /**
     * Whether this Entity is Left Handed.
     * @return true if left-handed, else false
     */
    boolean isLeftHanded();

    /**
     * Sets this Entity to be left-handed.
     * @param leftHanded true if left-handed, else false
     */
    void setLeftHanded(boolean leftHanded);

    /**
     * Whether this Mob can breathe underwater.
     * @return true if mob can breathe underwater, else false
     */
    boolean canBreatheUnderwater();

    /**
     * Whether this Mob should discard friction.
     * <p>
     * If this is set to true, the entity's movement will not be amplified while naturally traveling.
     * @return true if mob discards friction, else false
     */
    boolean shouldDiscardFriction();

    /**
     * Sets whether this Mob should discard friction. See {@link #shouldDiscardFriction()} for more information on what this actually does.
     * @param discard true if mob discards friction, else false
     * @see #shouldDiscardFriction()
     */
    void setDiscardFriction(boolean discard);

    /**
     * Represents EquipmentSlots consisting of your Main Hand and Off-Hand
     */
    enum InteractionHand {

        /**
         * Represents the Main Hand
         */
        MAIN_HAND,
        /**
         * Represents the Off-Hand
         */
        OFF_HAND

        ;

        InteractionHand() {}

        /**
         * Converts this InteractionHand to an EquipmentSlot.
         * @return EquipmentSlot Variant
         */
        public EquipmentSlot toEquipmentSlot() {
            if (this == InteractionHand.MAIN_HAND) return EquipmentSlot.HAND;
            return EquipmentSlot.OFF_HAND;
        }

    }

    /**
     * Represents the result of an interaction
     */
    enum InteractionResult {
        /**
         * Represents a successful interaction result.
         */
        SUCCESS,
        /**
         * Represents an interaction result that caused an item to be consumed.
         */
        CONSUME,
        /**
         * Represents an interaction result that caused an item to be partially consumed.
         */
        CONSUME_PARTIAL,
        /**
         * Represents an interaction result that did nothing.
         */
        PASS,
        /**
         * Represents a failed interaction result.
         */
        FAIL
        ;

        InteractionResult() {}

    }

    /**
     * Makes this Mob interact with a Player.
     * @param p Player to interact with
     * @param hand Hand to use
     * @return Result of interaction
     */
    InteractionResult interact(@NotNull Player p, @Nullable InteractionHand hand);

    /**
     * Whether this Entity is sensitive to water (e.g. enderman)
     * @return true if sensitive to water, else false
     */
    boolean isSensitiveToWater();

    /**
     * Whether this Mob is affected by Potions.
     * @return true if mob is affected, else false
     */
    boolean isAffectedByPotions();

    /**
     * Whether this Mob is blocking.
     * @return true if mob is blocking, else false
     */
    boolean isBlocking();

    /**
     * Fetch the percentage of how much armor is covering this Mob.
     * @return Percentage of Armor Coverage
     */
    float getArmorCoverPercentage();

    /**
     * Makes this Mob use the Item in its Hand.
     * @param hand Hand to use
     */
    void useItem(@Nullable InteractionHand hand);

    /**
     * Whether this Mob is currently using an Item.
     * @return true if mob is using item, else false
     */
    boolean isUsingItem();

    /**
     * Whether this Mob is Immune to Fire/Lava Damage.
     * @return true if Mob is immune to fire, else false
     */
    boolean isFireImmune();

    /**
     * Whether this Mob is swinging its arm.
     * @return true if mob is swinging its arm, else false
     */
    boolean isSwinging();

    /**
     * Whether this Mob can be ridden underwater.
     * @return true if mob can be ridden underwater, else false
     */
    boolean canRideUnderwater();

    /**
     * Whether this Mob is invisible to this Player.
     * @param p Player to use
     * @return true if mob is invisible to this player, else false
     */
    boolean isInvisibleTo(@Nullable Player p);

    /**
     * Gets this Mob's Main Hand.
     * @return Mob's Main Hand
     */
    @NotNull
    InteractionHand getMainHand();

    /**
     * Fetches the entity's current default drops.
     * @return List of Default Drops
     */
    List<ItemStack> getDefaultDrops();

    /**
     * Sets the entity's current default drops.
     * @param drops Array of Default Drops
     */
    void setDefaultDrops(@Nullable ItemStack... drops);

    /**
     * Sets the entity's current default drops.
     * @param drops Iterable of Default Drops
     */
    default void setDefaultDrops(@Nullable Iterable<ItemStack> drops) {
        if (drops == null) return;
        setDefaultDrops(ImmutableList.copyOf(drops).toArray(new ItemStack[0]));
    }

    /**
     * Sets the entity's current default drops.
     * @param drops Array of Default Drops
     */
    default void setDefaultDrops(@Nullable Material... drops) {
        if (drops == null) return;
        ItemStack[] items = new ItemStack[drops.length];
        for (int i = 0; i < drops.length; i++) items[i] = new ItemStack(drops[i]);
        setDefaultDrops(items);
    }

    /**
     * Whether this Mob is currently in combat.
     * @return true if mob is in combat, else false
     * @deprecated Use {@link EntityCombatTracker#isInCombat()} instead.
     */
    @Deprecated
    boolean isInCombat();

    /**
     * Fetches the Mob's current flying speed.
     * @return Current Flying Speed (between 0.0F and 1.0F)
     */
    float getFlyingSpeed();

    /**
     * Sets the Mob's current flying speed.
     * @param speed Flying Speed to set
     * @throws IllegalArgumentException if speed is not between 0.0F and 1.0F
     */
    void setFlyingSpeed(float speed) throws IllegalArgumentException;

    /**
     * Whether the Mob is currently forced to drop its loot.
     * @return true if mob is forced to drop loot, else false
     */
    boolean isForcingDrops();

    /**
     * Sets if the Mob should be forced to drop its loot.
     * @param drop true if mob is forced to drop loot, else false
     */
    void setForcingDrops(boolean drop);

    /**
     * Whether this Mob is currently moving.
     * @return true if mob is moving, else false
     */
    boolean isMoving();

    /**
     * Utility Method to normalize the rotation value to be within {@code 0.0F} and {@code 360.0F}.
     * @param rotation Rotation to normalize
     * @return Normalized Rotation
     */
    static float normalizeRotation(float rotation) {
        return rotation > 360 ? (rotation - (float) (360 * Math.floor(rotation / 360))) : rotation;
    }

    /**
     * Fetches the Mob's current body rotation (between 0.0F and 360.0F)
     * @return Current Body Rotation
     */
    float getBodyRotation();

    /**
     * Sets the Mob's current body rotation.
     * <br><br>
     * If the rotation is not between 0.0F and 360.0F, it will be wrapped to be between 0.0F and 360.0F (e.g. 480.0F = 120.0F).
     * @param rotation Body Rotation to set
     */
    void setBodyRotation(float rotation);

    /**
     * Fetches the Mob's current head rotation (between 0.0F and 360.0F)
     * @return Current Head Rotation
     */
    float getHeadRotation();

    /**
     * Sets the Mob's current head rotation.
     * <br><br>
     * If the rotation is not between 0.0F and 360.0F, it will be wrapped to be between 0.0F and 360.0F (e.g. 480.0F = 120.0F).
     * @param rotation Head Rotation to set
     */
    void setHeadRotation(float rotation);

    /**
     * Fetches the Mob's current yaw (between 0.0F and 360.0F)
     * @return Current Yaw
     */
    float getYaw();

    /**
     * Sets the Mob's current yaw.
     * <br><br>
     * If the rotation is not between 0.0F and 360.0F, it will be wrapped to be between 0.0F and 360.0F (e.g. 480.0F = 120.0F).
     * @param rotation Yaw to set
     */
    void setYaw(float rotation);

    /**
     * Fetches the Mob's current pitch (between 0.0F and 360.0F)
     * @return Current Pitch
     */
    float getPitch();

    /**
     * Sets the Mob's current pitch.
     * <br><br>
     * If the rotation is not between 0.0F and 360.0F, it will be wrapped to be between 0.0F and 360.0F (e.g. 480.0F = 120.0F).
     * @param rotation Pitch to set
     */
    void setPitch(float rotation);

    /**
     * Fetches a set of all entities that this Mob will not collide with.
     * @return Entities that this Mob will not collide with
     */
    Set<? extends Entity> getCollideExemptions();

    /**
     * Adds an entity that this Mob will not collide with.
     * @param en Entity to add
     * @throws IllegalArgumentException if entity is null
     */
    void addCollideExemption(@NotNull Entity en) throws IllegalArgumentException;

    /**
     * Adds an array of entities that this Mob will not collide with.
     * @param entities Entities to add
     * @throws IllegalArgumentException if entities or any entity is null
     */
    default void addCollideExemptions(@NotNull Entity... entities) throws IllegalArgumentException {
        if (entities == null) throw new IllegalArgumentException("Entities cannot be null!");
        addCollideExemptions(Arrays.asList(entities));
    }

    /**
     * Adds a collection of entities that this Mob will not collide with.
     * @param entities Entities to add
     * @throws IllegalArgumentException if entities or any entity is null
     */
    default void addCollideExemptions(@NotNull Iterable<Entity> entities) throws IllegalArgumentException {
        if (entities == null) throw new IllegalArgumentException("Entities cannot be null!");
        for (Entity en : entities) {
            if (en == null) throw new IllegalArgumentException("Collection cannot contain any null entities!");
            addCollideExemption(en);
        }
    }

    /**
     * Removes an entity that this Mob will not collide with.
     * @param en Entity to remove
     * @throws IllegalArgumentException if entity is null
     */
    void removeCollideExemption(@NotNull Entity en) throws IllegalArgumentException;

    /**
     * Fetches the amount of experience this Mob will drop.
     * @return Amount of Experience
     */
    int getDroppedExperience();

    /**
     * Sets the amount of experience this Mob will drop.
     * @param exp Experience Dropped
     * @throws IllegalArgumentException if experience is less than 0
     */
    void setDroppedExperience(int exp) throws IllegalArgumentException;

    /**
     * Makes this Mob perform an Animation.
     * @param anim Animation to perform
     */
    void playAnimation(@NotNull EntityAnimation anim);

    /**
     * Fetches the Mob's current animation speed.
     * @return Current Animation Speed
     */
    float getAnimationSpeed();

    /**
     * Sets the Mob's current animation speed.
     * @param speed Animation Speed to set
     * @throws IllegalArgumentException if speed is less than 0.0F
     */
    void setAnimationSpeed(float speed) throws IllegalArgumentException;

    /**
     * Whether this Mob has vertical collision enabled.
     * @return true if mob has vertical collision, else false
     */
    boolean hasVerticalCollision();

    /**
     * Sets if this Mob has vertical collision enabled.
     * @param collision true if mob has vertical collision, else false
     */
    void setVerticalCollision(boolean collision);

    /**
     * Whether this Mob has horizontal collision enabled.
     * @return true if mob has horizontal collision, else false
     */
    boolean hasHorizontalCollision();

    /**
     * Sets if this Mob has horizontal collision enabled.
     * @param collision true if mob has horizontal collision, else false
     */
    void setHorizontalCollision(boolean collision);

    /**
     * Fetches how far this Mob has automatically walked.
     * @return Walking Distance
     */
    float getWalkDistance();

    /**
     * Fetches how far this Mob has moved.
     * @return Movement Distance
     */
    float getMoveDistance();

    /**
     * Fetches how far this Mob has flown.
     * @return Flying Distance
     */
    float getFlyDistance();

    /**
     * Whether this Entity is immune to explosions.
     * @return true if immune, else false
     */
    boolean isImmuneToExplosions();

    /**
     * Whether this Mob can be spawned in Peaceful Mode,
     * @return true if peaceful, else false
     */
    boolean isPeacefulCompatible();

    /**
     * Whether this Mob is currently in a Bubble Column.
     * @return true if in bubble column, else false
     */
    boolean isInBubbleColumn();

    /**
     * Whether this Mob is immune to being damaged by this DamageCause.
     * @param cause DamageCause to check
     * @return true if immune, else false
     */
    boolean isInvulnerableTo(@Nullable EntityDamageEvent.DamageCause cause);

    /**
     * Fetches the maximum distance this Mob can fall before taking damage.
     * @return Maximum Fall Distance
     */
    int getMaxFallDistance();

    /**
     * Whether this Mob is pushable by this entity.
     * @param entity Entity to check
     * @return true if entity can push this mob, else false
     */
    boolean isPushableBy(@Nullable Entity entity);

    /**
     * Fetches the maximum block height this entity can walk up without jumping.
     * @return Maximum step height
     */
    float getMaxUpStep();

    /**
     * Sets the maximum block height this entity can walk up without jumping.
     * @param maxUpStep Maximum step height
     */
    void setMaxUpStep(float maxUpStep);
    
    /**
     * Fetches the last position this Entity touched {@link Material#LAVA}.
     * @return Last Lava Position
     */
    Position getLastLavaContact();

    /**
     * <p>Sets the Mob's Riptiding Ticks.</p>
     * <p>The mob will begin riptiding if {@link #isRiptiding()} returns false, and the number is greater than 0.</p>
     * @param ticks Riptiding Ticks to set
     * @throws IllegalArgumentException if ticks is negative
     */
    void setRiptideTicks(int ticks) throws IllegalArgumentException;

    /**
     * Makes this Mob stop riptiding.
     */
    default void stopRiptiding() {
        setRiptideTicks(0);
    }

    /**
     * Adds Riptiding Ticks to the Mob's current Riptiding Ticks.
     * @param ticks Riptiding Ticks to add
     * @throws IllegalArgumentException if ticks is negative
     */
    default void addRiptideTicks(int ticks) throws IllegalArgumentException {
        if (ticks < 0) throw new IllegalArgumentException("Riptiding ticks cannot be negative!");
        setRiptideTicks(getRiptideTicks() + ticks);
    }

    /**
     * Fetches the Mob's current Riptiding Ticks.
     * @return Current Riptiding Ticks
     */
    int getRiptideTicks();

    /**
     * Whether this Mob is currently riptiding.
     * @return true if riptiding, else false
     */
    default boolean isRiptiding() {
        return getRiptideTicks() == 0;
    }
}
