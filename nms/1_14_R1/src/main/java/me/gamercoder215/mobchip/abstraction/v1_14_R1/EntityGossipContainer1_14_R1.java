package me.gamercoder215.mobchip.abstraction.v1_14_R1;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_14_R1;
import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.server.v1_14_R1.EntityVillager;
import net.minecraft.server.v1_14_R1.Reputation;
import net.minecraft.server.v1_14_R1.ReputationType;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings({"deprecation", "unchecked"})
public class EntityGossipContainer1_14_R1 implements EntityGossipContainer {
    private Reputation handle;
    private final Villager entity;

    public EntityGossipContainer1_14_R1(Villager v) {
        this.entity = v;

        try {
            Field containerF = EntityVillager.class.getDeclaredField("bM");
            containerF.setAccessible(true);
            handle = (Reputation) containerF.get(((CraftVillager) v).getHandle());
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
    }

    @Override
    public @NotNull Villager getEntity() {
        return entity;
    }

    @Override
    public void decay() {
        try {
            Field gossipsF = Reputation.class.getDeclaredField("a");
            gossipsF.setAccessible(true);
            Map<UUID, ?> gossips = (Map<UUID, ?>) gossipsF.get(handle);

            for (Object o : gossips.values()) {
                Field reputationsF = o.getClass().getDeclaredField("a");
                reputationsF.setAccessible(true);
                Object2IntMap<ReputationType> reputations = (Object2IntMap<ReputationType>) reputationsF.get(o);

                for (Object2IntMap.Entry<ReputationType> t : reputations.object2IntEntrySet()) {
                    int decay = t.getIntValue() - ChipUtil1_14_R1.fromNMS(t.getKey()).getDailyDecay();
                    if (decay < 2) reputations.remove(t.getKey());
                    else t.setValue(decay);
                }

                reputationsF.set(o, reputations);
            }
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
    }

    @Override
    public int getReputation(@NotNull Entity en, @Nullable GossipType... types) throws IllegalArgumentException {
        return handle.a(en.getUniqueId(), g -> Arrays.asList(types).contains(ChipUtil1_14_R1.fromNMS(g)));
    }

    @Override
    public void put(@NotNull Entity en, @NotNull GossipType type, int maxCap) throws IllegalArgumentException {
        handle.a(en.getUniqueId(), ChipUtil1_14_R1.toNMS(type), maxCap);
    }

    @Override
    public void remove(@NotNull Entity en, @NotNull GossipType type) throws IllegalArgumentException {
        try {
            Field map = Reputation.class.getDeclaredField("a");
            map.setAccessible(true);
            Map<UUID, ?> data = new HashMap<>((Map<UUID, ?>) map.get(handle));
            data.remove(en.getUniqueId());
            map.set(handle, data);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
    }

    @Override
    public void removeAll(@NotNull GossipType type) throws IllegalArgumentException {
        try {
            Field map = Reputation.class.getDeclaredField("a");
            map.setAccessible(true);
            Map<UUID, ?> data = new HashMap<>((Map<UUID, ?>) map.get(handle));

            for (UUID uuid : data.keySet()) {
                Object o = data.get(uuid);

                Method m = o.getClass().getDeclaredMethod("b", ReputationType.class);
                m.setAccessible(true);
                m.invoke(o, ChipUtil1_14_R1.toNMS(type));

                Method empty = o.getClass().getDeclaredMethod("b");
                empty.setAccessible(true);
                if ((boolean) empty.invoke(o)) data.remove(uuid);
            }
            map.set(handle, data);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
    }
}
