package me.gamercoder215.mobchip.abstraction.v1_16_R1;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_16_R1;
import me.gamercoder215.mobchip.combat.CombatEntry;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import net.minecraft.server.v1_16_R1.CombatTracker;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class EntityCombatTracker1_16_R1 implements EntityCombatTracker {

    private final CombatTracker handle;
    private final Mob m;

    public EntityCombatTracker1_16_R1(Mob m) {
        this.m = m;
        this.handle = ChipUtil1_16_R1.toNMS(m).getCombatTracker();
    }

    @Override
    public @NotNull String getCurrentDeathMessage() {
        return handle.getDeathMessage().getString();
    }

    @Override
    public @Nullable me.gamercoder215.mobchip.combat.CombatEntry getLatestEntry() {
        List<CombatEntry> l = getCombatEntries();
        return l.isEmpty() ? null : l.get(l.size() - 1);
    }

    @Override
    public @NotNull List<CombatEntry> getCombatEntries() {
        List<CombatEntry> entries = new ArrayList<>();
        try {
            Field f = CombatTracker.class.getDeclaredField("a");
            f.setAccessible(true);
            ((List<net.minecraft.server.v1_16_R1.CombatEntry>) f.get(handle)).stream().map(en -> ChipUtil1_16_R1.fromNMS(m, en)).forEach(entries::add);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
        return entries;
    }

    @Override
    public void recordEntry(@NotNull CombatEntry entry) {
        if (entry == null) return;
        try {
            Field f = CombatTracker.class.getDeclaredField("c");
            f.setAccessible(true);
            Object entries = f.get(handle);

            Method m = List.class.getMethod("add", Object.class);
            m.invoke(entries, ChipUtil1_16_R1.toNMS(entry));
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
    }

    @Override
    public int getCombatDuration() {
        return handle.f();
    }

    @Override
    public boolean isTakingDamage() {
        handle.g();
        try {
            Field damage = CombatTracker.class.getDeclaredField("g");
            damage.setAccessible(true);
            return damage.getBoolean(handle);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
        return false;
    }

    @Override
    public boolean isInCombat() {
        handle.g();
        try {
            Field combat = CombatTracker.class.getDeclaredField("f");
            combat.setAccessible(true);
            return combat.getBoolean(handle);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
        return false;
    }

    @Override
    public boolean hasLastDamageCancelled() {
        return ChipUtil1_16_R1.toNMS(m).forceExplosionKnockback;
    }
}
