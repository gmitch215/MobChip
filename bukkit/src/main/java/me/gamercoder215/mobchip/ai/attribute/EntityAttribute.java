package me.gamercoder215.mobchip.ai.attribute;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represents an Entity's Attribute
 */
public final class EntityAttribute implements Attribute {

    private static final ChipUtil wrapper = ChipUtil.getWrapper();

    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_MAX_HEALTH}
     */
    public static final Attribute GENERIC_MAX_HEALTH = wrapper.getDefaultAttribute("generic.max_health");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_FOLLOW_RANGE}
     */
    public static final Attribute GENERIC_FOLLOW_RANGE = wrapper.getDefaultAttribute("generic.follow_range");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_KNOCKBACK_RESISTANCE}
     */
    public static final Attribute GENERIC_KNOCKBACK_RESISTANCE = wrapper.getDefaultAttribute("generic.knockback_resistance");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_MOVEMENT_SPEED}
     */
    public static final Attribute GENERIC_MOVEMENT_SPEED = wrapper.getDefaultAttribute("generic.movement_speed");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_FLYING_SPEED}
     */
    public static final Attribute GENERIC_FLYING_SPEED = wrapper.getDefaultAttribute("generic.flying_speed");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_ATTACK_DAMAGE}
     */
    public static final Attribute GENERIC_ATTACK_DAMAGE = wrapper.getDefaultAttribute("generic.attack_damage");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_ATTACK_SPEED}
     */
    public static final Attribute GENERIC_ATTACK_SPEED = wrapper.getDefaultAttribute("generic.attack_speed");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_ARMOR}
     */
    public static final Attribute GENERIC_ARMOR = wrapper.getDefaultAttribute("generic.armor");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_ARMOR_TOUGHNESS}
     */
    public static final Attribute GENERIC_ARMOR_TOUGHNESS = wrapper.getDefaultAttribute("generic.armor_toughness");
    /**
     * Represents Attribute#GENERIC_ATTACK_KNOCKBACK
     */
    public static final Attribute GENERIC_ATTACK_KNOCKBACK = wrapper.getDefaultAttribute("generic.attack_knockback");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#GENERIC_LUCK}
     */
    public static final Attribute GENERIC_LUCK = wrapper.getDefaultAttribute("generic.luck");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#ZOMBIE_SPAWN_REINFORCEMENTS}
     */
    public static final Attribute ZOMBIE_SPAWN_REINFORCEMENTS = wrapper.getDefaultAttribute("zombie.spawn_reinforcements");
    /**
     * Represents {@link org.bukkit.attribute.Attribute#HORSE_JUMP_STRENGTH}
     */
    public static final Attribute HORSE_JUMP_STRENGTH = wrapper.getDefaultAttribute("horse.jump_strength");
    /**
     * Represents Attribute#GENERIC_MAX_ABSORPTION
     */
    public static final Attribute MAX_ABSORPTION = wrapper.getDefaultAttribute("generic.max_absorption");
    /**
     * Represents Attribute#GENERIC_GRAVITY
     */
    public static final Attribute GRAVITY = wrapper.getDefaultAttribute("generic.gravity");
    /**
     * Represents Attribute#GENERIC_SCALE
     */
    public static final Attribute SCALE = wrapper.getDefaultAttribute("generic.scale");
    /**
     * Represents Attribute#GENERIC_STEP_HEIGHT
     */
    public static final Attribute STEP_HEIGHT = wrapper.getDefaultAttribute("generic.step_height");
    /**
     * Represents Attribute#SAFE_FALL_DISTANCE
     */
    public static final Attribute SAFE_FALL_DISTANCE = wrapper.getDefaultAttribute("generic.safe_fall_distance");
    /**
     * Represents Attribute#FALL_DAMAGE_MULTIPLIER
     */
    public static final Attribute FALL_DAMAGE_MULTIPLIER = wrapper.getDefaultAttribute("generic.fall_damage_multiplier");
    /**
     * Represents Attribute#BLOCK_INTERACTION_RANGE
     */
    public static final Attribute BLOCK_INTERACTION_RANGE = wrapper.getDefaultAttribute("player.block_interaction_range");
    /**
     * Represents Attribute#ENTITY_INTERACTION_RANGE
     */
    public static final Attribute ENTITY_INTERACTION_RANGE = wrapper.getDefaultAttribute("player.entity_interaction_range");
    /**
     * Represents Attribute#BLOCK_BREAK_SPEED
     */
    public static final Attribute BLOCK_BREAK_SPEED = wrapper.getDefaultAttribute("player.block_break_speed");


    /**
     * Converts a Bukkit Attribute to a MobChip Attribute.
     * @param attribute Bukkit Attribute
     * @return MobChip Attribute, or null if Attribute is null
     */
    @Nullable
    public static Attribute fromBukkit(@Nullable org.bukkit.attribute.Attribute attribute) {
        if (attribute == null) return null;
        return new EntityAttribute(attribute);
    }

    private Attribute handle;

    private EntityAttribute(Attribute handle) {
        this.handle = handle;
    }

    private EntityAttribute(org.bukkit.attribute.Attribute bukkit) {
        try {
            for (Field f : EntityAttribute.class.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) continue;
                if (!Modifier.isFinal(f.getModifiers())) continue;

                if (f.getName().equals(bukkit.name())) {
                    f.setAccessible(true);
                    this.handle = (Attribute) f.get(null);
                    return;
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Fetches the EntityAttribute Builder.
     * @return Attribute Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public double getMinValue() {
        return handle.getMinValue();
    }

    @Override
    public double getMaxValue() {
        return handle.getMaxValue();
    }

    @Override
    public double getDefaultValue() {
        return handle.getDefaultValue();
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return handle.getKey();
    }

    @Override
    public boolean isClientSide() {
        return handle.isClientSide();
    }

    /**
     * Fetches an EntityAttribute from the Minecraft registrar.
     * @param key Key of the Attribute
     * @return Attribute found, or null if not found
     */
    @Nullable
    public static EntityAttribute getAttribute(@Nullable NamespacedKey key) {
        if (key == null) return null;
        if (!wrapper.existsAttribute(key)) return null;
        return new EntityAttribute(wrapper.getAttribute(key));
    }

    /**
     * Whether an Attribute exists in the Minecraft registrar.
     * @param key Key of the Attribute
     * @return true if attribute exists, else false
     */
    public static boolean exists(@Nullable NamespacedKey key) {
        return getAttribute(key) != null;
    }

    /**
     * Entity Attribute Builder
     */
    public static final class Builder {

        private NamespacedKey key;
        private double minValue = 1.0D;
        private double defaultValue = 1.0D;
        private double maxValue = 1024.0D;

        private boolean clientSide = true;

        private Builder() {}

        /**
         * Sets the Attribute's NamespacedKey.
         * @param key NamespacedKey
         * @return this builder, for chaining
         */
        public Builder setKey(@NotNull NamespacedKey key) {
            this.key = key;
            return this;
        }

        /**
         * Sets the Attribute's minimum value. Default: 1.0D
         * @param minValue Minimum value
         * @return this builder, for chaining
         */
        public Builder setMinValue(double minValue) {
            this.minValue = minValue;
            return this;
        }

        /**
         * Sets the Attribute's default value. Default: 1.0D
         * @param defaultValue Default value
         * @return this builder, for chaining
         */
        public Builder setDefaultValue(double defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        /**
         * Sets the Attribute's maximum value. Default: 1024.0D
         * @param maxValue Maximum value
         * @return this builder, for chaining
         */
        public Builder setMaxValue(double maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        /**
         * Sets whether the Attribute will appear on the client's side. Default: true
         * <br><br>
         * <strong>Do not use this method unless you know what you are doing!</strong>
         * @param clientSide Whether the Attribute will appear on the client's side
         * @return this builder, for chaining
         */
        public Builder setClientSide(boolean clientSide) {
            this.clientSide = clientSide;
            return this;
        }

        /**
         * Builds the EntityAttribute.
         * @return Attribute Created
         * @throws IllegalArgumentException if the operations don't make sense (e.g. min is greater than max), the key is null or a number is negative
         * @throws UnsupportedOperationException if this attribute already exists in the registry
         */
        public EntityAttribute build() throws IllegalArgumentException, UnsupportedOperationException {
            if (minValue < 0 || maxValue < 0 || defaultValue < 0) throw new IllegalArgumentException("Values cannot be negative");
            if (minValue > maxValue) throw new IllegalArgumentException("Minimum must be less than maximum");
            if (defaultValue < minValue || defaultValue > maxValue) throw new IllegalArgumentException("Default value must be between minimum and maximum");
            if (key == null) throw new IllegalArgumentException("Key cannot be null");

            if (wrapper.existsAttribute(key)) throw new UnsupportedOperationException("Attribute already exists");

            return new EntityAttribute(wrapper.registerAttribute(key, minValue, defaultValue, maxValue, clientSide));
        }
    }

}
