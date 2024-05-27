package me.gamercoder215.mobchip.abstraction.v1_13_R2;

import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.server.v1_13_R2.EntityInsentient;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

final class EntityNBT1_13_R2 extends NBTSection1_13_R2 implements EntityNBT {

    private final Mob mob;

    public EntityNBT1_13_R2(Mob m) {
        super(m);
        this.mob = m;
        EntityInsentient handle = ChipUtil1_13_R2.toNMS(m);
        NBTTagCompound root = new NBTTagCompound();
        handle.d(root);
    }

    @Override
    public @NotNull Mob getEntity() {
        return mob;
    }
}
