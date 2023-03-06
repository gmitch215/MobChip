package me.gamercoder215.mobchip.abstraction.v1_15_R1;

import me.gamercoder215.mobchip.ai.enderdragon.DragonPhase;
import net.minecraft.server.v1_15_R1.IDragonController;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderDragon;
import org.jetbrains.annotations.NotNull;

final class DragonPhase1_15_R1 implements DragonPhase {

    private final EnderDragon dragon;
    private final IDragonController handle;

    public DragonPhase1_15_R1(EnderDragon dragon, IDragonController handle) {
        this.dragon = dragon;
        this.handle = handle;
    }

    @Override
    public @NotNull EnderDragon getDragon() {
        return this.dragon;
    }

    @Override
    public @NotNull Location getTargetLocation() {
        return ChipUtil1_15_R1.fromNMS(handle.g(), dragon.getWorld());
    }

    @Override
    public void start() {
        handle.d();
    }

    @Override
    public void stop() {
        handle.e();
    }

    @Override
    public void clientTick() {
        handle.b();
    }

    @Override
    public void serverTick() {
        handle.c();
    }

    @Override
    public boolean isSitting() {
        return handle.a();
    }

    @Override
    public float getFlyingSpeed() {
        return handle.f();
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return NamespacedKey.minecraft(handle.toString().split(" ")[0].toLowerCase());
    }
}
