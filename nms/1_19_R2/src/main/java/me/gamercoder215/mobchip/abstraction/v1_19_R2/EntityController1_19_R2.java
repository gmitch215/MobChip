package me.gamercoder215.mobchip.abstraction.v1_19_R2;

import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.controller.NaturalMoveType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

final class EntityController1_19_R2 implements EntityController {

    private final JumpControl jumpC;
    private final MoveControl moveC;
    private final LookControl lookC;

    private final Mob m;

    private final net.minecraft.world.entity.Mob nms;

    public EntityController1_19_R2(Mob m) {
        net.minecraft.world.entity.Mob nms = ChipUtil1_19_R2.toNMS(m);
        this.lookC = nms.getLookControl();
        this.moveC = nms.getMoveControl();
        this.jumpC = nms.getJumpControl();
        this.m = m;
        this.nms = nms;
    }

    @Override
    public EntityController jump() {
        jumpC.jump();
        jumpC.tick();
        return this;
    }

    @Override
    public boolean isLookingAtTarget() {
        Vector dir = m.getLocation().getDirection();
        int x = dir.getBlockX();
        int y = dir.getBlockY();
        int z = dir.getBlockZ();
        return lookC.getWantedX() == x && lookC.getWantedY() == y && lookC.getWantedZ() == z;
    }

    @Override
    public EntityController moveTo(double x, double y, double z, double speedMod) {
        moveC.setWantedPosition(x, y, z, speedMod);
        moveC.tick();
        nms.getNavigation().moveTo(moveC.getWantedX(), moveC.getWantedY(), moveC.getWantedZ(), moveC.getSpeedModifier());
        nms.getNavigation().tick();
        return this;
    }

    @Override
    public EntityController naturalMoveTo(double x, double y, double z, NaturalMoveType type) {
        Vec3 vec = new Vec3(x, y, z);
        MoverType m = switch (type) {
            default -> MoverType.SELF;
            case PLAYER -> MoverType.PLAYER;
            case PISTON -> MoverType.PISTON;
            case SHULKER_BOX -> MoverType.SHULKER_BOX;
            case SHULKER -> MoverType.SHULKER;
        };

        nms.move(m, vec);
        return this;
    }

    @Override
    public EntityController strafe(float fwd, float right) {
        moveC.strafe(fwd, right);
        moveC.tick();
        nms.getNavigation().moveTo(moveC.getWantedX(), moveC.getWantedY(), moveC.getWantedZ(), moveC.getSpeedModifier());
        nms.getNavigation().tick();
        return this;
    }

    @Override
    public double getCurrentSpeedModifier() {
        return moveC.getSpeedModifier();
    }

    @Override
    public Location getTargetMoveLocation() {
        return new Location(m.getWorld(), moveC.getWantedX(), moveC.getWantedY(), moveC.getWantedZ());
    }

    @Override
    public Location getTargetLookLocation() {
        return new Location(m.getWorld(), lookC.getWantedX(), lookC.getWantedY(), lookC.getWantedZ());
    }

    @Override
    public EntityController lookAt(double x, double y, double z) {
        lookC.setLookAt(x, y, z);
        lookC.tick();
        return this;
    }

}
