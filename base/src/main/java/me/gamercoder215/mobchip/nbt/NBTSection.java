package me.gamercoder215.mobchip.nbt;

import org.bukkit.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a Section in the NBT File
 */
public interface NBTSection {

    /**
     * Fetches the current path of this section
     * @return The path of this section
     */
    @NotNull
    String getCurrentPath();

    /**
     * Fetches a Map of Keys to Values of this NBT Tag for the Entity
     * @param deep Whether to include children values (keys will be mapped with a "." in front of them)
     * @return Map Representation of the NBT Tag Values
     */
    @NotNull
    Map<String, Object> getValues(boolean deep);

    /**
     * Fetches a Set of Keys to Values of this NBT Tag for the Entity
     * @param deep Whether to include children values (keys will be mapped with a "." in front of them)
     * @return Set Representation of the NBT Tag Ke
     */
    default Set<String> getKeys(boolean deep) {
        return getValues(deep).keySet();
    }

    /**
     * Sets a NBT tag to an object. If value is null, this will call {@link #remove(String)}.
     * @param key Key of the tag to set
     * @param value Value of the tag to set
     */
    void set(@Nullable String key, @Nullable Object value);

    /**
     * Whether this key is present in the NBT Tag
     * @param key Key to check
     * @return true if the key is present, false otherwise
     */
    boolean isSet(@Nullable String key);

    /**
     * Removes a NBT tag from this Entity's NBT.
     * @param key Key of the tag to remove
     */
    void remove(@Nullable String key);

    /**
     * Fetches a double from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag
     */
    double getDouble(@Nullable String key);

    /**
     * Fetches a double from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag
     */
    double getDouble(@Nullable String key, double def);

    /**
     * Whether the key's assignment is a double.
     * @param key Key of the tag to check
     * @return true if key is a double, false otherwise
     */
    boolean isDouble(@Nullable String key);

    /**
     * Fetches an integer from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or 0 if not found
     */
    int getInteger(@Nullable String key);

    /**
     * Fetches an integer from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    int getInteger(@Nullable String key, int def);

    /**
     * Whether the key's assignment is an integer.
     * @param key Key of the tag to check
     * @return true if key is an integer, false otherwise
     */
    boolean isInt(@Nullable String key);

    /**
     * Fetches a boolean from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or false if not found
     */
    boolean getBoolean(@Nullable String key);

    /**
     * Fetches a boolean from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    boolean getBoolean(@Nullable String key, boolean def);

    /**
     * Whether the key's assignment is a boolean.
     * @param key Key to check
     * @return true if key is assigned a boolean, false otherwise
     */
    boolean isBoolean(@Nullable String key);

    /**
     * Fetches a float from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or 0 if not found
     */
    float getFloat(@Nullable String key);

    /**
     * Fetches a float from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    float getFloat(@Nullable String key, float def);

    /**
     * Whether the key's assignment is a float.
     * @param key Key to check
     * @return true if key is assigned a float, false otherwise
     */
    boolean isFloat(@Nullable String key);
    
    /**
     * Fetches a long from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or 0 if not found
     */
    long getLong(@Nullable String key);

    /**
     * Fetches a long from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    long getLong(@Nullable String key, long def);

    /**
     * Whether the key's assignment is a long.
     * @param key Key to check
     * @return true if key is assigned a long, false otherwise
     */
    boolean isLong(@Nullable String key);

    /**
     * Fetches a string from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    byte getByte(@Nullable String key);

    /**
     * Fetches a byte from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    byte getByte(@Nullable String key, byte def);

    /**
     * Whether the key's assignment is a byte.
     * @param key Key to check
     * @return true if key is assigned a byte, false otherwise
     */
    boolean isByte(@Nullable String key);
    
    /**
     * Fetches a string from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    String getString(@Nullable String key);

    /**
     * Fetches a string from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    String getString(@Nullable String key, @Nullable String def);

    /**
     * Whether the key's assignment is a string.
     * @param key Key to check
     * @return true if key is assigned to a string, false otherwise
     */
    boolean isString(@Nullable String key);

