package me.gamercoder215.mobchip.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.navigation.NavigationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Node;

/**
 * Utility class for Converting Bukkit Classes to NMS Classes and vice-versa
 */
public final class ChipConversions {

	private ChipConversions() {};
		
	
	public static Class<? extends net.minecraft.world.entity.Entity> toNMSClass(Class<? extends Entity> clazz) {
		try {
			return clazz.getDeclaredField("entity").getType().asSubclass(net.minecraft.world.entity.Entity.class);
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
	
	public static ServerPlayer convertType(Player p) {
		try {
			return ChipConversions.<ServerPlayer>getHandle(p);
		} catch (Exception e) {
			return null;
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
			return ChipConversions.<ServerLevel>getHandle(w);
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
			return ChipConversions.<net.minecraft.world.entity.Entity>getHandle(en);
		} catch (Exception e) {
			return null;
		}	
	}
	
	public static net.minecraft.world.entity.LivingEntity convertType(LivingEntity en) {
		try {
			return ChipConversions.<net.minecraft.world.entity.LivingEntity>getHandle(en);
		} catch (Exception e) {
			return null;
		}	
	}
	
	public static net.minecraft.world.entity.Mob convertType(Mob en) {
		try {
			return ChipConversions.<net.minecraft.world.entity.Mob>getHandle(en);
		} catch (Exception e) {
			return null;
		}	
	}
	
	public static net.minecraft.world.entity.PathfinderMob convertType(Creature en) {
		try {
			return ChipConversions.<net.minecraft.world.entity.PathfinderMob>getHandle(en);
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

	/**
	 * Converts a Block Position to a Location.
	 * @param w World to use
	 * @param pos Block Position to convert
	 * @return Converted Location
	 */
	public static Location toBukkit(World w, BlockPos pos) {
		return new Location(w, pos.getX(), pos.getY(), pos.getZ());
	}

	public static NavigationNode wrapNode(@NotNull Node node) {
		return new NavigationNode(node.x, node.y, node.z);
	}

	public static Class<? extends Pathfinder> wrapGoal(Class<? extends Goal> class1) {
		try {
			String className = class1.getSimpleName().replace("Goal", "");

			if (class1.getPackageName().contains("ai.goal.target")) {
				return Class.forName("me.gamercoder215.mobchip.ai.goal.target." + className).asSubclass(Pathfinder.class);
			} else {
				return Class.forName("me.gamercoder215.mobchip.ai.goal." + className).asSubclass(Pathfinder.class);
			}
		} catch (ClassNotFoundException e) {
			return null;
	 	} catch (Exception e) {
			e.printStackTrace();
			return null;
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
