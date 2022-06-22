package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.util.MobChipUtil;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.entity.Pose.*;

class BukkitBody implements EntityBody {

    private final Mob nmsMob;

    BukkitBody(Mob nmsMob) {
        this.nmsMob = nmsMob;
    }

    /**
     * Whether this Entity is Left Handed.
     *
     * @return true if left-handed, else false
     */
    @Override
    public boolean isLeftHanded() {
        return nmsMob.isLeftHanded();
    }

    /**
     * Sets this Entity to be left-handed.
     *
     * @param leftHanded true if left-handed, else false
     */
    @Override
    public void setLeftHanded(boolean leftHanded) {
        nmsMob.setLeftHanded(leftHanded);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    public boolean shouldDiscardFriction() {
        return nmsMob.shouldDiscardFriction();
    }

    @Override
    public void setDiscardFriction(boolean discard) {
        nmsMob.setDiscardFriction(discard);
    }

    /**
     * Makes this Mob interact with a Player.
     *
     * @param p    Player to interact with
     * @param hand Hand to use
     * @return Result of interaction
     */
    @Override
    public InteractionResult interact(@NotNull Player p, @Nullable InteractionHand hand) {
        final net.minecraft.world.InteractionHand h;

        if (hand == InteractionHand.OFF_HAND) h = net.minecraft.world.InteractionHand.OFF_HAND;
        else h = net.minecraft.world.InteractionHand.MAIN_HAND;

        return switch (nmsMob.interact(MobChipUtil.convert(p), h)) {
            case SUCCESS -> InteractionResult.SUCCESS;
            case CONSUME -> InteractionResult.CONSUME;
            case CONSUME_PARTIAL -> InteractionResult.CONSUME_PARTIAL;
            case FAIL -> InteractionResult.FAIL;
            default -> InteractionResult.PASS;
        };
    }

    @Override
    public boolean isSensitiveToWater() {
        return nmsMob.isSensitiveToWater();
    }

    @Override
    public @Nullable BoundingBox getPoseBounds(@Nullable Pose pose) {
        final net.minecraft.world.entity.Pose p;
        switch (pose) {
            case FALL_FLYING -> p = FALL_FLYING;
            case DYING -> p = DYING;
            case SLEEPING -> p = SLEEPING;
            case SPIN_ATTACK -> p = SPIN_ATTACK;
            case SNEAKING -> p = CROUCHING;
            case LONG_JUMPING -> p = LONG_JUMPING;
            case SWIMMING -> p = SWIMMING;
            default -> p = STANDING;
        }

        AABB box = nmsMob.getLocalBoundsForPose(p);
        return new BoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    @Override
    public boolean isAffectedByPotions() {
        return nmsMob.isAffectedByPotions();
    }

    @Override
    public boolean isBlocking() {
        return nmsMob.isBlocking();
    }

    @Override
    public float getArmorCoverPercentage() {
        return nmsMob.getArmorCoverPercentage();
    }

    @Override
    public void useItem(@Nullable InteractionHand hand) {
        if (hand == null) return;

        final net.minecraft.world.InteractionHand h;
        if (hand == InteractionHand.OFF_HAND) h = net.minecraft.world.InteractionHand.OFF_HAND;
        else h = net.minecraft.world.InteractionHand.MAIN_HAND;

        nmsMob.startUsingItem(h);
    }

    @Override
    public boolean isUsingItem() {
        return nmsMob.isUsingItem();
    }

    @Override
    public boolean isFireImmune() {
        return nmsMob.fireImmune();
    }

    @Override
    public boolean isSwinging() {
        return nmsMob.swinging;
    }

    @Override
    public boolean canRideUnderwater() {
        return nmsMob.rideableUnderWater();
    }

    @Override
    public boolean isInvisibleTo(@Nullable Player p) {
        return nmsMob.isInvisibleTo(MobChipUtil.convert(p));
    }

    @Override
    public @NotNull InteractionHand getMainHand() {
        return switch (nmsMob.getMainArm()) {
            case LEFT -> InteractionHand.OFF_HAND;
            case RIGHT -> InteractionHand.MAIN_HAND;
        };
    }

}
