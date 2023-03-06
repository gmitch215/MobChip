package me.gamercoder215.mobchip.abstraction;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Lifecycle;
import me.gamercoder215.mobchip.EntityBody;
import me.gamercoder215.mobchip.abstraction.v1_17_R1.*;
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
import me.gamercoder215.mobchip.ai.memories.MemoryStatus;
import me.gamercoder215.mobchip.ai.memories.Unit;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.schedule.Schedule;
import me.gamercoder215.mobchip.ai.sensing.EntitySenses;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.combat.CombatEntry;
import me.gamercoder215.mobchip.combat.CombatLocation;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.core.*;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeRanged;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.gossip.ReputationType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.ambient.EntityAmbient;
import net.minecraft.world.entity.ambient.EntityBat;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.*;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.npc.EntityVillagerTrader;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftSound;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_17_R1.entity.*;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

@SuppressWarnings({"unchecked", "rawtypes", "deprecation"})
public final class ChipUtil1_17_R1 implements ChipUtil {

    public static ItemStack fromNMS(net.minecraft.world.item.ItemStack item) { return CraftItemStack.asBukkitCopy(item); }

    @Override
    public void addCustomPathfinder(CustomPathfinder p, int priority, boolean target) {
        Mob m = p.getEntity();
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.bQ : mob.bP;

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
        PathfinderGoalSelector s = target ? mob.bQ : mob.bP;

        Set<WrappedPathfinder> pF = new HashSet<>();
        s.c().forEach(w -> pF.add(new WrappedPathfinder(fromNMS(w.j()), w.h())));

        return pF;
    }

    @Override
    public Collection<WrappedPathfinder> getRunningGoals(Mob m, boolean target) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.bQ : mob.bP;

        Collection<WrappedPathfinder> l = new HashSet<>();
        s.d().forEach(w -> l.add(new WrappedPathfinder(fromNMS(w.j()), w.h())));

