package me.gamercoder215.mobchip.bukkit;

import org.bukkit.Location;
import org.bukkit.World;

import me.gamercoder215.mobchip.ai.controller.EntityController;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;

final class BukkitController implements EntityController {

    private final JumpControl jumpC;
    private final LookControl lookC;
    private final MoveControl moveC;
    private final World w;
    BukkitController(World w, JumpControl jump, LookControl look, MoveControl move) {
        this.jumpC = jump;
        this.lookC = look;
        this.moveC = move;
        this.w = w;
    }

    @Override
    public EntityController jump() {
        jumpC.jump();
        jumpC.tick();
        return this;
    }

    @Override
    public boolean isLookingAtTarget() {
        return lookC.isLookingAtTarget();
    }

    @Override
    public EntityController moveTo(double x, double y, double z, double speedMod) {
        moveC.setWantedPosition(x, y, z, speedMod);
        moveC.tick();
        return this;
    }

    @Override
    public EntityController strafe(float fwd, float right) {
        moveC.strafe(fwd, right);
        moveC.tick();
        return this;
    }

    @Override
    public double getCurrentSpeedModifier() {
        return moveC.getSpeedModifier();
    }

    @Override
    public Location getTargetMoveLocation() {
        return new Location(w, moveC.getWantedX(), moveC.getWantedY(), moveC.getWantedZ());
    }

    @Override
    public Location getTargetLookLocation() {
        return new Location(w, lookC.getWantedX(), lookC.getWantedY(), lookC.getWantedZ());
    }

    @Override
    public EntityController lookAt(double x, double y, double z) {
        lookC.setLookAt(x, y, z);
        lookC.tick();
        return this;
    }
    
}
