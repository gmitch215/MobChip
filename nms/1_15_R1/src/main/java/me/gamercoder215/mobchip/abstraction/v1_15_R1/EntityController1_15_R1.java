package me.gamercoder215.mobchip.abstraction.v1_15_R1;

import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.controller.NaturalMoveType;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

final class EntityController1_15_R1 implements EntityController {

    private final ControllerJump jumpC;
    private final ControllerMove moveC;
    private final ControllerLook lookC;

    private final Mob m;
    private final EntityInsentient nms;

    public EntityController1_15_R1(Mob m) {
        EntityInsentient nms = ChipUtil1_15_R1.toNMS(m);
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
        return lookC.d() == x && lookC.e() == y && lookC.f() == z;
    }

    @Override
    public EntityController moveTo(double x, double y, double z, double speedMod) {
        moveC.a(x, y, z, speedMod);
        moveC.a();
        nms.getNavigation().a(moveC.d(), moveC.e(), moveC.f(), moveC.c());
        nms.getNavigation().c();
        return this;
    }

    @Override
    public EntityController naturalMoveTo(double x, double y, double z, NaturalMoveType type) {
        Vec3D vec = new Vec3D(x, y, z);
        final EnumMoveType m;
        switch (type) {
            default:
                m = EnumMoveType.SELF;
                break;
            case PLAYER:
                m = EnumMoveType.PLAYER;
                break;
            case PISTON:
                m = EnumMoveType.PISTON;
                break;
            case SHULKER_BOX:
                m = EnumMoveType.SHULKER_BOX;
                break;
            case SHULKER:
                m = EnumMoveType.SHULKER;
                break;
        }

        nms.move(m, vec);
        return this;
    }

    @Override
    public EntityController strafe(float fwd, float right) {
        moveC.a(fwd, right);
        moveC.a();
        nms.getNavigation().a(moveC.d(), moveC.e(), moveC.f(), moveC.c());
        nms.getNavigation().c();
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
        return new Location(m.getWorld(), lookC.d(), lookC.e(), lookC.f());
    }

    @Override
    public EntityController lookAt(double x, double y, double z) {
        lookC.a(x, y, z);
        lookC.a();
        return this;
    }

}
