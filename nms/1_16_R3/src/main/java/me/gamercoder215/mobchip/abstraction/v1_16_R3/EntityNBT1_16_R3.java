package me.gamercoder215.mobchip.abstraction.v1_16_R3;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_16_R3;
import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

public final class EntityNBT1_16_R3 extends NBTSection1_16_R3 implements EntityNBT {

    private final Mob mob;
    private final EntityInsentient handle;

    private final NBTTagCompound root;

    public EntityNBT1_16_R3(Mob m) {
        super(m);
        this.mob = m;
        this.handle = ChipUtil1_16_R3.toNMS(m);
        this.root = new NBTTagCompound();
        handle.d(root);
    }

    @Override
    public @NotNull Mob getEntity() {
        return mob;
    }
}
