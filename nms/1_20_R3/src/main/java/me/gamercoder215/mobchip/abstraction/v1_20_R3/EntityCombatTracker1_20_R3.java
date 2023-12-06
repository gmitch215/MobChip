package me.gamercoder215.mobchip.abstraction.v1_20_R3;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.combat.CombatEntry;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import net.minecraft.world.damagesource.CombatTracker;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
final class EntityCombatTracker1_20_R3 implements EntityCombatTracker {

    private final CombatTracker handle;
    private final Mob m;

    public EntityCombatTracker1_20_R3(Mob m) {
        this.m = m;
        this.handle = ChipUtil1_20_R3.toNMS(m).getCombatTracker();
    }

    @Override
    public @NotNull String getCurrentDeathMessage() {
        return handle.getDeathMessage().getString();
    }

    @Override
    public @Nullable CombatEntry getLatestEntry() {
        try {
            Field entriesF = CombatEntry.class.getDeclaredField("d");
            entriesF.setAccessible(true);
            List<net.minecraft.world.damagesource.CombatEntry> entries = (List<net.minecraft.world.damagesource.CombatEntry>) entriesF.get(handle);
            net.minecraft.world.damagesource.CombatEntry last = entries.get(entries.size() - 1);

            return last == null ? null : ChipUtil1_20_R3.fromNMS(m, last);
        } catch (ReflectiveOperationException e) {
            ChipUtil.printStackTrace(e);
            return null;
        }
    }

    @Override
    public @NotNull List<CombatEntry> getCombatEntries() {
        List<CombatEntry> entries = new ArrayList<>();
        try {
            Field f = CombatTracker.class.getDeclaredField("c");
            f.setAccessible(true);
            ((List<net.minecraft.world.damagesource.CombatEntry>) f.get(handle)).stream().map(en -> ChipUtil1_20_R3.fromNMS(m, en)).forEach(entries::add);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
        return entries;
    }

    @Override
    public void recordEntry(@NotNull CombatEntry entry) {
        if (entry == null) return;
        try {
            Field f = CombatTracker.class.getDeclaredField("d");
            f.setAccessible(true);
            Object entries = f.get(handle);

            Method m = List.class.getMethod("add", Object.class);
            m.invoke(entries, ChipUtil1_20_R3.toNMS(entry));
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
        }
    }

    @Override
    public int getCombatDuration() {
        return handle.getCombatDuration();
    }

    @Override
    public boolean isTakingDamage() {
        try {
            Field takingDamageF = CombatTracker.class.getDeclaredField("j");
            takingDamageF.setAccessible(true);
            return takingDamageF.getBoolean(handle);
        } catch (ReflectiveOperationException e) {
            ChipUtil.printStackTrace(e);
            return false;
        }
    }

    @Override
    public boolean isInCombat() {
        try {
            Field inCombatF = CombatTracker.class.getDeclaredField("i");
            inCombatF.setAccessible(true);
            return inCombatF.getBoolean(handle);
        } catch (ReflectiveOperationException e) {
            ChipUtil.printStackTrace(e);
            return false;
        }
    }

    @Override
    public boolean hasLastDamageCancelled() {
        return ChipUtil1_20_R3.toNMS(m).lastDamageCancelled;
    }
}
