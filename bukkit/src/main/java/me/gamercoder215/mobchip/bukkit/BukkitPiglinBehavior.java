package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.PiglinBehavior;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import net.minecraft.world.entity.monster.piglin.*;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class BukkitPiglinBehavior extends BukkitCreatureBehavior implements PiglinBehavior {

    final Piglin nmsMob;
    final org.bukkit.entity.Piglin piglin;

    public BukkitPiglinBehavior(Piglin piglin) {
        super(piglin);
        this.nmsMob = piglin;
        this.piglin = (org.bukkit.entity.Piglin) piglin.getBukkitEntity();
    }


    @Override
    public @NotNull BehaviorResult stopHunting() {
        return new BukkitBehaviorResult(new RememberIfHoglinWasKilled<>(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult startAdmiring(@Nullable Item want, int duration) {
        EntityBrain b = BukkitBrain.getBrain(piglin);
        b.setMemory(EntityMemory.NEAREST_WANTED_ITEM, want);
        return new BukkitBehaviorResult(new StartAdmiringItemIfSeen<>(duration), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult startHunting() {
        return new BukkitBehaviorResult(new StartHuntingHoglin<>(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult stopAdmiring() {
        EntityBrain b = BukkitBrain.getBrain(piglin);
        b.removeMemory(EntityMemory.IS_ADMIRING_ITEM);
        return new BukkitBehaviorResult(new StopHoldingItemIfNoLongerAdmiring<>(), level, nmsMob);
    }
}
