package me.gamercoder215.mobchip.abstraction.v1_18_R1;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_18_R1;
import me.gamercoder215.mobchip.ai.animation.EntityAnimation;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class EntityBody1_18_R1 implements EntityBody {
    private final net.minecraft.world.entity.Mob nmsMob;
    private final Mob m;

    public EntityBody1_18_R1(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_18_R1.toNMS(m);
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
        return nmsMob.canBreatheUnderwater();
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

        return switch (nmsMob.interact(ChipUtil1_18_R1.toNMS(p), h)) {
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
        return nmsMob.isInvisibleTo(ChipUtil1_18_R1.toNMS(p));
    }

    @Override
    public @NotNull InteractionHand getMainHand() {
        if (nmsMob.getMainArm() == HumanoidArm.LEFT) return InteractionHand.OFF_HAND;
        return InteractionHand.MAIN_HAND;
    }

    @Override
    public List<ItemStack> getDefaultDrops() {
        return new ArrayList<>(nmsMob.drops);
    }

    @Override
    public void setDefaultDrops(@Nullable ItemStack... drops) {
        nmsMob.drops = new ArrayList<>(Arrays.asList(drops));
    }

    @Override
    public boolean isInCombat() {
        return nmsMob.combatTracker.isInCombat();
    }

    @Override
    public float getFlyingSpeed() {
        return nmsMob.flyingSpeed;
    }

    @Override
    public void setFlyingSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0 || speed > 1) throw new IllegalArgumentException("Flying speed must be between 0.0F and 1.0F");
        nmsMob.flyingSpeed = speed;
    }

    @Override
    public boolean isForcingDrops() {
        return nmsMob.forceDrops;
    }

    @Override
    public void setForcingDrops(boolean drop) {
        nmsMob.forceDrops = drop;
    }

    @Override
    public boolean isMoving() {
        double x = nmsMob.getX() - nmsMob.xo;
        double z = nmsMob.getZ() - nmsMob.zo;
        return x * x + z * z > 2.500000277905201E-7D;
    }

    @Override
    public float getBodyRotation() {
        return nmsMob.yBodyRot;
    }

    @Override
    public void setBodyRotation(float rotation) {
        nmsMob.yBodyRot = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public float getHeadRotation() {
        return nmsMob.yHeadRot;
    }

    @Override
    public void setHeadRotation(float rotation) {
        nmsMob.yHeadRot = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public Set<? extends Entity> getCollideExemptions() {
        return nmsMob.collidableExemptions.stream().map(Bukkit::getEntity).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public void addCollideExemption(@NotNull Entity en) throws IllegalArgumentException {
        if (en == null) throw new IllegalArgumentException("Entity cannot be null");
        nmsMob.collidableExemptions.add(en.getUniqueId());
    }

    @Override
    public void removeCollideExemption(@NotNull Entity en) throws IllegalArgumentException {
        if (en == null) throw new IllegalArgumentException("Entity cannot be null");
        nmsMob.collidableExemptions.remove(en.getUniqueId());
    }

    @Override
    public int getDroppedExperience() {
        return nmsMob.expToDrop;
    }

    @Override
    public void setDroppedExperience(int exp) throws IllegalArgumentException {
        if (exp < 0) throw new IllegalArgumentException("Experience cannot be negative");
        nmsMob.expToDrop = exp;
    }

    @Override
    public void playAnimation(@NotNull EntityAnimation anim) {
        switch (anim) {
            case SPAWN -> nmsMob.spawnAnim();
            case DAMAGE -> nmsMob.animateHurt();
            case CRITICAL_DAMAGE -> {
                ClientboundAnimatePacket pkt = new ClientboundAnimatePacket(nmsMob, 4);
                for (Player p : ChipUtil1_18_R1.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_18_R1.toNMS(p).connection.send(pkt);
            }
            case MAGICAL_CRITICAL_DAMAGE -> {
                ClientboundAnimatePacket pkt = new ClientboundAnimatePacket(nmsMob, 5);
                for (Player p : ChipUtil1_18_R1.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_18_R1.toNMS(p).connection.send(pkt);
            }
        }
    }

    @Override
    public float getAnimationSpeed() {
        return nmsMob.animationSpeed;
    }

    @Override
    public void setAnimationSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0) throw new IllegalArgumentException("Animation speed cannot be negative");
        nmsMob.animationSpeed = speed;
    }

    @Override
    public boolean hasVerticalCollision() {
        return nmsMob.verticalCollision;
    }

    @Override
    public void setVerticalCollision(boolean collision) {
        nmsMob.verticalCollision = collision;
    }

    @Override
    public boolean hasHorizontalCollision() {
        return nmsMob.horizontalCollision;
    }

    @Override
    public void setHorizontalCollision(boolean collision) {
        nmsMob.horizontalCollision = collision;
    }

    @Override
    public float getWalkDistance() {
        return nmsMob.walkDist;
    }

    @Override
    public float getMoveDistance() {
        return nmsMob.moveDist;
    }

    @Override
    public float getFlyDistance() {
        return nmsMob.flyDist;
    }

    @Override
    public boolean isImmuneToExplosions() {
        return nmsMob.ignoreExplosion();
    }

    @Override
    public boolean isPeacefulCompatible() {
        try {
            Method m = net.minecraft.world.entity.Mob.class.getDeclaredMethod("P");
            m.setAccessible(true);
            return (boolean) m.invoke(nmsMob);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isInBubbleColumn() {
        return nmsMob.level.getBlockState(nmsMob.blockPosition()).is(Blocks.BUBBLE_COLUMN);
    }

    @Override
    public boolean isInvulnerableTo(EntityDamageEvent.@Nullable DamageCause cause) {
        return nmsMob.isInvulnerableTo(ChipUtil1_18_R1.toNMS(cause));
    }

    @Override
    public int getMaxFallDistance() {
        return nmsMob.getMaxFallDistance();
    }

    @Override
    public boolean isPushableBy(@Nullable Entity entity) {
        return EntitySelector.pushableBy(ChipUtil1_18_R1.toNMS(entity)).test(ChipUtil1_18_R1.toNMS(entity));
    }

    @Override
    public float getYaw() {
        return nmsMob.getYRot();
    }

    @Override
    public void setYaw(float rotation) {
        nmsMob.setYRot(EntityBody.normalizeRotation(rotation));
    }

    @Override
    public float getPitch() {
        return nmsMob.getXRot();
    }

    @Override
    public void setPitch(float rotation) {
        nmsMob.setXRot(EntityBody.normalizeRotation(rotation));
    }

    @Override
    public float getMaxUpStep() {
        return nmsMob.maxUpStep;
    }

    @Override
    public void setMaxUpStep(float maxUpStep) {
        nmsMob.maxUpStep = maxUpStep;
    }

    @Override
    public Position getLastLavaContact() {
        BlockPos p = nmsMob.lastLavaContact;
        if (p == null) return null;
        return new Position(p.getX(), p.getY(), p.getZ());
    }

    @Override
    public void setRiptideTicks(int ticks) {
        if (ticks < 0) throw new IllegalArgumentException("Riptide ticks cannot be negative");
        try {
            Field f = LivingEntity.class.getDeclaredField("bC");
            f.setAccessible(true);
            f.setInt(nmsMob, ticks);

            if (!nmsMob.level.isClientSide) {
                Method setFlags = LivingEntity.class.getDeclaredMethod("c", int.class, boolean.class);
                setFlags.setAccessible(true);
                setFlags.invoke(nmsMob, 4, true);
            }
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) Bukkit.getLogger().severe(ste.toString());
        }
    }

    @Override
    public int getRiptideTicks() {
        try {
            Field f = LivingEntity.class.getDeclaredField("bC");
            f.setAccessible(true);
            return f.getInt(nmsMob);
        } catch (ReflectiveOperationException e) {
            return 0;
        }
    }

    @Override
    public @NotNull Mob getEntity() {
        return m;
    }

    @Override
    public boolean shouldRenderFrom(double x, double y, double z) {
        return nmsMob.shouldRender(x, y, z);
    }

    @Override
    public boolean shouldRenderFromSqr(double dist) {
        return nmsMob.shouldRenderAtSqrDistance(dist);
    }

    @Override
    public void sendTo(@NotNull Player p) {
        Packet<?> packet = nmsMob.getAddEntityPacket();
        ((CraftPlayer) p).getHandle().connection.send(packet);
    }

    @Override
    public void resetFallDistance() {
        nmsMob.resetFallDistance();
    }

    @Override
    public boolean isInUnloadedChunk() {
        return nmsMob.touchingUnloadedChunk();
    }

    @Override
    public void naturalKnockback(double force, double xForce, double zForce) {
        nmsMob.knockback(force, xForce, zForce);
    }

    @Override
    public void eat(@NotNull ItemStack item) {
        nmsMob.eat(ChipUtil1_18_R1.toNMS(m.getWorld()), ChipUtil1_18_R1.toNMS(item));
    }
    
}
