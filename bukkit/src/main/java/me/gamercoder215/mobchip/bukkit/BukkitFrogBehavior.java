package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.FrogBehavior;
import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.ShootTongue;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

class BukkitFrogBehavior extends BukkitCreatureBehavior implements FrogBehavior {

    final Frog nmsMob;

    public BukkitFrogBehavior(Frog f) {
        super(f);
        this.nmsMob = f;
    }

    @Override
    public @NotNull BehaviorResult shootTongue(Sound tongueSound, Sound eatSound) {
        return new BukkitBehaviorResult(new ShootTongue(MobChipUtil.convert(tongueSound), MobChipUtil.convert(eatSound)), level, nmsMob);
    }
}
