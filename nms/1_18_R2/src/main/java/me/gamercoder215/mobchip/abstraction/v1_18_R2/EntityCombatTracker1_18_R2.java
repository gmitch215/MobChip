package me.gamercoder215.mobchip.abstraction.v1_18_R2;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_18_R2;
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
public class EntityCombatTracker1_18_R2 implements EntityCombatTracker {

    private final CombatTracker handle;
    private final Mob m;

    public EntityCombatTracker1_18_R2(Mob m) {
        this.m = m;
        this.handle = ChipUtil1_18_R2.toNMS(m).getCombatTracker();
    }

    @Override
    public @NotNull String getCurrentDeathMessage() {
        return handle.getDeathMessage().getString();
    }

    @Override
    public @Nullable CombatEntry getLatestEntry() {
        return handle.getLastEntry() == null ? null : ChipUtil1_18_R2.fromNMS(m, handle.getLastEntry());
    }

    @Override
    public @NotNull List<CombatEntry> getCombatEntries() {
        List<CombatEntry> entries = new ArrayList<>();
        try {
            Field f = CombatTracker.class.getDeclaredField("c");
            f.setAccessible(true);
            ((List<net.minecraft.world.damagesource.CombatEntry>) f.get(handle)).stream().map(en -> ChipUtil1_18_R2.fromNMS(m, en)).forEach(entries::add);
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
            m.invoke(entries, ChipUtil1_18_R2.toNMS(entry));
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
        return handle.isTakingDamage();
    }

    @Override
    public boolean isInCombat() {
        return handle.isInCombat();
    }

    @Override
    public boolean hasLastDamageCancelled() {
        return ChipUtil1_18_R2.toNMS(m).lastDamageCancelled;
    }
}
