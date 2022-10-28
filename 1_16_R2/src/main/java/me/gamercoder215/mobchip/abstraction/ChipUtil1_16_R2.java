package me.gamercoder215.mobchip.abstraction;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Lifecycle;
import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.abstraction.v1_16_R2.*;
import me.gamercoder215.mobchip.ai.attribute.Attribute;
import me.gamercoder215.mobchip.ai.attribute.AttributeInstance;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.ai.enderdragon.DragonPhase;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.*;
import me.gamercoder215.mobchip.ai.goal.target.*;
import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import me.gamercoder215.mobchip.ai.gossip.GossipType;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.combat.CombatLocation;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftSound;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R2.entity.*;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R2.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ChipUtil1_16_R2 implements ChipUtil {

    public static org.bukkit.inventory.ItemStack fromNMS(net.minecraft.server.v1_16_R2.ItemStack item) { return CraftItemStack.asBukkitCopy(item); }

    @Override
    public void addCustomPathfinder(CustomPathfinder p, int priority, boolean target) {
        Mob m = p.getEntity();
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.targetSelector : mob.goalSelector;
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
        PathfinderGoalSelector s = target ? mob.targetSelector : mob.goalSelector;

        Set<WrappedPathfinder> pF = new HashSet<>();

        try {
            Field f = s.getClass().getDeclaredField("d");
            f.setAccessible(true);
            Set<PathfinderGoalWrapped> goals = (Set<PathfinderGoalWrapped>) f.get(s);
            goals.forEach(w -> pF.add(new WrappedPathfinder(fromNMS(w.j()), w.h())));
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement e1 : e.getStackTrace()) {
                Bukkit.getLogger().severe(e1.toString());
            }
        }

        return pF;
    }

    @Override
    public Collection<WrappedPathfinder> getRunningGoals(Mob m, boolean target) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.targetSelector : mob.goalSelector;

        Collection<WrappedPathfinder> l = new HashSet<>();
        s.d().forEach(w -> l.add(new WrappedPathfinder(fromNMS(w.j()), w.h())));

        return l;
    }

    @Override
    public void setFlag(Mob m, Pathfinder.PathfinderFlag flag, boolean target, boolean value) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.targetSelector : mob.goalSelector;
        s.a(toNMS(flag), value);
    }

    public static final Map<Class<? extends LivingEntity>, Class<? extends EntityLiving>> BUKKIT_NMS_MAP = ImmutableMap.<Class<? extends LivingEntity>, Class<? extends EntityLiving>>builder()
            .put(LivingEntity.class, EntityLiving.class)
            .put(Mob.class, EntityInsentient.class)
            .put(Tameable.class, EntityTameableAnimal.class)
            .put(Player.class, EntityHuman.class)

            .put(AbstractHorse.class, EntityHorseAbstract.class)
            .put(AbstractVillager.class, EntityVillagerAbstract.class)
            .put(Donkey.class, EntityHorseDonkey.class)
            .put(ElderGuardian.class, EntityGuardianElder.class)
            .put(Golem.class, EntityGolem.class)
            .put(Husk.class, EntityZombieHusk.class)
            .put(HumanEntity.class, EntityHuman.class)
            .put(Mule.class, EntityHorseMule.class)
            .put(SkeletonHorse.class, EntityHorseSkeleton.class)
            .put(Stray.class, EntitySkeletonStray.class)
            .put(TraderLlama.class, EntityLlamaTrader.class)
            .put(WanderingTrader.class, EntityVillagerTrader.class)
            .put(WitherSkeleton.class, EntitySkeletonWither.class)
            .put(ZombieHorse.class, EntityHorseZombie.class)
            .build();

    public static Class<? extends EntityLiving> toNMS(Class<? extends LivingEntity> clazz) {
        if (BUKKIT_NMS_MAP.containsKey(clazz)) return BUKKIT_NMS_MAP.get(clazz);

        Class<? extends EntityLiving> nms = null;
        try {
            // Sometimes we can get lucky...
            nms = Class.forName(EntityInsentient.class.getPackage().getName() + "." + clazz.getSimpleName()).asSubclass(EntityLiving.class);
        } catch (ClassNotFoundException ignored) {}

        if (nms == null) try {
            // Some Pre-Mojang Mapping Classes start with "Entity"
            nms = Class.forName(EntityInsentient.class.getPackage().getName() + "." + "Entity" + clazz.getSimpleName()).asSubclass(EntityLiving.class);
        } catch (ClassNotFoundException ignored) {}

        if (nms == null) throw new AssertionError("Could not convert " + clazz.getName() + " to NMS class");

        return nms;
    }

    public static net.minecraft.server.v1_16_R2.ItemStack toNMS(org.bukkit.inventory.ItemStack i) {
        return CraftItemStack.asNMSCopy(i);
    }

    public static SoundEffect toNMS(Sound s) {
        return CraftSound.getSoundEffect(CraftSound.getSound(s));
    }

    public static PathfinderGoal toNMS(Pathfinder b) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);

        String name = b.getInternalName().startsWith("PathfinderGoal") ? b.getInternalName().replace("PathfinderGoal", "") : b.getInternalName();

        switch (name) {
            case "AvoidTarget": {
                PathfinderAvoidEntity<?> p = (PathfinderAvoidEntity<?>) b;
                return new PathfinderGoalAvoidTarget<>((EntityCreature) m, toNMS(p.getFilter()), p.getMaxDistance(), p.getSpeedModifier(), p.getSprintModifier());
            }
            case "ArrowAttack": {
                PathfinderRangedAttack p = (PathfinderRangedAttack) b;
                return new PathfinderGoalArrowAttack((IRangedEntity) m, p.getSpeedModifier(), p.getMinAttackInterval(), p.getMaxAttackInterval(), p.getRange());
            }
            case "Beg": {
                PathfinderBeg p = (PathfinderBeg) b;
                return new PathfinderGoalBeg((EntityWolf) m, p.getRange());
            }
            case "BowShoot": {
                PathfinderRangedBowAttack p = (PathfinderRangedBowAttack) b;
                return new PathfinderGoalBowShoot((EntityMonster) m, p.getSpeedModifier(), p.getInterval(), p.getRange());
            }
            case "BreakDoor": {
                PathfinderBreakDoor p = (PathfinderBreakDoor) b;
                return new PathfinderGoalBreakDoor(m, p.getBreakTime(), d -> p.getCondition().test(fromNMS(d)));
            }
            case "Breath": return new PathfinderGoalBreath((EntityCreature) m);
            case "Breed": {
                PathfinderBreed p = (PathfinderBreed) b;
                return new PathfinderGoalBreed((EntityAnimal) m, p.getSpeedModifier());
            }
            case "CatSitOnBed": {
                PathfinderCatOnBed p = (PathfinderCatOnBed) b;
                return new PathfinderGoalCatSitOnBed((EntityCat) m, p.getSpeedModifier(), Math.min((int) p.getRange(), 1));
            }
            case "CrossbowAttack": {
                PathfinderRangedCrossbowAttack p = (PathfinderRangedCrossbowAttack) b;
                return new PathfinderGoalCrossbowAttack((EntityMonster) m, p.getSpeedModifier(), p.getRange());
            }
            case "DoorOpen": {
                PathfinderOpenDoor p = (PathfinderOpenDoor) b;
                return new PathfinderGoalDoorOpen(m, p.mustClose());
            }
            case "EatTile": return new PathfinderGoalEatTile(m);
            case "FishSchool": return new PathfinderGoalFishSchool((EntityFishSchool) m);
            case "FleeSun": {
                PathfinderFleeSun p = (PathfinderFleeSun) b;
                return new PathfinderGoalFleeSun((EntityCreature) m, p.getSpeedModifier());

            }
            case "Float": return new PathfinderGoalFloat(m);
            case "FollowBoat": return new PathfinderGoalFollowBoat((EntityCreature) m);
            case "FollowEntity": {
                PathfinderFollowMob p = (PathfinderFollowMob) b;
                return new PathfinderGoalFollowEntity(m, p.getSpeedModifier(), p.getStopDistance(), p.getRange());
            }
            case "FollowOwner": {
                PathfinderFollowOwner p = (PathfinderFollowOwner) b;
                return new PathfinderGoalFollowOwner((EntityTameableAnimal) m, p.getSpeedModifier(), p.getStartDistance(), p.getStopDistance(), p.canFly());
            }
            case "FollowParent": {
                PathfinderFollowParent p = (PathfinderFollowParent) b;
                return new PathfinderGoalFollowParent((EntityAnimal) m, p.getSpeedModifier());
            }
            case "JumpOnBlock": {
                PathfinderCatOnBlock p = (PathfinderCatOnBlock) b;
                return new PathfinderGoalJumpOnBlock((EntityCat) m, p.getSpeedModifier());
            }
            case "LeapAtTarget": {
                PathfinderLeapAtTarget p = (PathfinderLeapAtTarget) b;
                return new PathfinderGoalLeapAtTarget(m, p.getHeight());
            }
            case "LlamaFollow": {
                PathfinderLlamaFollowCaravan p = (PathfinderLlamaFollowCaravan) b;
                return new PathfinderGoalLlamaFollow((EntityLlama) m, p.getSpeedModifier());
            }
            case "LookAtPlayer": {
                PathfinderLookAtEntity<?> p = (PathfinderLookAtEntity) b;
                return new PathfinderGoalLookAtPlayer(m, toNMS(p.getFilter()), p.getRange(), p.getProbability());
            }
            case "LookAtTradingPlayer": return new PathfinderGoalLookAtTradingPlayer((EntityVillagerAbstract) m);
            case "MeleeAttack": {
                PathfinderMeleeAttack p = (PathfinderMeleeAttack) b;
                return new PathfinderGoalMeleeAttack((EntityCreature) m, p.getSpeedModifier(), p.mustSee());
            }
            case "MoveThroughVillage": {
                PathfinderMoveThroughVillage p = (PathfinderMoveThroughVillage) b;
                return new PathfinderGoalMoveThroughVillage((EntityVillager) m, p.getSpeedModifier(), p.mustBeNight(), p.getMinDistance(), p.canUseDoors());
            }
            case "MoveTowardsRestriction": {
                PathfinderMoveTowardsRestriction p = (PathfinderMoveTowardsRestriction) b;
                return new PathfinderGoalMoveTowardsRestriction((EntityCreature) m, p.getSpeedModifier());
            }
            case "MoveTowardsTarget": {
                PathfinderMoveTowardsTarget p = (PathfinderMoveTowardsTarget) b;
                return new PathfinderGoalMoveTowardsTarget((EntityCreature) m, p.getSpeedModifier(), p.getRange());
            }
            case "NearestVillage": {
                PathfinderRandomStrollThroughVillage p = (PathfinderRandomStrollThroughVillage) b;
                return new PathfinderGoalNearestVillage((EntityCreature) m, p.getInterval());
            }
            case "OcelotAttack": return new PathfinderGoalOcelotAttack(m);
            case "OfferFlower": return new PathfinderGoalOfferFlower((EntityIronGolem) m);
            case "Panic": {
                PathfinderPanic p = (PathfinderPanic) b;
                return new PathfinderGoalPanic((EntityCreature) m, p.getSpeedModifier());
            }
            case "Perch": return new PathfinderGoalPerch((EntityPerchable) m);
            case "Raid": return new PathfinderGoalRaid<>((EntityRaider) m);
            case "RandomFly": {
                PathfinderRandomStrollFlying p = (PathfinderRandomStrollFlying) b;
                return new PathfinderGoalRandomFly((EntityCreature) m, p.getSpeedModifier());
            }
            case "RandomLookaround": return new PathfinderGoalRandomLookaround(m);
            case "RandomStroll": {
                PathfinderRandomStroll p = (PathfinderRandomStroll) b;
                return new PathfinderGoalRandomStroll((EntityCreature) m, p.getSpeedModifier(), p.getInterval());
            }
            case "RandomStrollLand": {
                PathfinderRandomStrollLand p = (PathfinderRandomStrollLand) b;
                return new PathfinderGoalRandomStrollLand((EntityCreature) m, p.getSpeedModifier(), p.getProbability());
            }
            case "RandomSwim": {
                PathfinderRandomSwim p = (PathfinderRandomSwim) b;
                return new PathfinderGoalRandomSwim((EntityCreature) m, p.getSpeedModifier(), p.getInterval());
            }
            case "RemoveBlock": {
                PathfinderRemoveBlock p = (PathfinderRemoveBlock) b;
                return new PathfinderGoalRemoveBlock(((CraftBlock) p.getBlock()).getNMS().getBlock(), (EntityCreature) m, p.getSpeedModifier(), Math.min((int) p.getBlock().getLocation().distance(mob.getLocation()), 1));
            }
            case "RestrictSun": return new PathfinderGoalRestrictSun((EntityCreature) m);
            case "Sit": return new PathfinderGoalSit((EntityTameableAnimal) m);
            case "StrollVillage": {
                PathfinderRandomStrollToVillage p = (PathfinderRandomStrollToVillage) b;
                return new PathfinderGoalStrollVillage((EntityCreature) m, p.getSpeedModifier(), true);
            }
            case "Swell": return new PathfinderGoalSwell((EntityCreeper) m);
            case "Tame": {
                PathfinderTameHorse p = (PathfinderTameHorse) b;
                return new PathfinderGoalTame((EntityHorseAbstract) m, p.getSpeedModifier());
            }
            case "Tempt": {
                PathfinderTempt p = (PathfinderTempt) b;
                return new PathfinderGoalTempt((EntityCreature) m, p.getSpeedModifier(), new RecipeItemStack(p.getItems().stream().map(CraftItemStack::asNMSCopy).filter(i -> !i.isEmpty()).map(RecipeItemStack.StackProvider::new)), true);
            }
            case "TradeWithPlayer": return new PathfinderGoalTradeWithPlayer((EntityVillagerAbstract) m);
            case "UseItem": {
                PathfinderUseItem p = (PathfinderUseItem) b;
                return new PathfinderGoalUseItem<>(m, toNMS(p.getItem()), toNMS(p.getFinishSound()), e -> p.getCondition().test(fromNMS(e)));
            }
            case "Water": return new PathfinderGoalWater((EntityCreature) m);
            case "WaterJump": {
                PathfinderDolphinJump p = (PathfinderDolphinJump) b;
                return new PathfinderGoalWaterJump((EntityDolphin) m, p.getInterval());
            }
            case "ZombieAttack": {
                PathfinderZombieAttack p = (PathfinderZombieAttack) b;
                return new PathfinderGoalZombieAttack((EntityZombie) m, p.getSpeedModifier(), p.mustSee());
            }

            // Target

            case "DefendVillage": return new PathfinderGoalDefendVillage((EntityIronGolem) m);
            case "HurtByTarget": {
                PathfinderHurtByTarget p = (PathfinderHurtByTarget) b;
                List<Class<? extends EntityLiving>> classes = new ArrayList<>();
                p.getIgnoring().stream().map(EntityType::getEntityClass).forEach(c -> classes.add(toNMS(c.asSubclass(LivingEntity.class))));

                return new PathfinderGoalHurtByTarget((EntityCreature) m, classes.toArray(new Class[0]));
            }
            case "NearestAttackableTarget": {
                PathfinderNearestAttackableTarget p = (PathfinderNearestAttackableTarget) b;
                return new PathfinderGoalNearestAttackableTarget<>(m, toNMS(p.getFilter()), p.getInterval(), p.mustSee(), p.mustReach(), t -> p.getCondition().test(fromNMS(t)));
            }
            case "NearestAttackableTargetWitch": {
                PathfinderNearestAttackableTargetRaider p = (PathfinderNearestAttackableTargetRaider) b;
                return new PathfinderGoalNearestAttackableTargetWitch<>((EntityRaider) m, toNMS(p.getFilter()), p.getInterval(), p.mustSee(), p.mustReach(), l -> p.getCondition().test(fromNMS(l)));
            }
            case "NearestHealableRaider": {
                PathfinderNearestHealableRaider p = (PathfinderNearestHealableRaider) b;
                return new PathfinderGoalNearestHealableRaider<>((EntityRaider) m, toNMS(p.getFilter()), p.mustSee(), l -> p.getCondition().test(fromNMS(l)));
            }
            case "OwnerHurtByTarget": return new PathfinderGoalOwnerHurtByTarget((EntityTameableAnimal) m);
            case "OwnerHurtTarget": return new PathfinderGoalOwnerHurtTarget((EntityTameableAnimal) m);
            case "RandomTargetNonTamed": {
                PathfinderWildTarget p = (PathfinderWildTarget) b;
                return new PathfinderGoalRandomTargetNonTamed<>((EntityTameableAnimal) m, toNMS(p.getFilter()), p.mustSee(), l -> p.getCondition().test(fromNMS(l)));
            }

            default: {
                if (b instanceof CustomPathfinder) {
                    CustomPathfinder p = (CustomPathfinder) b;
                    return custom(p);
                } else return null;
            }
        }
    }

    @Override
    public void addPathfinder(Pathfinder b, int priority, boolean target) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.targetSelector : m.goalSelector;

        PathfinderGoal g = toNMS(b);

        if (g == null) return;
        s.a(priority, g);
    }

    @Override
    public void removePathfinder(Pathfinder b, boolean target) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.targetSelector : m.goalSelector;

        final PathfinderGoal g = toNMS(b);
        if (g == null) return;
        s.a(g);
    }

    public static BehaviorResult.Status fromNMS(Behavior.Status status) {
        if (status == Behavior.Status.STOPPED) return BehaviorResult.Status.STOPPED;
        return BehaviorResult.Status.RUNNING;
    }

    public static LivingEntity fromNMS(EntityLiving l) {
        return (LivingEntity) l.getBukkitEntity();
    }

    @Override
    public BehaviorResult runBehavior(Mob m, String behaviorName, Object... args) {
        return runBehavior(m, behaviorName, Behavior.class.getPackage().getName(), args);
    }

    @Override
    public BehaviorResult runBehavior(Mob m, String behaviorName, String packageName, Object... args) {
        EntityInsentient nms = toNMS(m);
        String packageN = packageName.replace("{V}", "v1_16_R2");

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
            Class<?> bClass = Class.forName(packageN + "." + behaviorName);
            Constructor<?> c = bClass.getConstructor(ChipUtil.getArgTypes(args));
            Behavior<? super EntityLiving> b = (Behavior<? super EntityLiving>) c.newInstance(args);
            return new BehaviorResult1_16_R2(b, nms);
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
            return null;
        }
    }
    public static EntityPlayer toNMS(Player p) { return ((CraftPlayer) p).getHandle(); }

    @Override
    public Attribute getDefaultAttribute(String s) {
        return new Attribute1_16_R2((AttributeRanged) IRegistry.ATTRIBUTE.get(new MinecraftKey(s)));
    }

    public static Activity toNMS(me.gamercoder215.mobchip.ai.schedule.Activity a) {
        return IRegistry.ACTIVITY.get(new MinecraftKey(a.getKey().getKey()));
    }

    public static me.gamercoder215.mobchip.ai.schedule.Activity fromNMS(Activity a) {
        MinecraftKey key = IRegistry.ACTIVITY.getKey(a);
        return me.gamercoder215.mobchip.ai.schedule.Activity.getByKey(NamespacedKey.minecraft(key.getKey()));
    }

    public static me.gamercoder215.mobchip.ai.schedule.Schedule fromNMS(Schedule s) {
        me.gamercoder215.mobchip.ai.schedule.Schedule.Builder b = me.gamercoder215.mobchip.ai.schedule.Schedule.builder();
        for (int i = 0; i < 24000; i++) {
            if (s.a(i) == null) continue;
            me.gamercoder215.mobchip.ai.schedule.Activity a = fromNMS(s.a(i));
            b.addActivity(i, a);
        }

        return b.build();
    }

    public static Schedule toNMS(me.gamercoder215.mobchip.ai.schedule.Schedule s) {
        ScheduleBuilder b = new ScheduleBuilder(new Schedule());
        for (int i = 0; i < 24000; i++) {
            if (!s.contains(i)) continue;
            Activity a = toNMS(s.get(i));
            b.a(i, a);
        }

        return b.a();
    }

    public static <T extends EntityLiving> Behavior<T> toNMS(Consumer<Mob> en) {
        return new Behavior<T>(Collections.emptyMap()) {
            @Override
            protected void d(WorldServer var0, T m, long var2) {
                if (!(m instanceof EntityInsentient)) return;
                en.accept(fromNMS((EntityInsentient) m));
            }
        };
    }

    public static AbstractDragonController toNMS(CustomPhase c) {
        return new AbstractDragonController(toNMS(c.getDragon())) {
            @Override
            public DragonControllerPhase<? extends IDragonController> getControllerPhase() {
                try {
                    Method create = DragonControllerPhase.class.getDeclaredMethod("a");
                    create.setAccessible(true);
                    return (DragonControllerPhase<? extends IDragonController>) create.invoke(null, this.getClass(), c.getKey().getKey());
                } catch (Exception ignored) {}
                return DragonControllerPhase.HOVER;
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
            public float a(DamageSource s, float damage) { return c.onDamage(fromNMS(s), damage); }
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

    @Override
    public me.gamercoder215.mobchip.ai.schedule.Schedule getDefaultSchedule(String key) {
        return fromNMS(IRegistry.SCHEDULE.get(new MinecraftKey(key)));
    }

    public static Set<me.gamercoder215.mobchip.ai.schedule.Activity> getActiveActivities(Mob m) {
        EntityInsentient nmsMob = toNMS(m);

        try {
            Field active = nmsMob.getBehaviorController().getClass().getDeclaredField("j");
            active.setAccessible(true);
            Set<Activity> activities = (Set<Activity>) active.get(nmsMob.getBehaviorController());
            return activities.stream().map(ChipUtil1_16_R2::fromNMS).collect(Collectors.toSet());
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getClass().getSimpleName());
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
        }

        return Collections.emptySet();
    }

    @Override
    public EntityScheduleManager getManager(Mob m) { return new EntityScheduleManager1_16_R2(m); }

    @Override
    public EntityController getController(Mob m) {
        return new EntityController1_16_R2(m);
    }

    @Override
    public EntityNavigation getNavigation(Mob m) {
        return new EntityNavigation1_16_R2(m);
    }

    @Override
    public EntityBody getBody(Mob m) {
        return new EntityBody1_16_R2(m);
    }

    public static DamageSource toNMS(EntityDamageEvent.DamageCause c) {
        switch (c) {
            case FIRE:
            case FIRE_TICK: return DamageSource.FIRE;
            case LIGHTNING: return DamageSource.LIGHTNING;
            case SUFFOCATION: return DamageSource.STUCK;
            case LAVA: return DamageSource.LAVA;
            case HOT_FLOOR: return DamageSource.HOT_FLOOR;
            case CRAMMING: return DamageSource.CRAMMING;
            case DROWNING: return DamageSource.DROWN;
            case STARVATION: return DamageSource.STARVE;
            case CONTACT: return DamageSource.CACTUS;
            case MAGIC: return DamageSource.MAGIC;
            case FALL: return DamageSource.FALL;
            case FLY_INTO_WALL: return DamageSource.FLY_INTO_WALL;
            case VOID: return DamageSource.OUT_OF_WORLD;
            case WITHER: return DamageSource.WITHER;
            case FALLING_BLOCK: return DamageSource.FALLING_BLOCK;
            case DRAGON_BREATH: return DamageSource.DRAGON_BREATH;
            case DRYOUT: return DamageSource.DRYOUT;
            default: return DamageSource.GENERIC;
        }
    }

    public static EntityItem toNMS(org.bukkit.entity.Item i) {
        return (EntityItem) ((CraftItem) i).getHandle();
    }

    public static EntityLiving toNMS(LivingEntity en) {
        return ((CraftLivingEntity) en).getHandle();
    }

    public static Object toNMS(String key, Object value) {
        final Object nmsValue;

        if (value instanceof Location) {
            Location l = (Location) value;
            if (key.equals("nearest_bed") || key.equals("celebrate_location") || key.equals("nearest_repellent")) nmsValue = new BlockPosition(l.getX(), l.getY(), l.getZ());
            else nmsValue = GlobalPos.create(toNMS(l.getWorld()).getDimensionKey(), new BlockPosition(l.getX(), l.getY(), l.getZ()));
        }
        else if (value instanceof Location[]) {
            Location[] ls = (Location[]) value;
            List<GlobalPos> p = new ArrayList<>();

            for (Location l : ls) {
                p.add(GlobalPos.create(toNMS(l.getWorld()).getDimensionKey(), new BlockPosition(l.getX(), l.getY(), l.getZ())));
            }

            nmsValue = p;
        }
        else if (value instanceof Player) {
            Player p = (Player) value;
            if (key.equals("liked_player")) nmsValue = p.getUniqueId();
            else nmsValue = toNMS(p);
        }
        else if (value instanceof Memory.WalkingTarget) {
            Memory.WalkingTarget t = (Memory.WalkingTarget) value;
            nmsValue = new MemoryTarget(toNMS(t.getLocation()), (float) t.getSpeedModifier(), t.getDistance());
        }
        else if (value instanceof LivingEntity){
            LivingEntity l = (LivingEntity) value;
            nmsValue = toNMS(l);
        }
        else if (value instanceof Entity) {
            Entity e = (Entity) value;
            if (key.equals("angry_at")) nmsValue = e.getUniqueId();
            else nmsValue = toNMS(e);
        }
        else if (value instanceof org.bukkit.block.Block[]) {
            org.bukkit.block.Block[] b = (org.bukkit.block.Block[]) value;
            final Collection<GlobalPos> s;
            if (key.equals("doors_to_close")) s = new HashSet<>();
            else s = new ArrayList<>();

            for (org.bukkit.block.Block bl : b) {
                Location l = bl.getLocation();
                s.add(GlobalPos.create(toNMS(l.getWorld()).getDimensionKey(), new BlockPosition(l.getX(), l.getY(), l.getZ())));
            }
            nmsValue = s;
        }
        else if (value instanceof Villager[]) {
            Villager[] vs = (Villager[]) value;
            List<EntityLiving> s = new ArrayList<>();
            for (Villager v : vs) s.add(toNMS(v));
            nmsValue = s;
        }
        else if (value instanceof Player[]) {
            Player[] ps = (Player[]) value;
            List<EntityPlayer> s = new ArrayList<>();
            for (Player p : ps) s.add(toNMS(p));
            nmsValue = s;
        }
        else if (value instanceof LivingEntity[]) {
            LivingEntity[] ls = (LivingEntity[]) value;
            List<EntityLiving> s = new ArrayList<>();
            for (LivingEntity l : ls) s.add(toNMS(l));
            nmsValue = s;
        }
        else if (value instanceof EntityDamageEvent.DamageCause) {
            EntityDamageEvent.DamageCause c = (EntityDamageEvent.DamageCause) value;
            nmsValue = toNMS(c);
        }
        else nmsValue = value;

        return nmsValue;
    }

    public static Object fromNMS(Mob m, String key, Object nmsValue) {
        Object value = nmsValue;

        if (nmsValue instanceof GlobalPos) {
            GlobalPos l = (GlobalPos) nmsValue;
            BlockPosition pos = l.getBlockPosition();
            World w = ((CraftServer) Bukkit.getServer()).getHandle().getServer().f.b(IRegistry.L).a(l.getDimensionManager()).getWorld();
            value = new Location(w, pos.getX(), pos.getY(), pos.getZ());
        }
        else if (nmsValue instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) nmsValue;
            value = Bukkit.getPlayer(p.getUniqueID());
        }
        else if (nmsValue instanceof MemoryTarget) {
            MemoryTarget t = (MemoryTarget) nmsValue;
            BlockPosition p = t.a().b();
            value = new Memory.WalkingTarget(new Location(m.getWorld(), p.getX(), p.getY(), p.getZ()), t.b(), t.c());
        }
        else if (nmsValue instanceof EntityLiving) {
            EntityLiving l = (EntityLiving) nmsValue;
            value = Bukkit.getEntity(l.getUniqueID());
        }
        else if (nmsValue instanceof Set<?>) {
            Set<?> s = (Set<?>) nmsValue;
            if (key.equals("doors_to_close")) {
                List<org.bukkit.block.Block> l = new ArrayList<>();
                s.forEach(o -> l.add((org.bukkit.block.Block) fromNMS(m, key, o)));
                value = l.toArray(new org.bukkit.block.Block[0]);
            }
        }
        else if (nmsValue instanceof List<?>) {
            List<?> ls = (List<?>) nmsValue;
            switch (key) {
                case "visible_villager_babies": {
                    List<Villager> vl = new ArrayList<>();
                    ls.forEach(o -> vl.add((Villager) fromNMS((EntityLiving) o)));
                    value = vl.toArray(new Villager[0]);
                    break;
                }
                case "nearest_players": {
                    List<Player> pl = new ArrayList<>();
                    ls.forEach(o -> pl.add(Bukkit.getPlayer(((EntityPlayer) o).getUniqueID())));
                    value = pl.toArray(new Player[0]);
                    break;
                }
                case "mobs": {
                    List<LivingEntity> vl = new ArrayList<>();
                    ls.forEach(o -> vl.add(fromNMS((EntityLiving) o)));
                    value = vl.toArray(new LivingEntity[0]);
                    break;
                }
                case "secondary_job_site":
                case "interactable_doors": {
                    List<Location> l = new ArrayList<>();
                    ls.forEach(o -> l.add((Location) fromNMS(m, key, o)));
                    value = l.toArray(new Location[0]);
                    break;
                }
            }
        }
        else if (value instanceof DamageSource) {
            DamageSource c = (DamageSource) value;
            value = fromNMS(c);
        }
        else value = nmsValue;

        return value;
    }

    public static EntityDamageEvent.DamageCause fromNMS(DamageSource c) {
        switch (c.translationIndex) {
            case "inFire": return FIRE;
            case "lightningBolt": return LIGHTNING;
            case "onFire": return FIRE_TICK;
            case "lava": return LAVA;
            case "hotFloor": return HOT_FLOOR;
            case "inWall": return SUFFOCATION;
            case "cramming": return CRAMMING;
            case "drown": return DROWNING;
            case "starve": return STARVATION;
            case "cactus":
            case "sweetBerryBush": return CONTACT;
            case "fall": return FALL;
            case "flyIntoWall": return FLY_INTO_WALL;
            case "outOfWorld": return VOID;
            case "magic": return MAGIC;
            case "wither": return WITHER;
            case "anvil": case "fallingBlock": return FALLING_BLOCK;
            case "dragonBreath": return DRAGON_BREATH;
            case "dryout": return DRYOUT;
            default: return CUSTOM;
        }
    }


    @Override
    public <T> void setMemory(Mob mob, Memory<T> m, T value) {
        if (value == null) {
            removeMemory(mob, m);
            return;
        }

        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = toNMS(m);
        String key = IRegistry.MEMORY_MODULE_TYPE.getKey(type).getKey();
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
        String key = IRegistry.MEMORY_MODULE_TYPE.getKey(type).getKey();
        Object nmsValue = toNMS(key, value);

        nms.getBehaviorController().a(type, nmsValue, durationTicks);
    }

    @Override
    public <T> T getMemory(Mob mob, Memory<T> m) {
        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = toNMS(m);
        String key = IRegistry.MEMORY_MODULE_TYPE.getKey(type).getKey();

        return m.getBukkitClass().cast(fromNMS(mob, key, nms.getBehaviorController().getMemory(type)));
    }

    @Override
    public long getExpiry(Mob mob, Memory<?> m) {
        // doesn't exist
        return 0;
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
        return nms.ev();
    }

    @Override
    public void clearRestriction(Mob m) {
        EntityInsentient nms = toNMS(m);
        nms.a((BlockPosition) null, 0);
    }

    @Override
    public void restrictTo(Mob m, double x, double y, double z, int radius) {
        EntityInsentient nms = toNMS(m);
        nms.a(new BlockPosition(x, y, z), radius);
    }

    @Override
    public Location getRestriction(Mob m) {
        EntityInsentient nms = toNMS(m);
        BlockPosition c = nms.ew();
        return new Location(m.getWorld(), c.getX(), c.getY(), c.getZ());
    }

    @Override
    public int getRestrictionRadius(Mob m) {
        EntityInsentient nms = toNMS(m);
        return ((int) nms.ex()) < 0 ? Integer.MAX_VALUE : (int) nms.ex();
    }

    @Override
    public boolean hasRestriction(Mob m) {
        EntityInsentient nms = toNMS(m);
        return nms.ez();
    }

    @Override
    public boolean canSee(Mob m, Entity en) {
        EntityInsentient nms = toNMS(m);
        return nms.getEntitySenses().a(toNMS(en));
    }

    public static net.minecraft.server.v1_16_R2.Entity toNMS(Entity en) {
        return ((CraftEntity) en).getHandle();
    }

    public static VillagerProfession toNMS(Villager.Profession p) {
        switch (p) {
            case FARMER: return VillagerProfession.FARMER;
            case FISHERMAN: return VillagerProfession.FISHERMAN;
            case LIBRARIAN: return VillagerProfession.LIBRARIAN;
            case WEAPONSMITH: return VillagerProfession.WEAPONSMITH;
            case TOOLSMITH: return VillagerProfession.TOOLSMITH;
            case BUTCHER: return VillagerProfession.BUTCHER;
            case FLETCHER: return VillagerProfession.FLETCHER;
            case MASON: return VillagerProfession.MASON;
            case CLERIC: return VillagerProfession.CLERIC;
            case ARMORER: return VillagerProfession.ARMORER;
            case NITWIT: return VillagerProfession.NITWIT;
            case SHEPHERD: return VillagerProfession.SHEPHERD;
            case CARTOGRAPHER: return VillagerProfession.CARTOGRAPHER;
            case LEATHERWORKER: return VillagerProfession.LEATHERWORKER;
            default: return VillagerProfession.NONE;
        }
    }

    public static <T extends Entity> Class<? extends T> fromNMS(Class<? extends net.minecraft.server.v1_16_R2.Entity> clazz, Class<T> cast) {
        try {
            String name = clazz.getSimpleName();
            if (name.contains("Entity")) name = name.replace("Entity", "");

            final Class<? extends Entity> bukkit;

            switch (name) {
                case "": bukkit = Entity.class; break;
                case "Living": bukkit = LivingEntity.class; break;
                case "Lightning": bukkit = LightningStrike.class; break;
                case "Insentient": bukkit = Mob.class; break;
                case "TameableAnimal": bukkit = Tameable.class; break;

                case "Animal": bukkit = Animals.class; break;
                case "FishSchool": bukkit = Fish.class; break;
                case "HorseAbstract": bukkit = AbstractHorse.class; break;
                case "HorseMule": bukkit = Mule.class; break;
                case "HorseSkeleton": bukkit = SkeletonHorse.class; break;
                case "HorseZombie": bukkit = ZombieHorse.class; break;
                case "HorseDonkey": bukkit = Donkey.class; break;
                case "WaterAnimal": bukkit = WaterMob.class; break;

                case "GiantZombie": bukkit = Giant.class; break;
                case "GuardianElder": bukkit = ElderGuardian.class; break;
                case "IllagerIllusioner": bukkit = Illusioner.class; break;
                case "SkeletonStray": bukkit = Stray.class; break;
                case "SkeletonWither": bukkit = WitherSkeleton.class; break;
                case "ZombieHusk": bukkit = Husk.class; break;
                case "ZombieVillager": bukkit = ZombieVillager.class; break;

                case "Villager": bukkit = Villager.class; break;
                case "VillagerAbstract": bukkit = AbstractVillager.class; break;
                case "VillagerTrader": bukkit = WanderingTrader.class; break;

                case "Human": bukkit = HumanEntity.class; break;
                case "Player": bukkit = Player.class; break;

                case "FireballFireball": bukkit = SizedFireball.class; break;
                case "Fireworks": bukkit = Firework.class; break;
                case "FishingHook": bukkit = FishHook.class; break;
                case "Potion": bukkit = ThrownPotion.class; break;
                case "ProjectileThrowable": bukkit = ThrowableProjectile.class; break;
                case "ThrownTrident": bukkit = Trident.class; break;

                case "MinecartAbstract": bukkit = Minecart.class; break;
                case "MinecartChest": bukkit = StorageMinecart.class; break;
                case "MinecartCommandBlock": bukkit = CommandMinecart.class; break;
                case "MinecartFurnace": bukkit = PoweredMinecart.class; break;
                case "MinecartHopper": bukkit = HopperMinecart.class; break;
                case "MinecartMobSpawner": bukkit = SpawnerMinecart.class; break;
                case "MinecartTNT": bukkit = ExplosiveMinecart.class; break;

                default: bukkit = Class.forName("org.bukkit.entity." + name).asSubclass(Entity.class);
            }

            return bukkit.asSubclass(cast);
        } catch (ClassNotFoundException e) {
            return cast;
        }
    }

    public static EntityInsentient toNMS(Mob m) { return ((CraftMob) m).getHandle(); }

    public static EntityType[] getEntityTypes(Class<?>... nms) {
        List<EntityType> types = new ArrayList<>();
        for (Class<?> c : nms) {

            Class<? extends Entity> bukkit = fromNMS((Class<? extends net.minecraft.server.v1_16_R2.Entity>) c, Entity.class);
            for (EntityType t : EntityType.values()) {
                if (t.getEntityClass() != null && t.getEntityClass().isAssignableFrom(bukkit)) types.add(t);
            }
        }
        return types.toArray(new EntityType[0]);
    }

    public static EnumDifficulty toNMS(org.bukkit.Difficulty d) {
        switch (d) {
            case PEACEFUL: return EnumDifficulty.PEACEFUL;
            default: return EnumDifficulty.EASY;
            case NORMAL: return EnumDifficulty.NORMAL;
            case HARD: return EnumDifficulty.HARD;
        }
    }

    public static org.bukkit.Difficulty fromNMS(EnumDifficulty d) {
        switch (d) {
            case PEACEFUL: return org.bukkit.Difficulty.PEACEFUL;
            default: return org.bukkit.Difficulty.EASY;
            case NORMAL: return org.bukkit.Difficulty.NORMAL;
            case HARD: return org.bukkit.Difficulty.HARD;
        }
    }

    public static EntityCreature toNMS(Creature c) { return ((CraftCreature) c).getHandle();}

    public static PathfinderGoal.Type toNMS(Pathfinder.PathfinderFlag f) {
        switch (f) {
            default: return PathfinderGoal.Type.MOVE;
            case JUMPING: return PathfinderGoal.Type.JUMP;
            case TARGETING: return PathfinderGoal.Type.TARGET;
            case LOOKING: return PathfinderGoal.Type.LOOK;
        }
    }

    public static Pathfinder.PathfinderFlag fromNMS(PathfinderGoal.Type f) {
        switch (f) {
            default: return Pathfinder.PathfinderFlag.MOVEMENT;
            case JUMP: return Pathfinder.PathfinderFlag.JUMPING;
            case TARGET: return Pathfinder.PathfinderFlag.TARGETING;
            case LOOK: return Pathfinder.PathfinderFlag.LOOKING;
        }
    }

    public static float getFloat(PathfinderGoal o, String name) { return getObject(o, name, Float.class); }

    public static double getDouble(PathfinderGoal o, String name) { return getObject(o, name, Double.class); }

    public static boolean getBoolean(PathfinderGoal o, String name) { return getObject(o, name, Boolean.class); }

    public static int getInt(PathfinderGoal o, String name) { return getObject(o, name, Integer.class); }

    public static <T> T getObject(PathfinderGoal o, String name, Class<T> cast) {
        try {
            Class<? extends PathfinderGoal> clazz = o.getClass();

            while (clazz.getSuperclass() != null) {
                try {
                    Field f = clazz.getDeclaredField(name);
                    f.setAccessible(true);
                    return cast.cast(f.get(o));
                } catch (NoSuchFieldException | ClassCastException e) {
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

    public static Mob fromNMS(EntityInsentient m) { return (Mob) m.getBukkitEntity(); }

    public static World fromNMS(net.minecraft.server.v1_16_R2.World l) { return l.getWorld(); }

    public static WorldServer toNMS(World w) { return ((CraftWorld) w).getHandle(); }

    public static BlockPosition toNMS(Location l) { return new BlockPosition(l.getX(), l.getY(), l.getZ()); }

    public static List<ItemStack> fromNMS(RecipeItemStack in) {
        in.buildChoices();
        return Arrays.stream(in.choices).map(CraftItemStack::asBukkitCopy).collect(Collectors.toList());
    }

    public static Sound fromNMS(SoundEffect s) {
        try {
            Field key = s.getClass().getDeclaredField("b");
            key.setAccessible(true);
            String value = key.get(s).toString();

            for (CraftSound cs : CraftSound.values()) {
                Field sound = cs.getClass().getDeclaredField("minecraftKey");
                sound.setAccessible(true);
                String cvalue = sound.get(cs).toString();
                if (cvalue.equals(value)) return Sound.valueOf(cs.name());
            }

            return null;
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s1 : e.getStackTrace()) Bukkit.getLogger().severe(s1.toString());
            return null;
        }
    }

    public static Mob getEntity(PathfinderGoal g) {
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

    public static Object invoke(PathfinderGoal g, String method, Object... args) {
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

    public static PathfinderGoal custom(CustomPathfinder p) {
        CustomGoal1_16_R2 g = new CustomGoal1_16_R2(p);
        EnumSet<PathfinderGoal.Type> flags = EnumSet.noneOf(PathfinderGoal.Type.class);
        Arrays.stream(p.getFlags()).map(ChipUtil1_16_R2::toNMS).forEach(flags::add);
        g.a(flags);
        return g;
    }

    public static CustomPathfinder custom(PathfinderGoal g) {
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
            public boolean canInterrupt() { return g.C_(); }

            @Override
            public void start() {
                g.c();
            }

            @Override
            public void tick() {
                g.e();
            }

            @Override
            public void stop() { g.d(); }

            @Override
            public String getInternalName() {
                return g.getClass().getSimpleName();
            }
        };
    }

    public static BlockPosition getPosWithBlock(net.minecraft.server.v1_16_R2.Block block, BlockPosition bp, IBlockAccess g) {
        if (g.getType(bp).a(block)) return bp;
        else {
            BlockPosition[] bp1 = new BlockPosition[]{new BlockPosition(bp.down()),  bp.west(), bp.east(), bp.north(), bp.south(), new BlockPosition(bp.up())};
            for (BlockPosition bps : bp1) if (g.getType(bps).a(block)) return bps;
            return null;
        }
    }

    public static Location fromNMS(BlockPosition p, World w) { return new Location(w, p.getX(), p.getY(), p.getZ()); }

    private Pathfinder fromNMS(PathfinderGoal g) {
        if (g instanceof CustomGoal1_16_R2) {
            return ((CustomGoal1_16_R2) g).getPathfinder();
        }

        Mob m = getEntity(g);
        String name = g.getClass().getSimpleName();

        if (name.startsWith("PathfinderGoal")) {
            name = name.replace("PathfinderGoal", "");

            switch (name) {
                case "AvoidTarget": return new PathfinderAvoidEntity<>((Creature) m, fromNMS(getObject(g, "f", Class.class), LivingEntity.class), getFloat(g, "c"), getDouble(g, "i"), getDouble(g, "j"));
                case "ArrowAttack": return new PathfinderRangedAttack(m, getDouble(g, "e"), getFloat(g, "i"), getInt(g, "g"), getInt(g, "h"));
                case "Beg": return new PathfinderBeg((Wolf) m, getFloat(g, "d"));
                case "BowShoot": return new PathfinderRangedBowAttack(m, getDouble(g, "b"), (float) Math.sqrt(getFloat(g, "d")), getInt(g, "c"));
                case "BreakDoor": return new PathfinderBreakDoor(m, getInt(g, "c"), d -> getObject(g, "g", Predicate.class).test(toNMS(d)));
                case "Breath": return new PathfinderBreathAir((Creature) m);
                case "Breed": return new PathfinderBreed((Animals) m, getDouble(g, "g"));
                case "CatSitOnBed": return new PathfinderCatOnBed((Cat) m, getDouble(g, "b"), getInt(g, "i"));
                case "CrossbowAttack": return new PathfinderRangedCrossbowAttack((Pillager) m, getDouble(g, "c"), (float) Math.sqrt(getFloat(g, "d")));
                case "DoorOpen": return new PathfinderOpenDoor(m, getBoolean(g, "a"));
                case "WaterJump": return new PathfinderDolphinJump((Dolphin) m, getInt(g, "c"));
                case "EatTile": return new PathfinderEatTile(m);
                case "Water": return new PathfinderFindWater((Creature) m);
                case "FishSchool": return new PathfinderFollowFishLeader((Fish) m);
                case "FleeSun": return new PathfinderFleeSun((Creature) m, getDouble(g, "e"));
                case "Float": return new PathfinderFloat(m);
                case "FollowBoat": return new PathfinderFollowBoat((Creature) m);
                case "FollowEntity": return new PathfinderFollowMob(m, getDouble(g, "d"), getFloat(g, "g"), getFloat(g, "i"));
                case "FollowOwner": return new PathfinderFollowOwner((Tameable) m, getDouble(g, "d"), getFloat(g, "h"), getFloat(g, "g"));
                case "FollowParent": return new PathfinderFollowParent((Animals) m, getDouble(g, "c"));
                case "HorseTrap": return new PathfinderSkeletonTrap((SkeletonHorse) m);
                case "LeapAtTarget": return new PathfinderLeapAtTarget(m, getFloat(g, "c"));
                case "JumpOnBlock": return new PathfinderCatOnBlock((Cat) m, getDouble(g, "g"));
                case "LlamaFollow": return new PathfinderLlamaFollowCaravan((Llama) m, getDouble(g, "b"));
                case "LookAtPlayer": return new PathfinderLookAtEntity<>(m, fromNMS(getObject(g, "e", Class.class), LivingEntity.class), getFloat(g, "c"), getFloat(g, "d"));
                case "LookAtTradingPlayer": return new PathfinderLookAtTradingPlayer((AbstractVillager) m);
                case "MeleeAttack": return new PathfinderMeleeAttack((Creature) m, getDouble(g, "d"), getBoolean(g, "e"));
                case "MoveThroughVillage": return new PathfinderMoveThroughVillage((Creature) m, getBoolean(g, "e"), getDouble(g, "b"), getInt(g, "g"), getBoolean(g, "e"));
                case "NearestVillage": return new PathfinderRandomStrollThroughVillage((Creature) m, getInt(g, "b"));
                case "GotoTarget": return new PathfinderMoveToBlock((Creature) m, l -> fromNMS(getObject(g, "e", BlockPosition.class), m.getWorld()).equals(l), getDouble(g, "b"), getInt(g, "i"), getInt(g, "j"));
                case "Raid": return new PathfinderMoveToRaid((Raider) m);
                case "MoveTowardsRestriction": return new PathfinderMoveTowardsRestriction((Creature) m, getDouble(g, "e"));
                case "MoveTowardsTarget": return new PathfinderMoveTowardsTarget((Creature) m, getDouble(g, "f"), getFloat(g, "g"));
                case "OcelotAttack": return new PathfinderOcelotAttack((Ocelot) m);
                case "OfferFlower": return new PathfinderOfferFlower((IronGolem) m);
                case "Panic": return new PathfinderPanic((Creature) m, getDouble(g, "b"));
                case "Perch": return new PathfinderRideShoulder((Parrot) m);
                case "RandomLookaround": return new PathfinderRandomLook(m);
                case "RandomStroll": return new PathfinderRandomStroll((Creature) m, getDouble(g, "e"), getInt(g, "f"));
                case "RandomStrollLand": return new PathfinderRandomStrollLand((Creature) m, getDouble(g, "e"), getFloat(g, "h"));
                case "RandomSwim": return new PathfinderRandomSwim((Creature) m, getDouble(g, "e"), getInt(g, "f"));
                case "RandomFly": return new PathfinderRandomStrollFlying((Creature) m, getDouble(g, "e"));
                case "RemoveBlock": return new PathfinderRemoveBlock((Creature) m, m.getWorld().getBlockAt(fromNMS(getPosWithBlock(getObject(g, "g", Block.class), toNMS(m.getLocation()), toNMS(m.getWorld())), m.getWorld())), getDouble(g, "b"));
                case "RestrictSun": return new PathfinderRestrictSun((Creature) m);
                case "Sit": return new PathfinderSit((Tameable) m);
                case "StrollVillage": return new PathfinderRandomStrollToVillage((Creature) m, getDouble(g, "e"));
                case "Swell": return new PathfinderSwellCreeper((Creeper) m);
                case "Tame": return new PathfinderTameHorse((AbstractHorse) m);
                case "Tempt": return new PathfinderTempt((Creature) m, getDouble(g, "e"), fromNMS(getObject(g, "l", RecipeItemStack.class)));
                case "TradeWithPlayer": return new PathfinderTradePlayer((AbstractVillager) m);
                case "UseItem": return new PathfinderUseItem(m, fromNMS(getObject(g, "b", net.minecraft.server.v1_16_R2.ItemStack.class)), en -> getObject(g, "c", Predicate.class).test(toNMS(en)), fromNMS(getObject(g, "d", SoundEffect.class)));
                case "ZombieAttack": return new PathfinderZombieAttack((Zombie) m, getDouble(g, "d"), getBoolean(g, "e"));

                // Target
                case "NearestAttackableTarget": return new PathfinderNearestAttackableTarget<>(m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getInt(g, "b"), getBoolean(g, "f"), getBoolean(g, "a"));
                case "NearestAttackableTargetWitch": return new PathfinderNearestAttackableTargetRaider<>((Raider) m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getInt(g, "b"), getBoolean(g, "f"), getBoolean(g, "a"), l -> getObject(g, "d", PathfinderTargetCondition.class).a(null, toNMS(l)));
                case "NearestHealableRaider": return new PathfinderNearestHealableRaider<>((Raider) m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getBoolean(g, "f"),  l -> getObject(g, "d", PathfinderTargetCondition.class).a(null, toNMS(l)));
                case "DefendVillage": return new PathfinderDefendVillage((IronGolem) m);
                case "HurtByTarget": return new PathfinderHurtByTarget((Creature) m, getEntityTypes(getObject(g, "d", Class[].class)));
                case "OwnerHurtByTarget": return new PathfinderOwnerHurtByTarget((Tameable) m);
                case "OwnerHurtTarget": return new PathfinderOwnerHurtTarget((Tameable) m);
                case "RandomTargetNonTamed": return new PathfinderWildTarget<>((Tameable) m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getBoolean(g, "f"), l -> getObject(g, "d", PathfinderTargetCondition.class).a(null, toNMS(l)));

                default: return custom(g);
            }
        } else return custom(g);
    }

    @Override
    public Attribute registerAttribute(NamespacedKey key, double defaultV, double min, double max, boolean client) {
        if (existsAttribute(key)) return null;

        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        IRegistryWritable<AttributeBase> writable = server.aX().b(IRegistry.y);
        ResourceKey<AttributeBase> nmsKey = ResourceKey.a(IRegistry.y, toNMS(key));
        Attribute1_16_R2 att = new Attribute1_16_R2(key, defaultV, min, max, client);
        writable.a(nmsKey, att, Lifecycle.stable());
        return att;
    }

    @Override
    public boolean existsAttribute(NamespacedKey key) {
        return IRegistry.ATTRIBUTE.getOptional(toNMS(key)).isPresent();
    }

    public static MinecraftKey toNMS(NamespacedKey key) {
        return CraftNamespacedKey.toMinecraft(key);
    }

    @Override
    public Attribute getAttribute(NamespacedKey key) {
        AttributeBase a = IRegistry.ATTRIBUTE.get(toNMS(key));
        if (!(a instanceof AttributeRanged)) return null;
        return new Attribute1_16_R2((AttributeRanged) a);
    }

    @Override
    public AttributeInstance getAttributeInstance(Mob m, Attribute a) {
        AttributeBase nmsAttribute = IRegistry.ATTRIBUTE.get(toNMS(a.getKey()));
        return new AttributeInstance1_16_R2(a, toNMS(m).getAttributeInstance(nmsAttribute));
    }

    public static ReputationType toNMS(GossipType t) {
        return ReputationType.a(t.getKey().getKey());
    }

    public static GossipType fromNMS(ReputationType t) {
        return GossipType.getByKey(NamespacedKey.minecraft(t.f));
    }

    @Override
    public EntityGossipContainer getGossipContainer(Villager v) {
        return new EntityGossipContainer1_16_R2(v);
    }

    public static Entity fromNMS(net.minecraft.server.v1_16_R2.Entity en) {
        return en.getBukkitEntity();
    }

    public static me.gamercoder215.mobchip.combat.CombatEntry fromNMS(Mob m, net.minecraft.server.v1_16_R2.CombatEntry en) {
        int time = 0;
        float health = 0;

        try {
            Field timeF = net.minecraft.server.v1_16_R2.CombatEntry.class.getDeclaredField("b");
            timeF.setAccessible(true);
            time = timeF.getInt(en);

            Field healthF = net.minecraft.server.v1_16_R2.CombatEntry.class.getDeclaredField("d");
            healthF.setAccessible(true);
            health = healthF.getFloat(en);
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getClass().getSimpleName());
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
        }

        return new me.gamercoder215.mobchip.combat.CombatEntry(m, fromNMS(en.a()), time, health, en.c(), en.g() == null ? null : CombatLocation.getByKey(NamespacedKey.minecraft(en.g())), en.j(), en.a().getEntity() == null ? null : fromNMS(en.a().getEntity()));
    }

    public static net.minecraft.server.v1_16_R2.CombatEntry toNMS(me.gamercoder215.mobchip.combat.CombatEntry en) {
        return new net.minecraft.server.v1_16_R2.CombatEntry(toNMS(en.getCause()), en.getCombatTime(), en.getHealthBeforeDamage(), en.getDamage(), en.getLocation().getKey().getKey(), en.getFallDistance());
    }

    @Override
    public EntityCombatTracker getCombatTracker(Mob m) { return new EntityCombatTracker1_16_R2(m); }

    @Override
    public void knockback(EnderDragon a, List<Entity> list) {
        EntityEnderDragon nmsMob = toNMS(a);

        try {
            Method m = EntityEnderDragon.class.getDeclaredMethod("a", List.class);
            m.setAccessible(true);
            m.invoke(nmsMob, list.stream().map(ChipUtil1_16_R2::toNMS).collect(Collectors.toList()));
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getClass().getSimpleName());
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
        }
    }

    public static EntityEnderDragon toNMS(EnderDragon dragon) {
        return ((CraftEnderDragon) dragon).getHandle();
    }

    public static Location fromNMS(IPosition p, World w) { return new Location(w, p.getX(), p.getY(), p.getZ()); }

    @Override
    public DragonPhase fromBukkit(EnderDragon d, EnderDragon.Phase phase) {
        EntityEnderDragon nms = toNMS(d);
        final IDragonController i;
        switch (phase) {
            case CIRCLING: i = new DragonControllerHold(nms); break;
            case STRAFING: i = new DragonControllerStrafe(nms); break;
            case FLY_TO_PORTAL: i = new DragonControllerLandingFly(nms); break;
            case LAND_ON_PORTAL: i = new DragonControllerLanding(nms); break;
            case LEAVE_PORTAL: i = new DragonControllerFly(nms); break;
            case BREATH_ATTACK: i = new DragonControllerLandedFlame(nms); break;
            case SEARCH_FOR_BREATH_ATTACK_TARGET: i = new DragonControllerLandedSearch(nms); break;
            case ROAR_BEFORE_ATTACK: i = new DragonControllerLandedAttack(nms); break;
            case CHARGE_PLAYER: i = new DragonControllerCharge(nms); break;
            case DYING: i = new DragonControllerDying(nms); break;
            default: i = new DragonControllerHover(nms); break;
        }

        return new DragonPhase1_16_R2(d, i);
    }

    @Override
    public DragonPhase getCurrentPhase(EnderDragon dragon) {
        return new DragonPhase1_16_R2(dragon, toNMS(dragon).getDragonControllerManager().a());
    }

    public static MemoryModuleType<?> toNMS(Memory<?> mem) {
        return IRegistry.MEMORY_MODULE_TYPE.get(mem instanceof EntityMemory<?> ? new MinecraftKey(mem.getKey().getKey()) : new MinecraftKey(mem.getKey().getNamespace(), mem.getKey().getKey()));
    }

    @Override
    public void registerMemory(Memory<?> m) {
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        IRegistryWritable<MemoryModuleType<?>> writable = server.aX().b(IRegistry.D);
        ResourceKey<MemoryModuleType<?>> nmsKey = ResourceKey.a(IRegistry.D, toNMS(m.getKey()));
        writable.a(nmsKey, toNMS(m), Lifecycle.stable());
    }

    @Override
    public boolean existsMemory(Memory<?> m) {
        if (m instanceof EntityMemory<?>) return true;
        return IRegistry.MEMORY_MODULE_TYPE.getOptional(toNMS(m.getKey())).isPresent();
    }

    @Override
    public EntityNBT getNBTEditor(Mob m) {
        return new EntityNBT1_16_R2(m);
    }

}
