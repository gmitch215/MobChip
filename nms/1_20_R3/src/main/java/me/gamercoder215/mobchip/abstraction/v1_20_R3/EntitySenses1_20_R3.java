package me.gamercoder215.mobchip.abstraction.v1_20_R3;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.mobchip.ai.sensing.EntitySenses;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
final class EntitySenses1_20_R3 implements EntitySenses {

    private final Mob m;
    private final net.minecraft.world.entity.Mob nmsMob;

    private final Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> sensorsHandle = new HashMap<>();

    public EntitySenses1_20_R3(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_20_R3.toNMS(m);

        try {
            Field sensorsF = Brain.class.getDeclaredField("e");
            sensorsF.setAccessible(true);
            Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> sensors = (Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>) sensorsF.get(nmsMob.getBrain());

            sensorsHandle.putAll(sensors);
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) Bukkit.getLogger().severe(st.toString());
        }
    }

    private void save() {
        try {
            Field sensorsF = Brain.class.getDeclaredField("e");
            sensorsF.setAccessible(true);

            sensorsF.set(nmsMob.getBrain(), sensorsHandle);
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) Bukkit.getLogger().severe(st.toString());
        }
    }

    @Override
    public @NotNull Mob getEntity() {
        return m;
    }

    @Override
    public @NotNull List<Sensor<?>> getSensors() {
        return sensorsHandle.values()
                .stream()
                .map(ChipUtil1_20_R3::fromNMS)
                .collect(Collectors.toList());
    }

    @Override
    public void addSensor(@NotNull Sensor<?> sensor) throws IllegalArgumentException {
        if (!new ChipUtil1_20_R3().existsSensor(sensor.getKey())) throw new IllegalArgumentException("Unregistered Sensor: " + sensor.getKey());

        sensorsHandle.put(ChipUtil1_20_R3.toNMSType(sensor), ChipUtil1_20_R3.toNMS(sensor));
        save();
    }

    @Override
    public void removeSensor(@NotNull Sensor<?> sensor) {
        if (!new ChipUtil1_20_R3().existsSensor(sensor.getKey())) throw new IllegalArgumentException("Unregistered Sensor: " + sensor.getKey());

        SensorType<?> type = ChipUtil1_20_R3.toNMSType(sensor);
        ResourceLocation key = BuiltInRegistries.SENSOR_TYPE.getKey(type);

        Iterator<Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>> it = sensorsHandle.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> entry = it.next();
            SensorType<?> currentType = entry.getKey();
            ResourceLocation currentKey = BuiltInRegistries.SENSOR_TYPE.getKey(currentType);

            if (currentKey.equals(key)) {
                it.remove();
                break;
            }
        }

        sensorsHandle.clear();
        sensorsHandle.putAll(ImmutableList.copyOf(it)
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        save();
    }

    @Override
    public void removeSensor(@NotNull NamespacedKey key) {
        if (!new ChipUtil1_20_R3().existsSensor(key)) throw new IllegalArgumentException("Unregistered Sensor: " + key);

        ResourceLocation keyH = ChipUtil1_20_R3.toNMS(key);
        Iterator<Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>> it = sensorsHandle.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> entry = it.next();
            SensorType<?> currentType = entry.getKey();
            ResourceLocation currentKey = BuiltInRegistries.SENSOR_TYPE.getKey(currentType);

            if (currentKey.equals(keyH)) {
                it.remove();
                break;
            }
        }

        sensorsHandle.clear();
        sensorsHandle.putAll(ImmutableList.copyOf(it)
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        save();
    }

    @Override
    public boolean hasSensor(@NotNull NamespacedKey key) {
        AtomicBoolean b = new AtomicBoolean(false);

        for (SensorType<?> t : sensorsHandle.keySet()) {
            ResourceLocation currentKey = BuiltInRegistries.SENSOR_TYPE.getKey(t);
            if (ChipUtil1_20_R3.toNMS(key).equals(currentKey)) {
                b.set(true);
                break;
            }
        }

        return b.get();
    }
}
