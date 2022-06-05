package me.gamercoder215.mobchip.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.navigation.NavigationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;

/**
 * Utility class for Converting Bukkit Classes to NMS Classes and vice-versa
 */
@SuppressWarnings("ALL")
public final class ChipConversions {

	private ChipConversions() {}

	public static Class<? extends net.minecraft.world.entity.Entity> toNMSClass(Class<? extends Entity> clazz) {
		try {
			return clazz.getDeclaredField("entity").getType().asSubclass(net.minecraft.world.entity.Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<? extends net.minecraft.world.entity.LivingEntity> toLivingNMSClass(Class<? extends LivingEntity> clazz) {
		try {
			return clazz.getDeclaredField("entity").getType().asSubclass(net.minecraft.world.entity.LivingEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T extends Entity> Class<? extends T> toBukkitClass(Class<T> bukkit, Class<? extends net.minecraft.world.entity.Entity> clazz) {
		try {
			Method m = clazz.getDeclaredMethod("getBukkitEntity");
			m.setAccessible(true);

			return m.getReturnType().asSubclass(bukkit);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static net.minecraft.world.item.ItemStack convertType(ItemStack p) {
		try {
			Class<?> craftStack = craftClass("inventory.CraftItemStack");

			return (net.minecraft.world.item.ItemStack) craftStack.getDeclaredMethod("asNMSCopy", ItemStack.class).invoke(null, p);
		} catch (Exception e) {
			return null;
		}
	}

	public static ItemStack convertType(net.minecraft.world.item.ItemStack p) {
		try {
			Class<?> craftStack = craftClass("inventory.CraftItemStack");

			return (ItemStack) craftStack.getDeclaredMethod("asBukkitCopy", net.minecraft.world.item.ItemStack.class).invoke(null, p);
		} catch (Exception e) {
			return null;
		}
	}

	public static SoundEvent convertType(Sound p) {
		try {
			Class<?> craftStack = craftClass("CraftSound");

			return (SoundEvent) craftStack.getDeclaredMethod("getSoundEffect", Sound.class).invoke(null, p);
		} catch (Exception e) {
			return null;
		}
	}

	public static Sound convertType(SoundEvent p) {
		try {
			Class<?> craftStack = craftClass("CraftSound");

			return (Sound) craftStack.getDeclaredMethod("getBukkit", SoundEvent.class).invoke(null, p);
		} catch (Exception e) {
			return null;
		}
	}

	public static ServerPlayer convertType(Player p) {
		try {
			return ChipConversions.getHandle(p);
		} catch (Exception e) {
			return null;
		}
	}

	public static net.minecraft.world.entity.monster.hoglin.Hoglin convertType(Hoglin p) {
		try {
			return ChipConversions.getHandle(p);
		} catch (Exception e) {
			return null;
		}
	}

	public static net.minecraft.world.entity.monster.piglin.Piglin convertType(Piglin p) {
		try {
			return ChipConversions.getHandle(p);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static AgeableMob convertType(Ageable a) {
		try {
			return ChipConversions.getHandle(a);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static DamageSource convertType(DamageCause c) {
		switch (c) {
			case FIRE: return DamageSource.IN_FIRE;
			case LIGHTNING: return DamageSource.LIGHTNING_BOLT;
			case FIRE_TICK: return DamageSource.ON_FIRE;
			case SUFFOCATION: return DamageSource.IN_WALL;
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
			case FREEZE: return DamageSource.FREEZE;
			case DRYOUT: return DamageSource.DRY_OUT;
			default: return DamageSource.GENERIC;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getHandle(Object o) {
		try {
			return (T) o.getClass().getMethod("getHandle").invoke(o);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static ServerLevel convertType(World w) {
		try {
			return ChipConversions.getHandle(w);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static List<net.minecraft.world.entity.LivingEntity> convertType(List<LivingEntity> list) {
		List<net.minecraft.world.entity.LivingEntity> l = new ArrayList<>();
		
		for (LivingEntity en : list) l.add(ChipConversions.convertType(en));
		
		return l;
	}
	
	public static LivingEntity convertType(net.minecraft.world.entity.LivingEntity en) {
		return (LivingEntity) en.getBukkitEntity();
	}
	
	public static net.minecraft.world.entity.Entity convertType(Entity en) {
		try {
			return ChipConversions.getHandle(en);
		} catch (Exception e) {
			return null;
		}	
	}

	public static Mob convertType(net.minecraft.world.entity.Mob m) {
		return (Mob) m.getBukkitEntity();
	}
	
	public static net.minecraft.world.entity.LivingEntity convertType(LivingEntity en) {
		try {
			return ChipConversions.getHandle(en);
		} catch (Exception e) {
			return null;
		}	
	}
	
	public static net.minecraft.world.entity.Mob convertType(Mob en) {
		try {
			return ChipConversions.getHandle(en);
		} catch (Exception e) {
			return null;
		}	
	}
	
	public static PathfinderMob convertType(Creature en) {
		try {
			return ChipConversions.getHandle(en);
		} catch (Exception e) {
			return null;
		}	
	}

	public static Creature convertType(PathfinderMob en) {
		try {
			return (Creature) en.getBukkitEntity();
		} catch (Exception e) {
			return null;
		}
	}

	public static EntityType<?> convertType(org.bukkit.entity.EntityType type) {
		return Registry.ENTITY_TYPE.get(convertType(type.getKey()));
	}
	
	public static ResourceLocation convertType(NamespacedKey key) {
		try {
			return (ResourceLocation) craftClass("util.CraftNamespacedKey").getMethod("toMinecraft", NamespacedKey.class).invoke(null, key);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static RangedAttribute convertType(Attribute att) {
		try {
			return (RangedAttribute) craftClass("attribute.CraftAttributeMap").getMethod("toMinecraft", Attribute.class).invoke(null, att);
		} catch (Exception e) {
			return null;
		}
	}
	
	private static Class<?> craftClass(String suffix) {
		try {
			String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
			return Class.forName("org.bukkit.craftbukkit." + version + "." + suffix);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static BlockPos convertType(Location loc) {
		return new BlockPos(loc.getX(), loc.getY(), loc.getZ());
	}

	public static net.minecraft.world.Difficulty convertType(Difficulty d) {
		return net.minecraft.world.Difficulty.byName(d.name().toLowerCase());
	}
	
	public static Difficulty convertType(net.minecraft.world.Difficulty d) {
		return Difficulty.valueOf(d.getKey().toUpperCase());
	}
	
	public static Pathfinder wrapGoal(Goal g) {
		try {
			Constructor<? extends Pathfinder> constr = wrapGoal(g.getClass()).getConstructor(g.getClass()); 
			constr.setAccessible(true);
			return constr.newInstance(g);
		} catch (ExceptionInInitializerError e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static World convertType(Level l) {
		return l.getWorld();
	}

	public static Location convertType(World w, BlockPos pos) {
		return new Location(w, pos.getX(), pos.getY(), pos.getZ());
	}

	public static NavigationNode wrapNode(@NotNull Node node) {
		return new NavigationNode(node.x, node.y, node.z);
	}

    private static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class<?>> classes = new ArrayList<>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
    	return classes.toArray(new Class[0]);
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

	public static List<Class<? extends Pathfinder>> getPathfinders() {
		try {
			Class<?>[] arr1 = getClasses("me.gamercoder215.mobchip.ai.goal");
			Class<?>[] arr2 = getClasses("me.gamercoder215.mobchip.ai.goal.target");

			List<Class<? extends Pathfinder>> list = new ArrayList<>();
			for (Class<?> clazz : arr1) try {
				list.add(clazz.asSubclass(Pathfinder.class));
			} catch (ClassCastException ignored) {
			}
			for (Class<?> clazz : arr2) try {
				list.add(clazz.asSubclass(Pathfinder.class));
			} catch (ClassCastException ignored) {
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static Class<? extends Pathfinder> wrapGoal(Class<? extends Goal> class1) {
		try {
			if (class1.equals(Goal.class)) return Pathfinder.class;
			for (Class<? extends Pathfinder> clazz : getPathfinders()) {
				if (clazz.getDeclaredMethod("getHandle").getReturnType().getCanonicalName().equals(class1.getCanonicalName())) return clazz;				
			}
			return null;
		} catch (Exception e) {
			return Pathfinder.class;
		}
	}

	public static Entity getById(int id) {
		for (World w : Bukkit.getWorlds()) {
			for (Entity en : w.getEntities()) {
				if (en.getEntityId() == id) return en;
			}
		}
		return null;
	}
}
