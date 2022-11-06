package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_17_R1;
import me.gamercoder215.mobchip.ai.sensing.EntitySenses;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.BehaviorController;
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
public final class EntitySenses1_17_R1 implements EntitySenses {

    private final Mob m;
    private final EntityInsentient nmsMob;

    private final Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> sensorsHandle = new HashMap<>();

    public EntitySenses1_17_R1(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_17_R1.toNMS(m);

        try {
            Field sensorsF = BehaviorController.class.getDeclaredField("e");
            sensorsF.setAccessible(true);
            Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> sensors = (Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>) sensorsF.get(nmsMob.getBehaviorController());

            sensorsHandle.putAll(sensors);
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) Bukkit.getLogger().severe(st.toString());
        }
    }

    private void save() {
        try {
            Field sensorsF = BehaviorController.class.getDeclaredField("e");
            sensorsF.setAccessible(true);

            sensorsF.set(nmsMob.getBehaviorController(), sensorsHandle);
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) Bukkit.getLogger().severe(st.toString());
        }
    }

    @Override
    public @NotNull List<Sensor<?>> getSensors() {
        return sensorsHandle.values()
                .stream()
                .map(ChipUtil1_17_R1::fromNMS)
                .collect(Collectors.toList());
    }

    @Override
    public void addSensor(@NotNull Sensor<?> sensor) throws IllegalArgumentException {
        if (!new ChipUtil1_17_R1().existsSensor(sensor.getKey())) throw new IllegalArgumentException("Unregistered Sensor: " + sensor.getKey());

        sensorsHandle.put(ChipUtil1_17_R1.toNMSType(sensor), ChipUtil1_17_R1.toNMS(sensor));
        save();
    }

    @Override
    public void removeSensor(@NotNull Sensor<?> sensor) {
        if (!new ChipUtil1_17_R1().existsSensor(sensor.getKey())) throw new IllegalArgumentException("Unregistered Sensor: " + sensor.getKey());

        SensorType<?> type = ChipUtil1_17_R1.toNMSType(sensor);
        MinecraftKey key = IRegistry.as.getKey(type);

        Iterator<Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>> it = sensorsHandle.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> entry = it.next();
            SensorType<?> currentType = entry.getKey();
            MinecraftKey currentKey = IRegistry.as.getKey(currentType);

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
        if (!new ChipUtil1_17_R1().existsSensor(key)) throw new IllegalArgumentException("Unregistered Sensor: " + key);

        MinecraftKey keyH = ChipUtil1_17_R1.toNMS(key);
        Iterator<Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>> it = sensorsHandle.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> entry = it.next();
            SensorType<?> currentType = entry.getKey();
            MinecraftKey currentKey = IRegistry.as.getKey(currentType);

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
            MinecraftKey currentKey = IRegistry.as.getKey(t);
            if (ChipUtil1_17_R1.toNMS(key).equals(currentKey)) {
                b.set(true);
                break;
            }
        }

        return b.get();
    }
}
