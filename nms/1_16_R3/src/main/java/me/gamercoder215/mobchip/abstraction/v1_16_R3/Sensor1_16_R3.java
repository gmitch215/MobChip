package me.gamercoder215.mobchip.abstraction.v1_16_R3;

import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.MemoryModuleType;
import net.minecraft.server.v1_16_R3.Sensor;
import net.minecraft.server.v1_16_R3.WorldServer;

import java.util.Set;
import java.util.stream.Collectors;

final class Sensor1_16_R3 extends Sensor<EntityLiving> {

    private final me.gamercoder215.mobchip.ai.sensing.Sensor<?> s;

    public Sensor1_16_R3(me.gamercoder215.mobchip.ai.sensing.Sensor<?> s) {
        this.s = s;
    }

    @Override
    protected void a(WorldServer level, EntityLiving en) {
        s.run(ChipUtil1_16_R3.fromNMS(level), ChipUtil1_16_R3.fromNMS(en));
    }

    @Override
    public Set<MemoryModuleType<?>> a() {
        return s.required().stream().map(ChipUtil1_16_R3::toNMS).collect(Collectors.toSet());
    }

    public me.gamercoder215.mobchip.ai.sensing.Sensor<?> getSensor() {
        return s;
    }

}
