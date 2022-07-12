package me.gamercoder215.mobchip.abstraction;

import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.ai.animation.EntityAnimation;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.enderdragon.CustomPhase;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.*;
import me.gamercoder215.mobchip.ai.goal.target.*;
import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.util.Position;
import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R2.CraftSound;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_13_R2.entity.*;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ChipUtil1_13_R2 implements ChipUtil {
    private static org.bukkit.inventory.ItemStack fromNMS(net.minecraft.server.v1_13_R2.ItemStack item) { return CraftItemStack.asBukkitCopy(item); }

    @Override
    public void addCustomPathfinder(CustomPathfinder p, int priority, boolean target) {
        Mob m = p.getEntity();
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.targetSelector : mob.goalSelector;

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
            public void c() {
                p.start();
            }

            @Override
            public void e() {
                p.tick();
            }

        };

        s.a(priority, g);
    }

    @Override
    public Set<WrappedPathfinder> getGoals(Mob m, boolean target) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.targetSelector : mob.goalSelector;

        Set<WrappedPathfinder> pF = new HashSet<>();

        try {
            Field f = s.getClass().getDeclaredField("b");
            f.setAccessible(true);
            Set<?> goals = (Set<?>) f.get(s);

            for (Object o : goals) {
                Field goalF = o.getClass().getDeclaredField("a");
                goalF.setAccessible(true);
                PathfinderGoal goal = (PathfinderGoal) goalF.get(o);

                Field priorityF = o.getClass().getDeclaredField("b");
                priorityF.setAccessible(true);
                int priority = priorityF.getInt(o);

                pF.add(new WrappedPathfinder(fromNMS(goal), priority));
            }
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
        try {
            Field f = s.getClass().getDeclaredField("b");
            f.setAccessible(true);
            Set<?> goals = (Set<?>) f.get(s);

            for (Object o : goals) {
                Field goalF = o.getClass().getDeclaredField("a");
                goalF.setAccessible(true);
                PathfinderGoal goal = (PathfinderGoal) goalF.get(o);

                Field priorityF = o.getClass().getDeclaredField("b");
                priorityF.setAccessible(true);
                int priority = priorityF.getInt(o);

                Field runningF = o.getClass().getDeclaredField("c");
                runningF.setAccessible(true);
                boolean running = runningF.getBoolean(o);

                if (running) l.add(new WrappedPathfinder(fromNMS(goal), priority));
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement e1 : e.getStackTrace()) {
                Bukkit.getLogger().severe(e1.toString());
            }
        }

        return l;
    }

    @Override
    public void setFlag(Mob m, Pathfinder.PathfinderFlag flag, boolean target, boolean value) {
        // flags don't exist
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

    @Override
    public void removePathfinder(Pathfinder b, boolean target) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.targetSelector : m.goalSelector;

        final PathfinderGoal g = toNMS(b);
        if (g == null) return;
        s.a(g);
    }

    @Override
    public void clearPathfinders(Mob mob, boolean target) {
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.targetSelector : m.goalSelector;

        getGoals(mob, target).forEach(w -> removePathfinder(w.getPathfinder(), target));
    }

    private static PathfinderGoal toNMS(Pathfinder b) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);

        final PathfinderGoal g;
        switch (b.getInternalName()) {
            case "AvoidTarget": {
                PathfinderAvoidEntity<?> p = (PathfinderAvoidEntity<?>) b;
                g = new PathfinderGoalAvoidTarget<>((EntityCreature) m, toNMS(p.getFilter()), p.getMaxDistance(), p.getSpeedModifier(), p.getSprintModifier());
                break;
            }
            case "ArrowAttack": {
                PathfinderRangedAttack p = (PathfinderRangedAttack) b;
                g = new PathfinderGoalArrowAttack((IRangedEntity) m, p.getSpeedModifier(), p.getMinAttackInterval(), p.getMaxAttackInterval(), p.getRange());
                break;
            }
            case "Beg": {
                PathfinderBeg p = (PathfinderBeg) b;
                g = new PathfinderGoalBeg((EntityWolf) m, p.getRange());
                break;
            }
            case "BreakDoor": {
                PathfinderBreakDoor p = (PathfinderBreakDoor) b;
                g = new PathfinderGoalBreakDoor(m);
                break;
            }
            case "Breath": {
                PathfinderBreathAir p = (PathfinderBreathAir) b;
                g = new PathfinderGoalBreath((EntityCreature) m);
                break;
            }
            case "Breed": {
                PathfinderBreed p = (PathfinderBreed) b;
                g = new PathfinderGoalBreed((EntityAnimal) m, p.getSpeedModifier());
                break;
            }
            case "EatTile": {
                PathfinderEatTile p = (PathfinderEatTile) b;
                g = new PathfinderGoalEatTile(m);
                break;
            }
            case "FishSchool": {
                PathfinderFollowFishLeader p = (PathfinderFollowFishLeader) b;
                g = new PathfinderGoalFishSchool((EntityFishSchool) m);
                break;
            }
            case "FleeSun": {
                PathfinderFleeSun p = (PathfinderFleeSun) b;
                g = new PathfinderGoalFleeSun((EntityCreature) m, p.getSpeedModifier());
                break;
            }
            case "Float": {
                PathfinderFloat p = (PathfinderFloat) b;
                g = new PathfinderGoalFloat(m);
                break;
            }
            case "FollowBoat": {
                PathfinderFollowBoat p = (PathfinderFollowBoat) b;
                g = new PathfinderGoalFollowBoat((EntityCreature) m);
                break;
            }
            case "FollowEntity": {
                PathfinderFollowMob p = (PathfinderFollowMob) b;
                g = new PathfinderGoalFollowEntity(m, p.getSpeedModifier(), p.getStopDistance(), p.getRange());
                break;
            }
            case "FollowOwner": {
                PathfinderFollowOwner p = (PathfinderFollowOwner) b;
                g = new PathfinderGoalFollowOwner((EntityTameableAnimal) m, p.getSpeedModifier(), p.getStartDistance(), p.getStopDistance());
                break;
            }
            case "FollowParent": {
                PathfinderFollowParent p = (PathfinderFollowParent) b;
                g = new PathfinderGoalFollowParent((EntityTameableAnimal) m, p.getSpeedModifier());
                break;
            }
            case "LeapAtTarget": {
                PathfinderLeapAtTarget p = (PathfinderLeapAtTarget) b;
                g = new PathfinderGoalLeapAtTarget(m, p.getHeight());
                break;
            }
            case "LlamaFollow": {
                PathfinderLlamaFollowCaravan p = (PathfinderLlamaFollowCaravan) b;
                g = new PathfinderGoalLlamaFollow((EntityLlama) m, p.getSpeedModifier());
                break;
            }
            case "LookAtPlayer": {
                PathfinderLookAtEntity<?> p = (PathfinderLookAtEntity) b;
                g = new PathfinderGoalLookAtPlayer(m, toNMS(p.getFilter()), p.getRange(), p.getProbability());
                break;
            }
            case "MeleeAttack": {
                PathfinderMeleeAttack p = (PathfinderMeleeAttack) b;
                g = new PathfinderGoalMeleeAttack((EntityCreature) m, p.getSpeedModifier(), p.mustSee());
                break;
            }
            case "MoveThroughVillage": {
                PathfinderMoveThroughVillage p = (PathfinderMoveThroughVillage) b;
                g = new PathfinderGoalMoveThroughVillage((EntityVillager) m, p.getSpeedModifier(), p.mustBeNight());
                break;
            }
            case "MoveTowardsRestriction": {
                PathfinderMoveTowardsRestriction p = (PathfinderMoveTowardsRestriction) b;
                g = new PathfinderGoalMoveTowardsRestriction((EntityCreature) m, p.getSpeedModifier());
                break;
            }
            case "MoveTowardsTarget": {
                PathfinderMoveTowardsTarget p = (PathfinderMoveTowardsTarget) b;
                g = new PathfinderGoalMoveTowardsTarget((EntityCreature) m, p.getSpeedModifier(), p.getRange());
                break;
            }
            case "OcelotAttack": {
                PathfinderOcelotAttack p = (PathfinderOcelotAttack) b;
                g = new PathfinderGoalOcelotAttack(m);
                break;
            }
            case "OfferFlower": {
                PathfinderOfferFlower p = (PathfinderOfferFlower) b;
                g = new PathfinderGoalOfferFlower((EntityIronGolem) m);
                break;
            }
            case "Panic": {
                PathfinderPanic p = (PathfinderPanic) b;
                g = new PathfinderGoalPanic((EntityCreature) m, p.getSpeedModifier());
                break;
            }
            case "Perch": {
                PathfinderRideShoulder p = (PathfinderRideShoulder) b;
                g = new PathfinderGoalPerch((EntityPerchable) m);
                break;
            }
            case "RandomFly": {
                PathfinderRandomStrollFlying p = (PathfinderRandomStrollFlying) b;
                g = new PathfinderGoalRandomFly((EntityCreature) m, p.getSpeedModifier());
                break;
            }
            case "RandomLookaround": {
                PathfinderRandomLook p = (PathfinderRandomLook) b;
                g = new PathfinderGoalRandomLookaround(m);
                break;
            }
            case "RandomStroll": {
                PathfinderRandomStroll p = (PathfinderRandomStroll) b;
                g = new PathfinderGoalRandomStroll((EntityCreature) m, p.getSpeedModifier(), p.getInterval());
                break;
            }
            case "RandomStrollLand": {
                PathfinderRandomStrollLand p = (PathfinderRandomStrollLand) b;
                g = new PathfinderGoalRandomStrollLand((EntityCreature) m, p.getSpeedModifier(), p.getProbability());
                break;
            }
            case "RandomSwim": {
                PathfinderRandomSwim p = (PathfinderRandomSwim) b;
                g = new PathfinderGoalRandomSwim((EntityCreature) m, p.getSpeedModifier(), p.getInterval());
                break;
            }
            case "RemoveBlock": {
                PathfinderRemoveBlock p = (PathfinderRemoveBlock) b;
                g = new PathfinderGoalRemoveBlock(((CraftBlock) p.getBlock()).getNMS().getBlock(), (EntityCreature) m, p.getSpeedModifier(), Math.min((int) p.getBlock().getLocation().distance(mob.getLocation()), 1));
                break;
            }
            case "RestrictSun": {
                PathfinderRestrictSun p = (PathfinderRestrictSun) b;
                g = new PathfinderGoalRestrictSun((EntityCreature) m);
                break;
            }
            case "Sit": {
                PathfinderSit p = (PathfinderSit) b;
                g = new PathfinderGoalSit((EntityTameableAnimal) m);
                break;
            }
            case "Swell": {
                PathfinderSwellCreeper p = (PathfinderSwellCreeper) b;
                g = new PathfinderGoalSwell((EntityCreeper) m);
                break;
            }
            case "Tame": {
                PathfinderTameHorse p = (PathfinderTameHorse) b;
                g = new PathfinderGoalTame((EntityHorseAbstract) m, p.getSpeedModifier());
                break;
            }
            case "Tempt": {
                PathfinderTempt p = (PathfinderTempt) b;
                g = new PathfinderGoalTempt((EntityCreature) m, p.getSpeedModifier(), new RecipeItemStack(p.getItems().stream().map(CraftItemStack::asNMSCopy).filter(i -> !i.isEmpty()).map(RecipeItemStack.StackProvider::new)), true);
                break;
            }
            case "Water": {
                PathfinderFindWater p = (PathfinderFindWater) b;
                g = new PathfinderGoalWater((EntityCreature) m);
                break;
            }
            case "WaterJump": {
                PathfinderDolphinJump p = (PathfinderDolphinJump) b;
                g = new PathfinderGoalWaterJump((EntityDolphin) m, p.getInterval());
                break;
            }
            case "ZombieAttack": {
                PathfinderZombieAttack p = (PathfinderZombieAttack) b;
                g = new PathfinderGoalZombieAttack((EntityZombie) m, p.getSpeedModifier(), p.mustSee());
                break;
            }

            // Target

            case "DefendVillage": {
                PathfinderDefendVillage p = (PathfinderDefendVillage) b;
                g = new PathfinderGoalDefendVillage((EntityIronGolem) m);
                break;
            }
            case "HurtByTarget": {
                PathfinderHurtByTarget p = (PathfinderHurtByTarget) b;
                List<Class<? extends EntityLiving>> classes = new ArrayList<>();
                p.getIgnoring().stream().map(EntityType::getEntityClass).forEach(c -> classes.add(toNMS(c.asSubclass(LivingEntity.class))));

                g = new PathfinderGoalHurtByTarget((EntityCreature) m, true, classes.toArray(new Class[0]));
                break;
            }
            case "NearestAttackableTarget": {
                PathfinderNearestAttackableTarget p = (PathfinderNearestAttackableTarget) b;
                g = new PathfinderGoalNearestAttackableTarget<>((EntityCreature) m, toNMS(p.getFilter()), p.getInterval(), p.mustSee(), p.mustReach(), t -> p.getCondition().test(fromNMS(t)));
                break;
            }
            case "OwnerHurtByTarget": {
                PathfinderOwnerHurtByTarget p = (PathfinderOwnerHurtByTarget) b;
                g = new PathfinderGoalOwnerHurtByTarget((EntityTameableAnimal) m);
                break;
            }
            case "OwnerHurtTarget": {
                PathfinderOwnerHurtTarget p = (PathfinderOwnerHurtTarget) b;
                g = new PathfinderGoalOwnerHurtTarget((EntityTameableAnimal) m);
                break;
            }

            default: {
                if (b instanceof CustomPathfinder) {
                    CustomPathfinder p = (CustomPathfinder) b;
                    g = new PathfinderGoal() {
                        @Override
                        public boolean a() {
                            return p.canStart();
                        }

                        @Override
                        public boolean b() {
                            return p.canContinueToUse();
                        }

                        @Override
                        public void c() {
                            p.start();
                        }

                        @Override
                        public void e() {
                            p.tick();
                        }
                    };
                } else g = null;
            }
        }

        return g;
    }

    @Override
    public void addPathfinder(Pathfinder b, int priority, boolean target) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.targetSelector : m.goalSelector;

        String name = b.getInternalName().startsWith("PathfinderGoal") ? b.getInternalName().replace("PathfinderGoal", "") : b.getInternalName();

        PathfinderGoal g = toNMS(b);

        if (g == null) return;
        s.a(priority, g);
    }

    private static LivingEntity fromNMS(EntityLiving l) {
        return (LivingEntity) l.getBukkitEntity();
    }

    @Override
    public BehaviorResult runBehavior(Mob m, String behaviorName, Object... args) {
        return runBehavior(m, behaviorName, null, args);
    }

    @Override
    public BehaviorResult runBehavior(Mob m, String behaviorName, String packageName, Object... args) {
        // behaviors don't exist
        return null;
    }

    private static EntityPlayer toNMS(Player p) { return ((CraftPlayer) p).getHandle(); }

    private static final class EntityController1_13_R2 implements EntityController {

        private final ControllerJump jumpC;
        private final ControllerMove moveC;
        private final ControllerLook lookC;

        private final Mob m;

        public EntityController1_13_R2(Mob m) {
            EntityInsentient nms = toNMS(m);
            this.lookC = nms.getControllerLook();
            this.moveC = nms.getControllerMove();
            this.jumpC = nms.getControllerJump();
            this.m = m;
        }

        @Override
        public EntityController jump() {
            jumpC.a();
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
            return this;
        }

        @Override
        public EntityController strafe(float fwd, float right) {
            moveC.a(fwd, right);
            moveC.a();
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
            lookC.a(x, y, z, 0, 0);
            lookC.a();
            return this;
        }

    }

    private static final class NavigationPath1_13_R2 implements NavigationPath {
        private String name;
        private final Mob m;
        private final PathEntity handle;

        NavigationPath1_13_R2(@NotNull PathEntity nms, @NotNull Mob m) {
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
            try {
                this.handle.a();

                Field points = this.handle.getClass().getDeclaredField("a");
                points.setAccessible(true);
                PathPoint[] pathPoints = (PathPoint[]) points.get(this.handle);

                PathPoint n = pathPoints[handle.e()];
                new EntityController1_13_R2(m).moveTo(n.a, n.b, n.c);
            } catch (Exception e) {
                Bukkit.getLogger().severe(e.getMessage());
                for (StackTraceElement s : e.getStackTrace()) Bukkit.getLogger().severe(s.toString());
            }
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
            return this.handle.b();
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

    private static final class EntityNavigation1_13_R2 implements EntityNavigation {

        private final NavigationAbstract handle;

        private int speedMod;
        private int range;
        private final List<Position> points;
        private BlockPosition finalPos;

        private final Mob m;

        EntityNavigation1_13_R2(Mob m) {
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
            return new NavigationPath1_13_R2(handle.a(finalPos.getX(), finalPos.getY(), finalPos.getZ()), m);
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

    private static final class EntityBody1_13_R2 implements EntityBody {
        private final EntityInsentient nmsMob;

        EntityBody1_13_R2(Mob nmsMob) {
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
            nmsMob.p(leftHanded);
        }

        @Override
        public boolean canBreatheUnderwater() {
            return nmsMob.cp();
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
         * @param p Player to interact with
         * @param hand Hand to use
         * @return Result of interaction
         */
        @Override
        public InteractionResult interact(@NotNull Player p, @Nullable InteractionHand hand) {
            final EnumHand h;

            if (hand == InteractionHand.OFF_HAND) h = EnumHand.OFF_HAND;
            else h = EnumHand.MAIN_HAND;

            if (nmsMob.b(toNMS(p), h)) return InteractionResult.SUCCESS;
            else return InteractionResult.FAIL;
        }

        @Override
        public boolean isSensitiveToWater() {
            return nmsMob.df();
        }

        @Override
        public boolean isAffectedByPotions() {
            return nmsMob.de();
        }

        @Override
        public boolean isBlocking() {
            return nmsMob.isBlocking();
        }

        @Override
        public float getArmorCoverPercentage() {
            return nmsMob.cX();
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
            return nmsMob.ax;
        }

        @Override
        public boolean canRideUnderwater() {
            return nmsMob.aY();
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
            } catch (Exception ignored) {}
        }

        @Override
        public boolean isInCombat() {
            try {
                Field inCombat = nmsMob.combatTracker.getClass().getDeclaredField("f");
                inCombat.setAccessible(true);
                return inCombat.getBoolean(nmsMob.combatTracker);
            } catch (Exception ignored) {}
            return false;
        }

        @Override
        public float getFlyingSpeed() {
            return nmsMob.aU;
        }

        @Override
        public void setFlyingSpeed(float speed) throws IllegalArgumentException {
            if (speed < 0 || speed > 1) throw new IllegalArgumentException("Flying speed must be between 0.0F and 1.0F");
            nmsMob.aU = speed;
        }

        @Override
        public boolean isForcingDrops() {
            try {
                Field forceDrops = EntityLiving.class.getDeclaredField("forceDrops");
                forceDrops.setAccessible(true);
                return forceDrops.getBoolean(nmsMob);
            } catch (Exception ignored) {}
            return false;
        }

        @Override
        public void setForcingDrops(boolean drop) {
            try {
                Field forceDrops = EntityLiving.class.getDeclaredField("forceDrops");
                forceDrops.setAccessible(true);
                forceDrops.set(nmsMob, drop);
            } catch (Exception ignored) {}
        }

        @Override
        public boolean isMoving() {
            return false; // doesn't exist
        }

        @Override
        public float getBodyRotation() {
            return nmsMob.aQ;
        }

        @Override
        public void setBodyRotation(float rotation) {
            nmsMob.aQ = rotation > 360 ? (rotation - (float) (360 * Math.floor(rotation / 360))) : rotation;
        }

        @Override
        public float getHeadRotation() {
            return nmsMob.aS;
        }

        @Override
        public void setHeadRotation(float rotation) {
            nmsMob.aS = rotation > 360 ? (rotation - (float) (360 * Math.floor(rotation / 360))) : rotation;
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
                    nmsMob.aC = 10;
                    nmsMob.hurtTicks = nmsMob.aC;
                    nmsMob.aD = 0.0F;
                    break;
                }
                case CRITICAL_DAMAGE: {
                    PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 4);
                    for (Player p : fromNMS(nmsMob).getWorld().getPlayers()) toNMS(p).playerConnection.sendPacket(pkt);
                    break;
                }
                case MAGICAL_CRITICAL_DAMAGE: {
                    PacketPlayOutAnimation pkt = new PacketPlayOutAnimation(nmsMob, 5);
                    for (Player p : fromNMS(nmsMob).getWorld().getPlayers()) toNMS(p).playerConnection.sendPacket(pkt);
                    break;
                }
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
    }

    @Override
    public me.gamercoder215.mobchip.ai.schedule.Schedule getDefaultSchedule(String key) {
        return null; // doesn't exist
    }

    private static final class EntityScheduleManager1_13_R2 implements EntityScheduleManager {

        private final EntityInsentient nmsMob;
        private final Mob m;

        EntityScheduleManager1_13_R2(Mob m) {
            this.m = m;
            this.nmsMob = toNMS(m);
        }


        @Override
        public @Nullable me.gamercoder215.mobchip.ai.schedule.Schedule getCurrentSchedule() {
            return null; // doesn't exist
        }

        @Override
        public void setSchedule(@NotNull me.gamercoder215.mobchip.ai.schedule.Schedule s) {
            // doesn't exist
        }

        @Override
        public @NotNull Set<me.gamercoder215.mobchip.ai.schedule.Activity> getActiveActivities() {
            return Collections.emptySet(); // doesn't exist
        }

        @Override
        public void setDefaultActivity(@NotNull me.gamercoder215.mobchip.ai.schedule.Activity a) {
            // doesn't exist
        }

        @Override
        public void useDefaultActivity() {
            // doesn't exist
        }

        @Override
        public void setRunningActivity(@NotNull me.gamercoder215.mobchip.ai.schedule.Activity a) {
            // doesn't exist
        }

        @Override
        public @Nullable me.gamercoder215.mobchip.ai.schedule.Activity getRunningActivity() {
            return null; // doesn't exist
        }

        @Override
        public boolean isRunning(@NotNull me.gamercoder215.mobchip.ai.schedule.Activity a) {
            return false; // doesn't exist
        }

        @Override
        public int size() {
            return 0; // doesn't exist
        }

        @Override
        public boolean isEmpty() {
            return true; // doesn't exist
        }

        @Nullable
        @Override
        public Consumer<Mob> put(@NotNull me.gamercoder215.mobchip.ai.schedule.Activity key, Consumer<Mob> value) {
            return value; // doesn't exist
        }

        @Override
        public void clear() {
            // doesn't exist
        }

    }

    @Override
    public EntityScheduleManager getManager(Mob m) { return new EntityScheduleManager1_13_R2(m); }

    @Override
    public EntityController getController(Mob m) {
        return new EntityController1_13_R2(m);
    }

    @Override
    public EntityNavigation getNavigation(Mob m) {
        return new EntityNavigation1_13_R2(m);
    }

    @Override
    public EntityBody getBody(Mob m) {
        return new EntityBody1_13_R2(m);
    }

    private static EntityItem toNMS(org.bukkit.entity.Item i) {
        return (EntityItem) ((CraftItem) i).getHandle();
    }

    private static EntityLiving toNMS(LivingEntity en) {
        return ((CraftLivingEntity) en).getHandle();
    }

    private static Object fromNMS(Mob m, String key, Object nmsValue) {
        Object value = nmsValue;

        if (nmsValue instanceof EntityHuman) {
            EntityHuman p = (EntityHuman) nmsValue;
            value = Bukkit.getPlayer(p.getUniqueID());
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
                    ls.forEach(o -> pl.add(Bukkit.getPlayer(((EntityHuman) o).getUniqueID())));
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

    private static EntityDamageEvent.DamageCause fromNMS(DamageSource c) {
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
        // memories don't exist
    }

    @Override
    public <T> void setMemory(Mob mob, Memory<T> m, T value, long durationTicks) {
        // memories don't exist
    }

    @Override
    public <T> T getMemory(Mob mob, Memory<T> m) {
        // memories don't exist
        return null;
    }

    @Override
    public long getExpiry(Mob mob, Memory<?> m) {
        // doesn't exist
        return 0;
    }

    @Override
    public boolean contains(Mob mob, Memory<?> m) {
        // memories don't exist
        return false;
    }

    @Override
    public void removeMemory(Mob mob, Memory<?> m) {
        // memories don't exist
    }

    @Override
    public boolean isRestricted(Mob m) {
        // doesn't exist
        return false;
    }

    @Override
    public void clearRestriction(Mob m) {
        // doesn't exist
    }

    @Override
    public void restrictTo(Mob m, double x, double y, double z, int radius) {
        // doesn't exist
    }

    @Override
    public Location getRestriction(Mob m) {
        // doesn't exist
        return null;
    }

    @Override
    public int getRestrictionRadius(Mob m) {
        // doesn't exist
        return 0;
    }

    @Override
    public boolean hasRestriction(Mob m) {
        // doesn't exist
        return false;
    }

    @Override
    public boolean canSee(Mob m, Entity en) {
        EntityInsentient nms = toNMS(m);
        return nms.getEntitySenses().a(toNMS(en));
    }

    private static net.minecraft.server.v1_13_R2.Entity toNMS(Entity en) {
        return ((CraftEntity) en).getHandle();
    }

    private static AbstractDragonController toNMS(CustomPhase c) {
        return new AbstractDragonController(((CraftEnderDragon) c.getDragon()).getHandle()) {
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
            public float a(EntityComplexPart p, DamageSource s, float damage) { return c.onDamage(fromNMS(s), damage); }
        };
    }

    @Override
    public void setCustomPhase(EnderDragon a, CustomPhase c) {
        EntityEnderDragon nmsMob = ((CraftEnderDragon) a).getHandle();
        AbstractDragonController nmsPhase = toNMS(c);
        try {
            new DragonControllerManager(nmsMob).setControllerPhase(nmsPhase.getControllerPhase());
        } catch (IndexOutOfBoundsException ignored) {}
    }

    private static <T extends Entity> Class<? extends T> fromNMS(Class<? extends net.minecraft.server.v1_13_R2.Entity> clazz, Class<T> cast) {
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

                case "Human": bukkit = HumanEntity.class; break;
                case "Player": bukkit = Player.class; break;

                case "Fireworks": bukkit = Firework.class; break;
                case "FishingHook": bukkit = FishHook.class; break;
                case "Potion": bukkit = ThrownPotion.class; break;
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

    private static EntityInsentient toNMS(Mob m) { return ((CraftMob) m).getHandle(); }

    private static EntityType[] getEntityTypes(Class<?>... nms) {
        List<EntityType> types = new ArrayList<>();
        for (Class<?> c : nms) {

            Class<? extends Entity> bukkit = fromNMS((Class<? extends net.minecraft.server.v1_13_R2.Entity>) c, Entity.class);
            for (EntityType t : EntityType.values()) if (t.getEntityClass().isAssignableFrom(bukkit)) types.add(t);
        }
        return types.toArray(new EntityType[0]);
    }

    private static EnumDifficulty toNMS(org.bukkit.Difficulty d) {
        switch (d) {
            case PEACEFUL: return EnumDifficulty.PEACEFUL;
            default: return EnumDifficulty.EASY;
            case NORMAL: return EnumDifficulty.NORMAL;
            case HARD: return EnumDifficulty.HARD;
        }
    }

    private static EntityCreature toNMS(Creature c) { return ((CraftCreature) c).getHandle();}

    private static float getFloat(PathfinderGoal o, String name) { return getObject(o, name, Float.class); }

    private static double getDouble(PathfinderGoal o, String name) { return getObject(o, name, Double.class); }

    private static boolean getBoolean(PathfinderGoal o, String name) { return getObject(o, name, Boolean.class); }

    private static int getInt(PathfinderGoal o, String name) { return getObject(o, name, Integer.class); }

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

    private static org.bukkit.World fromNMS(net.minecraft.server.v1_13_R2.World l) { return l.getWorld(); }

    private static WorldServer toNMS(org.bukkit.World w) { return ((CraftWorld) w).getHandle(); }

    private static BlockPosition toNMS(Location l) { return new BlockPosition(l.getX(), l.getY(), l.getZ()); }

    private static List<ItemStack> fromNMS(RecipeItemStack in) { return Arrays.stream(in.choices).map(CraftItemStack::asBukkitCopy).collect(Collectors.toList()); }

    private static Sound fromNMS(SoundEffect s) {
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
                return new PathfinderFlag[0];
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
            public String getInternalName() {
                return g.getClass().getSimpleName();
            }
        };
    }

    private static BlockPosition getPosWithBlock(net.minecraft.server.v1_13_R2.Block block, BlockPosition bp, IBlockAccess g) {
        if (g.getType(bp).getBlock().equals(block)) return bp;
        else {
            BlockPosition[] bp1 = new BlockPosition[]{new BlockPosition(bp.down()),  bp.west(), bp.east(), bp.north(), bp.south(), new BlockPosition(bp.up())};
            for (BlockPosition bps : bp1) if (g.getType(bps).getBlock().equals(block)) return bps;
            return null;
        }
    }

    private static Location fromNMS(BlockPosition p, World w) { return new Location(w, p.getX(), p.getY(), p.getZ()); }

    private Pathfinder fromNMS(PathfinderGoal g) {
        Mob m = getEntity(g);
        String name = g.getClass().getSimpleName();

        if (name.startsWith("PathfinderGoal")) {
            name = name.replace("PathfinderGoal", "");

            switch (name) {
                case "AvoidTarget": return new PathfinderAvoidEntity<>((Creature) m, fromNMS(getObject(g, "f", Class.class), LivingEntity.class), getFloat(g, "c"), getDouble(g, "i"), getDouble(g, "j"));
                case "ArrowAttack": return new PathfinderRangedAttack(m, getDouble(g, "e"), getFloat(g, "i"), getInt(g, "g"), getInt(g, "h"));
                case "Beg": return new PathfinderBeg((Wolf) m);
                case "BreakDoor": return new PathfinderBreakDoor(m, getInt(g, "i"), d -> getObject(g, "h", Predicate.class).test(toNMS(d)));
                case "Breath": return new PathfinderBreathAir((Creature) m);
                case "Breed": return new PathfinderBreed((Animals) m, getDouble(g, "g"));
                case "DoorOpen": return new PathfinderOpenDoor(m, getBoolean(g, "a"));
                case "WaterJump": return new PathfinderDolphinJump((Dolphin) m, getInt(g, "c"));
                case "EatTile": return new PathfinderEatTile(m);
                case "Water": return new PathfinderFindWater((Creature) m);
                case "FleeSun": return new PathfinderFleeSun((Creature) m, getDouble(g, "e"));
                case "Float": return new PathfinderFloat(m);
                case "FollowBoat": return new PathfinderFollowBoat((Creature) m);
                case "FollowEntity": return new PathfinderFollowMob(m, getDouble(g, "d"), getFloat(g, "g"), getFloat(g, "i"));
                case "FollowOwner": return new PathfinderFollowOwner((Tameable) m, getDouble(g, "h"), getFloat(g, "l"), getFloat(g, "k"), getBoolean(g, "n"));
                case "FollowParent": return new PathfinderFollowParent((Animals) m, getDouble(g, "f"));
                case "HorseTrap": return new PathfinderSkeletonTrap((SkeletonHorse) m);
                case "LeapAtTarget": return new PathfinderLeapAtTarget(m, getFloat(g, "c"));
                case "LlamaFollow": return new PathfinderLlamaFollowCaravan((Llama) m, getDouble(g, "b"));
                case "LookAtPlayer": return new PathfinderLookAtEntity<>(m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getFloat(g, "d"), getFloat(g, "e"), getBoolean(g, "i"));
                case "MeleeAttack": return new PathfinderMeleeAttack((Creature) m, getDouble(g, "b"), getBoolean(g, "c"));
                case "MoveThroughVillage": return new PathfinderMoveThroughVillage((Creature) m, getObject(g, "b", BooleanSupplier.class), getDouble(g, "b"), getInt(g, "g"), getBoolean(g, "e"));
                case "NearestVillage": return new PathfinderRandomStrollThroughVillage((Creature) m, getInt(g, "b"));
                case "GotoTarget": return new PathfinderMoveToBlock((Creature) m, l -> (boolean) invoke(g, "a", toNMS(l.getWorld()), toNMS(l)), getDouble(g, "b"), getInt(g, "l"), getInt(g, "m"));
                case "MoveTowardsRestriction": return new PathfinderMoveTowardsRestriction((Creature) m, getDouble(g, "e"));
                case "MoveTowardsTarget": return new PathfinderMoveTowardsTarget((Creature) m, getDouble(g, "f"), getFloat(g, "g"));
                case "OcelotAttack": return new PathfinderOcelotAttack((Ocelot) m);
                case "OfferFlower": return new PathfinderOfferFlower((IronGolem) m);
                case "Panic": return new PathfinderPanic((Creature) m, getDouble(g, "c"));
                case "Perch": return new PathfinderRideShoulder((Parrot) m);
                case "RandomLookaround": return new PathfinderRandomLook(m);
                case "RandomStroll": return new PathfinderRandomStroll((Creature) m, getDouble(g, "f"), getInt(g, "g"));
                case "RandomStrollLand": return new PathfinderRandomStrollLand((Creature) m, getDouble(PathfinderGoalRandomStroll.class.cast(g), "f"), getFloat(g, "j"));
                case "RandomSwim": return new PathfinderRandomSwim((Creature) m, getDouble(PathfinderGoalRandomStroll.class.cast(g), "f"), getInt(PathfinderGoalRandomStroll.class.cast(g), "g"));
                case "RandomFly": return new PathfinderRandomStrollFlying((Creature) m, getDouble(PathfinderGoalRandomStroll.class.cast(g), "f"));
                case "RemoveBlock": return new PathfinderRemoveBlock((Creature) m, m.getWorld().getBlockAt(fromNMS(getPosWithBlock(getObject(g, "g", Block.class), toNMS(m.getLocation()), toNMS(m.getWorld())), m.getWorld())), getDouble(PathfinderGoalGotoTarget.class.cast(g), "b"));
                case "RestrictSun": return new PathfinderRestrictSun((Creature) m);
                case "Sit": return new PathfinderSit((Tameable) m);
                case "StrollVillage": return new PathfinderRandomStrollToVillage((Creature) m, getDouble(PathfinderGoalRandomStroll.class.cast(g), "f"));
                case "StrollVillageGolem": return new PathfinderRandomStrollInVillage((Creature) m, getDouble(PathfinderGoalRandomStroll.class.cast(g), "f"));
                case "Swell": return new PathfinderSwellCreeper((Creeper) m);
                case "Tame": return new PathfinderTameHorse((AbstractHorse) m);
                case "Tempt": return new PathfinderTempt((Creature) m, getDouble(g, "e"), fromNMS(getObject(g, "m", RecipeItemStack.class)));
                case "UniversalAngerReset": return new PathfinderResetAnger(m, getBoolean(g, "c"));
                case "UseItem": return new PathfinderUseItem(m, fromNMS(getObject(g, "b", net.minecraft.server.v1_13_R2.ItemStack.class)), en -> getObject(g, "c", Predicate.class).test(toNMS(en)), fromNMS(getObject(g, "d", SoundEffect.class)));
                case "ZombieAttack": return new PathfinderZombieAttack((Zombie) m, getDouble(PathfinderGoalMeleeAttack.class.cast(g), "b"), getBoolean(PathfinderGoalMeleeAttack.class.cast(g), "c"));

                // Target
                case "NearestAttackableTarget": return new PathfinderNearestAttackableTarget<>(m, fromNMS(getObject(g, "a", Class.class), LivingEntity.class), getInt(g, "b"), getBoolean(PathfinderGoalTarget.class.cast(g), "f"), getBoolean(PathfinderGoalTarget.class.cast(g), "d"));
                case "DefendVillage": return new PathfinderDefendVillage((IronGolem) m);
                case "HurtByTarget": return new PathfinderHurtByTarget((Creature) m, getEntityTypes(getObject(g, "i", Class[].class)));
                case "OwnerHurtByTarget": return new PathfinderOwnerHurtByTarget((Tameable) m);
                case "OwnerHurtTarget": return new PathfinderOwnerHurtTarget((Tameable) m);

                default: return custom(g);
            }
        } else return custom(g);
    }

}
