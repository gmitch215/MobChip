package me.gamercoder215.mobchip.ai.attribute;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an Entity's Attribute
 */
public final class EntityAttribute implements Attribute {

    private Attribute handle;

    private static final ChipUtil wrapper = ChipUtil.getWrapper();

    private EntityAttribute(Attribute handle) {
        this.handle = handle;
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
    @Nullable
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
