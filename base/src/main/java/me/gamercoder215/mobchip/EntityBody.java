package me.gamercoder215.mobchip;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Represents an Entity's Body and Attributes
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
     * @param drops Collection of Default Drops
     */
    default void setDefaultDrops(@Nullable Collection<ItemStack> drops) {
        if (drops == null) return;
        setDefaultDrops(drops.toArray(new ItemStack[0]));
    }
}
