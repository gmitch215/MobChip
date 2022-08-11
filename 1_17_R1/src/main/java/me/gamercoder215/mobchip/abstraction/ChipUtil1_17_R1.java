package me.gamercoder215.mobchip.abstraction;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.ai.animation.EntityAnimation;
import me.gamercoder215.mobchip.ai.attribute.Attribute;
import me.gamercoder215.mobchip.ai.attribute.AttributeInstance;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.ai.enderdragon.DragonPhase;
import me.gamercoder215.mobchip.ai.goal.*;
import me.gamercoder215.mobchip.ai.goal.target.*;
import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import me.gamercoder215.mobchip.ai.gossip.GossipType;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.schedule.Schedule;
import me.gamercoder215.mobchip.combat.CombatEntry;
import me.gamercoder215.mobchip.combat.CombatLocation;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.util.Position;
import net.minecraft.core.*;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeRanged;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.control.ControllerJump;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.gossip.Reputation;
import net.minecraft.world.entity.ai.gossip.ReputationType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.*;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftSound;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.attribute.CraftAttributeInstance;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_17_R1.entity.*;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class ChipUtil1_17_R1 implements ChipUtil {

    private static ItemStack fromNMS(net.minecraft.world.item.ItemStack item) { return CraftItemStack.asBukkitCopy(item); }

    @Override
    public void addCustomPathfinder(CustomPathfinder p, int priority, boolean target) {
        Mob m = p.getEntity();
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.bP : mob.bO;

        PathfinderGoal g = custom(p);

        Pathfinder.PathfinderFlag[] flags = p.getFlags() == null ? new Pathfinder.PathfinderFlag[0] : p.getFlags();
        for (Pathfinder.PathfinderFlag f : flags) {
            EnumSet<PathfinderGoal.Type> nmsFlags = g.i() == null ? EnumSet.allOf(PathfinderGoal.Type.class) : EnumSet.copyOf(g.i());
            nmsFlags.add(toNMS(f));
            g.a(nmsFlags);
        }

        s.a(priority, g);
    }

    @Override
    public Set<WrappedPathfinder> getGoals(Mob m, boolean target) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.bP : mob.bO;

        Set<WrappedPathfinder> pF = new HashSet<>();
        s.c().forEach(w -> pF.add(new WrappedPathfinder(fromNMS(w.j()), w.h())));

        return pF;
    }

    @Override
    public Collection<WrappedPathfinder> getRunningGoals(Mob m, boolean target) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.bP : mob.bO;

        Collection<WrappedPathfinder> l = new HashSet<>();
        s.d().forEach(w -> l.add(new WrappedPathfinder(fromNMS(w.j()), w.h())));

        return l;
    }

    @Override
    public void setFlag(Mob m, Pathfinder.PathfinderFlag flag, boolean target, boolean value) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.bP : mob.bO;
        s.a(toNMS(flag), value);
    }

    private static Class<? extends EntityLiving> toNMS(Class<? extends LivingEntity> clazz) {
        try {
            Method m = clazz.getDeclaredMethod("getHandle");
            return m.getReturnType().asSubclass(EntityLiving.class);
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());

            return null;
        }
    }

    private static net.minecraft.world.item.ItemStack toNMS(ItemStack i) {
        return CraftItemStack.asNMSCopy(i);
    }

    private static SoundEffect toNMS(Sound s) {
        return CraftSound.getSoundEffect(s);
    }

    private static PathfinderGoal toNMS(Pathfinder b) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);

        return switch (b.getInternalName()) {
            case "AvoidTarget" -> {
                PathfinderAvoidEntity<?> p = (PathfinderAvoidEntity<?>) b;
                yield new PathfinderGoalAvoidTarget<>((EntityCreature) m, toNMS(p.getFilter()), p.getMaxDistance(), p.getSpeedModifier(), p.getSprintModifier());
            }
            case "ArrowAttack" -> {
                PathfinderRangedAttack p = (PathfinderRangedAttack) b;
                yield new PathfinderGoalArrowAttack((IRangedEntity) m, p.getSpeedModifier(), p.getMinAttackInterval(), p.getMaxAttackInterval(), p.getRange());
            }
            case "Beg" -> {
                PathfinderBeg p = (PathfinderBeg) b;
                yield new PathfinderGoalBeg((EntityWolf) m, p.getRange());
            }
            case "BowShoot" -> {
                PathfinderRangedBowAttack p = (PathfinderRangedBowAttack) b;
                yield new PathfinderGoalBowShoot((EntityMonster) m, p.getSpeedModifier(), p.getInterval(), p.getRange());
            }
            case "BreakDoor" -> {
                PathfinderBreakDoor p = (PathfinderBreakDoor) b;
                yield new PathfinderGoalBreakDoor(m, p.getBreakTime(), d -> p.getCondition().test(fromNMS(d)));
            }
            case "Breath" -> new PathfinderGoalBreath((EntityCreature) m);
            case "Breed" -> {
                PathfinderBreed p = (PathfinderBreed) b;
                yield new PathfinderGoalBreed((EntityAnimal) m, p.getSpeedModifier());
            }
            case "CatSitOnBed" -> {
                PathfinderCatOnBed p = (PathfinderCatOnBed) b;
                yield new PathfinderGoalCatSitOnBed((EntityCat) m, p.getSpeedModifier(), Math.min((int) p.getRange(), 1));
            }
            case "CrossbowAttack" -> {
                PathfinderRangedCrossbowAttack p = (PathfinderRangedCrossbowAttack) b;
                yield new PathfinderGoalCrossbowAttack((EntityMonster) m, p.getSpeedModifier(), p.getRange());
            }
            case "DoorOpen" -> {
                PathfinderOpenDoor p = (PathfinderOpenDoor) b;
                yield new PathfinderGoalDoorOpen(m, p.mustClose());
            }
            case "EatTile" -> new PathfinderGoalEatTile(m);
            case "FishSchool" -> new PathfinderGoalFishSchool((EntityFishSchool) m);
            case "FleeSun" -> {
                PathfinderFleeSun p = (PathfinderFleeSun) b;
                yield new PathfinderGoalFleeSun((EntityCreature) m, p.getSpeedModifier());
            }
            case "Float" -> new PathfinderGoalFloat(m);
            case "FollowBoat" -> new PathfinderGoalFollowBoat((EntityCreature) m);
            case "FollowEntity" -> {
                PathfinderFollowMob p = (PathfinderFollowMob) b;
                yield new PathfinderGoalFollowEntity(m, p.getSpeedModifier(), p.getStopDistance(), p.getRange());
            }
            case "FollowOwner" -> {
                PathfinderFollowOwner p = (PathfinderFollowOwner) b;
                yield new PathfinderGoalFollowOwner((EntityTameableAnimal) m, p.getSpeedModifier(), p.getStartDistance(), p.getStopDistance(), p.canFly());
            }
            case "FollowParent" -> {
                PathfinderFollowParent p = (PathfinderFollowParent) b;
                yield new PathfinderGoalFollowParent((EntityTameableAnimal) m, p.getSpeedModifier());
            }
            case "JumpOnBlock" -> {
                PathfinderCatOnBlock p = (PathfinderCatOnBlock) b;
                yield new PathfinderGoalJumpOnBlock((EntityCat) m, p.getSpeedModifier());
            }
            case "LeapAtTarget" -> {
                PathfinderLeapAtTarget p = (PathfinderLeapAtTarget) b;
                yield new PathfinderGoalLeapAtTarget(m, p.getHeight());
            }
            case "LlamaFollow" -> {
                PathfinderLlamaFollowCaravan p = (PathfinderLlamaFollowCaravan) b;
                yield new PathfinderGoalLlamaFollow((EntityLlama) m, p.getSpeedModifier());
            }
            case "LookAtPlayer" -> {
                PathfinderLookAtEntity<?> p = (PathfinderLookAtEntity) b;
                yield new PathfinderGoalLookAtPlayer(m, toNMS(p.getFilter()), p.getRange(), p.getProbability(), p.isHorizontal());
            }
            case "LookAtTradingPlayer" -> new PathfinderGoalLookAtTradingPlayer((EntityVillagerAbstract) m);
            case "MeleeAttack" -> {
                PathfinderMeleeAttack p = (PathfinderMeleeAttack) b;
                yield new PathfinderGoalMeleeAttack((EntityCreature) m, p.getSpeedModifier(), p.mustSee());
            }
            case "MoveThroughVillage" -> {
                PathfinderMoveThroughVillage p = (PathfinderMoveThroughVillage) b;
                yield new PathfinderGoalMoveThroughVillage((EntityVillager) m, p.getSpeedModifier(), p.mustBeNight(), p.getMinDistance(), p.canUseDoors());
            }
            case "MoveTowardsRestriction" -> {
                PathfinderMoveTowardsRestriction p = (PathfinderMoveTowardsRestriction) b;
                yield new PathfinderGoalMoveTowardsRestriction((EntityCreature) m, p.getSpeedModifier());
            }
            case "MoveTowardsTarget" -> {
                PathfinderMoveTowardsTarget p = (PathfinderMoveTowardsTarget) b;
                yield new PathfinderGoalMoveTowardsTarget((EntityCreature) m, p.getSpeedModifier(), p.getRange());
            }
            case "NearestVillage" -> {
                PathfinderRandomStrollThroughVillage p = (PathfinderRandomStrollThroughVillage) b;
                yield new PathfinderGoalNearestVillage((EntityCreature) m, p.getInterval());
            }
            case "OcelotAttack" -> new PathfinderGoalOcelotAttack(m);
            case "OfferFlower" -> new PathfinderGoalOfferFlower((EntityIronGolem) m);
            case "Panic" -> {
                PathfinderPanic p = (PathfinderPanic) b;
                yield new PathfinderGoalPanic((EntityCreature) m, p.getSpeedModifier());
            }
            case "Perch" -> new PathfinderGoalPerch((EntityPerchable) m);
            case "Raid" -> new PathfinderGoalRaid<>((EntityRaider) m);
            case "RandomFly" -> {
                PathfinderRandomStrollFlying p = (PathfinderRandomStrollFlying) b;
                yield new PathfinderGoalRandomFly((EntityCreature) m, p.getSpeedModifier());
            }
            case "RandomLookaround" -> new PathfinderGoalRandomLookaround(m);
            case "RandomStroll" -> {
                PathfinderRandomStroll p = (PathfinderRandomStroll) b;
                yield new PathfinderGoalRandomStroll((EntityCreature) m, p.getSpeedModifier(), p.getInterval());
            }
            case "RandomStrollLand" -> {
                PathfinderRandomStrollLand p = (PathfinderRandomStrollLand) b;
                yield new PathfinderGoalRandomStrollLand((EntityCreature) m, p.getSpeedModifier(), p.getProbability());
            }
            case "RandomSwim" -> {
                PathfinderRandomSwim p = (PathfinderRandomSwim) b;
                yield new PathfinderGoalRandomSwim((EntityCreature) m, p.getSpeedModifier(), p.getInterval());
            }
            case "RemoveBlock" -> {
                PathfinderRemoveBlock p = (PathfinderRemoveBlock) b;
                yield new PathfinderGoalRemoveBlock(((CraftBlock) p.getBlock()).getNMS().getBlock(), (EntityCreature) m, p.getSpeedModifier(), Math.min((int) p.getBlock().getLocation().distance(mob.getLocation()), 1));
            }
            case "RestrictSun" -> new PathfinderGoalRestrictSun((EntityCreature) m);
            case "Sit" -> new PathfinderGoalSit((EntityTameableAnimal) m);
            case "StrollVillage" -> {
                PathfinderRandomStrollToVillage p = (PathfinderRandomStrollToVillage) b;
                yield new PathfinderGoalStrollVillage((EntityCreature) m, p.getSpeedModifier(), true);
            }
            case "StrollVillageGolem" -> {
                PathfinderRandomStrollInVillage p = (PathfinderRandomStrollInVillage) b;
                yield new PathfinderGoalStrollVillageGolem((EntityCreature) m, p.getSpeedModifier());
            }
            case "Swell" -> new PathfinderGoalSwell((EntityCreeper) m);
            case "Tame" -> {
                PathfinderTameHorse p = (PathfinderTameHorse) b;
                yield new PathfinderGoalTame((EntityHorseAbstract) m, p.getSpeedModifier());
            }
            case "Tempt" -> {
                PathfinderTempt p = (PathfinderTempt) b;
                yield new PathfinderGoalTempt((EntityCreature) m, p.getSpeedModifier(), RecipeItemStack.a(p.getItems().stream().map(CraftItemStack::asNMSCopy)), true);
            }
            case "TradeWithPlayer" -> {
                PathfinderTradePlayer p = (PathfinderTradePlayer) b;
                yield new PathfinderGoalTradeWithPlayer((EntityVillagerAbstract) m);
            }
            case "UseItem" -> {
                PathfinderUseItem p = (PathfinderUseItem) b;
                yield new PathfinderGoalUseItem<>(m, toNMS(p.getItem()), toNMS(p.getFinishSound()), e -> p.getCondition().test(fromNMS(e)));
            }
            case "Water" -> new PathfinderGoalWater((EntityCreature) m);
            case "WaterJump" -> {
                PathfinderDolphinJump p = (PathfinderDolphinJump) b;
                yield new PathfinderGoalWaterJump((EntityDolphin) m, p.getInterval());
            }
            case "ZombieAttack" -> {
                PathfinderZombieAttack p = (PathfinderZombieAttack) b;
                yield new PathfinderGoalZombieAttack((EntityZombie) m, p.getSpeedModifier(), p.mustSee());
            }
            case "UniversalAngerReset" -> {
                PathfinderResetAnger p = (PathfinderResetAnger) b;
                yield new PathfinderGoalUniversalAngerReset<>((EntityInsentient & IEntityAngerable) m, p.isAlertingOthers());
            }

            // Target

            case "DefendVillage" -> new PathfinderGoalDefendVillage((EntityIronGolem) m);
            case "HurtByTarget" -> {
                PathfinderHurtByTarget p = (PathfinderHurtByTarget) b;
                List<Class<? extends EntityLiving>> classes = new ArrayList<>();
                p.getIgnoring().stream().map(EntityType::getEntityClass).forEach(c -> classes.add(toNMS(c.asSubclass(LivingEntity.class))));

                yield new PathfinderGoalHurtByTarget((EntityCreature) m, classes.toArray(new Class[0]));
            }
            case "NearestAttackableTarget" -> {
                PathfinderNearestAttackableTarget p = (PathfinderNearestAttackableTarget) b;
                yield new PathfinderGoalNearestAttackableTarget<>(m, toNMS(p.getFilter()), p.getInterval(), p.mustSee(), p.mustReach(), t -> p.getCondition().test(fromNMS(t)));
            }
            case "NearestAttackableTargetWitch" -> {
                PathfinderNearestAttackableTargetRaider p = (PathfinderNearestAttackableTargetRaider) b;
                yield new PathfinderGoalNearestAttackableTargetWitch<>((EntityRaider) m, toNMS(p.getFilter()), p.getInterval(), p.mustSee(), p.mustReach(), l -> p.getCondition().test(fromNMS(l)));
            }
            case "NearestHealableRaider" -> {
                PathfinderNearestHealableRaider p = (PathfinderNearestHealableRaider) b;
                yield new PathfinderGoalNearestHealableRaider<>((EntityRaider) m, toNMS(p.getFilter()), p.mustSee(), l -> p.getCondition().test(fromNMS(l)));
            }
            case "OwnerHurtByTarget" -> new PathfinderGoalOwnerHurtByTarget((EntityTameableAnimal) m);
            case "OwnerHurtTarget" -> new PathfinderGoalOwnerHurtTarget((EntityTameableAnimal) m);
            case "RandomTargetNonTamed" -> {
                PathfinderWildTarget p = (PathfinderWildTarget) b;
                yield new PathfinderGoalRandomTargetNonTamed<>((EntityTameableAnimal) m, toNMS(p.getFilter()), p.mustSee(), l -> p.getCondition().test(fromNMS(l)));
            }
            
            default -> {
                if (b instanceof CustomPathfinder p) yield custom(p);
                else yield null;
            }
        };
    }

    @Override
    public void addPathfinder(Pathfinder b, int priority, boolean target) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.bP : m.bO;

        String name = b.getInternalName().startsWith("PathfinderGoal") ? b.getInternalName().replace("PathfinderGoal", "") : b.getInternalName();

        final PathfinderGoal g = toNMS(b);

        if (g == null) return;
        s.a(priority, g);
    }

    @Override
    public void removePathfinder(Pathfinder b, boolean target) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.bP : m.bO;

        final PathfinderGoal g = toNMS(b);
        if (g == null) return;
        s.a(g);
    }

    @Override
    public void clearPathfinders(Mob mob, boolean target) {
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.bP : m.bO;
        s.a();
    }

    private static BehaviorResult.Status fromNMS(Behavior.Status status) {
        if (status == Behavior.Status.a) return BehaviorResult.Status.STOPPED;
        return BehaviorResult.Status.RUNNING;
    }

    private static final class BehaviorResult1_17_R1 extends BehaviorResult {
        private final Behavior<? super EntityLiving> b;
        private final EntityInsentient mob;
        private final WorldServer l;

        private BehaviorResult1_17_R1(Behavior<? super EntityLiving> b, EntityInsentient mob) {
            this.b = b;
            this.mob = mob;
            this.l = toNMS(Bukkit.getWorld(mob.t.getWorld().getUID()));

            b.e(l, mob, 0);
        }

        @Override
        public @NotNull Status getStatus() {
            return fromNMS(b.a());
        }

        @Override
        public void stop() {
            b.g(l, mob, 0);
        }
    }

    private static LivingEntity fromNMS(EntityLiving l) {
        return (LivingEntity) l.getBukkitEntity();
    }

    @Override
    public BehaviorResult runBehavior(Mob m, String behaviorName, Object... args) {
        return runBehavior(m, behaviorName, Behavior.class.getPackage().getName(), args);
    }

    @Override
    public BehaviorResult runBehavior(Mob m, String behaviorName, String packageName, Object... args) {
        EntityInsentient nms = toNMS(m);
        String packageN = packageName.replace("{V}", "v1_17_R1");

        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if (o instanceof Villager.Profession) args[i] = toNMS((Villager.Profession) o);
            if (o instanceof Memory<?>) args[i] = toNMS((Memory<?>) o);

            if (o instanceof Predicate) args[i] = (Predicate) obj -> {
                if (obj instanceof EntityInsentient) return ((Predicate<Mob>) o).test(fromNMS((EntityInsentient) obj));

                return ((Predicate) o).test(obj);
            };

            if (o instanceof Function) args[i] = (Function) obj -> {
                if (obj instanceof EntityLiving) return ((Function<LivingEntity, ?>) o).apply(fromNMS((EntityLiving) obj));

                return ((Function) o).apply(obj);
            };
        }

        try {
            Class<?> bClass = Class.forName(packageName + "." + behaviorName);
            Constructor<?> c = bClass.getConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
            Behavior<? super EntityLiving> b = (Behavior<? super EntityLiving>) c.newInstance(args);
            return new BehaviorResult1_17_R1(b, nms);
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
            return null;
        }
    }

    private static EntityPlayer toNMS(Player p) { return ((CraftPlayer) p).getHandle(); }

    private static final class EntityController1_17_R1 implements EntityController {

        private final ControllerJump jumpC;
        private final ControllerMove moveC;
        private final ControllerLook lookC;

        private final Mob m;

        private final EntityInsentient nms;

        public EntityController1_17_R1(Mob m) {
            EntityInsentient nms = toNMS(m);
            this.lookC = nms.getControllerLook();
            this.moveC = nms.getControllerMove();
            this.jumpC = nms.getControllerJump();
            this.m = m;
            this.nms = nms;
        }

        @Override
        public EntityController jump() {
            jumpC.jump();
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
            nms.getNavigation().c();
            return this;
        }

        @Override
        public EntityController strafe(float fwd, float right) {
            moveC.a(fwd, right);
            moveC.a();
            nms.getNavigation().a(moveC.d(), moveC.e(), moveC.f(), moveC.c());
            nms.getNavigation().c();
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
            lookC.a(x, y, z);
            lookC.a();
            return this;
        }

    }

    private static final class NavigationPath1_17_R1 implements NavigationPath {
        private String name;
        private final Mob m;
        private final PathEntity handle;

        NavigationPath1_17_R1(@NotNull PathEntity nms, @NotNull Mob m) {
            this.m = m;
            this.name = "bukkitpath";
            this.handle = nms;
        }

        private final List<Position> nodes = new ArrayList<>();

        /**
         * Advances this path.
         */
        @Override
        public void advance() {
            this.handle.a();
            PathPoint n = handle.h();
            new EntityController1_17_R1(m).moveTo(n.a, n.b, n.c);
        }

        /**
         * Get this Path's Name.
         * @return this path's name
         */
        public String getName() {
            return this.name;
        }

        /**
         * Sets this Path's Name.
         * @param name this path's new name
         */
        public void setName(@NotNull String name) {
            this.name = name;
        }

        /**
         * Whether this NavigationPath is complete.
         * @return true if complete, else false
         */
        @Override
        public boolean isDone() {
            return this.handle.c();
        }

        /**
         * Get the size of this NavigationPath.
         * @return size
         */
        public int size() {
            return nodes.size();
        }

        /**
         * Whether this NavigationPath is empty.
         * @return true if empty, else false
         */
        @Override
        public boolean isEmpty() {
            return nodes.isEmpty();
        }

        /**
         * Whether this Path contains this Navigation Node.
         * @param o Position
         * @return true if contains, else false
         */
        @Override
        public boolean contains(@Nullable Position o) {
            return nodes.contains(o);
        }

        @Override
        @NotNull
        public Iterator<Position> iterator() {
            return nodes.iterator();
        }

        /**
         * Converts this NavigationPath into an Array of Nodes.
         * @return Array of Position
         */
        @NotNull
        @Override
        public Position[] toArray() {
            return nodes.toArray(new Position[0]);
        }

        /**
         * Returns the index of this Navigation Node.
         * @param o Position to fetch
         * @return Index found
         * @see List#indexOf(Object)
         */
        @Override
        public int indexOf(@Nullable Position o) {
            return nodes.indexOf(o);
        }

        /**
         * Returns the last index of this Navigation Node.
         * @param o Position to fetch
         * @return Index found
         * @see List#lastIndexOf(Object)
         */
        @Override
        public int lastIndexOf(@Nullable Position o) {
            return nodes.lastIndexOf(o);
        }
    }

    private static final class EntityNavigation1_17_R1 implements EntityNavigation {

        private final NavigationAbstract handle;

        private int speedMod;
        private int range;
        private final List<Position> points;
        private BlockPosition finalPos;

        private final Mob m;

        EntityNavigation1_17_R1(Mob m) {
            this.handle = toNMS(m).getNavigation();
            this.points = new ArrayList<>();

            this.speedMod = 1;
            this.range = Integer.MAX_VALUE;
            this.m = m;
        }

        @Override
        public double getSpeedModifier() {
            return this.speedMod;
        }

        @Override
        public void setSpeedModifier(double mod) throws IllegalArgumentException {
            if (mod > Integer.MAX_VALUE) throw new IllegalArgumentException("Must be integer");
            this.speedMod = (int) Math.floor(mod);
        }

        @Override
        public EntityNavigation recompute() {
            this.handle.j();
            return this;
        }

        @Override
        public EntityNavigation addPoint(@NotNull Position point) {
            this.points.add(point);
            return this;
        }

        @Override
        public EntityNavigation addPoint(int index, @NotNull Position point) {
            this.points.add(index, point);
            return this;
        }

        @Override
        public EntityNavigation removePoint(@NotNull Position point) {
            this.points.remove(point);
            return this;
        }

        @Override
        public EntityNavigation removePoint(int index) {
            this.points.remove(index);
            return this;
        }

        @Override
        @NotNull
        public NavigationPath buildPath() {
            return new NavigationPath1_17_R1(handle.a(finalPos, range, speedMod), m);
        }

        @Override
        public EntityNavigation setFinalPoint(@NotNull Position node) {
            this.finalPos = new BlockPosition(node.getX(), node.getY(), node.getZ());
            return this;
        }

        @Override
        public EntityNavigation setRange(int range) {
            this.range = range;
            return this;
        }
    }

    private static final class EntityBody1_17_R1 implements EntityBody {
        private final EntityInsentient nmsMob;

        EntityBody1_17_R1(Mob nmsMob) {
            this.nmsMob = toNMS(nmsMob);
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

            return switch (nmsMob.a(toNMS(p), h)) {
                case a -> InteractionResult.SUCCESS;
                case b -> InteractionResult.CONSUME;
                case c -> InteractionResult.CONSUME_PARTIAL;
                case e -> InteractionResult.FAIL;
                default -> InteractionResult.PASS;
            };
        }

        @Override
        public boolean isSensitiveToWater() {
            return nmsMob.ew();
        }

        @Override
        public boolean isAffectedByPotions() {
            return nmsMob.eP();
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
            return nmsMob.c((EntityHuman) toNMS(p));
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
            nmsMob.aX = rotation > 360 ? (rotation - (float) (360 * Math.floor(rotation / 360))) : rotation;
        }

        @Override
        public float getHeadRotation() {
            return nmsMob.aZ;
        }

        @Override
        public void setHeadRotation(float rotation) {
            nmsMob.aZ = rotation > 360 ? (rotation - (float) (360 * Math.floor(rotation / 360))) : rotation;
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
                    for (Player p : fromNMS(nmsMob).getWorld().getPlayers()) toNMS(p).b.sendPacket(pkt);
                }
                case MAGICAL_CRITICAL_DAMAGE -> {
                    PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 5);
                    for (Player p : fromNMS(nmsMob).getWorld().getPlayers()) toNMS(p).b.sendPacket(pkt);
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
    }

    @Override
    public Attribute getDefaultAttribute(String s) {
        return new Attribute1_17_R1((AttributeRanged) IRegistry.al.get(new MinecraftKey(s)));
    }

    private static net.minecraft.world.entity.schedule.Activity toNMS(Activity a) {
        return IRegistry.au.get(new MinecraftKey(a.getKey().getKey()));
    }

    private static Activity fromNMS(net.minecraft.world.entity.schedule.Activity a) {
        MinecraftKey key = IRegistry.au.getKey(a);
        return Activity.getByKey(NamespacedKey.minecraft(key.getKey()));
    }

    private static Schedule fromNMS(net.minecraft.world.entity.schedule.Schedule s) {
        Schedule.Builder b = Schedule.builder();
        for (int i = 0; i < 24000; i++) {
            if (s.a(i) == null) continue;
            Activity a = fromNMS(s.a(i));
            b.addActivity(i, a);
        }

        return b.build();
    }

    private static net.minecraft.world.entity.schedule.Schedule toNMS(Schedule s) {
        net.minecraft.world.entity.schedule.ScheduleBuilder b = new ScheduleBuilder(new net.minecraft.world.entity.schedule.Schedule());
        for (int i = 0; i < 24000; i++) {
            if (!s.contains(i)) continue;
            net.minecraft.world.entity.schedule.Activity a = toNMS(s.get(i));
            b.a(i, a);
        }

        return b.a();
    }

    private static <T extends EntityLiving> Behavior<T> toNMS(Consumer<Mob> en) {
        return new Behavior<>(Collections.emptyMap()) {
            @Override
            protected void d(WorldServer var0, T m, long var2) {
                if (!(m instanceof EntityInsentient)) return;
                en.accept(fromNMS((EntityInsentient) m));
            }
        };
    }

    @Override
    public Schedule getDefaultSchedule(String key) {
        return fromNMS(IRegistry.at.get(new MinecraftKey(key)));
    }

    @SuppressWarnings("deprecation")
    private static final class EntityScheduleManager1_17_R1 implements EntityScheduleManager {

        private final EntityInsentient nmsMob;
        private final Mob m;

        EntityScheduleManager1_17_R1(Mob m) {
            this.m = m;
            this.nmsMob = toNMS(m);
        }


        @Override
        public @Nullable Schedule getCurrentSchedule() {
            return fromNMS(nmsMob.getBehaviorController().getSchedule());
        }

        @Override
        public void setSchedule(@NotNull Schedule s) {
            nmsMob.getBehaviorController().setSchedule(toNMS(s));
        }

        @Override
        public @NotNull Set<Activity> getActiveActivities() {
            return nmsMob.getBehaviorController().c().stream().map(ChipUtil1_17_R1::fromNMS).collect(Collectors.toSet());
        }

        @Override
        public void setDefaultActivity(@NotNull Activity a) {
            nmsMob.getBehaviorController().b(toNMS(a));
        }

        @Override
        public void useDefaultActivity() {
            nmsMob.getBehaviorController().e();
        }

        @Override
        public void setRunningActivity(@NotNull Activity a) {
            nmsMob.getBehaviorController().a(toNMS(a));
        }

        @Override
        public @Nullable Activity getRunningActivity() {
            return nmsMob.getBehaviorController().f().isPresent() ? fromNMS(nmsMob.getBehaviorController().f().get()) : null;
        }

        @Override
        public boolean isRunning(@NotNull Activity a) {
            return nmsMob.getBehaviorController().c(toNMS(a));
        }

        @Override
        public int size() {
            return nmsMob.getBehaviorController().d().size();
        }

        @Override
        public boolean isEmpty() {
            return nmsMob.getBehaviorController().d().isEmpty();
        }

        @Nullable
        @Override
        public Consumer<Mob> put(@NotNull Activity key, Consumer<Mob> value) {
            nmsMob.getBehaviorController().a(toNMS(key), 0, ImmutableList.of(toNMS(value)));
            return value;
        }

        @Override
        public void clear() {
            nmsMob.getBehaviorController().g();
        }

    }

    @Override
    public EntityScheduleManager getManager(Mob m) { return new EntityScheduleManager1_17_R1(m); }

    @Override
    public EntityController getController(Mob m) {
        return new EntityController1_17_R1(m);
    }

    @Override
    public EntityNavigation getNavigation(Mob m) {
        return new EntityNavigation1_17_R1(m);
    }

    @Override
    public EntityBody getBody(Mob m) {
        return new EntityBody1_17_R1(m);
    }

    private static DamageSource toNMS(EntityDamageEvent.DamageCause c) {
        return switch (c) {
            case FIRE -> DamageSource.a;
            case LIGHTNING -> DamageSource.b;
            case FIRE_TICK -> DamageSource.c;
            case SUFFOCATION -> DamageSource.f;
            case LAVA -> DamageSource.d;
            case HOT_FLOOR -> DamageSource.e;
            case CRAMMING -> DamageSource.g;
            case DROWNING -> DamageSource.h;
            case STARVATION -> DamageSource.i;
            case CONTACT -> DamageSource.j;
            case MAGIC -> DamageSource.o;
            case FALL -> DamageSource.k;
            case FLY_INTO_WALL -> DamageSource.l;
            case VOID -> DamageSource.m;
            case WITHER -> DamageSource.p;
            case FALLING_BLOCK -> DamageSource.r;
            case DRAGON_BREATH -> DamageSource.s;
            case FREEZE -> DamageSource.v;
            case DRYOUT -> DamageSource.t;
            default -> DamageSource.n;
        };
    }

    private static AbstractDragonController toNMS(CustomPhase c) {
        return new AbstractDragonController(toNMS(c.getDragon())) {
            @Override
            public DragonControllerPhase<? extends IDragonController> getControllerPhase() {
                try {
                    Method create = DragonControllerPhase.class.getDeclaredMethod("a");
                    create.setAccessible(true);
                    return (DragonControllerPhase<? extends IDragonController>) create.invoke(null, this.getClass(), c.getKey().getKey());
                } catch (Exception ignored) {}
                return DragonControllerPhase.k;
            }

            public void d() { c.start(); }
            public void e() { c.stop(); }
            public boolean a() { return c.isSitting(); }
            public void b() { c.clientTick(); }
            public void c() { c.serverTick(); }
            public void a(EntityEnderCrystal crystal, BlockPosition pos, DamageSource s, EntityHuman p) {
                EnderCrystal bCrystal = (EnderCrystal) crystal.getBukkitEntity();
                c.onCrystalDestroyed(bCrystal, fromNMS(s), p == null ? null : Bukkit.getPlayer(p.getUniqueID()));
            }
            public Vec3D g() {
                Location l = c.getTargetLocation();
                return new Vec3D(l.getX(), l.getY(), l.getZ());
            }
            public float f() { return c.getFlyingSpeed(); }
            public float onHurt(DamageSource s, float damage) { return c.onDamage(fromNMS(s), damage); }
        };
    }

    @Override
    public void setCustomPhase(EnderDragon a, CustomPhase c) {
        EntityEnderDragon nmsMob = toNMS(a);
        AbstractDragonController nmsPhase = toNMS(c);
        try {
            new DragonControllerManager(nmsMob).setControllerPhase(nmsPhase.getControllerPhase());
        } catch (IndexOutOfBoundsException ignored) {}
    }

    private static EntityItem toNMS(org.bukkit.entity.Item i) {
        return (EntityItem) ((CraftItem) i).getHandle();
    }

    private static EntityLiving toNMS(LivingEntity en) {
        return ((CraftLivingEntity) en).getHandle();
    }

    private static Object toNMS(String key, Object value) {
        final Object nmsValue;

        if (value instanceof Location l) {
            if (key.equals("nearest_bed") || key.equals("celebrate_location") || key.equals("nearest_repellent")) nmsValue = new BlockPosition(l.getX(), l.getY(), l.getZ());
            else nmsValue = GlobalPos.create(toNMS(l.getWorld()).getDimensionKey(), new BlockPosition(l.getX(), l.getY(), l.getZ()));
        }
        else if (value instanceof Location[] ls) {
            List<GlobalPos> p = new ArrayList<>();

            for (Location l : ls) {
                p.add(GlobalPos.create(toNMS(l.getWorld()).getDimensionKey(), new BlockPosition(l.getX(), l.getY(), l.getZ())));
            }

            nmsValue = p;
        }
        else if (value instanceof Player p) {
            if (key.equals("liked_player")) nmsValue = p.getUniqueId();
            else nmsValue = toNMS(p);
        }
        else if (value instanceof Memory.WalkingTarget t) nmsValue = new MemoryTarget(toNMS(t.getLocation()), (float) t.getSpeedModifier(), t.getDistance());
        else if (value instanceof LivingEntity l) nmsValue = toNMS(l);
        else if (value instanceof Entity e) {
            if (key.equals("angry_at")) nmsValue = e.getUniqueId();
            else nmsValue = toNMS(e);
        }
        else if (value instanceof org.bukkit.block.Block[] b) {
            final Collection<GlobalPos> s;
            if (key.equals("doors_to_close")) s = new HashSet<>();
            else s = new ArrayList<>();

            for (org.bukkit.block.Block bl : b) {
                Location l = bl.getLocation();
                s.add(GlobalPos.create(toNMS(l.getWorld()).getDimensionKey(), new BlockPosition(l.getX(), l.getY(), l.getZ())));
            }
            nmsValue = s;
        }
        else if (value instanceof Villager[] vs) {
            List<EntityLiving> s = new ArrayList<>();
            for (Villager v : vs) s.add(toNMS(v));
            nmsValue = s;
        }
        else if (value instanceof Player[] ps) {
            List<EntityHuman> s = new ArrayList<>();
            for (Player p : ps) s.add(toNMS(p));
            nmsValue = s;
        }
        else if (value instanceof LivingEntity[] ls) {
            List<EntityLiving> s = new ArrayList<>();
            for (LivingEntity l : ls) s.add(toNMS(l));
            nmsValue = s;
        }
        else if (value instanceof EntityDamageEvent.DamageCause c) nmsValue = toNMS(c);
        else nmsValue = value;

        return nmsValue;
    }

    private static Object fromNMS(Mob m, String key, Object nmsValue) {
        Object value = nmsValue;

        if (nmsValue instanceof GlobalPos l) {
            BlockPosition pos = l.getBlockPosition();
            World w = ((CraftServer) Bukkit.getServer()).getHandle().getServer().l.d(IRegistry.Q).a(l.getDimensionManager()).getWorld();
            value = new Location(w, pos.getX(), pos.getY(), pos.getZ());
        }
        else if (nmsValue instanceof List<?> li) {
            switch (key) {
                case "secondary_job_site", "interactable_doors" -> {
                    List<Location> l = new ArrayList<>();
                    li.forEach(o -> l.add((Location) fromNMS(m, key, o)));
                    value = l.toArray(new Location[0]);
                }
                case "nearest_players" -> {
                    List<Player> l = new ArrayList<>();
                    li.forEach(o -> l.add(Bukkit.getPlayer(((EntityHuman) o).getUniqueID())));
                    value = l.toArray(new Player[0]);
                }
                case "visible_villager_babies" -> {
                    List<Villager> l = new ArrayList<>();
                    li.forEach(o -> l.add((Villager) fromNMS((EntityLiving) o)));
                    value = l.toArray(new Villager[0]);
                }
                case "mobs" -> {
                    List<LivingEntity> vl = new ArrayList<>();
                    li.forEach(o -> vl.add(fromNMS((EntityLiving) o)));
                    value = vl.toArray(new LivingEntity[0]);
                }
            }
        }
        else if (nmsValue instanceof EntityHuman p) value = Bukkit.getPlayer(p.getUniqueID());
        else if (nmsValue instanceof MemoryTarget t) {
            BlockPosition p = t.a().b();
            value = new Memory.WalkingTarget(new Location(m.getWorld(), p.getX(), p.getY(), p.getZ()), t.b(), t.c());
        }
        else if (nmsValue instanceof EntityLiving l) value = Bukkit.getEntity(l.getUniqueID());
        else if (nmsValue instanceof Set<?> s) {
            if (key.equals("doors_to_close")) {
                List<org.bukkit.block.Block> l = new ArrayList<>();
                s.forEach(o -> l.add((org.bukkit.block.Block) fromNMS(m, key, o)));
                value = l.toArray(new org.bukkit.block.Block[0]);
            }
        }
        else if (value instanceof DamageSource c) value = fromNMS(c);
        else value = nmsValue;

        return value;
    }

    private static EntityDamageEvent.DamageCause fromNMS(DamageSource c) {
        return switch (c.y) {
            case "inFire" -> FIRE;
            case "lightningBolt" -> LIGHTNING;
            case "onFire" -> FIRE_TICK;
            case "lava" -> LAVA;
            case "hotFloor" -> HOT_FLOOR;
            case "inWall" -> SUFFOCATION;
            case "cramming" -> CRAMMING;
            case "drown" -> DROWNING;
            case "starve" -> STARVATION;
            case "cactus", "sweetBerryBush", "stalagmite" -> CONTACT;
            case "fall" -> FALL;
            case "flyIntoWall" -> FLY_INTO_WALL;
            case "outOfWorld" -> VOID;
            case "magic" -> MAGIC;
            case "wither" -> WITHER;
            case "anvil", "fallingBlock", "fallingStalactite" -> FALLING_BLOCK;
            case "dragonBreath" -> DRAGON_BREATH;
            case "dryout" -> DRYOUT;
            case "freeze" -> FREEZE;
            default -> CUSTOM;
        };
    }


    @Override
    public <T> void setMemory(Mob mob, Memory<T> m, T value) {
        if (value == null) {
            removeMemory(mob, m);
            return;
        }

        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = toNMS(m);
        String key = IRegistry.ar.getKey(type).getKey();
        Object nmsValue = toNMS(key, value);

        nms.getBehaviorController().setMemory(type, nmsValue);
    }

    @Override
    public <T> void setMemory(Mob mob, Memory<T> m, T value, long durationTicks) {
        if (value == null) {
            removeMemory(mob, m);
            return;
        }

        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = toNMS(m);
        String key = IRegistry.ar.getKey(type).getKey();
        Object nmsValue = toNMS(key, value);

        nms.getBehaviorController().a(type, nmsValue, durationTicks);
    }

    @Override
    public <T> T getMemory(Mob mob, Memory<T> m) {
        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = toNMS(m);
        String key = IRegistry.ar.getKey(type).getKey();

        return m.getBukkitClass().cast(fromNMS(mob, key, nms.getBehaviorController().getMemory(type)));
    }

    @Override
    public long getExpiry(Mob mob, Memory<?> m) {
        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = toNMS(m);

        return nms.getBehaviorController().d(type);
    }

    @Override
    public boolean contains(Mob mob, Memory<?> m) {
        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = toNMS(m);

        return nms.getBehaviorController().hasMemory(type);
    }

    @Override
    public void removeMemory(Mob mob, Memory<?> m) {
        EntityInsentient nms = toNMS(mob);
        MemoryModuleType<?> type = toNMS(m);
        nms.getBehaviorController().removeMemory(type);
    }

    @Override
    public boolean isRestricted(Mob m) {
        EntityInsentient nms = toNMS(m);
        return nms.fg();
    }

    @Override
    public void clearRestriction(Mob m) {
        EntityInsentient nms = toNMS(m);
        nms.fj();
    }

    @Override
    public void restrictTo(Mob m, double x, double y, double z, int radius) {
        EntityInsentient nms = toNMS(m);
        nms.a(new BlockPosition(x, y, z), radius);
    }

    @Override
    public Location getRestriction(Mob m) {
        EntityInsentient nms = toNMS(m);
        BlockPosition c = nms.fh();
        return new Location(m.getWorld(), c.getX(), c.getY(), c.getZ());
    }

    @Override
    public int getRestrictionRadius(Mob m) {
        EntityInsentient nms = toNMS(m);
        return ((int) nms.fi()) < 0 ? Integer.MAX_VALUE : (int) nms.fi();
    }

    @Override
    public boolean hasRestriction(Mob m) {
        EntityInsentient nms = toNMS(m);
        return nms.fk();
    }

    @Override
    public boolean canSee(Mob m, Entity en) {
        EntityInsentient nms = toNMS(m);
        return nms.getEntitySenses().a(toNMS(en));
    }

    private static net.minecraft.world.entity.Entity toNMS(Entity en) {
        return ((CraftEntity) en).getHandle();
    }

    private static VillagerProfession toNMS(Villager.Profession p) {
        return switch (p) {
            case FARMER -> VillagerProfession.f;
            case FISHERMAN -> VillagerProfession.g;
            case LIBRARIAN -> VillagerProfession.j;
            case WEAPONSMITH -> VillagerProfession.o;
            case TOOLSMITH -> VillagerProfession.n;
            case BUTCHER -> VillagerProfession.c;
            case FLETCHER -> VillagerProfession.h;
            case MASON -> VillagerProfession.k;
            case CLERIC -> VillagerProfession.e;
            case ARMORER -> VillagerProfession.b;
            case NITWIT -> VillagerProfession.l;
            case SHEPHERD -> VillagerProfession.m;
            case CARTOGRAPHER -> VillagerProfession.d;
            case LEATHERWORKER -> VillagerProfession.i;
            default -> VillagerProfession.a;
        };
    }

    private static <T extends Entity> Class<? extends T> fromNMS(Class<? extends net.minecraft.world.entity.Entity> clazz, Class<T> cast) {
        try {
            String name = clazz.getSimpleName();
            if (name.contains("Entity")) name = name.replace("Entity", "");

            Class<? extends Entity> bukkit = switch (name) {
                case "" -> Entity.class;
                case "Living" -> LivingEntity.class;
                case "Lightning" -> LightningStrike.class;
                case "Insentient" -> Mob.class;
                case "TameableAnimal" -> Tameable.class;

                case "Animal" -> Animals.class;
                case "FishSchool" -> Fish.class;
                case "HorseAbstract" -> AbstractHorse.class;
                case "HorseMule" -> Mule.class;
                case "HorseSkeleton" -> SkeletonHorse.class;
                case "HorseZombie" -> ZombieHorse.class;
                case "HorseDonkey" -> Donkey.class;
                case "WaterAnimal" -> WaterMob.class;

                case "GiantZombie" -> Giant.class;
                case "GuardianElder" -> ElderGuardian.class;
                case "IllagerIllusioner" -> Illusioner.class;
                case "SkeletonAbstract" -> AbstractSkeleton.class;
                case "SkeletonStray" -> Stray.class;
                case "SkeletonWither" -> WitherSkeleton.class;
                case "ZombieHusk" -> Husk.class;
                case "ZombieVillager" -> ZombieVillager.class;

                case "Villager" -> Villager.class;
                case "VillagerAbstract" -> AbstractVillager.class;
                case "VillagerTrader" -> WanderingTrader.class;

                case "Human" -> HumanEntity.class;
                case "Player" -> Player.class;

                case "FireballFireball" -> SizedFireball.class;
                case "Fireworks" -> Firework.class;
                case "FishingHook" -> FishHook.class;
                case "Potion" -> ThrownPotion.class;
                case "ProjectileThrowable" -> ThrowableProjectile.class;
                case "ThrownTrident" -> Trident.class;

                case "MinecartAbstract" -> Minecart.class;
                case "MinecartChest" -> StorageMinecart.class;
                case "MinecartCommandBlock" -> CommandMinecart.class;
                case "MinecartFurnace" -> PoweredMinecart.class;
                case "MinecartHopper" -> HopperMinecart.class;
                case "MinecartMobSpawner" -> SpawnerMinecart.class;
                case "MinecartTNT" -> ExplosiveMinecart.class;

                default -> Class.forName("org.bukkit.entity." + name).asSubclass(Entity.class);
            };

            return bukkit.asSubclass(cast);
        } catch (ClassNotFoundException e) {
            return cast;
        }
    }

    private static EntityInsentient toNMS(Mob m) { return ((CraftMob) m).getHandle(); }

    private static EntityType[] getEntityTypes(Class<?>... nms) {
        List<EntityType> types = new ArrayList<>();
        for (Class<?> c : nms) {

            Class<? extends Entity> bukkit = fromNMS((Class<? extends net.minecraft.world.entity.Entity>) c, Entity.class);
            for (EntityType t : EntityType.values()) if (t.getEntityClass().isAssignableFrom(bukkit)) types.add(t);
        }
        return types.toArray(new EntityType[0]);
    }

    private static EnumDifficulty toNMS(org.bukkit.Difficulty d) {
        return switch (d) {
            case PEACEFUL -> EnumDifficulty.a;
            case EASY -> EnumDifficulty.b;
            case NORMAL -> EnumDifficulty.c;
            case HARD -> EnumDifficulty.d;
        };
    }

    private static org.bukkit.Difficulty fromNMS(EnumDifficulty d) {
        return switch (d) {
            case a -> org.bukkit.Difficulty.PEACEFUL;
            case b -> org.bukkit.Difficulty.EASY;
            case c -> org.bukkit.Difficulty.NORMAL;
            case d -> org.bukkit.Difficulty.HARD;
        };
    }

    private static EntityCreature toNMS(Creature c) { return ((CraftCreature) c).getHandle();}

    private static PathfinderGoal.Type toNMS(Pathfinder.PathfinderFlag f) {
        return switch (f) {
            case MOVEMENT -> PathfinderGoal.Type.a;
            case JUMPING -> PathfinderGoal.Type.c;
            case TARGETING -> PathfinderGoal.Type.d;
            case LOOKING -> PathfinderGoal.Type.b;
        };
    }

    private static Pathfinder.PathfinderFlag fromNMS(PathfinderGoal.Type f) {
        return switch (f) {
            case a -> Pathfinder.PathfinderFlag.MOVEMENT;
            case c -> Pathfinder.PathfinderFlag.JUMPING;
            case d -> Pathfinder.PathfinderFlag.TARGETING;
            case b -> Pathfinder.PathfinderFlag.LOOKING;
        };
    }

    private static float getFloat(PathfinderGoal o, String name) { return getObject(o, name, Float.class); }

    private static double getDouble(PathfinderGoal o, String name) {
        return getObject(o, name, Double.class);
    }

    private static boolean getBoolean(PathfinderGoal o, String name) {
        return getObject(o, name, Boolean.class);
    }

    private static int getInt(PathfinderGoal o, String name) {
        return getObject(o, name, Integer.class);
    }

    private static <T> T getObject(PathfinderGoal o, String name, Class<T> cast) {
        try {
            Class<? extends PathfinderGoal> clazz = o.getClass();

            while (clazz.getSuperclass() != null) {
                try {
                    Field f = clazz.getDeclaredField(name);
                    f.setAccessible(true);
                    return cast.cast(f.get(o));
                } catch (NoSuchFieldException e) {
                    if (PathfinderGoal.class.isAssignableFrom(clazz.getSuperclass())) clazz = (Class<? extends PathfinderGoal>) clazz.getSuperclass();
                    else break;
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
        }

        return null;
    }

    private static Mob fromNMS(EntityInsentient m) { return (Mob) m.getBukkitEntity(); }

    private static World fromNMS(net.minecraft.world.level.World l) { return l.getWorld(); }

    private static WorldServer toNMS(World w) { return ((CraftWorld) w).getHandle(); }

    private static BlockPosition toNMS(Location l) { return new BlockPosition(l.getX(), l.getY(), l.getZ()); }

    private static List<ItemStack> fromNMS(RecipeItemStack in) { return Arrays.stream(in.c).map(CraftItemStack::asBukkitCopy).collect(Collectors.toList()); }

    private static Sound fromNMS(SoundEffect s) { return CraftSound.getBukkit(s); }

    private static Mob getEntity(PathfinderGoal g) {
        try {
            Class<? extends PathfinderGoal> clazz = g.getClass();

            while (clazz.getSuperclass() != null) {
                for (Field f : clazz.getDeclaredFields()) {
                    f.setAccessible(true);
                    if (EntityInsentient.class.isAssignableFrom(f.getType()) && Modifier.isFinal(f.getModifiers())) {
                        return fromNMS((EntityInsentient) f.get(g));
                    }
                }

                if (PathfinderGoal.class.isAssignableFrom(clazz.getSuperclass())) clazz = (Class<? extends PathfinderGoal>) clazz.getSuperclass();
                else break;
            }

            return null;
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
            return null;
        }
    }

    private static Object invoke(PathfinderGoal g, String method, Object... args) {
        try {
            Method m = g.getClass().getDeclaredMethod(method);
            m.setAccessible(true);

            return m.invoke(g, args);
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
            return null;
        }
    }

    private static CustomPathfinder custom(PathfinderGoal g) {
        return new CustomPathfinder(getEntity(g)) {
            @Override
            public @NotNull PathfinderFlag[] getFlags() {
                PathfinderFlag[] flags = new PathfinderFlag[g.i().size()];
                int i = 0;
                for (PathfinderGoal.Type f : g.i()) {
                    flags[i] = fromNMS(f);
                    i++;
                }
                return flags;
            }

            @Override
            public boolean canStart() {
                return g.a();
            }

            @Override
            public void start() {
                g.c();
            }

            @Override
            public void tick() {
                g.e();
            }

            @Override
            public boolean canInterrupt() { return g.C_(); }

            @Override
            public void stop() { g.d(); }

            @Override
            public String getInternalName() {
                return g.getClass().getSimpleName();
            }
        };
    }

    private static PathfinderGoal custom(CustomPathfinder p) {
        PathfinderGoal g = new PathfinderGoal() {
            @Override
            public boolean a() {
                return p.canStart();
            }
            @Override
            public boolean b() {
                return p.canContinueToUse();
            }
            @Override
            public boolean C_() {
                return p.canInterrupt();
            }

            @Override
            public void c() {
                p.start();
            }

            @Override
            public void e() {
                p.tick();
            }

            @Override
            public void d() { p.stop(); }

        };
        EnumSet<PathfinderGoal.Type> flags = EnumSet.noneOf(PathfinderGoal.Type.class);
        Arrays.stream(p.getFlags()).map(ChipUtil1_17_R1::toNMS).forEach(flags::add);
        g.a(flags);
        return g;
    }

    private static BlockPosition getPosWithBlock(net.minecraft.world.level.block.Block block, BlockPosition bp, IBlockAccess g) {
        if (g.getType(bp).a(block)) return bp;
        else {
            BlockPosition[] bp1 = new BlockPosition[]{new BlockPosition(bp.down()),  bp.west(), bp.east(), bp.north(), bp.south(), new BlockPosition(bp.up())};
            for (BlockPosition bps : bp1) if (g.getType(bps).a(block)) return bps;
            return null;
        }
    }

    private static Location fromNMS(BlockPosition p, World w) { return new Location(w, p.getX(), p.getY(), p.getZ()); }

    private Pathfinder fromNMS(PathfinderGoal g) {
        Mob m = getEntity(g);
        String name = g.getClass().getSimpleName();

        if (name.startsWith("PathfinderGoal")) {
            name = name.replace("PathfinderGoal", "");

            return switch (name) {
                case "AvoidTarget" -> new PathfinderAvoidEntity<>((Creature) m, fromNMS(getObject(g, "f", Class.class), LivingEntity.class), getFloat(g, "c"), getDouble(g, "i"), getDouble(g, "j"));
                case "ArrowAttack" -> new PathfinderRangedAttack(m, getDouble(g, "e"), getFloat(g, "i"), getInt(g, "g"), getInt(g, "h"));
                case "Beg" -> new PathfinderBeg((Wolf) m, getFloat(g, "d"));
                case "BowShoot" -> new PathfinderRangedBowAttack(m, getDouble(g, "b"), (float) Math.sqrt(getFloat(g, "d")), getInt(g, "c"));
                case "BreakDoor" -> new PathfinderBreakDoor(m, getInt(g, "i"), d -> getObject(g, "h", Predicate.class).test(toNMS(d)));
                case "Breath" -> new PathfinderBreathAir((Creature) m);
                case "Breed" -> new PathfinderBreed((Animals) m, getDouble(g, "g"));
                case "CatSitOnBed" -> new PathfinderCatOnBed((Cat) m, getDouble(g, "b"), getInt(g, "l"));
                case "CrossbowAttack" -> new PathfinderRangedCrossbowAttack((Pillager) m, getDouble(g, "d"), (float) Math.sqrt(getFloat(g, "e")));
                case "DoorOpen" -> new PathfinderOpenDoor(m, getBoolean(g, "a"));
                case "WaterJump" -> new PathfinderDolphinJump((Dolphin) m, getInt(g, "c"));
                case "EatTile" -> new PathfinderEatTile(m);
                case "Water" -> new PathfinderFindWater((Creature) m);
                case "FleeSun" -> new PathfinderFleeSun((Creature) m, getDouble(g, "e"));
                case "Float" -> new PathfinderFloat(m);
                case "FollowBoat" -> new PathfinderFollowBoat((Creature) m);
                case "FollowEntity" -> new PathfinderFollowMob(m, getDouble(g, "d"), getFloat(g, "g"), getFloat(g, "i"));
                case "FollowOwner" -> new PathfinderFollowOwner((Tameable) m, getDouble(g, "h"), getFloat(g, "l"), getFloat(g, "k"), getBoolean(g, "n"));
                case "FollowParent" -> new PathfinderFollowParent((Animals) m, getDouble(g, "f"));
                case "HorseTrap" -> new PathfinderSkeletonTrap((SkeletonHorse) m);
                case "LeapAtTarget" -> new PathfinderLeapAtTarget(m, getFloat(g, "c"));
                case "JumpOnBlock" -> new PathfinderCatOnBlock((Cat) m, getDouble(g, "g"));
                case "LlamaFollow" -> new PathfinderLlamaFollowCaravan((Llama) m, getDouble(g, "b"));
                case "LookAtPlayer" -> new PathfinderLookAtEntity<>(m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getFloat(g, "d"), getFloat(g, "e"), getBoolean(g, "i"));
                case "LookAtTradingPlayer" -> new PathfinderLookAtTradingPlayer((AbstractVillager) m);
                case "MeleeAttack" -> new PathfinderMeleeAttack((Creature) m, getDouble(g, "b"), getBoolean(g, "c"));
                case "MoveThroughVillage" -> new PathfinderMoveThroughVillage((Creature) m, getBoolean(g, "a"), getDouble(g, "b"), getInt(g, "g"), getBoolean(g, "e"));
                case "NearestVillage" -> new PathfinderRandomStrollThroughVillage((Creature) m, getInt(g, "b"));
                case "GotoTarget" -> new PathfinderMoveToBlock((Creature) m, l -> (boolean) invoke(g, "a", toNMS(l.getWorld()), toNMS(l)), getDouble(g, "b"), getInt(g, "l"), getInt(g, "m"));
                case "Raid" -> new PathfinderMoveToRaid((Raider) m);
                case "MoveTowardsRestriction" -> new PathfinderMoveTowardsRestriction((Creature) m, getDouble(g, "e"));
                case "MoveTowardsTarget" -> new PathfinderMoveTowardsTarget((Creature) m, getDouble(g, "f"), getFloat(g, "g"));
                case "OcelotAttack" -> new PathfinderOcelotAttack((Ocelot) m);
                case "OfferFlower" -> new PathfinderOfferFlower((IronGolem) m);
                case "Panic" -> new PathfinderPanic((Creature) m, getDouble(g, "c"));
                case "Perch" -> new PathfinderRideShoulder((Parrot) m);
                case "RandomLookaround" -> new PathfinderRandomLook(m);
                case "RandomStroll" -> new PathfinderRandomStroll((Creature) m, getDouble(g, "f"), getInt(g, "g"));
                case "RandomStrollLand" -> new PathfinderRandomStrollLand((Creature) m, getDouble(g, "f"), getFloat(g, "j"));
                case "RandomSwim" -> new PathfinderRandomSwim((Creature) m, getDouble(g, "f"), getInt(g, "g"));
                case "RandomFly" -> new PathfinderRandomStrollFlying((Creature) m, getDouble(g, "f"));
                case "RemoveBlock" -> new PathfinderRemoveBlock((Creature) m, m.getWorld().getBlockAt(fromNMS(getPosWithBlock( getObject(g, "g", Block.class), toNMS(m.getLocation()), toNMS(m.getWorld())), m.getWorld())), getDouble(g, "b"));
                case "RestrictSun" -> new PathfinderRestrictSun((Creature) m);
                case "Sit" -> new PathfinderSit((Tameable) m);
                case "StrollVillage" -> new PathfinderRandomStrollToVillage((Creature) m, getDouble(g, "f"));
                case "StrollVillageGolem" -> new PathfinderRandomStrollInVillage((Creature) m, getDouble(g, "f"));
                case "Swell" -> new PathfinderSwellCreeper((Creeper) m);
                case "Tame" -> new PathfinderTameHorse((AbstractHorse) m);
                case "Tempt" -> new PathfinderTempt((Creature) m, getDouble(g, "e"), fromNMS(getObject(g, "m", RecipeItemStack.class)));
                case "TradeWithPlayer" -> new PathfinderTradePlayer((AbstractVillager) m);
                case "UniversalAngerReset" -> new PathfinderResetAnger(m, getBoolean(g, "c"));
                case "UseItem" -> new PathfinderUseItem(m, fromNMS(getObject(g, "b", net.minecraft.world.item.ItemStack.class)), en -> getObject(g, "c", Predicate.class).test(toNMS(en)), fromNMS(getObject(g, "d", SoundEffect.class)));
                case "ZombieAttack" -> new PathfinderZombieAttack((Zombie) m, getDouble(g, "b"), getBoolean(g, "c"));

                // Target
                case "NearestAttackableTarget" -> new PathfinderNearestAttackableTarget<>(m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getInt(g, "b"), getBoolean(g, "f"), getBoolean(g, "d"));
                case "NearestAttackableTargetWitch" -> new PathfinderNearestAttackableTargetRaider<>((Raider) m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getInt(g, "b"), true, true, l -> getObject(g, "d", PathfinderTargetCondition.class).a(null, toNMS(l)));
                case "NearestHealableRaider" -> new PathfinderNearestHealableRaider<>((Raider) m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), true,  l -> getObject(g, "d", PathfinderTargetCondition.class).a(null, toNMS(l)));
                case "DefendVillage" -> new PathfinderDefendVillage((IronGolem) m);
                case "HurtByTarget" -> new PathfinderHurtByTarget((Creature) m, getEntityTypes(getObject(g, "i", Class[].class)));
                case "OwnerHurtByTarget" -> new PathfinderOwnerHurtByTarget((Tameable) m);
                case "OwnerHurtTarget" -> new PathfinderOwnerHurtTarget((Tameable) m);
                case "RandomTargetNonTamed" -> new PathfinderWildTarget<>((Tameable) m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getBoolean(g, "f"), l -> getObject(g, "d", PathfinderTargetCondition.class).a(null, toNMS(l)));

                default -> custom(g);
            };
        } else return custom(g);
    }

    private static class Attribute1_17_R1 extends AttributeRanged implements Attribute {

        private final NamespacedKey key;
        private final double defaultV;
        private final double min;
        private final double max;

        public Attribute1_17_R1(AttributeRanged a) {
            super(a.getName(), a.getDefault(), a.d(), a.e());
            this.key = IRegistry.al.getKey(a) == null ? NamespacedKey.minecraft(a.getName()) : CraftNamespacedKey.fromMinecraft(IRegistry.al.getKey(a));
            this.defaultV = a.getDefault();
            this.min = a.d();
            this.max = a.e();
        }

        public Attribute1_17_R1(NamespacedKey key, double defaultV, double min, double max, boolean clientSide) {
            super("attribute.name." +  key.getKey().toLowerCase(), defaultV, min, max);
            this.key = key;
            this.min = min;
            this.defaultV = defaultV;
            this.max = max;
            this.a(clientSide);
        }

        public double getMinValue() {
            return this.min;
        }

        public double getDefaultValue() {
            return this.defaultV;
        }

        public double getMaxValue() {
            return this.max;
        }

        @Override
        public boolean isClientSide() {
            return b();
        }

        @NotNull
        @Override
        public NamespacedKey getKey() {
            return this.key;
        }
    }

    private static class AttributeInstance1_17_R1 implements AttributeInstance {

        private final AttributeModifiable handle;
        private final Attribute a;

        AttributeInstance1_17_R1(Attribute a, AttributeModifiable handle) {
            this.a = a;
            this.handle = handle;
        }

        @Override
        public @NotNull Attribute getGenericAttribute() {
            return this.a;
        }

        @Override
        public double getBaseValue() {
            return handle.getBaseValue();
        }

        @Override
        public void setBaseValue(double v) {
            handle.setValue(v);
        }

        @NotNull
        @Override
        public Collection<AttributeModifier> getModifiers() {
            return handle.getModifiers().stream().map(CraftAttributeInstance::convert).collect(Collectors.toSet());
        }

        @Override
        public void addModifier(@NotNull AttributeModifier mod) {
            Preconditions.checkArgument(mod != null, "modifier");
            handle.addModifier(CraftAttributeInstance.convert(mod));
        }

        @Override
        public void removeModifier(@NotNull AttributeModifier mod) {
            Preconditions.checkArgument(mod != null, "modifier");
            handle.removeModifier(CraftAttributeInstance.convert(mod));
        }

        @Override
        public double getValue() {
            return handle.getValue();
        }

        @Override
        public double getDefaultValue() {
            return handle.getAttribute().getDefault();
        }
    }

    @Override
    public Attribute registerAttribute(NamespacedKey key, double defaultV, double min, double max, boolean client) {
        if (existsAttribute(key)) return null;

        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        IRegistryWritable<AttributeBase> writable = server.getCustomRegistry().b(IRegistry.y);
        ResourceKey<AttributeBase> nmsKey = ResourceKey.a(IRegistry.y, toNMS(key));
        Attribute1_17_R1 att = new Attribute1_17_R1(key, defaultV, min, max, client);
        writable.a(nmsKey, att, Lifecycle.stable());
        return att;
    }

    @Override
    public boolean existsAttribute(NamespacedKey key) {
        return IRegistry.al.c(toNMS(key));
    }

    private static MinecraftKey toNMS(NamespacedKey key) {
        return CraftNamespacedKey.toMinecraft(key);
    }

    @Override
    public Attribute getAttribute(NamespacedKey key) {
        AttributeBase a = IRegistry.al.get(toNMS(key));
        if (!(a instanceof AttributeRanged)) return null;
        return new Attribute1_17_R1((AttributeRanged) a);
    }

    @Override
    public AttributeInstance getAttributeInstance(Mob m, Attribute a) {
        AttributeBase nmsAttribute = IRegistry.al.get(toNMS(a.getKey()));
        return new AttributeInstance1_17_R1(a, toNMS(m).getAttributeInstance(nmsAttribute));
    }

    private static ReputationType toNMS(GossipType t) {
        return ReputationType.a(t.getKey().getKey());
    }

    private static GossipType fromNMS(ReputationType t) {
        return GossipType.getByKey(NamespacedKey.minecraft(t.i));
    }

    private static class EntityGossipContainer1_17_R1 implements EntityGossipContainer {
        private final Reputation handle;

        EntityGossipContainer1_17_R1(Villager v) {
            this.handle = ((CraftVillager) v).getHandle().fS();
        }

        @Override
        public void decay() {
            handle.b();
        }

        @Override
        public int getReputation(@NotNull Entity en, @Nullable GossipType... types) throws IllegalArgumentException {
            return handle.a(en.getUniqueId(), g -> Arrays.asList(types).contains(fromNMS(g)));
        }

        @Override
        public void put(@NotNull Entity en, @NotNull GossipType type, int maxCap) throws IllegalArgumentException {
            handle.a(en.getUniqueId(), toNMS(type), maxCap);
        }

        @Override
        public void remove(@NotNull Entity en, @NotNull GossipType type) throws IllegalArgumentException {
            handle.a(en.getUniqueId(), toNMS(type));
        }

        @Override
        public void removeAll(@NotNull GossipType type) throws IllegalArgumentException {
            handle.a(toNMS(type));
        }
    }

    @Override
    public EntityGossipContainer getGossipContainer(Villager v) {
        return new EntityGossipContainer1_17_R1(v);
    }

    private static Entity fromNMS(net.minecraft.world.entity.Entity en) {
        return en.getBukkitEntity();
    }

    private static CombatEntry fromNMS(Mob m, net.minecraft.world.damagesource.CombatEntry en) {
        return new CombatEntry(m, fromNMS(en.a()), en.b(), en.d(), en.c(), en.g() == null ? null : CombatLocation.getByKey(NamespacedKey.minecraft(en.g())), en.j(), en.i() == null ? null : fromNMS(en.i()));
    }

    private static net.minecraft.world.damagesource.CombatEntry toNMS(CombatEntry en) {
        return new net.minecraft.world.damagesource.CombatEntry(toNMS(en.getCause()), en.getCombatTime(), en.getHealthBeforeDamage(), en.getDamage(), en.getLocation().getKey().getKey(), en.getFallDistance());
    }

    private static class EntityCombatTracker1_17_R1 implements EntityCombatTracker {

        private final CombatTracker handle;
        private final Mob m;

        EntityCombatTracker1_17_R1(Mob m) {
            this.m = m;
            this.handle = toNMS(m).getCombatTracker();
        }

        @Override
        public @NotNull String getCurrentDeathMessage() {
            return handle.getDeathMessage().getString();
        }

        @Override
        public @Nullable CombatEntry getLatestEntry() {
            return handle.i() == null ? null : fromNMS(m, handle.i());
        }

        @Override
        public @NotNull List<CombatEntry> getCombatEntries() {
            List<CombatEntry> entries = new ArrayList<>();
            try {
                Field f = CombatTracker.class.getDeclaredField("c");
                f.setAccessible(true);
                ((List<net.minecraft.world.damagesource.CombatEntry>) f.get(handle)).stream().map(en -> fromNMS(m, en)).forEach(entries::add);
            } catch (Exception e) {
                Bukkit.getLogger().severe(e.getClass().getSimpleName());
                Bukkit.getLogger().severe(e.getMessage());
                for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
            }
            return entries;
        }

        @Override
        public void recordEntry(@NotNull CombatEntry entry) {
            if (entry == null) return;
            try {
                Field f = CombatTracker.class.getDeclaredField("c");
                f.setAccessible(true);
                Object entries = f.get(handle);

                Method m = List.class.getMethod("add", Object.class);
                m.invoke(entries, toNMS(entry));
            } catch (Exception e) {
                Bukkit.getLogger().severe(e.getClass().getSimpleName());
                Bukkit.getLogger().severe(e.getMessage());
                for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
            }
        }

        @Override
        public int getCombatDuration() {
            return handle.f();
        }

        @Override
        public boolean isTakingDamage() {
            return handle.d();
        }

        @Override
        public boolean isInCombat() {
            return handle.e();
        }
    }

    @Override
    public EntityCombatTracker getCombatTracker(Mob m) { return new EntityCombatTracker1_17_R1(m); }

    @Override
    public void knockback(EnderDragon a, List<Entity> list) {
        EntityEnderDragon nmsMob = toNMS(a);

        try {
            Method m = EntityEnderDragon.class.getDeclaredMethod("a", List.class);
            m.setAccessible(true);
            m.invoke(nmsMob, list.stream().map(ChipUtil1_17_R1::toNMS).collect(Collectors.toList()));
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getClass().getSimpleName());
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
        }
    }

    private static final class DragonPhase1_17_R1 implements DragonPhase {

        private final EnderDragon dragon;
        private final IDragonController handle;

        DragonPhase1_17_R1(EnderDragon dragon, IDragonController handle) {
            this.dragon = dragon;
            this.handle = handle;
        }

        @Override
        public @NotNull EnderDragon getDragon() {
            return this.dragon;
        }

        @Override
        public @NotNull Location getTargetLocation() {
            return fromNMS(handle.g(), dragon.getWorld());
        }

        @Override
        public void start() {
            handle.d();
        }

        @Override
        public void stop() {
            handle.e();
        }

        @Override
        public void clientTick() {
            handle.b();
        }

        @Override
        public void serverTick() {
            handle.c();
        }

        @Override
        public boolean isSitting() {
            return handle.a();
        }

        @Override
        public float getFlyingSpeed() {
            return handle.f();
        }

        @NotNull
        @Override
        public NamespacedKey getKey() {
            return NamespacedKey.minecraft(handle.toString().split(" ")[0].toLowerCase());
        }
    }

    private static EntityEnderDragon toNMS(EnderDragon dragon) {
        return ((CraftEnderDragon) dragon).getHandle();
    }

    private static Location fromNMS(IPosition p, World w) { return new Location(w, p.getX(), p.getY(), p.getZ()); }

    @Override
    public DragonPhase fromBukkit(EnderDragon d, EnderDragon.Phase phase) {
        EntityEnderDragon nms = toNMS(d);
        IDragonController i = switch (phase) {
            case CIRCLING -> new DragonControllerHold(nms);
            case STRAFING -> new DragonControllerStrafe(nms);
            case FLY_TO_PORTAL -> new DragonControllerLandingFly(nms);
            case LAND_ON_PORTAL -> new DragonControllerLanding(nms);
            case LEAVE_PORTAL -> new DragonControllerFly(nms);
            case BREATH_ATTACK -> new DragonControllerLandedFlame(nms);
            case SEARCH_FOR_BREATH_ATTACK_TARGET -> new DragonControllerLandedSearch(nms);
            case ROAR_BEFORE_ATTACK -> new DragonControllerLandedAttack(nms);
            case CHARGE_PLAYER -> new DragonControllerCharge(nms);
            case DYING -> new DragonControllerDying(nms);
            default -> new DragonControllerHover(nms);
        };

        return new DragonPhase1_17_R1(d, i);
    }

    @Override
    public DragonPhase getCurrentPhase(EnderDragon dragon) {
        return new DragonPhase1_17_R1(dragon, toNMS(dragon).getDragonControllerManager().a());
    }

    @Override
    public void updateActivities(Creature c) {
        EntityCreature nms = toNMS(c);
        if (c instanceof Axolotl) AxolotlAi.a((net.minecraft.world.entity.animal.axolotl.Axolotl) nms);
    }

    private static MemoryModuleType<?> toNMS(Memory<?> mem) {
        return IRegistry.ar.get(mem instanceof EntityMemory<?> ? new MinecraftKey(mem.getKey().getKey()) : new MinecraftKey(mem.getKey().getNamespace(), mem.getKey().getKey()));
    }

    @Override
    public void registerMemory(Memory<?> m) {
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        IRegistryWritable<MemoryModuleType<?>> writable = server.getCustomRegistry().b(IRegistry.F);
        ResourceKey<MemoryModuleType<?>> nmsKey = ResourceKey.a(IRegistry.F, toNMS(m.getKey()));
        writable.a(nmsKey, toNMS(m), Lifecycle.stable());
    }

    @Override
    public boolean existsMemory(Memory<?> m) {
        if (m instanceof EntityMemory<?>) return true;
        return IRegistry.ar.c(new MinecraftKey(m.getKey().getNamespace(), m.getKey().getKey()));
    }

}
