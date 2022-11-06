package me.gamercoder215.mobchip.abstraction.v1_17_R1;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_17_R1;
import me.gamercoder215.mobchip.ai.animation.EntityAnimation;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumMainHand;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
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

public final class EntityBody1_17_R1 implements EntityBody {
    private final EntityInsentient nmsMob;
    private final Mob m;

    public EntityBody1_17_R1(Mob m) {
        this.nmsMob = ChipUtil1_17_R1.toNMS(m);
        this.m = m;
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
        return nmsMob.dr();
    }

    @Override
    public boolean shouldDiscardFriction() {
        return nmsMob.dL();
    }

    @Override
    public void setDiscardFriction(boolean discard) {
        nmsMob.p(discard);
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
        final EnumHand h;

        if (hand == InteractionHand.OFF_HAND) h = EnumHand.b;
        else h = EnumHand.a;

        return switch (nmsMob.a(ChipUtil1_17_R1.toNMS(p), h)) {
            case a -> InteractionResult.SUCCESS;
            case b -> InteractionResult.CONSUME;
            case c -> InteractionResult.CONSUME_PARTIAL;
            case e -> InteractionResult.FAIL;
            default -> InteractionResult.PASS;
        };
    }

    @Override
    public boolean isSensitiveToWater() {
        return nmsMob.ex();
    }

    @Override
    public boolean isAffectedByPotions() {
        return nmsMob.eQ();
    }

    @Override
    public boolean isBlocking() {
        return nmsMob.isBlocking();
    }

    @Override
    public float getArmorCoverPercentage() {
        return nmsMob.en();
    }

    @Override
    public void useItem(@Nullable InteractionHand hand) {
        if (hand == null) return;

        final EnumHand h;
        if (hand == InteractionHand.OFF_HAND) h = EnumHand.b;
        else h = EnumHand.a;

        nmsMob.c(h);
    }

    @Override
    public boolean isUsingItem() {
        return nmsMob.isHandRaised();
    }

    @Override
    public boolean isFireImmune() {
        return nmsMob.isFireProof();
    }

    @Override
    public boolean isSwinging() {
        return nmsMob.aF;
    }

    @Override
    public boolean canRideUnderwater() {
        return nmsMob.bC();
    }

    @Override
    public boolean isInvisibleTo(@Nullable Player p) {
        return nmsMob.c((EntityHuman) ChipUtil1_17_R1.toNMS(p));
    }

    @Override
    public @NotNull InteractionHand getMainHand() {
        if (nmsMob.getMainHand() == EnumMainHand.a) return InteractionHand.OFF_HAND;
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
        return nmsMob.getCombatTracker().e();
    }

    @Override
    public float getFlyingSpeed() {
        return nmsMob.bb;
    }

    @Override
    public void setFlyingSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0 || speed > 1) throw new IllegalArgumentException("Flying speed must be between 0.0F and 1.0F");
        nmsMob.bb = speed;
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
        double x = nmsMob.locX() - nmsMob.u;
        double z = nmsMob.locZ() - nmsMob.w;
        return x * x + z * z > 2.500000277905201E-7D;
    }

    @Override
    public float getBodyRotation() {
        return nmsMob.aX;
    }

    @Override
    public void setBodyRotation(float rotation) {
        nmsMob.aX = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public float getHeadRotation() {
        return nmsMob.aZ;
    }

    @Override
    public void setHeadRotation(float rotation) {
        nmsMob.aZ = EntityBody.normalizeRotation(rotation);
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
            case SPAWN -> nmsMob.doSpawnEffect();
            case DAMAGE -> nmsMob.bv();
            case CRITICAL_DAMAGE -> {
                PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 4);
                for (Player p : ChipUtil1_17_R1.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_17_R1.toNMS(p).b.sendPacket(pkt);
            }
            case MAGICAL_CRITICAL_DAMAGE -> {
                PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 5);
                for (Player p : ChipUtil1_17_R1.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_17_R1.toNMS(p).b.sendPacket(pkt);
            }
        }
    }

    @Override
    public float getAnimationSpeed() {
        return nmsMob.aS;
    }

    @Override
    public void setAnimationSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0) throw new IllegalArgumentException("Animation speed cannot be negative");
        nmsMob.aS = speed;
    }

    @Override
    public boolean hasVerticalCollision() {
        return nmsMob.B;
    }

    @Override
    public void setVerticalCollision(boolean collision) {
        nmsMob.B = collision;
    }

    @Override
    public boolean hasHorizontalCollision() {
        return nmsMob.A;
    }

    @Override
    public void setHorizontalCollision(boolean collision) {
        nmsMob.A = collision;
    }

    @Override
    public float getWalkDistance() {
        return nmsMob.H;
    }

    @Override
    public float getMoveDistance() {
        return nmsMob.I;
    }

    @Override
    public float getFlyDistance() {
        return nmsMob.J;
    }

    @Override
    public boolean isImmuneToExplosions() {
        return nmsMob.cx();
    }

    @Override
    public boolean isPeacefulCompatible() {
        try {
            Method m = EntityInsentient.class.getDeclaredMethod("Q");
            m.setAccessible(true);
            return (boolean) m.invoke(nmsMob);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isInBubbleColumn() {
        return nmsMob.t.getType(nmsMob.getChunkCoordinates()).a(Blocks.lq);
    }

    @Override
    public boolean isInvulnerableTo(EntityDamageEvent.@Nullable DamageCause cause) {
        return nmsMob.isInvulnerable(ChipUtil1_17_R1.toNMS(cause));
    }

    @Override
    public int getMaxFallDistance() {
        return nmsMob.ce();
    }

    @Override
    public boolean isPushableBy(@Nullable Entity entity) {
        return IEntitySelector.a(ChipUtil1_17_R1.toNMS(entity)).test(ChipUtil1_17_R1.toNMS(entity));
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
        return nmsMob.O;
    }

    @Override
    public void setMaxUpStep(float maxUpStep) {
        nmsMob.O = maxUpStep;
    }

    @Override
    public Position getLastLavaContact() {
        // doesn't exist
        return null;
    }

    @Override
    public void setRiptideTicks(int ticks) {
        if (ticks < 0) throw new IllegalArgumentException("Riptide ticks cannot be negative");
        try {
            Field f = EntityLiving.class.getDeclaredField("bC");
            f.setAccessible(true);
            f.setInt(nmsMob, ticks);

            if (!nmsMob.t.isClientSide()) {
                Method setFlags = EntityLiving.class.getDeclaredMethod("c", int.class, boolean.class);
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
            Field f = EntityLiving.class.getDeclaredField("bC");
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
        return nmsMob.j(x, y, z);
    }

    @Override
    public boolean shouldRenderFromSqr(double dist) {
        return nmsMob.a(dist);
    }

    @Override
    public void sendTo(@NotNull Player p) {
        Packet<?> packet = nmsMob.getPacket();
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }

    @Override
    public void resetFallDistance() {
        nmsMob.K = 0.0F;
    }

    @Override
    public boolean isInUnloadedChunk() {
        return nmsMob.cM();
    }

    @Override
    public void naturalKnockback(double force, double xForce, double zForce) {
        nmsMob.p(force, xForce, zForce);
    }

    @Override
    public void eat(@NotNull ItemStack item) {
        nmsMob.a(ChipUtil1_17_R1.toNMS(m.getWorld()), ChipUtil1_17_R1.toNMS(item));
    }
}