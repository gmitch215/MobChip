package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.controller.NaturalMoveType;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.ai.control.ControllerJump;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

final class EntityController1_17_R1 implements EntityController {

    private final ControllerJump jumpC;
    private final ControllerMove moveC;
    private final ControllerLook lookC;

    private final Mob m;

    private final EntityInsentient nms;

    public EntityController1_17_R1(Mob m) {
        EntityInsentient nms = ChipUtil1_17_R1.toNMS(m);
        this.lookC = nms.getControllerLook();
        this.moveC = nms.getControllerMove();
        this.jumpC = nms.getControllerJump();
        this.m = m;
        this.nms = nms;
    }

    @Override
    public EntityController jump() {
        jumpC.jump();
        jumpC.b();
        return this;
    }

    @Override
    public boolean isLookingAtTarget() {
        Vector dir = m.getLocation().getDirection();
        int x = dir.getBlockX();
        int y = dir.getBlockY();
        int z = dir.getBlockZ();
        return lookC.e() == x && lookC.f() == y && lookC.g() == z;
    }

    @Override
    public EntityController moveTo(double x, double y, double z, double speedMod) {
        moveC.a(x, y, z, speedMod);
        moveC.a();
        return this;
    }

    @Override
    public EntityController naturalMoveTo(double x, double y, double z, NaturalMoveType type) {
        Vec3D vec = new Vec3D(x, y, z);
        EnumMoveType m = switch (type) {
            default -> EnumMoveType.a;
            case PLAYER -> EnumMoveType.b;
            case PISTON -> EnumMoveType.c;
            case SHULKER_BOX -> EnumMoveType.d;
            case SHULKER -> EnumMoveType.e;
        };

        nms.move(m, vec);
        return this;
    }

    @Override
    public EntityController strafe(float fwd, float right) {
        moveC.a(fwd, right);
        moveC.a();
        return this;
    }

    @Override
    public double getCurrentSpeedModifier() {
        return moveC.c();
    }

    @Override
    public Location getTargetMoveLocation() {
        return new Location(m.getWorld(), moveC.d(), moveC.e(), moveC.f());
    }

    @Override
    public Location getTargetLookLocation() {
        return new Location(m.getWorld(), lookC.e(), lookC.f(), lookC.g());
    }

    @Override
    public EntityController lookAt(double x, double y, double z) {
        lookC.a(x, y, z);
        lookC.a();
        return this;
    }

}
