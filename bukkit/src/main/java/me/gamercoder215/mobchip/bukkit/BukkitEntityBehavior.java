package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.EntityBehavior;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.*;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

class BukkitEntityBehavior implements EntityBehavior {

    final Mob nmsMob;
    final ServerLevel level;

    BukkitEntityBehavior(Mob nmsMob) {
        this.nmsMob = nmsMob;
        this.level = (ServerLevel) nmsMob.level;
    }

    @Override
    public BehaviorResult backupIfClose(int minDistance, float speedModifier) {
        return new BukkitBehaviorResult(new BackUpIfTooClose<>(minDistance, speedModifier), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult passiveIf(@NotNull EntityMemory<?> memory, int durationTicks) throws IllegalArgumentException {
        Validate.notNull(memory, "Memory cannot be null");
        return new BukkitBehaviorResult(new BecomePassiveIfMemoryPresent(memory.getHandle(), durationTicks), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult eraseIf(@NotNull Predicate<org.bukkit.entity.Mob> function, @NotNull EntityMemory<?> memory) throws IllegalArgumentException {
        Validate.notNull(function, "Function cannot be null");
        Validate.notNull(memory, "Memory cannot be null");
        return new BukkitBehaviorResult( new EraseMemoryIf<Mob>(m -> function.test(ChipConversions.convertType(m)), memory.getHandle()), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult moveToCelebrateLocation(int minDist, float speedMod) {
        return new BukkitBehaviorResult(new GoToCelebrateLocation<>(minDist, speedMod), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult moveToWantedItem(int minDist, float speedMod, boolean requireTarget) {
        return new BukkitBehaviorResult(new GoToWantedItem<>(speedMod, requireTarget, minDist), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult jumpOnBed(float speedMod) {
        return new BukkitBehaviorResult(new JumpOnBed(speedMod), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult meleeAttack(int cooldown) {
        return new BukkitBehaviorResult(new MeleeAttack(cooldown), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult wakeUp() {
        return new BukkitBehaviorResult(new WakeUp(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult ringBell() {
        return new BukkitBehaviorResult(new RingBell(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult reactToBell() {
        return new BukkitBehaviorResult(new ReactToBell(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult interactWithDoor() {
        return new BukkitBehaviorResult(new InteractWithDoor(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult sleep() {
        return new BukkitBehaviorResult(new SleepInBed(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult socializeAtBell() {
        return new BukkitBehaviorResult(new SocializeAtBell(), level, nmsMob);
    }
}