    /**
     * Fetches a bukkit NamespacedKey from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    NamespacedKey getNamespacedKey(@Nullable String key);

    /**
     * Fetches a bukkit NamespacedKey from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    NamespacedKey getNamespacedKey(@Nullable String key, @Nullable NamespacedKey def);

    /**
     * Whether the key's assignment is a bukkit NamespacedKey.
     * @param key Key to check
     * @return true if key is a NamespacedKey, false otherwise
     */
    boolean isNamespacedKey(@Nullable String key);
    
    /**
     * Fetches a UUID from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    UUID getUUID(@Nullable String key);

    /**
     * Fetches a UUID from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    UUID getUUID(@Nullable String key, @Nullable UUID def);

    /**
     * Whether the key's assignment is a UUID.
     * @param key Key to check
     * @return Whether the key's assignment is a UUID
     */
    boolean isUUID(@Nullable String key);

    /**
     * Fetches a bukkit OfflinePlayer from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    OfflinePlayer getOfflinePlayer(@Nullable String key);

    /**
     * Fetches a bukkit OfflinePlayer from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    OfflinePlayer getOfflinePlayer(@Nullable String key, @Nullable OfflinePlayer def);

    /**
     * Whether the key's assignment is an Offline Player.
     * @param key Key to check
     * @return true if the key is an Offline Player, false otherwise
     */
    boolean isOfflinePlayer(@Nullable String key);
    
    /**
     * Fetches an enum from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param enumClass Class of Enum type
     * @param <T> Enum type
     * @return Value of the tag, or null if not found
     */
    @Nullable
    <T extends Enum<T>> T getEnum(@Nullable String key, Class<T> enumClass);

    /**
     * Fetches an enum from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param enumClass Class of Enum type
     * @param def Default value to return if the tag is not found
     * @param <T> Enum type
     * @return Value of the tag, or default if not found
     */
    @Nullable
    <T extends Enum<T>> T getEnum(@Nullable String key, Class<T> enumClass, @Nullable T def);

    /**
     * Whether the key's assignment is an enum.
     * @param key Key to check
     * @return true if the key is an enum, false otherwise
     */
    boolean isEnum(@Nullable String key);

    /**
     * Whether the key's assignment is an enum and matches the given enum class.
     * @param key Key to check
     * @param enumClass Class of Enum type
     * @return true if the key is an enum and matches the given enum class, false otherwise
     * @param <T> Enum type
     * @throws IllegalArgumentException if the key is null
     */
    <T extends Enum<T>> boolean isEnum(@Nullable String key, Class<T> enumClass) throws IllegalArgumentException;
    
    /**
     * Fetches a bukkit Location from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    Location getLocation(@Nullable String key);

    /**
     * Fetches a bukkit Location from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    Location getLocation(@Nullable String key, @Nullable Location def);

    /**
     * Whether the key's assignment is a bukkit Location.
     * @param key Key of the tag to check
     * @return true if the key's assignment is a bukkit Location, false otherwise
     */
    boolean isLocation(@Nullable String key);

    /**
     * Fetches a bukkit Vector from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    Vector getVector(@Nullable String key);

    /**
     * Fetches a bukkit Vector from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    Vector getVector(@Nullable String key, @Nullable Vector def);
    
    /**
     * Whether the key's assignment is a bukkit Vector.
     * @param key Key to check
     * @return true if key's assignment is a bukkit Vector, false otherwise
     */
    boolean isVector(@Nullable String key);

    /**
     * Fetches a bukkit ItemStack from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    ItemStack getItemStack(@Nullable String key);

    /**
     * Fetches a bukkit ItemStack from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    ItemStack getItemStack(@Nullable String key, @Nullable ItemStack def);

    /**
     * Whether the key's assignment is a bukkit ItemStack.
     * @param key Key to check
     * @return true if key's assignment is a bukkit ItemStack, false otherwise
     */
    boolean isItemStack(@Nullable String key);

    /**
     * Fetches a ConfigurationSerializable Object from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param clazz Class of the ConfigurationSerializable Object to fetch
     * @param <T> ConfigurationSerializable Object type
     * @return Value of the tag, or null if not found
     */
    @Nullable
    <T extends ConfigurationSerializable> T getObject(@Nullable String key, @NotNull Class<T> clazz);

