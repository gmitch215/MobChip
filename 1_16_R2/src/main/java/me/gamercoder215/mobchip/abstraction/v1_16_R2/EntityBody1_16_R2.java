package me.gamercoder215.mobchip.abstraction.v1_16_R2;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_16_R2;
import me.gamercoder215.mobchip.ai.animation.EntityAnimation;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
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

@SuppressWarnings("unchecked")
public final class EntityBody1_16_R2 implements EntityBody {
    private final EntityInsentient nmsMob;
    private final Mob m;

    public EntityBody1_16_R2(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_16_R2.toNMS(m);
    }

    private void update() {
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(nmsMob.getId(), nmsMob.getDataWatcher(), true);

        for (Player p : m.getWorld().getPlayers())
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
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
        return nmsMob.cM();
    }

    @Override
    public boolean shouldDiscardFriction() {
        return false;
    }

    @Override
    public void setDiscardFriction(boolean discard) {
        // doesn't exist
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

        if (hand == InteractionHand.OFF_HAND) h = EnumHand.OFF_HAND;
        else h = EnumHand.MAIN_HAND;

        switch (nmsMob.a(ChipUtil1_16_R2.toNMS(p), h)) {
            case SUCCESS:
                return InteractionResult.SUCCESS;
            case CONSUME:
                return InteractionResult.CONSUME;
            case FAIL:
                return InteractionResult.FAIL;
            default:
                return InteractionResult.PASS;
        }
    }

    @Override
    public boolean isSensitiveToWater() {
        return nmsMob.dN();
    }

    @Override
    public boolean isAffectedByPotions() {
        return nmsMob.eh();
    }

    @Override
    public boolean isBlocking() {
        return nmsMob.isBlocking();
    }

    @Override
    public float getArmorCoverPercentage() {
        return nmsMob.dE();
    }

    @Override
    public void useItem(@Nullable InteractionHand hand) {
        if (hand == null) return;

        final EnumHand h;
        if (hand == InteractionHand.OFF_HAND) h = EnumHand.OFF_HAND;
        else h = EnumHand.MAIN_HAND;

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
        return nmsMob.ai;
    }

    @Override
    public boolean canRideUnderwater() {
        return nmsMob.bs();
    }

    @Override
    public boolean isInvisibleTo(@Nullable Player p) {
        return false;
    }

    @Override
    public @NotNull InteractionHand getMainHand() {
        if (nmsMob.getMainHand() == EnumMainHand.LEFT) return InteractionHand.OFF_HAND;
        return InteractionHand.MAIN_HAND;
    }

