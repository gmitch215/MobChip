package me.gamercoder215.mobchip.abstraction.v1_14_R1;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.abstraction.ChipUtil1_14_R1;
import me.gamercoder215.mobchip.ai.animation.EntityAnimation;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
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

@SuppressWarnings("unchecked")
public final class EntityBody1_14_R1 implements EntityBody {
    private final EntityInsentient nmsMob;
    private final Mob m;

    public EntityBody1_14_R1(Mob m) {
        this.m = m;
        this.nmsMob = ChipUtil1_14_R1.toNMS(m);
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
        nmsMob.p(leftHanded);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return nmsMob.cB();
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

        if (nmsMob.b(ChipUtil1_14_R1.toNMS(p), h)) return InteractionResult.SUCCESS;
        else return InteractionResult.FAIL;
    }

    @Override
    public boolean isSensitiveToWater() {
        return nmsMob.du();
    }

    @Override
    public boolean isAffectedByPotions() {
        return nmsMob.dt();
    }

    @Override
    public boolean isBlocking() {
        return nmsMob.isBlocking();
    }

    @Override
    public float getArmorCoverPercentage() {
        return nmsMob.dm();
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
        return nmsMob.at;
    }

    @Override
    public boolean canRideUnderwater() {
        return nmsMob.be();
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
        return nmsMob.aO;
    }

    @Override
    public void setFlyingSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0 || speed > 1) throw new IllegalArgumentException("Flying speed must be between 0.0F and 1.0F");
        nmsMob.aO = speed;
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
        return nmsMob.aL;
    }

    @Override
    public void setBodyRotation(float rotation) {
        nmsMob.aL = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public float getHeadRotation() {
        return nmsMob.aN;
    }

    @Override
    public void setHeadRotation(float rotation) {
        nmsMob.aN = EntityBody.normalizeRotation(rotation);
    }

    @Override
    public Set<? extends Entity> getCollideExemptions() {
        return Collections.emptySet(); // doesn't exist
    }

    @Override
    public void addCollideExemption(@NotNull Entity en) throws IllegalArgumentException {
        // doesn't exist
    }

    @Override
    public void removeCollideExemption(@NotNull Entity en) throws IllegalArgumentException {
        // doesn't exist
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
                nmsMob.az = 0.0F;
                break;
            }
            case CRITICAL_DAMAGE: {
                PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 4);
                for (Player p : ChipUtil1_14_R1.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_14_R1.toNMS(p).playerConnection.sendPacket(pkt);
                break;
            }
            case MAGICAL_CRITICAL_DAMAGE: {
                PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 5);
                for (Player p : ChipUtil1_14_R1.fromNMS(nmsMob).getWorld().getPlayers())
                    ChipUtil1_14_R1.toNMS(p).playerConnection.sendPacket(pkt);
                break;
            }
            default: break;
        }
    }

    @Override
    public float getAnimationSpeed() {
        return nmsMob.aG;
    }

    @Override
    public void setAnimationSpeed(float speed) throws IllegalArgumentException {
        if (speed < 0) throw new IllegalArgumentException("Animation speed cannot be negative");
        nmsMob.aG = speed;
    }

    @Override
    public boolean hasVerticalCollision() {
        return nmsMob.y;
    }

    @Override
    public void setVerticalCollision(boolean collision) {
        nmsMob.y = collision;
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
        return nmsMob.D;
    }

    @Override
    public float getMoveDistance() {
        return nmsMob.E;
    }

    @Override
    public float getFlyDistance() {
        return 0F; // doesn't exist
    }

    @Override
    public boolean isImmuneToExplosions() {
        return nmsMob.bR();
    }

    @Override
    public boolean isPeacefulCompatible() {
        try {
            Method m = EntityInsentient.class.getDeclaredMethod("I");
            m.setAccessible(true);
            return (boolean) m.invoke(nmsMob);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isInBubbleColumn() {
        return nmsMob.world.getType(nmsMob.getChunkCoordinates()).getBlock().equals(Blocks.BUBBLE_COLUMN);
    }

    @Override
    public boolean isInvulnerableTo(EntityDamageEvent.@Nullable DamageCause cause) {
        return nmsMob.isInvulnerable(ChipUtil1_14_R1.toNMS(cause));
    }

    @Override
    public int getMaxFallDistance() {
        return nmsMob.bu();
    }

    @Override
    public boolean isPushableBy(@Nullable Entity entity) {
        return IEntitySelector.a(ChipUtil1_14_R1.toNMS(entity)).test(ChipUtil1_14_R1.toNMS(entity));
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
        return nmsMob.K;
    }

    @Override
    public void setMaxUpStep(float maxUpStep) {
        nmsMob.K = maxUpStep;
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
            Field f = EntityLiving.class.getDeclaredField("bq");
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
            Field f = EntityLiving.class.getDeclaredField("bq");
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
        Packet<?> packet = nmsMob.N();
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
        nmsMob.a(nmsMob, forceF, xForce, zForce);
    }

    @Override
    public void eat(@NotNull ItemStack item) {
        nmsMob.a(ChipUtil1_14_R1.toNMS(m.getWorld()), ChipUtil1_14_R1.toNMS(item));
    }

}
