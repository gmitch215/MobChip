package me.gamercoder215.mobchip.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.navigation.NavigationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Node;

/**
 * Utility class for Converting Bukkit Classes to NMS Classes and vice-versa
 */
public final class ChipConversions {

	private ChipConversions() {};
		
	/**
	 * Converts a Bukkit Class into a NMS Class.
	 * @param clazz Bukkit class
	 * @return Converted NMS Class
	 */
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

	/**
	 * Converts a Bukkit EntityType to a NMS EntityType.
	 * @param type EntityType to convert
	 * @return NMS EntityType
	 */
	public static EntityType<?> convertType(org.bukkit.entity.EntityType type) {
		return Registry.ENTITY_TYPE.get(CraftNamespacedKey.toMinecraft(type.getKey()));
	}

	/**
	 * Converts a Bukkit Location to a NMS Block Position.
	 * @param loc Location to convert
	 * @return NMS Block Position
	 */
	public static BlockPos convertType(@NotNull Location loc) {
		return new BlockPos(loc.getX(), loc.getY(), loc.getZ());
	}

	/**
	 * Converts a NMS Goal into a MobChip Pathfinder.
	 */
	public static Pathfinder wrapGoal(@NotNull Goal g) {
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
