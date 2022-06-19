package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.WardenBehavior;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.warden.*;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.NotNull;

class BukkitWardenBehavior extends BukkitCreatureBehavior implements WardenBehavior {

    final Warden nmsMob;

    public BukkitWardenBehavior(Mob m) {
        super((PathfinderMob) m);
        if (!(m instanceof Warden w)) throw new IllegalArgumentException("Expected Warden but found Mob");
        this.nmsMob = w;
    }

    @Override
    public @NotNull BehaviorResult dig(int duration) {
        return new BukkitBehaviorResult(new Digging<>(duration), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult emerge(int duration) {
        return new BukkitBehaviorResult(new Emerging<>(duration), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult roar() {
        return new BukkitBehaviorResult(new Roar(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult sonicBoom() {
        return new BukkitBehaviorResult(new SonicBoom(), level, nmsMob);
    }

    @Override
    public @NotNull BehaviorResult sniff(int duration) {
        new BukkitBehaviorResult(new TryToSniff(), level, nmsMob);
        return new BukkitBehaviorResult(new Sniffing<>(duration), level, nmsMob);
    }
}
