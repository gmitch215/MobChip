package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.behavior.AxolotlBehavior;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.PlayDead;
import org.jetbrains.annotations.NotNull;

class BukkitAxolotlBehavior extends BukkitCreatureBehavior implements AxolotlBehavior {

    final Axolotl nmsMob;

    public BukkitAxolotlBehavior(Axolotl a) {
        super(a);
        this.nmsMob = a;
    }

    @Override
    public @NotNull BehaviorResult playDead() {
        return new BukkitBehaviorResult(new PlayDead(), level, nmsMob);
    }
}
