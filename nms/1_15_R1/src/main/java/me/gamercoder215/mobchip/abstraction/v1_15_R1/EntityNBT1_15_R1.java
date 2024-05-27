package me.gamercoder215.mobchip.abstraction.v1_15_R1;

import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.server.v1_15_R1.EntityInsentient;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

final class EntityNBT1_15_R1 extends NBTSection1_15_R1 implements EntityNBT {

    private final Mob mob;

    public EntityNBT1_15_R1(Mob m) {
        super(m);
        this.mob = m;
        EntityInsentient handle = ChipUtil1_15_R1.toNMS(m);
        NBTTagCompound root = new NBTTagCompound();
        handle.d(root);
    }

    @Override
    public @NotNull Mob getEntity() {
        return mob;
    }
}
