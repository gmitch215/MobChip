package me.gamercoder215.mobchip.util;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.navigation.NavigationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@SuppressWarnings("ALL")
public final class MobChipUtil {
	
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

	private static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
	}

	public static ChipUtil getWrapper() {
		try {
			String var10000 = ChipUtil.class.getPackage().getName();
			return (ChipUtil)Class.forName(var10000 + ".ChipUtil" + getServerVersion()).getConstructor().newInstance();
		} catch (Exception var1) {
			var1.printStackTrace();
			return null;
		}
	}

	// Static

	public static ItemStack convert(net.minecraft.world.item.ItemStack item) {
		return getWrapper().convert(item);
	}

	public static net.minecraft.world.item.ItemStack convert(ItemStack item) {
		return getWrapper().convert(item);
	}

	public static SoundEvent convert(Sound s) {
		return getWrapper().convert(s);
	}

	public static ServerPlayer convert(Player p) {
		return getWrapper().convert(p);
	}

	public static Hoglin convert(org.bukkit.entity.Hoglin p) {
		return getWrapper().convert(p);
	}

	public static Piglin convert(org.bukkit.entity.Piglin p) {
		return getWrapper().convert(p);
	}

	public static AgeableMob convert(Ageable a) {
		return getWrapper().convert(a);
	}

	public static ServerLevel convert(World w) {
		return getWrapper().convert(w);
	}

	public static List<LivingEntity> convert(List<org.bukkit.entity.LivingEntity> list) {
		return getWrapper().convert(list);
	}

	public static net.minecraft.world.entity.Entity convert(Entity en) {
		return getWrapper().convert(en);
	}

	public static Mob convert(org.bukkit.entity.Mob m) {
		return getWrapper().convert(m);
	}

	public static LivingEntity convert(org.bukkit.entity.LivingEntity en) {
		return getWrapper().convert(en);
	}

	public static PathfinderMob convert(Creature c) {
		return getWrapper().convert(c);
	}

	public static ResourceLocation convert(NamespacedKey key) {
		return getWrapper().convert(key);
	}

	public static RangedAttribute convert(Attribute a) {
		return getWrapper().convert(a);
	}

	public static DamageSource convert(EntityDamageEvent.DamageCause c) {
		return getWrapper().convert(c);
	}

	public static org.bukkit.entity.LivingEntity convert(LivingEntity en) {
		return getWrapper().convert(en);
	}

	public static org.bukkit.entity.Mob convert(Mob mob) {
		return getWrapper().convert(mob);
	}

	public static Creature convert(PathfinderMob mob) {
		return getWrapper().convert(mob);
	}

	public static EntityType<?> convert(org.bukkit.entity.EntityType type) {
		return getWrapper().convert(type);
	}

	public static BlockPos convert(Location loc) {
		return getWrapper().convert(loc);
	}

	public static Difficulty convert(org.bukkit.Difficulty d) {
		return getWrapper().convert(d);
	}

	public static org.bukkit.Difficulty convert(Difficulty d) {
		return getWrapper().convert(d);
	}

	public static World convert(Level l) {
		return getWrapper().convert(l);
	}

	public static Location convert(World w, BlockPos pos) {
		return getWrapper().convert(w, pos);
	}

	public static <T extends Entity> Class<? extends T> toBukkitClass(Class<T> bukkit, Class<? extends net.minecraft.world.entity.Entity> clazz) {
		return getWrapper().toBukkitClass(bukkit, clazz);
	}

	public static Class<? extends net.minecraft.world.entity.Entity> toNMSClass(Class<? extends Entity> clazz) {
		return getWrapper().toNMSClass(clazz);
	}

	public static Class<? extends LivingEntity> toLivingNMSClass(Class<? extends org.bukkit.entity.LivingEntity> clazz) {
		return getWrapper().toLivingNMSClass(clazz);
	}

	public static Sound convert(SoundEvent s) {
		return getWrapper().convert(s);
	}
}