        return l;
    }

    @Override
    public void setFlag(Mob m, Pathfinder.PathfinderFlag flag, boolean target, boolean value) {
        EntityInsentient mob = toNMS(m);
        PathfinderGoalSelector s = target ? mob.bQ : mob.bP;
        s.a(toNMS(flag), value);
    }

    public static final Map<Class<? extends LivingEntity>, Class<? extends EntityLiving>> BUKKIT_NMS_MAP = ImmutableMap.<Class<? extends LivingEntity>, Class<? extends EntityLiving>>builder()
            .put(LivingEntity.class, EntityLiving.class)
            .put(Mob.class, EntityInsentient.class)
            .put(Tameable.class, EntityTameableAnimal.class)

            // Below are not in the root package (LET'S KEEP THEM ALPHABETICAL!!!)
            .put(AbstractHorse.class, EntityHorseAbstract.class)
            .put(AbstractVillager.class, EntityVillagerAbstract.class)
            .put(Ambient.class, EntityAmbient.class)
            .put(Axolotl.class, net.minecraft.world.entity.animal.axolotl.Axolotl.class)
            .put(Bat.class, EntityBat.class)
            .put(Bee.class, EntityBee.class)
            .put(Blaze.class, EntityBlaze.class)
            .put(Cat.class, EntityCat.class)
            .put(CaveSpider.class, EntityCaveSpider.class)
            .put(Chicken.class, EntityChicken.class)
            .put(Cod.class, EntityCod.class)
            .put(Cow.class, EntityCow.class)
            .put(Creeper.class, EntityCreeper.class)
            .put(Dolphin.class, EntityDolphin.class)
            .put(Donkey.class, EntityHorseDonkey.class)
            .put(Drowned.class, EntityDrowned.class)
            .put(ElderGuardian.class, EntityGuardianElder.class)
            .put(EnderDragon.class, EntityEnderDragon.class)
            .put(Enderman.class, EntityEnderman.class)
            .put(Endermite.class, EntityEndermite.class)
            .put(Evoker.class, EntityEvoker.class)
            .put(Fox.class, EntityFox.class)
            .put(Ghast.class, EntityGhast.class)
            .put(Giant.class, EntityGiantZombie.class)
            .put(Goat.class, net.minecraft.world.entity.animal.goat.Goat.class)
            .put(Golem.class, EntityGolem.class)
            .put(Guardian.class, EntityGuardian.class)
            .put(Hoglin.class, EntityHoglin.class)
            .put(Horse.class, EntityHorse.class)
            .put(HumanEntity.class, EntityHuman.class)
            .put(Husk.class, EntityZombieHusk.class)
            .put(IronGolem.class, EntityIronGolem.class)
            .put(Llama.class, EntityLlama.class)
            .put(MagmaCube.class, EntityMagmaCube.class)
            .put(Mule.class, EntityHorseMule.class)
            .put(MushroomCow.class, EntityMushroomCow.class)
            .put(Ocelot.class, EntityOcelot.class)
            .put(Panda.class, EntityPanda.class)
            .put(Parrot.class, EntityParrot.class)
            .put(Phantom.class, EntityPhantom.class)
            .put(Pig.class, EntityPig.class)
            .put(PigZombie.class, EntityPigZombie.class)
            .put(Piglin.class, EntityPiglin.class)
            .put(PiglinBrute.class, EntityPiglinBrute.class)
            .put(Pillager.class, EntityPillager.class)
            .put(Player.class, EntityHuman.class)
            .put(PolarBear.class, EntityPolarBear.class)
            .put(PufferFish.class, EntityPufferFish.class)
            .put(Rabbit.class, EntityRabbit.class)
            .put(Raider.class, EntityRaider.class)
            .put(Ravager.class, EntityRavager.class)
            .put(Salmon.class, EntitySalmon.class)
            .put(Sheep.class, EntitySheep.class)
            .put(Shulker.class, EntityShulker.class)
            .put(Silverfish.class, EntitySilverfish.class)
            .put(Skeleton.class, EntitySkeleton.class)
            .put(SkeletonHorse.class, EntityHorseSkeleton.class)
            .put(Slime.class, EntitySlime.class)
            .put(Snowman.class, EntitySnowman.class)
            .put(Spider.class, EntitySpider.class)
            .put(Squid.class, EntitySquid.class)
            .put(Stray.class, EntitySkeletonStray.class)
            .put(Strider.class, EntityStrider.class)
            .put(TraderLlama.class, EntityLlamaTrader.class)
            .put(TropicalFish.class, EntityTropicalFish.class)
            .put(Turtle.class, EntityTurtle.class)
            .put(Vex.class, EntityVex.class)
            .put(Villager.class, EntityVillager.class)
            .put(Vindicator.class, EntityVindicator.class)
            .put(WanderingTrader.class, EntityVillagerTrader.class)
            .put(Witch.class, EntityWitch.class)
            .put(Wither.class, EntityWither.class)
            .put(WitherSkeleton.class, EntitySkeletonWither.class)
            .put(Wolf.class, EntityWolf.class)
            .put(Zoglin.class, EntityZoglin.class)
            .put(Zombie.class, EntityZombie.class)
            .put(ZombieHorse.class, EntityHorseZombie.class)
            .put(ZombieVillager.class, EntityZombieVillager.class)
            .build();

    public static Class<? extends EntityLiving> toNMS(Class<? extends LivingEntity> clazz) {
        if (BUKKIT_NMS_MAP.containsKey(clazz)) return BUKKIT_NMS_MAP.get(clazz);

        Class<? extends EntityLiving> nms = null;
        try {
            // Sometimes we can get lucky...
            nms = Class.forName(EntityInsentient.class.getPackageName() + "." + clazz.getSimpleName()).asSubclass(EntityLiving.class);

            // Some Pre-Mojang Mapping Classes start with "Entity"
            if (nms == null) nms = Class.forName(EntityInsentient.class.getPackageName() + "." + "Entity" + clazz.getSimpleName()).asSubclass(EntityLiving.class);
        } catch (ClassNotFoundException ignored) {}

        if (nms == null) throw new AssertionError("Could not convert " + clazz.getName() + " to NMS class");

        return nms;
    }
    public static net.minecraft.world.item.ItemStack toNMS(ItemStack i) {
        return CraftItemStack.asNMSCopy(i);
    }

    public static SoundEffect toNMS(Sound s) {
        return CraftSound.getSoundEffect(s);
    }

    public static PathfinderGoal toNMS(Pathfinder b) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);

        String name = b.getInternalName().startsWith("PathfinderGoal") ? b.getInternalName().replace("PathfinderGoal", "") : b.getInternalName();

        return switch (name) {
            case "AvoidTarget" -> {
                PathfinderAvoidEntity p = (PathfinderAvoidEntity) b;
                Predicate<LivingEntity> avoidP = p.getAvoidPredicate() == null ?
                        en -> true :
                        en -> p.getAvoidPredicate().test(en);
                Predicate<LivingEntity> avoidingP = p.getAvoidingPredicate() == null ?
                        en -> true :
                        en -> p.getAvoidingPredicate().test(en);

                yield new PathfinderGoalAvoidTarget<>((EntityCreature) m, toNMS(p.getFilter()), en -> avoidP.test(fromNMS(en)), p.getMaxDistance(), p.getSpeedModifier(), p.getSprintModifier(), en -> avoidingP.test(fromNMS(en)));
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
                yield new PathfinderGoalFollowParent((EntityAnimal) m, p.getSpeedModifier());
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
        PathfinderGoalSelector s = target ? m.bQ : m.bP;

        final PathfinderGoal g = toNMS(b);

        if (g == null) return;
        s.a(priority, g);
    }

    @Override
    public void removePathfinder(Pathfinder b, boolean target) {
        Mob mob = b.getEntity();
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.bQ : m.bP;

        final PathfinderGoal g = toNMS(b);
        if (g == null) return;
        s.a(g);
    }

    @Override
    public void clearPathfinders(Mob mob, boolean target) {
        EntityInsentient m = toNMS(mob);
        PathfinderGoalSelector s = target ? m.bQ : m.bP;
        s.a();
    }

    public static BehaviorResult.Status fromNMS(Behavior.Status status) {
        if (status == Behavior.Status.a) return BehaviorResult.Status.STOPPED;
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
            Constructor<?> c = bClass.getConstructor(ChipUtil.getArgTypes(args));
            Behavior<? super EntityLiving> b = (Behavior<? super EntityLiving>) c.newInstance(args);
            return new BehaviorResult1_17_R1(b, nms);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
            return null;
        }
    }

    public static EntityPlayer toNMS(Player p) { return ((CraftPlayer) p).getHandle(); }

    @Override
    public Attribute getDefaultAttribute(String s) {
        return new Attribute1_17_R1((AttributeRanged) IRegistry.al.get(new MinecraftKey(s)));
    }

    public static net.minecraft.world.entity.schedule.Activity toNMS(Activity a) {
        return IRegistry.au.get(new MinecraftKey(a.getKey().getKey()));
    }

    public static Activity fromNMS(net.minecraft.world.entity.schedule.Activity a) {
        MinecraftKey key = IRegistry.au.getKey(a);
        return Activity.getByKey(NamespacedKey.minecraft(key.getKey()));
    }

    public static Schedule fromNMS(net.minecraft.world.entity.schedule.Schedule s) {
        Schedule.Builder b = Schedule.builder();
        for (int i = 0; i < 24000; i++) {
            if (s.a(i) == null) continue;
            Activity a = fromNMS(s.a(i));
            b.addActivity(i, a);
        }

        return b.build();
    }

    public static net.minecraft.world.entity.schedule.Schedule toNMS(Schedule s) {
        net.minecraft.world.entity.schedule.ScheduleBuilder b = new ScheduleBuilder(new net.minecraft.world.entity.schedule.Schedule());
        for (int i = 0; i < 24000; i++) {
            if (!s.contains(i)) continue;
            net.minecraft.world.entity.schedule.Activity a = toNMS(s.get(i));
            b.a(i, a);
        }

        return b.a();
    }

    public static <T extends EntityLiving> Behavior<T> toNMS(Consumer<Mob> en) {
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

    public static DamageSource toNMS(EntityDamageEvent.DamageCause c) {
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

    public static AbstractDragonController toNMS(CustomPhase c) {
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

            @Override
            public void d() { c.start(); }

            @Override
            public void e() { c.stop(); }

            @Override
            public boolean a() { return c.isSitting(); }

            @Override
            public void b() { c.clientTick(); }

            @Override
            public void c() { c.serverTick(); }

            @Override
            public void a(EntityEnderCrystal crystal, BlockPosition pos, DamageSource s, EntityHuman p) {
                EnderCrystal bCrystal = (EnderCrystal) crystal.getBukkitEntity();
                c.onCrystalDestroyed(bCrystal, fromNMS(s), p == null ? null : Bukkit.getPlayer(p.getUniqueID()));
            }

            @Override
            public Vec3D g() {
                Location l = c.getTargetLocation();
                return new Vec3D(l.getX(), l.getY(), l.getZ());
            }
            @Override
            public float f() { return c.getFlyingSpeed(); }

            @Override
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

    public static EntityItem toNMS(org.bukkit.entity.Item i) {
        return (EntityItem) ((CraftItem) i).getHandle();
    }

    public static EntityLiving toNMS(LivingEntity en) {
        return ((CraftLivingEntity) en).getHandle();
    }

    public static Object toNMS(String key, Object value) {
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
        else if (value instanceof Unit u) nmsValue = net.minecraft.util.Unit.a;
        else nmsValue = value;

        return nmsValue;
    }

    public static Object fromNMS(Mob m, String key, Object nmsValue) {
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
        else if (value instanceof net.minecraft.util.Unit u) value = Unit.INSTANCE;
        else value = nmsValue;

        return value;
    }

    public static EntityDamageEvent.DamageCause fromNMS(DamageSource c) {
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
    public MemoryStatus getMemoryStatus(Mob mob, Memory<?> m) {
        EntityInsentient nms = toNMS(mob);
        MemoryModuleType<?> nmsM = toNMS(m);

        if (nms.getBehaviorController().a(nmsM, net.minecraft.world.entity.ai.memory.MemoryStatus.a)) return MemoryStatus.PRESENT;
        if (nms.getBehaviorController().a(nmsM, net.minecraft.world.entity.ai.memory.MemoryStatus.b)) return MemoryStatus.ABSENT;

        return MemoryStatus.REGISTERED;
    }

    @Override
    public void setMemory(Mob mob, String memoryKey, Object value) {
        EntityInsentient nms = toNMS(mob);
        MemoryModuleType type = IRegistry.ar.get(new MinecraftKey(memoryKey));
        Object nmsValue = toNMS(memoryKey, value);

        nms.getBehaviorController().setMemory(type, nmsValue);
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
        return nms.fh();
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
        BlockPosition c = nms.fi();
        return new Location(m.getWorld(), c.getX(), c.getY(), c.getZ());
    }

    @Override
    public int getRestrictionRadius(Mob m) {
        EntityInsentient nms = toNMS(m);
        return ((int) nms.fj()) < 0 ? Integer.MAX_VALUE : (int) nms.fj();
    }

    @Override
    public boolean hasRestriction(Mob m) {
        EntityInsentient nms = toNMS(m);
        return nms.fl();
    }

    @Override
    public boolean canSee(Mob m, Entity en) {
        EntityInsentient nms = toNMS(m);
        return nms.getEntitySenses().a(toNMS(en));
    }

    public static net.minecraft.world.entity.Entity toNMS(Entity en) {
        return ((CraftEntity) en).getHandle();
    }

    public static VillagerProfession toNMS(Villager.Profession p) {
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

    public static <T extends Entity> Class<? extends T> fromNMS(Class<? extends net.minecraft.world.entity.Entity> clazz, Class<T> cast) {
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

    public static EntityInsentient toNMS(Mob m) { return ((CraftMob) m).getHandle(); }

    public static EntityType[] getEntityTypes(Class<?>... nms) {
        List<EntityType> types = new ArrayList<>();
        for (Class<?> c : nms) {

            Class<? extends Entity> bukkit = fromNMS((Class<? extends net.minecraft.world.entity.Entity>) c, Entity.class);
            for (EntityType t : EntityType.values()) {
                if (t.getEntityClass() != null && t.getEntityClass().isAssignableFrom(bukkit)) types.add(t);
            }
        }
        return types.toArray(new EntityType[0]);
    }

    public static EnumDifficulty toNMS(org.bukkit.Difficulty d) {
        return switch (d) {
            case PEACEFUL -> EnumDifficulty.a;
            case EASY -> EnumDifficulty.b;
            case NORMAL -> EnumDifficulty.c;
            case HARD -> EnumDifficulty.d;
        };
    }

    public static org.bukkit.Difficulty fromNMS(EnumDifficulty d) {
        return switch (d) {
            case a -> org.bukkit.Difficulty.PEACEFUL;
            case b -> org.bukkit.Difficulty.EASY;
            case c -> org.bukkit.Difficulty.NORMAL;
            case d -> org.bukkit.Difficulty.HARD;
        };
    }

    public static EntityCreature toNMS(Creature c) { return ((CraftCreature) c).getHandle();}

    public static PathfinderGoal.Type toNMS(Pathfinder.PathfinderFlag f) {
        return switch (f) {
            case MOVEMENT -> PathfinderGoal.Type.a;
            case JUMPING -> PathfinderGoal.Type.c;
            case TARGETING -> PathfinderGoal.Type.d;
            case LOOKING -> PathfinderGoal.Type.b;
        };
    }

    public static Pathfinder.PathfinderFlag fromNMS(PathfinderGoal.Type f) {
        return switch (f) {
            case a -> Pathfinder.PathfinderFlag.MOVEMENT;
            case c -> Pathfinder.PathfinderFlag.JUMPING;
            case d -> Pathfinder.PathfinderFlag.TARGETING;
            case b -> Pathfinder.PathfinderFlag.LOOKING;
        };
    }

    public static float getFloat(PathfinderGoal o, String name) { return getObject(o, name, Float.class); }

    public static double getDouble(PathfinderGoal o, String name) {
        return getObject(o, name, Double.class);
    }

    public static boolean getBoolean(PathfinderGoal o, String name) {
        return getObject(o, name, Boolean.class);
    }

    public static int getInt(PathfinderGoal o, String name) {
        return getObject(o, name, Integer.class);
    }

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
            ChipUtil.printStackTrace(e);
        }

        return null;
    }

    public static Mob fromNMS(EntityInsentient m) { return (Mob) m.getBukkitEntity(); }

    public static World fromNMS(net.minecraft.world.level.World l) { return l.getWorld(); }

    public static WorldServer toNMS(World w) { return ((CraftWorld) w).getHandle(); }

    public static BlockPosition toNMS(Location l) { return new BlockPosition(l.getX(), l.getY(), l.getZ()); }

    public static List<ItemStack> fromNMS(RecipeItemStack in) { return Arrays.stream(in.c).map(CraftItemStack::asBukkitCopy).collect(Collectors.toList()); }

    public static Sound fromNMS(SoundEffect s) { return CraftSound.getBukkit(s); }

    public static Mob getEntity(PathfinderGoal g) {
        // For no discernible reason, the Mob field in PathfinderGoalDoorInteract
        // is not final, unlike the mob fields in every other pathfinder.
        // Since PathfinderGoalDoorInteract and subclasses each only have one mob field,
        // we can simply ignore the "final" check in this case.
        boolean ignoreNonFinal = g instanceof PathfinderGoalDoorInteract;
        try {
            Class<? extends PathfinderGoal> clazz = g.getClass();

            while (clazz.getSuperclass() != null) {
                for (Field f : clazz.getDeclaredFields()) {
                    f.setAccessible(true);
                    if (EntityInsentient.class.isAssignableFrom(f.getType()) && (ignoreNonFinal || Modifier.isFinal(f.getModifiers()))) {
                        return fromNMS((EntityInsentient) f.get(g));
                    }
                }

                if (PathfinderGoal.class.isAssignableFrom(clazz.getSuperclass())) clazz = (Class<? extends PathfinderGoal>) clazz.getSuperclass();
                else break;
            }
            return null;
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
            return null;
        }
    }

    public static Object invoke(PathfinderGoal g, String method, Object... args) {
        try {
            Method m = g.getClass().getDeclaredMethod(method);
            m.setAccessible(true);

            return m.invoke(g, args);
        } catch (Exception e) {
            ChipUtil.printStackTrace(e);
            return null;
        }
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

    public static PathfinderGoal custom(CustomPathfinder p) {
        CustomGoal1_17_R1 g = new CustomGoal1_17_R1(p);
        EnumSet<PathfinderGoal.Type> flags = EnumSet.noneOf(PathfinderGoal.Type.class);
        Arrays.stream(p.getFlags()).map(ChipUtil1_17_R1::toNMS).forEach(flags::add);
        g.a(flags);
        return g;
    }

    public static BlockPosition getPosWithBlock(net.minecraft.world.level.block.Block block, BlockPosition bp, IBlockAccess g) {
        if (g.getType(bp).a(block)) return bp;
        else {
            BlockPosition[] bp1 = new BlockPosition[]{new BlockPosition(bp.down()),  bp.west(), bp.east(), bp.north(), bp.south(), new BlockPosition(bp.up())};
            for (BlockPosition bps : bp1) if (g.getType(bps).a(block)) return bps;
            return null;
        }
    }

    public static Location fromNMS(BlockPosition p, World w) { return new Location(w, p.getX(), p.getY(), p.getZ()); }

    private Pathfinder fromNMS(PathfinderGoal g) {
        if (g instanceof CustomGoal1_17_R1 custom) {
            return custom.getPathfinder();
        }

        Mob m = getEntity(g);
        String name = g.getClass().getSimpleName();

        if (name.startsWith("PathfinderGoal")) {
            name = name.replace("PathfinderGoal", "");

            return switch (name) {
                case "AvoidTarget" -> new PathfinderAvoidEntity<>((Creature) m, fromNMS(getObject(g, "f", Class.class), LivingEntity.class), getFloat(g, "c"), getDouble(g, "i"), getDouble(g, "j"), en -> getObject(g, "g", Predicate.class).test(toNMS(en)), en -> getObject(g, "h", Predicate.class).test(toNMS(en)));
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
                case "JumpOnBlock" -> new PathfinderCatOnBlock((Cat) m, getDouble(g, "b"));
                case "LlamaFollow" -> new PathfinderLlamaFollowCaravan((Llama) m, getDouble(g, "b"));
                case "LookAtPlayer" -> new PathfinderLookAtEntity<>(m, fromNMS(getObject(g, "f", Class.class), LivingEntity.class), getFloat(g, "d"), getFloat(g, "e"), getBoolean(g, "i"));
                case "LookAtTradingPlayer" -> new PathfinderLookAtTradingPlayer((AbstractVillager) m);
                case "MeleeAttack" -> new PathfinderMeleeAttack((Creature) m, getDouble(g, "b"), getBoolean(g, "c"));
                case "MoveThroughVillage" -> new PathfinderMoveThroughVillage((Creature) m, getBoolean(g, "a"), getDouble(g, "b"), getInt(g, "g"), getBoolean(g, "e"));
                case "NearestVillage" -> new PathfinderRandomStrollThroughVillage((Creature) m, getInt(g, "b"));
                case "GotoTarget" -> new PathfinderMoveToBlock((Creature) m, l -> (boolean) invoke(g, "a", toNMS(l.getWorld()), toNMS(l)), getDouble(g, "b"), getInt(g, "l"), getInt(g, "m"));
                case "Raid" -> new PathfinderMoveToRaid((Raider) m);
                case "MoveTowardsRestriction" -> new PathfinderMoveTowardsRestriction((Creature) m, getDouble(g, "e"));
                case "MoveTowardsTarget" -> new PathfinderMoveTowardsTarget((Creature) m, getDouble(g, "f"), getFloat(g, "g"));
                case "OcelotAttack" -> new PathfinderOcelotAttack(m);
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

    public static MinecraftKey toNMS(NamespacedKey key) {
        return CraftNamespacedKey.toMinecraft(key);
    }

    @Override
    public Attribute getAttribute(NamespacedKey key) {
        AttributeBase a = IRegistry.al.get(toNMS(key));
        if (!(a instanceof AttributeRanged)) return null;
        return new Attribute1_17_R1((AttributeRanged) a);
    }

    @NotNull
    private AttributeInstance1_17_R1 getOrCreateInstance(Mob m, Attribute a) {
        EntityInsentient nms = toNMS(m);
        AttributeMapBase map = nms.getAttributeMap();
        AttributeBase nmsA = IRegistry.al.get(toNMS(a.getKey()));

        AttributeModifiable handle = toNMS(m).getAttributeInstance(nmsA);
        if (handle != null) return new AttributeInstance1_17_R1(a, handle);

        try {
            Field attributesF = AttributeMapBase.class.getDeclaredField("b");
            attributesF.setAccessible(true);
            Map<AttributeBase, AttributeModifiable> attributes = (Map<AttributeBase, AttributeModifiable>) attributesF.get(map);

            handle = new AttributeModifiable(nmsA, ignored -> {});
            attributes.put(nmsA, handle);

            return new AttributeInstance1_17_R1(a, handle);
        } catch (ReflectiveOperationException e) {
            ChipUtil.printStackTrace(e);
        }

        throw new RuntimeException("Failed to create AttributeInstance");
    }

    @Override
    public AttributeInstance getAttributeInstance(Mob m, Attribute a) {
        AttributeBase nmsAttribute = IRegistry.al.get(toNMS(a.getKey()));
        return getOrCreateInstance(m, a);
    }

    public static ReputationType toNMS(GossipType t) {
        return ReputationType.a(t.getKey().getKey());
    }

    public static GossipType fromNMS(ReputationType t) {
        return GossipType.getByKey(NamespacedKey.minecraft(t.i));
    }

    @Override
    public EntityGossipContainer getGossipContainer(Villager v) {
        return new EntityGossipContainer1_17_R1(v);
    }

    public static Entity fromNMS(net.minecraft.world.entity.Entity en) {
        return en.getBukkitEntity();
    }

    public static CombatEntry fromNMS(Mob m, net.minecraft.world.damagesource.CombatEntry en) {
        return new CombatEntry(m, fromNMS(en.a()), en.b(), en.d(), en.c(), en.j(), en.g() == null ? null : CombatLocation.getByKey(NamespacedKey.minecraft(en.g())), en.i() == null ? null : fromNMS(en.i()));
    }

    public static net.minecraft.world.damagesource.CombatEntry toNMS(CombatEntry en) {
        return new net.minecraft.world.damagesource.CombatEntry(toNMS(en.getCause()), en.getCombatTime(), en.getHealthBeforeDamage(), en.getDamage(), en.getLocation().getKey().getKey(), en.getFallDistance());
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
            ChipUtil.printStackTrace(e);
        }
    }

    public static EntityEnderDragon toNMS(EnderDragon dragon) {
        return ((CraftEnderDragon) dragon).getHandle();
    }

    public static Location fromNMS(IPosition p, World w) { return new Location(w, p.getX(), p.getY(), p.getZ()); }

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

    public static MemoryModuleType<?> toNMS(Memory<?> mem) {
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

    @Override
    public EntityNBT getNBTEditor(Mob m) {
        return new EntityNBT1_17_R1(m);
    }

    public static net.minecraft.world.entity.ai.sensing.Sensor<?> toNMS(Sensor<?> s) {
        if (s instanceof SensorDefault1_17_R1) return ((SensorDefault1_17_R1) s).getHandle();
        return new Sensor1_17_R1(s);
    }

    public static SensorType<?> toNMSType(Sensor<?> s) {
        try {
            Constructor<SensorType> c = SensorType.class.getConstructor(Supplier.class);
            c.setAccessible(true);

            Supplier<net.minecraft.world.entity.ai.sensing.Sensor<?>> sup = () -> toNMS(s);

            return (SensorType<?>) c.newInstance(sup);
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().severe(e.getMessage());
            for (StackTraceElement st : e.getStackTrace()) Bukkit.getLogger().severe(st.toString());
        }

        return null;
    }

    public static Sensor<?> fromNMS(net.minecraft.world.entity.ai.sensing.Sensor<?> type) {
        if (type instanceof Sensor1_17_R1) return ((Sensor1_17_R1) type).getSensor();
        return new SensorDefault1_17_R1(type);
    }

    public static NamespacedKey fromNMS(MinecraftKey loc) {
        return new NamespacedKey(loc.getNamespace(), loc.getKey());
    }

    public static Memory<?> fromNMS(MemoryModuleType<?> memory) {
        return EntityMemory.getByKey(fromNMS(IRegistry.ar.getKey(memory)));
    }

    public static MemoryModuleType<?> getMemory(NamespacedKey key) {
        return IRegistry.ar.get(new MinecraftKey(key.getNamespace(), key.getKey()));
    }

    @Override
    public void registerSensor(Sensor<?> s) {
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        IRegistryWritable<SensorType<?>> writable = server.getCustomRegistry().b(IRegistry.G);
        ResourceKey<SensorType<?>> nmsKey = ResourceKey.a(IRegistry.G, toNMS(s.getKey()));
        writable.a(nmsKey, toNMSType(s), Lifecycle.stable());
    }

    @Override
    public boolean existsSensor(NamespacedKey key) {
        return IRegistry.as.c(new MinecraftKey(key.getNamespace(), key.getKey()));
    }

    @Override
    public Sensor<?> getSensor(NamespacedKey key) {
        return fromNMS(IRegistry.as.get(toNMS(key)).a());
    }

    @Override
    public EntitySenses getSenses(Mob m) {
        return new EntitySenses1_17_R1(m);
    }

    @Override
    public EnderCrystal getNearestCrystal(EnderDragon d) {
        EntityEnderDragon nms = toNMS(d);
        if (nms.bY == null) return null;
        return (EnderCrystal) nms.bY.getBukkitEntity();
    }

}
