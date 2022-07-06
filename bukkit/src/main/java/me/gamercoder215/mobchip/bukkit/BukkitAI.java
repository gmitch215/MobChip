package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.WrappedPathfinder;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderAddEvent;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderClearEvent;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static me.gamercoder215.mobchip.abstraction.ChipUtil.getWrapper;

final class BukkitAI implements EntityAI {
	
	private final Set<WrappedPathfinder> goals = new HashSet<>();
	private final boolean target;

	private final Mob m;

	protected BukkitAI(Mob m, boolean target) {
		this.target = target;
		this.m = m;
		updateMap();
	}

	private void updateMap() {
		goals.clear();
		goals.addAll(wrapper.getGoals(m, target));
	}

	private static final ChipUtil wrapper = getWrapper();

	private void updateAI() {
		wrapper.clearPathfinders(m, target);
		wrapper.addPathfinders(goals, target);
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
	public boolean contains(Object o) {
		if (!(o instanceof WrappedPathfinder)) return false;
		WrappedPathfinder p = (WrappedPathfinder) o;
		return wrapper.getGoals(m, target).contains(p);
	}

	@Override
	public boolean contains(Pathfinder value) {
		return wrapper.getGoals(m, target).stream().map(WrappedPathfinder::getPathfinder).collect(Collectors.toList()).contains(value);
	}

	@Override
	public Pathfinder put(@NotNull Pathfinder p, int priority) {
		add(new WrappedPathfinder(p, priority));
		return p;
	}

	@Override
	public void putAll(@NotNull Map<? extends Pathfinder, Integer> map) {
		map.forEach(this::put);
	}

	@Override
	public boolean remove(@NotNull Pathfinder p) {
		for (WrappedPathfinder w : wrapper.getGoals(m, target)) if (w.getPathfinder().equals(p)) return remove(w);
		return false;
	}

	@Override
	public boolean isRunning(Pathfinder p) {
		return wrapper.getRunningGoals(m, target).stream().map(WrappedPathfinder::getPathfinder).collect(Collectors.toList()).contains(p);
	}

	@NotNull
	@Override
	public Iterator<WrappedPathfinder> iterator() {
		return goals.iterator();
	}

	@NotNull
	@Override
	public WrappedPathfinder[] toArray() {
		return goals.toArray(new WrappedPathfinder[0]);
	}

	@Override
	public <T> T[] toArray(@NotNull T[] a) {
		return a;
	}

	@Override
	public boolean add(WrappedPathfinder p) {
		boolean success = goals.add(p);

		PathfinderAddEvent event = new PathfinderAddEvent(this, p.getPathfinder(), target, p.getPriority());
		Bukkit.getPluginManager().callEvent(event);
		updateAI();

		return success;
	}

	@Override
	public boolean remove(Object key) {
		if (!(key instanceof WrappedPathfinder)) return false;
		WrappedPathfinder p = (WrappedPathfinder) key;

		boolean success = goals.remove(key);

		PathfinderRemoveEvent event = new PathfinderRemoveEvent(this, p.getPathfinder(), target);
		Bukkit.getPluginManager().callEvent(event);
		updateAI();

		return success;
	}

	@Override
	public boolean containsAll(@NotNull Collection<?> c) {
		return goals.containsAll(c);
	}

	@Override
	public boolean addAll(@NotNull Collection<? extends WrappedPathfinder> c) {
		AtomicBoolean success = new AtomicBoolean(true);

		c.forEach(w -> { if (!add(w)) success.set(false); });

		return success.get();
	}

	@Override
	public boolean retainAll(@NotNull Collection<?> c) {
		return goals.retainAll(c);
	}

	@Override
	public boolean removeAll(@NotNull Collection<?> c) {
		return goals.removeAll(c);
	}

	@Override
	public void clear() {
		goals.clear();
		updateAI();
		PathfinderClearEvent event = new PathfinderClearEvent(this, this.target);
		Bukkit.getPluginManager().callEvent(event);
	}

	/**
	 * Fetches all pathfinders that this Entity is running.
	 * @return Set of running Pathfinders
	 */
	@Override
	public @NotNull Set<WrappedPathfinder> getRunningGoals() {
		return new HashSet<>(wrapper.getRunningGoals(m, target));
	}

	/**
	 * Disables all Pathfinders with this flag.
	 * @param flag Flag to disable
	 */
	@Override
	public void disableFlag(@Nullable Pathfinder.PathfinderFlag flag) {
		wrapper.setFlag(m, flag, target, false);
	}

	/**
	 * Enables all Pathfinders with this flag.
	 * @param flag Flag to enable
	 */
	@Override
	public void enableFlag(@Nullable Pathfinder.PathfinderFlag flag) {
		wrapper.setFlag(m, flag, target,true);
	}
}
