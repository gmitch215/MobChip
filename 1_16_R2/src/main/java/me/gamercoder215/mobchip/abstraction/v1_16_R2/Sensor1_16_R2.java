package me.gamercoder215.mobchip.abstraction.v1_16_R2;

import me.gamercoder215.mobchip.abstraction.ChipUtil1_16_R2;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.MemoryModuleType;
import net.minecraft.server.v1_16_R2.Sensor;
import net.minecraft.server.v1_16_R2.WorldServer;

import java.util.Set;
import java.util.stream.Collectors;

public final class Sensor1_16_R2 extends Sensor<EntityLiving> {

    private final me.gamercoder215.mobchip.ai.sensing.Sensor<?> s;

    public Sensor1_16_R2(me.gamercoder215.mobchip.ai.sensing.Sensor<?> s) {
        this.s = s;
    }

    @Override
    protected void a(WorldServer level, EntityLiving en) {
        s.run(ChipUtil1_16_R2.fromNMS(level), ChipUtil1_16_R2.fromNMS(en));
    }

    @Override
    public Set<MemoryModuleType<?>> a() {
        return s.required().stream().map(ChipUtil1_16_R2::toNMS).collect(Collectors.toSet());
    }

    public me.gamercoder215.mobchip.ai.sensing.Sensor<?> getSensor() {
        return s;
    }

}
