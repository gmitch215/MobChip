package me.gamercoder215.mobchip.bukkit;

import java.util.*;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.PathfinderInfo;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderAddEvent;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderClearEvent;
import me.gamercoder215.mobchip.bukkit.events.pathfinder.PathfinderRemoveEvent;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class BukkitAI implements EntityAI {
	
	private final Map<Integer, Goal> goals = new HashMap<>();
	private final GoalSelector sel;
	private final boolean target;
	
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
		if (!(value instanceof PathfinderInfo)) return false;
		PathfinderInfo info = (PathfinderInfo) value;
		for (Goal g : sel.getAvailableGoals()) {
			if (g.getClass().getSimpleName().equals(info.getInternalName())) return true;
		}

		return false;
	}

	@Override
	public Pathfinder get(Object key) {
		if (!(key instanceof Integer)) return null;
		Integer in = (Integer) key;
		return ChipConversions.wrapGoal(goals.get(in));
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
		Pathfinder p = ChipConversions.wrapGoal(g);
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
			values.add(ChipConversions.wrapGoal(g.getGoal()));	
		}

		return values;
	}

	@Override
	public @NotNull Set<Entry<Integer, Pathfinder>> entrySet() {
		Set<Entry<Integer, Pathfinder>> entries = new HashSet<>();
		for (WrappedGoal g : sel.getAvailableGoals()) {
			entries.add(new AbstractMap.SimpleEntry<>(g.getPriority(), ChipConversions.wrapGoal(g.getGoal())));
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
		sel.getRunningGoals().forEach(r -> running.add(ChipConversions.wrapGoal(r.getGoal())));
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
