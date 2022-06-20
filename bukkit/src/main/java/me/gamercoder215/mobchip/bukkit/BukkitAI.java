package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.PathfinderInfo;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderAddEvent;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderClearEvent;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderRemoveEvent;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

final class BukkitAI implements EntityAI {
	
	private final Map<Integer, Goal> goals = new HashMap<>();
	private final GoalSelector sel;
	private final boolean target;

	static Pathfinder wrapGoal(Goal g) {
		try {
			Constructor<? extends Pathfinder> constr = wrapGoal(g.getClass()).getConstructor(g.getClass());
			constr.setAccessible(true);
			return constr.newInstance(g);
		} catch (ExceptionInInitializerError e) {
			return null;
		} catch (Exception e) {
			Logger.getGlobal().severe(e.getMessage());
			return null;
		}
	}

	static Class<? extends Pathfinder> wrapGoal(Class<? extends Goal> class1) {
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

	static List<Class<? extends Pathfinder>> getPathfinders() {
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
			Logger.getGlobal().severe(e.getMessage());
			return new ArrayList<>();
		}
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

	protected BukkitAI(GoalSelector sel, boolean target) {
		this.sel = sel;
		this.target = target;
		updateMap();
	}
	
	private void updateMap() {
		goals.clear();
		for (WrappedGoal g : sel.getAvailableGoals()) goals.put(g.getPriority(), g.getGoal());
	}

	private void updateAI() {
		sel.removeAllGoals();
		for (Map.Entry<Integer, Goal> entry : goals.entrySet()) sel.addGoal(entry.getKey(), entry.getValue());
	}

	@Override
	public int size() {
		return goals.size();
	}

	@Override
	public boolean isEmpty() {
		return goals.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return goals.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		if (!(value instanceof PathfinderInfo info)) return false;
		for (Goal g : sel.getAvailableGoals()) {
			if (g.getClass().getSimpleName().equals(info.getInternalName())) return true;
		}

		return false;
	}

	@Override
	public Pathfinder get(Object key) {
		if (!(key instanceof Integer in)) return null;
		return wrapGoal(goals.get(in));
	}

	@Override
	public Pathfinder put(Integer key, Pathfinder value) {
		if (value == null) return value;
		PathfinderAddEvent event = new PathfinderAddEvent(this, value, this.target, key);
		Bukkit.getPluginManager().callEvent(event);
		if (!(event.isCancelled())) {
			goals.put(key, value.getHandle());
			updateAI();
		}
		return value;
	}

	private void putNoAI(Integer key, Pathfinder value) {
		goals.put(key, value.getHandle());
	}

	@Override
	public Pathfinder remove(Object key) {
		Goal g = goals.remove(key);
		updateAI();
		Pathfinder p = wrapGoal(g);
		PathfinderRemoveEvent event = new PathfinderRemoveEvent(this, p, this.target);
		Bukkit.getPluginManager().callEvent(event);
		return p;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Pathfinder> m) {
		for (Map.Entry<? extends Integer, ? extends Pathfinder> entry : m.entrySet()) {
			PathfinderAddEvent event = new PathfinderAddEvent(this, entry.getValue(), this.target, entry.getKey());
			Bukkit.getPluginManager().callEvent(event);
			if (!(event.isCancelled())) {
				putNoAI(entry.getKey(), entry.getValue());
			}
		}
		updateAI();
	}

	@Override
	public void clear() {
		goals.clear();
		updateAI();
		PathfinderClearEvent event = new PathfinderClearEvent(this, this.target);
		Bukkit.getPluginManager().callEvent(event);
	}

	@Override
	public @NotNull Set<Integer> keySet() {
		return goals.keySet();
	}

	@Override
	public @NotNull List<Pathfinder> values() {
		List<Pathfinder> values = new ArrayList<>();
		for (WrappedGoal g : sel.getAvailableGoals()) {
			values.add(wrapGoal(g.getGoal()));
		}

		return values;
	}

	@Override
	public @NotNull Set<Map.Entry<Integer, Pathfinder>> entrySet() {
		Set<Map.Entry<Integer, Pathfinder>> entries = new HashSet<>();
		for (WrappedGoal g : sel.getAvailableGoals()) {
			entries.add(new AbstractMap.SimpleEntry<>(g.getPriority(), wrapGoal(g.getGoal())));
		}

		return entries;
	}

	/**
	 * Fetches all pathfinders that this Entity is running.
	 * @return Set of running Pathfinders
	 */
	@Override
	public @NotNull Set<Pathfinder> getRunningGoals() {
		Set<Pathfinder> running = new HashSet<>();
		sel.getRunningGoals().forEach(r -> running.add(wrapGoal(r.getGoal())));
		return running;
	}

	/**
	 * Disables all Pathfinders with this flag.
	 * @param flag Flag to disable
	 */
	@Override
	public void disableFlag(@Nullable Pathfinder.PathfinderFlag flag) {
		sel.disableControlFlag(flag.getHandle());
	}

	/**
	 * Enables all Pathfinders with this flag.
	 * @param flag Flag to enable
	 */
	@Override
	public void enableFlag(@Nullable Pathfinder.PathfinderFlag flag) {
		sel.enableControlFlag(flag.getHandle());
	}
}
