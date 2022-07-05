package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.PathfinderInfo;
import me.gamercoder215.mobchip.ai.goal.WrappedPathfinder;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderClearEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

	private static final ChipUtil wrapper = getWrapper();
	
	private void updateMap() {
		goals.clear();
		// TODO
	}

	private void updateAI() {
		// TODO
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
	public boolean contains(Object value) {
		if (!(value instanceof PathfinderInfo)) return false;
		PathfinderInfo info = (PathfinderInfo) value;
		// TODO
		return false;
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
		return false;
	}

	private void addNoAi(WrappedPathfinder value) {
		goals.add(value);
	}

	@Override
	public boolean remove(Object key) {
		// TODO
		return false;
	}

	@Override
	public boolean containsAll(@NotNull Collection<?> c) {
		// TODO
		return false;
	}

	@Override
	public boolean addAll(@NotNull Collection<? extends WrappedPathfinder> c) {
		// TODO
		return false;
	}

	@Override
	public boolean retainAll(@NotNull Collection<?> c) {
		// TODO
		return false;
	}

	@Override
	public boolean removeAll(@NotNull Collection<?> c) {
		// TODO
		return false;
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
