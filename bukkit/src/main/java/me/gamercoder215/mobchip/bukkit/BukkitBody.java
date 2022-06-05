package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static net.minecraft.world.entity.Pose.*;

class BukkitBody implements EntityBody {

    private Mob nmsMob;

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
        switch (nmsMob.interact(ChipConversions.convertType(p), hand.getHandle())) {
            case SUCCESS: return InteractionResult.SUCCESS;
            case CONSUME: return InteractionResult.CONSUME;
            case CONSUME_PARTIAL: return InteractionResult.CONSUME_PARTIAL;
            case FAIL: return InteractionResult.FAIL;
            default: return InteractionResult.PASS;
        }
    }

    @Override
    public void swing(InteractionHand hand, boolean broadcast) {
        if (hand == null) return;
        nmsMob.swing(hand.getHandle(), broadcast);
    }

    @Override
    public boolean isSensitiveToWater() {
        return nmsMob.isSensitiveToWater();
    }

    @Override
    public @Nullable BoundingBox getPoseBounds(@Nullable Pose pose) {
        final net.minecraft.world.entity.Pose p;
        switch (pose) {
            case FALL_FLYING: {
                p = FALL_FLYING;
                break;
            }
            case DYING: {
                p = DYING;
                break;
            }
            case SLEEPING: {
                p = SLEEPING;
                break;
            }
            case SPIN_ATTACK: {
                p = SPIN_ATTACK;
                break;
            }
            case SNEAKING: {
                p = CROUCHING;
                break;
            }
            case LONG_JUMPING: {
                p = LONG_JUMPING;
                break;
            }
            case SWIMMING: {
                p = SWIMMING;
                break;
            }
            default: {
                p = STANDING;
                break;
            }
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
        nmsMob.startUsingItem(hand.getHandle());
    }

    @Override
    public boolean isUsingItem() {
        return nmsMob.isUsingItem();
    }

    @Override
    public boolean isFireImmune() {
        return nmsMob.fireImmune();
    }

}
