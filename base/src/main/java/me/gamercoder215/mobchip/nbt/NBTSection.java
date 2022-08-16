package me.gamercoder215.mobchip.nbt;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
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
