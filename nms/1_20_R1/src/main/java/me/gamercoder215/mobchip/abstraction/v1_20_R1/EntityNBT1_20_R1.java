package me.gamercoder215.mobchip.abstraction.v1_20_R1;

import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

final class EntityNBT1_20_R1 extends NBTSection1_20_R1 implements EntityNBT {

    private final Mob mob;

    public EntityNBT1_20_R1(Mob m) {
        super(m);
        this.mob = m;
        net.minecraft.world.entity.Mob handle = ChipUtil1_20_R1.toNMS(m);
        CompoundTag root = new CompoundTag();
        handle.saveWithoutId(root);
    }

    @Override
    public @NotNull Mob getEntity() {
        return mob;
    }
}