    @Override
    public List<ItemStack> getDefaultDrops() {
        try {
            Field dropsF = EntityLiving.class.getDeclaredField("drops");
            dropsF.setAccessible(true);
            List<ItemStack> drops = (List<ItemStack>) dropsF.get(nmsMob);
            return new ArrayList<>(drops);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void setDefaultDrops(@Nullable ItemStack... drops) {
        try {
            Field dropsF = EntityLiving.class.getDeclaredField("drops");
            dropsF.setAccessible(true);
            dropsF.set(nmsMob, drops == null ? new ArrayList<>() : Arrays.asList(drops));
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean isInCombat() {
        try {
            Field inCombat = nmsMob.combatTracker.getClass().getDeclaredField("f");
            inCombat.setAccessible(true);
            return inCombat.getBoolean(nmsMob.combatTracker);
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public float getFlyingSpeed() {
        return nmsMob.aE;
    }

    @Override
    public void setFlyingSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0 || speed > 1) throw new IllegalArgumentException("Flying speed must be between 0.0F and 1.0F");
        nmsMob.aE = speed;
    }

    @Override
    public boolean isForcingDrops() {
        try {
            Field forceDrops = EntityLiving.class.getDeclaredField("forceDrops");
            forceDrops.setAccessible(true);
            return forceDrops.getBoolean(nmsMob);
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public void setForcingDrops(boolean drop) {
        try {
            Field forceDrops = EntityLiving.class.getDeclaredField("forceDrops");
            forceDrops.setAccessible(true);
            forceDrops.set(nmsMob, drop);
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean isMoving() {
        return false; // doesn't exist
    }

    @Override
    public float getBodyRotation() {
        return nmsMob.aA;
    }

    @Override
    public void setBodyRotation(float rotation) {
        nmsMob.aA = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public float getHeadRotation() {
        return nmsMob.aC;
    }

    @Override
    public void setHeadRotation(float rotation) {
        nmsMob.aC = EntityBody.normalizeRotation(rotation);
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
            case DAMAGE: {
                nmsMob.hurtDuration = 10;
                nmsMob.hurtTicks = nmsMob.hurtDuration;
                nmsMob.ap = 0.0F;
                break;
            }
            case CRITICAL_DAMAGE: {
                PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 4);
                for (Player p : ChipUtil1_16_R2.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_16_R2.toNMS(p).playerConnection.sendPacket(pkt);
                break;
            }
            case MAGICAL_CRITICAL_DAMAGE: {
                PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 5);
                for (Player p : ChipUtil1_16_R2.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_16_R2.toNMS(p).playerConnection.sendPacket(pkt);
                break;
            }
            default: break;
        }
    }

    @Override
    public float getAnimationSpeed() {
        return nmsMob.av;
    }

    @Override
    public void setAnimationSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0) throw new IllegalArgumentException("Animation speed cannot be negative");
        nmsMob.av = speed;
    }

    @Override
    public boolean hasVerticalCollision() {
        return nmsMob.v;
    }

    @Override
    public void setVerticalCollision(boolean collision) {
        nmsMob.v = collision;
    }

    @Override
    public boolean hasHorizontalCollision() {
        return nmsMob.positionChanged;
    }

    @Override
    public void setHorizontalCollision(boolean collision) {
        nmsMob.positionChanged = collision;
    }

    @Override
    public float getWalkDistance() {
        return nmsMob.A;
    }

    @Override
    public float getMoveDistance() {
        return nmsMob.B;
    }

    @Override
    public float getFlyDistance() {
        return 0F; // doesn't exist
    }

    @Override
    public boolean isImmuneToExplosions() {
        return nmsMob.ci();
    }

    @Override
    public boolean isPeacefulCompatible() {
        try {
            Method m = EntityInsentient.class.getDeclaredMethod("L");
            m.setAccessible(true);
            return (boolean) m.invoke(nmsMob);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isInBubbleColumn() {
        return nmsMob.world.getType(nmsMob.getChunkCoordinates()).a(Blocks.BUBBLE_COLUMN);
    }

    @Override
    public boolean isInvulnerableTo(EntityDamageEvent.@Nullable DamageCause cause) {
        return nmsMob.isInvulnerable(ChipUtil1_16_R2.toNMS(cause));
    }

    @Override
    public int getMaxFallDistance() {
        return nmsMob.bO();
    }

    @Override
    public boolean isPushableBy(@Nullable Entity entity) {
        return IEntitySelector.a(ChipUtil1_16_R2.toNMS(entity)).test(ChipUtil1_16_R2.toNMS(entity));
    }

    @Override
    public float getYaw() {
        return nmsMob.yaw;
    }

    @Override
    public void setYaw(float rotation) {
        nmsMob.yaw = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public float getPitch() {
        return nmsMob.pitch;
    }

    @Override
    public void setPitch(float rotation) {
        nmsMob.pitch = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public float getMaxUpStep() {
        return nmsMob.G;
    }

    @Override
    public void setMaxUpStep(float maxUpStep) {
        nmsMob.G = maxUpStep;
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
            Field f = EntityLiving.class.getDeclaredField("bf");
            f.setAccessible(true);
            f.setInt(nmsMob, ticks);

            if (!nmsMob.world.isClientSide) {
                Method setFlags = EntityLiving.class.getDeclaredMethod("c", int.class, boolean.class);
                setFlags.setAccessible(true);
                setFlags.invoke(nmsMob, 4, true);
            }
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) Bukkit.getLogger().severe(ste.toString());
        }

        update();
    }

    @Override
    public int getRiptideTicks() {
        try {
            Field f = EntityLiving.class.getDeclaredField("bf");
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
        // doesn't exist
        return false;
    }

    @Override
    public boolean shouldRenderFromSqr(double dist) {
        // doesn't exist
        return false;
    }

    @Override
    public void sendTo(@NotNull Player p) {
        Packet<?> packet = nmsMob.P();
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void resetFallDistance() {
        nmsMob.fallDistance = 0.0F;
    }

    @Override
    public boolean isInUnloadedChunk() {
        // doesn't exist
        return false;
    }

    @Override
    public void naturalKnockback(double force, double xForce, double zForce) {
        float forceF = Math.abs((float) force);
        nmsMob.a(forceF, xForce, zForce);
    }

    @Override
    public void eat(@NotNull ItemStack item) {
        nmsMob.a(ChipUtil1_16_R2.toNMS(m.getWorld()), ChipUtil1_16_R2.toNMS(item));
    }

}
