package me.gamercoder215.mobchip.abstraction;

import me.gamercoder215.mobchip.nbt.NBTSection;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class NBTSection1_16_R2 implements NBTSection {

    private final NBTTagCompound tag;
    private final Runnable saveFunc;

    private final String currentPath;

    NBTSection1_16_R2(NBTTagCompound tag, Runnable saveFunc, String path) {
        this.tag = tag;
        this.saveFunc = saveFunc;
        this.currentPath = path;
    }

    NBTSection1_16_R2(Mob m) {
        this.tag = new NBTTagCompound();
        this.currentPath = "";
        ChipUtil1_16_R2.toNMS(m).save(tag);
        this.saveFunc = () -> ChipUtil1_16_R2.toNMS(m).load(tag);
    }

    static NBTBase serialize(Object v) {
        if (v.getClass().isArray()) {
            NBTTagList tag = new NBTTagList();
            for (int i = 0; i < Array.getLength(v); i++) tag.add(i, serialize(Array.get(v, i)));
            return tag;
        }

        if (v instanceof Collection<?>) {
            List<?> collection = new ArrayList<>((Collection<?>) v);
            NBTTagCompound coll = new NBTTagCompound();

            try {
                coll.setString(ChipUtil.CLASS_TAG, Collection.class.getName());
                NBTTagList tag = new NBTTagList();
                for (int i = 0; i < collection.size(); i++) tag.add(i, serialize(collection.get(i)));
                coll.set("values", tag);

                Field idF = NBTTagList.class.getDeclaredField("w");
                idF.setAccessible(true);

                coll.setByte("id", idF.getByte(tag));
            } catch (ReflectiveOperationException e) {
                Bukkit.getLogger().severe("Failed to serialize collection: " + e.getMessage());
                for (StackTraceElement ste : e.getStackTrace()) Bukkit.getLogger().severe(ste.toString());
            }

            return coll;
        }

        if (v instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) v;
            NBTTagCompound tag = new NBTTagCompound();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) v).entrySet()) {
                tag.set(entry.getKey().toString(), serialize(entry.getValue()));
            }
            return tag;
        }

        if (v instanceof Enum<?>) {
            Enum<?> enumeration = (Enum<?>) v;
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString(ChipUtil.CLASS_TAG, enumeration.getClass().getName());
            tag.setString("value", ((Enum<?>) v).name());
            return tag;
        }

        if (v instanceof ConfigurationSerializable) {
            ConfigurationSerializable serializable = (ConfigurationSerializable) v;
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString(ChipUtil.CLASS_TAG, serializable.getClass().getName());
            tag.set("value", serialize(serializable.serialize()));
            return tag;
        }

        switch (v.getClass().getSimpleName().toLowerCase()) {
            case "short": return NBTTagShort.a((short) v);
            case "float": return NBTTagFloat.a((float) v);
            case "long": return NBTTagLong.a((long) v);
            case "byte": return NBTTagByte.a((byte) v);
            case "integer": case "int": return NBTTagInt.a((int) v);
            case "double": return NBTTagDouble.a((double) v);
            case "uuid": {
                UUID uid = (UUID) v;
                NBTTagCompound uuid = new NBTTagCompound();
                uuid.setString(ChipUtil.CLASS_TAG, uid.getClass().getName());
                uuid.setLong("least", uid.getLeastSignificantBits());
                uuid.setLong("most", uid.getMostSignificantBits());
                return uuid;
            }
            case "namespacedkey": {
                NamespacedKey key = (NamespacedKey) v;
                NBTTagCompound nmsKey = new NBTTagCompound();
                nmsKey.setString(ChipUtil.CLASS_TAG, key.getClass().getName());
                nmsKey.setString("namespace", key.getNamespace());
                nmsKey.setString("key", key.getKey());
                return nmsKey;
            }
            case "itemstack": {
                ItemStack item = (ItemStack) v;
                NBTTagCompound stack = new NBTTagCompound();
                stack.setString(ChipUtil.CLASS_TAG, item.getClass().getName());
                stack.set("item", CraftItemStack.asNMSCopy(item).getOrCreateTag());

                return stack;
            }
            case "offlineplayer": {
                OfflinePlayer p = (OfflinePlayer) v;
                NBTTagCompound player = new NBTTagCompound();
                player.setString(ChipUtil.CLASS_TAG, OfflinePlayer.class.getName());
                player.setString("id", p.getUniqueId().toString());

                return player;
            }
            case "location": {
                Location l = (Location) v;
                NBTTagCompound loc = new NBTTagCompound();
                loc.setString(ChipUtil.CLASS_TAG, l.getClass().getName());
                loc.setDouble("x", l.getX());
                loc.setDouble("y", l.getY());
                loc.setDouble("z", l.getZ());
                loc.setFloat("yaw", l.getYaw());
                loc.setFloat("pitch", l.getPitch());
                loc.setString("world", l.getWorld().getName());
                return loc;
            }
            case "vector": {
                Vector vec = (Vector) v;
                NBTTagCompound vector = new NBTTagCompound();
                vector.setString(ChipUtil.CLASS_TAG, vec.getClass().getName());
                vector.setDouble("x", vec.getX());
                vector.setDouble("y", vec.getY());
                vector.setDouble("z", vec.getZ());
                return vector;
            }
            case "color": {
                Color color = (Color) v;
                NBTTagCompound clr = new NBTTagCompound();
                clr.setString(ChipUtil.CLASS_TAG, color.getClass().getName());
                clr.setInt("rgb", color.asRGB());
                return clr;
            }
            default: return NBTTagString.a(v.toString());
        }
    }

    static Object deserialize(NBTBase v) {
        if (v instanceof NBTTagList) {
            NBTTagList list = (NBTTagList) v;
            List<Object> l = new ArrayList<>();
            list.stream().map(NBTSection1_16_R2::deserialize).forEach(l::add);
            return l.toArray();
        }

        if (v instanceof NBTTagCompound) {
            NBTTagCompound cmp = (NBTTagCompound) v;
            boolean isClass = cmp.get(ChipUtil.CLASS_TAG) != null && cmp.get(ChipUtil.CLASS_TAG) instanceof NBTTagString && !cmp.getString(ChipUtil.CLASS_TAG).isEmpty();

            if (isClass) {
                String className = cmp.getString(ChipUtil.CLASS_TAG);
                try {
                    Class<?> clazz = Class.forName(className);

                    if (clazz.isEnum()) return Enum.valueOf(clazz.asSubclass(Enum.class), cmp.getString("value"));

                    if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                        try {
                            Method deserialize = clazz.getDeclaredMethod("deserialize", Map.class);
                            deserialize.setAccessible(true);
                            return clazz.cast(deserialize.invoke(null, deserialize(cmp.getCompound("value"))));
                        } catch (NoSuchMethodException e) {
                            Bukkit.getLogger().severe("Class does not have deserialize method: " + className);
                            for (StackTraceElement ste : e.getStackTrace()) Bukkit.getLogger().severe(ste.toString());
                        } catch (InvocationTargetException e) {
                            Bukkit.getLogger().severe("Failed to deserialize class: " + className);
                            for (StackTraceElement ste : e.getStackTrace()) Bukkit.getLogger().severe(ste.toString());
                        } catch (ReflectiveOperationException e) {
                            Bukkit.getLogger().severe(e.getMessage());
                            for (StackTraceElement ste : e.getStackTrace()) Bukkit.getLogger().severe(ste.toString());
                        }
                    }

                    switch (clazz.getSimpleName()) {
                        case "map": {
                            Map<String, Object> map = new HashMap<>();
                            for (String key : cmp.getKeys()) map.put(key, deserialize(cmp.get(key)));
                            return map;
                        }
                        case "collection": {
                            int id = cmp.getInt("id");
                            NBTTagList list = cmp.getList("values", id);

                            List<? super Object> l = new ArrayList<>();
                            list.stream().map(NBTSection1_16_R2::deserialize).forEach(l::add);
                            return new ArrayList<>(l);
                        }
                        case "uuid": {
                            long most = cmp.getLong("most");
                            long least = cmp.getLong("least");
                            return new UUID(most, least);
                        }
                        case "offlineplayer": {
                            UUID uid = UUID.fromString(cmp.getString("id"));
                            return Bukkit.getOfflinePlayer(uid);
                        }
                        case "namespacedkey": {
                            String namespace = cmp.getString("namespace");
                            String key = cmp.getString("key");
                            return new NamespacedKey(namespace, key);
                        }
                        case "itemstack": {
                            NBTTagCompound item = cmp.getCompound("item");
                            return CraftItemStack.asBukkitCopy(net.minecraft.server.v1_16_R2.ItemStack.a(item));
                        }
                        case "location": {
                            String world = cmp.getString("world");
                            World w = Bukkit.getWorld(world);
                            if (w == null) throw new IllegalArgumentException("World not found: " + world);
                            double x = cmp.getDouble("x");
                            double y = cmp.getDouble("y");
                            double z = cmp.getDouble("z");
                            float yaw = cmp.getFloat("yaw");
                            float pitch = cmp.getFloat("pitch");
                            return new Location(w, x, y, z, yaw, pitch);
                        }
                        case "vector": {
                            double x = cmp.getDouble("x");
                            double y = cmp.getDouble("y");
                            double z = cmp.getDouble("z");
                            return new Vector(x, y, z);
                        }
                        case "color": {
                            int rgb = cmp.getInt("rgb");
                            return Color.fromRGB(rgb);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    throw new AssertionError("Unknown Class: " + className);
                }
            } else {
                Map<String, Object> map = new HashMap<>();
                for (String key : cmp.getKeys()) map.put(key, deserialize(cmp.get(key)));
                return map;
            }
        }

        switch (v.getTypeId()) {
            case 1: return ((NBTTagByte) v).asByte();
            case 2: return ((NBTTagShort) v).asShort();
            case 3: return ((NBTTagInt) v).asInt();
            case 4: return ((NBTTagLong) v).asLong();
            case 5: return ((NBTTagFloat) v).asFloat();
            case 6: return ((NBTTagDouble) v).asDouble();
            case 7: return ((NBTTagByteArray) v).getBytes();
            default: return v.asString();
        }
    }

    private void save() {
        saveFunc.run();
    }

    @Override
    public @NotNull String getCurrentPath() {
        return currentPath;
    }

    @Override
    public @NotNull Map<String, Object> getValues(boolean deep) {
        Map<String, Object> map = tag.getKeys().stream().filter(k -> !(tag.get(k) instanceof NBTTagCompound)).collect(Collectors.toMap(Function.identity(), k -> deserialize(tag.get(k))));
        if (!deep) return map;

        tag.getKeys().stream().filter(k -> (tag.get(k) instanceof NBTTagCompound)).forEach(s -> {
            NBTSection sec = getSection(s);
            sec.getValues(true).forEach((k, v) -> map.put(s + "." + k, v));
        });

        return map;
    }

    @Override
    public void set(@Nullable String key, @Nullable Object value) {
        if (key == null) return;
        if (key.equals(ChipUtil.CLASS_TAG)) return;

        if (value == null) tag.remove(key);
        else tag.set(key, serialize(value));
        save();
    }

    @Override
    public boolean isSet(@Nullable String key) {
        return false;
    }

    @Override
    public void remove(@Nullable String key) {
        tag.remove(key);
        save();
    }

    private <T> T get(String key) {
        if (key == null) return null;
        return (T) getValues(true).getOrDefault(key, null);
    }

    private boolean contains(String key) {
        if (key == null) return false;
        return getKeys(true).contains(key);
    }

    @Override
    public double getDouble(@Nullable String key) {
        return get(key);
    }

    @Override
    public double getDouble(@Nullable String key, double def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isDouble(@Nullable String key) {
        return contains(key) && (get(key) instanceof Double || isInt(key));
    }

    @Override
    public int getInteger(@Nullable String key) {
        return get(key);
    }

    @Override
    public int getInteger(@Nullable String key, int def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isInt(@Nullable String key) {
        return contains(key) && get(key) instanceof Integer;
    }

    @Override
    public boolean getBoolean(@Nullable String key) {
        return tag.getBoolean(key);
    }

    @Override
    public boolean getBoolean(@Nullable String key, boolean def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isBoolean(@Nullable String key) {
        return contains(key) && get(key) instanceof Boolean;
    }

    @Override
    public float getFloat(@Nullable String key) {
        return get(key);
    }

    @Override
    public float getFloat(@Nullable String key, float def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isFloat(@Nullable String key) {
        return contains(key) && (get(key) instanceof Float || isInt(key));
    }

    @Override
    public long getLong(@Nullable String key) {
        return get(key);
    }

    @Override
    public long getLong(@Nullable String key, long def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isLong(@Nullable String key) {
        return contains(key) && get(key) instanceof Long;
    }

    @Override
    public byte getByte(@Nullable String key) {
        return get(key);
    }

    @Override
    public byte getByte(@Nullable String key, byte def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isByte(@Nullable String key) {
        return contains(key) && get(key) instanceof Byte;
    }

    @Override
    public @Nullable String getString(@Nullable String key) {
        return contains(key) ? null : get(key);
    }

    @Override
    public @Nullable String getString(@Nullable String key, @Nullable String def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isString(@Nullable String key) {
        return contains(key) && get(key) instanceof String;
    }

    @Override
    public @Nullable NamespacedKey getNamespacedKey(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable NamespacedKey getNamespacedKey(@Nullable String key, @Nullable NamespacedKey def) {
        return tag.get(key) == null ? def : get(key);
    }

    @Override
    public boolean isNamespacedKey(@Nullable String key) {
        return contains(key) && get(key) instanceof NamespacedKey;
    }

    @Override
    public @Nullable UUID getUUID(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable UUID getUUID(@Nullable String key, @Nullable UUID def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isUUID(@Nullable String key) {
        return contains(key) && get(key) instanceof UUID;
    }

    @Override
    public @Nullable OfflinePlayer getOfflinePlayer(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable OfflinePlayer getOfflinePlayer(@Nullable String key, @Nullable OfflinePlayer def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isOfflinePlayer(@Nullable String key) {
        return contains(key) && get(key) instanceof OfflinePlayer;
    }

    @Override
    public <T extends Enum<T>> @Nullable T getEnum(@Nullable String key, Class<T> enumClass) {
        return enumClass.cast(get(key));
    }

    @Override
    public <T extends Enum<T>> @Nullable T getEnum(@Nullable String key, Class<T> enumClass, @Nullable T def) {
        return contains(key) ? def : enumClass.cast(get(key));
    }

    @Override
    public boolean isEnum(@Nullable String key) {
        return contains(key) && get(key) instanceof Enum;
    }

    @Override
    public <T extends Enum<T>> boolean isEnum(@Nullable String key, Class<T> enumClass) throws IllegalArgumentException {
        return contains(key) && get(key) instanceof Enum && enumClass.isAssignableFrom(get(key).getClass());
    }

    @Override
    public @Nullable Location getLocation(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable Location getLocation(@Nullable String key, @Nullable Location def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isLocation(@Nullable String key) {
        return contains(key) && get(key) instanceof Location;
    }

    @Override
    public @Nullable Vector getVector(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable Vector getVector(@Nullable String key, @Nullable Vector def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isVector(@Nullable String key) {
        return contains(key) && get(key) instanceof Vector;
    }

    @Override
    public @Nullable ItemStack getItemStack(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable ItemStack getItemStack(@Nullable String key, @Nullable ItemStack def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isItemStack(@Nullable String key) {
        return contains(key) && get(key) instanceof ItemStack;
    }

    @Override
    public <T extends ConfigurationSerializable> @Nullable T getObject(@Nullable String key, @NotNull Class<T> clazz) {
        return clazz.cast(get(key));
    }

    @Override
    public <T extends ConfigurationSerializable> @Nullable T getObject(@Nullable String key, @NotNull Class<T> clazz, @Nullable T def) {
        return contains(key) ? def : clazz.cast(get(key));
    }

    @Override
    public @Nullable Color getColor(@Nullable String path) {
        return get(path);
    }

    @Override
    public @Nullable Color getColor(@Nullable String path, @Nullable Color def) {
        return contains(path) ? def : get(path);
    }

    @Override
    public boolean isColor(@Nullable String path) {
        return contains(path) && get(path) instanceof Color;
    }

    @Override
    public @Nullable NBTSection getSection(@Nullable String key) {
        return tag.get(key) == null ? null : new NBTSection1_16_R2(tag.getCompound(key), this::save, currentPath + "." + key);
    }

    @Override
    public @Nullable NBTSection getSection(@Nullable String key, @Nullable NBTSection def) {
        return tag.get(key) == null ? def : new NBTSection1_16_R2(tag.getCompound(key), this::save, currentPath + "." + key);
    }

    @Override
    public @NotNull NBTSection getOrCreateSection(@NotNull String key) throws IllegalArgumentException {
        return getSection(key, new NBTSection1_16_R2(new NBTTagCompound(), this::save, currentPath + "." + key));
    }

    @Override
    public @NotNull NBTSection getOrCreateSection(@NotNull String key, Map<String, Object> map) throws IllegalArgumentException {
        NBTSection sec = getOrCreateSection(key);
        map.forEach(sec::set);
        return sec;
    }

    @Override
    public boolean isSection(@Nullable String key) {
        return isSet(key) && tag.get(key) instanceof NBTTagCompound && tag.getCompound(key).getString(ChipUtil.CLASS_TAG).isEmpty();
    }

    @Override
    public @NotNull List<?> getList(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable List<?> getList(@Nullable String key, @Nullable List<?> def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isList(@Nullable String key) {
        return contains(key) && get(key) instanceof List;
    }

    @Override
    public @NotNull Map<String, Object> getMap(@Nullable String key) {
        return get(key);
    }

    @Override
    public @Nullable Map<String, Object> getMap(@Nullable String key, @Nullable Map<String, Object> def) {
        return contains(key) ? def : get(key);
    }

    @Override
    public boolean isMap(@Nullable String key) {
        return contains(key) && get(key) instanceof Map<?, ?>;
    }

}
