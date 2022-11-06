package me.gamercoder215.mobchip.abstraction.v1_16_R2;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_16_R2;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.IRegistry;
import net.minecraft.server.v1_16_R2.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SensorDefault1_16_R2 implements Sensor<LivingEntity> {

    private final net.minecraft.server.v1_16_R2.Sensor<?> handle;

    public SensorDefault1_16_R2(net.minecraft.server.v1_16_R2.Sensor<?> handle) {
        this.handle = handle;
    }

    public net.minecraft.server.v1_16_R2.Sensor<?> getHandle() {
        return handle;
    }

    @Override
    public @NotNull List<Memory<?>> required() {
        return handle.a().stream().map(ChipUtil1_16_R2::fromNMS).collect(Collectors.toList());
    }

    @Override
    public int getScanRate() {
        try {
            Field scan = net.minecraft.server.v1_16_R2.Sensor.class.getDeclaredField("d");
            scan.setAccessible(true);
            return scan.getInt(handle);
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) Bukkit.getLogger().severe(st.toString());
        }

        return DEFAULT_SCAN_RATE;
    }

    @Override
    public @NotNull Class<LivingEntity> getEntityClass() {
        return LivingEntity.class; // not stored in the handle
    }

    @Override
    public void run(@NotNull World w, LivingEntity entity) {
        try {
            Method doTick = net.minecraft.server.v1_16_R2.Sensor.class.getDeclaredMethod("a", WorldServer.class, EntityLiving.class);
            doTick.setAccessible(true);
            doTick.invoke(handle, ChipUtil1_16_R2.toNMS(w), ChipUtil1_16_R2.toNMS(entity));
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) Bukkit.getLogger().severe(st.toString());
        }
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        AtomicReference<NamespacedKey> key = new AtomicReference<>(NamespacedKey.minecraft("unknown"));

        IRegistry.SENSOR_TYPE.g()
                .filter(s -> s.a().equals(handle))
                .findFirst()
                .ifPresent(s -> key.set(ChipUtil1_16_R2.fromNMS(IRegistry.SENSOR_TYPE.getKey(s))));

        return key.get();
    }
}