    /**
     * Fetches a ConfigurationSerializable Object from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param clazz Class of the ConfigurationSerializable Object to fetch
     * @param def Default value to return if the tag is not found
     * @param <T> ConfigurationSerializable Object type
     * @return Value of the tag, or default if not found
     */
    @Nullable
    <T extends ConfigurationSerializable> T getObject(@Nullable String key, @NotNull Class<T> clazz, @Nullable T def);

    /**
     * Fetches a bukkit Color from this Entity's NBT.
     * @param path Key of the tag to fetch 
     * @return Value of the tag, or null if not found
     */
    @Nullable
    Color getColor(@Nullable String path);
    
    /**
     * Fetches a bukkit Color from this Entity's NBT.
     * @param path Key of the tag to fetch 
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    Color getColor(@Nullable String path, @Nullable Color def);

    /**
     * Whether the key's assignment is a bukkit Color.
     * @param path Key to check
     * @return true if key's assignment is a bukkit Color, false otherwise
     */
    boolean isColor(@Nullable String path);
    
    /**
     * Fetches a NBTSection from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or null if not found
     */
    @Nullable
    NBTSection getSection(@Nullable String key);

    /**
     * Fetches a NBTSection from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    NBTSection getSection(@Nullable String key, @Nullable NBTSection def);

    /**
     * Creates a new NBTSection. If this key already exists, it will call {@link #getSection(String)}.
     * @param key Key of the tag to create
     * @return New NBTSection with the given key and value
     * @throws IllegalArgumentException If the key is null
     */
    @NotNull
    NBTSection getOrCreateSection(@NotNull String key) throws IllegalArgumentException;

    /**
     * Creates a new NBTSection with the given key and value. If this key already exists, it will call {@link #getSection(String)}.
     * @param key Key of the tag to create
     * @param map Contents of the Section
     * @return New NBTSection with the given key and value
     * @throws IllegalArgumentException If the key or map is null
     */
    @NotNull
    NBTSection getOrCreateSection(@NotNull String key, Map<String, Object> map) throws IllegalArgumentException;

    /**
     * Whether the key's assignment is a NBTSection.
     * @param key Key to check
     * @return true if key's assignment is a NBTSection, false otherwise
     */
    boolean isSection(@Nullable String key);
    
    /**
     * Fetches an Object List of ConfigurationSerializable Objects from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or empty list if not found
     */
    @NotNull
    List<?> getList(@Nullable String key);

    /**
     * Fetches an Object List from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    List<?> getList(@Nullable String key, @Nullable List<?> def);

    /**
     * Whether this key's assignment is a list.
     * @param key Key to check
     * @return true if key's assignment is a list, false otherwise
     */
    boolean isList(@Nullable String key);

    /**
     * Fetches a String to Object Map from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or empty map if not found
     */
    @NotNull
    Map<String, Object> getMap(@Nullable String key);

    /**
     * Fetches a String to Object Map from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    Map<String, Object> getMap(@Nullable String key, @Nullable Map<String, Object> def);

    /**
     * Whether this key's assignment is a map.
     * @param key Key to check
     * @return true if key's assignment is a map, false otherwise
     */
    boolean isMap(@Nullable String key);

    /**
     * Fetches a String List from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or empty list if not found
     */
    @NotNull
    default List<String> getStringList(@Nullable String key) {
        return getList(key).stream().map(Object::toString).collect(Collectors.toList());
    }

    /**
     * Fetches a String List from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    default List<String> getStringList(@Nullable String key, @Nullable List<String> def) {
        if (getList(key) == null) return def;
        return getStringList(key);
    }

    /**
     * Fetches an Integer List from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @return Value of the tag, or empty list if not found
     */
    @NotNull
    default List<Integer> getIntegerList(@Nullable String key) {
        if (getList(key) == null) return null;
        return getList(key).stream().map(Object::toString).map(s -> { try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; } }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Fetches an Integer List from this Entity's NBT.
     * @param key Key of the tag to fetch
     * @param def Default value to return if the tag is not found
     * @return Value of the tag, or default if not found
     */
    @Nullable
    default List<Integer> getIntegerList(@Nullable String key, @Nullable List<Integer> def) {
        if (getList(key) == null) return def;
        return getIntegerList(key);
    }

}
