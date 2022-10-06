package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_17_R1;
import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.EntityInsentient;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

public final class EntityNBT1_17_R1 extends NBTSection1_17_R1 implements EntityNBT {

    private final Mob mob;
    private final EntityInsentient handle;

    private final NBTTagCompound root;

    public EntityNBT1_17_R1(Mob m) {
        super(m);
        this.mob = m;
        this.handle = ChipUtil1_17_R1.toNMS(m);
        this.root = new NBTTagCompound();
        handle.d(root);
    }

    @Override
    public @NotNull Mob getEntity() {
        return mob;
    }
}
