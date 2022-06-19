package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.controller.EntityController;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

final class BukkitController implements EntityController {

    private final JumpControl jumpC;
    private final LookControl lookC;
    private final MoveControl moveC;
    private final World w;

    private final Mob m;
    BukkitController(Mob m, World w, JumpControl jump, LookControl look, MoveControl move) {
        this.jumpC = jump;
        this.lookC = look;
        this.moveC = move;
        this.w = w;
        this.m = m;
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
