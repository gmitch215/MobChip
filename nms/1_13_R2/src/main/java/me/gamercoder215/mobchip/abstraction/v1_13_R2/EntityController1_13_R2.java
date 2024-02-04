package me.gamercoder215.mobchip.abstraction.v1_13_R2;

import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.controller.NaturalMoveType;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

final class EntityController1_13_R2 implements EntityController {

    private final ControllerJump jumpC;
    private final ControllerMove moveC;
    private final ControllerLook lookC;

    private final Mob m;
    private final EntityInsentient nms;

    public EntityController1_13_R2(Mob m) {
        EntityInsentient nms = ChipUtil1_13_R2.toNMS(m);
        this.lookC = nms.getControllerLook();
        this.moveC = nms.getControllerMove();
        this.jumpC = nms.getControllerJump();
        this.m = m;
        this.nms = nms;
    }

    @Override
    public EntityController jump() {
        jumpC.a();
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
        nms.getNavigation().a(moveC.d(), moveC.e(), moveC.f(), moveC.c());
        nms.getNavigation().d();
        return this;
    }

    @Override
    public EntityController naturalMoveTo(double x, double y, double z, NaturalMoveType type) {
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

        nms.move(m, x, y, z);
        return this;
    }

    @Override
    public EntityController strafe(float fwd, float right) {
        moveC.a(fwd, right);
        moveC.a();
        nms.getNavigation().a(moveC.d(), moveC.e(), moveC.f(), moveC.c());
        nms.getNavigation().d();
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
        lookC.a(x, y, z, 0, 0);
        lookC.a();
        return this;
    }

    @Override
    public @NotNull Vector getDeltaMovement() {
        return new Vector(nms.motX, nms.motY, nms.motZ);
    }

    @Override
    public void setDeltaMovement(@NotNull Vector delta) {
        nms.motX = delta.getX();
        nms.motY = delta.getY();
        nms.motZ = delta.getZ();
    }

}
