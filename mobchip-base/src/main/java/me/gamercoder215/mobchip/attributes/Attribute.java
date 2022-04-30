package me.gamercoder215.mobchip.attributes;

import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.util.ChipRegistry.RegistryKey;

/**
 * Represents an extendible Attribute
 */
public abstract class Attribute {
	
	/**
	 * Maximum Name Length
	 */
	public static final int MAX_NAME_LENGTH = 64;
	
	private final RegistryKey id;
	
	private double defaultValue;
	private double min;
	private double max;
	
	/**
	 * Constructs an Attribute, with min, max, and default all being 1.
	 * @param id Registry ID, cannot be null
	 * @throws IllegalArgumentException if ID is null or value is greater than {@value #MAX_NAME_LENGTH}
	 */
	protected Attribute(@NotNull RegistryKey id) throws IllegalArgumentException {
		this(id, 1);
	}
	

	/**
	 * Constructs an Attribute, with min, max, and default all the same.
	 * @param id Registry ID, cannot be null
	 * @param defaultValue Default value of Attribute
	 * @throws IllegalArgumentException if ID is null or value is greater than {@value #MAX_NAME_LENGTH}
	 */
	protected Attribute(@NotNull RegistryKey id, double defaultValue) throws IllegalArgumentException {
		this(id, defaultValue, defaultValue, defaultValue);
	}

	/**
	 * Constructs an Attribute.
	 * @param id ID of Attribute
	 * @param defaultValue Default Value of Attribute
	 * @param min Minimum Value of Attribute
	 * @param max Maximum Value of Attribute
	 * @throws IllegalArgumentException if ID is null, value is greater than {@value #MAX_NAME_LENGTH}, <code>min > max</code>, <code>max < min</code>, or <code>defaultValue < min || defaultValue > max</code>
	 */
	protected Attribute(@NotNull RegistryKey id, double defaultValue, double min, double max) throws IllegalArgumentException {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		if (id.getValue().length() > MAX_NAME_LENGTH) throw new IllegalArgumentException("Name cannot be greater than 64 chars");
		if (min > max) throw new IllegalArgumentException("Minimum must be less than or equal to max");
		if (defaultValue < min) throw new IllegalArgumentException("Default less than minimum");
		if (defaultValue > max) throw new IllegalArgumentException("Default greater than maximum");
		
		this.id = id;
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Returns the ID of this Attribute.
	 * <p>
	 * Ex: <code>player.strength</code>
	 * @return ID of this Attribute.
	 */
	@NotNull
	public final RegistryKey getId() {
		return this.id;
	}
	
	/**
	 * Get the Default Value of this Attribute.
	 * @return Default Value
	 */
	public final double getDefaultValue() {
		return this.defaultValue;
	}
	
	/**
	 * Sets the Default value of this Attribute.
	 * @param defaultValue New Default Value
	 * @throws IllegalArgumentException if less than min or greater than max
	 */
	public final void setDefaultValue(double defaultValue) throws IllegalArgumentException {
		if (defaultValue < min) throw new IllegalArgumentException("Default less than minimum");
		if (defaultValue > max) throw new IllegalArgumentException("Default greater than maximum");
		
		this.defaultValue = defaultValue;
	}
	
	/**
	 * Get the Minimum Value of this Attribute.
	 * @return Current Minimum Value, or {@link #getDefaultValue()} if not set
	 */
	public final double getMinValue() {
		return this.min;
	}
	
	/**
	 * Get the Maximum Value of this Attribute.
	 * @return Current Maximum Value, or {@link #getDefaultValue()} if not set
	 */
	public final double getMaxValue() {
		return this.max;
	}
	
	/**
	 * Sets the Maximum Value of this Attribute
	 * @param max New Maximum Value
	 * @throws IllegalArgumentException if max is less than the current min
	 */
	public final void setMaxValue(double max) throws IllegalArgumentException {
		if (max < min) throw new IllegalArgumentException("Maximum must be more than minimum");
		this.max = max;
	}
	
	/**
	 * Sets the Minimum Value of this Attribute
	 * @param min New Minimum Value
	 * @throws IllegalArgumentException if min is more than the current max
	 */
	public final void setMinValue(double min) throws IllegalArgumentException {
		if (min > max) throw new IllegalArgumentException("Minimum must be less than maximum");
		this.min = min;
	}
	
	/**
	 * Sets both the Maximum and Minimum Value of this Attribute.
	 * <p>
	 * Useful for when changing both at once.
	 * @param min New Minimum Value
	 * @param max New Maximum Value
	 * @throws IllegalArgumentException if given minimum is less than given maximum
	 */
	public final void setMinMaxValue(double min, double max) throws IllegalArgumentException {
		if (min > max) throw new IllegalArgumentException("Minimum must be less than maximum");
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Creates a Custom Attribute.
	 * @param key RegistryKey to use
	 * @param defaultValue Default Value of Attribute
	 * @param min Minimum Value
	 * @param max Maximum Value
	 * @return Created Attribute
	 * @see #Attribute(RegistryKey, double, double, double)
	 */
	@NotNull
	public static final Attribute create(@NotNull RegistryKey key, double defaultValue, double min, double max) {
		return new Attribute(key, defaultValue, min, max) {};
	}

}
