package me.gamercoder215.mobchip.bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import me.gamercoder215.mobchip.ai.goal.PathfinderInfo;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

/**
 * Bukkit/Spigot Implementation of the MobChip API
 */
public final class BukkitAI implements EntityAI {
	
	private Map<Integer, Goal> goals = new HashMap<>();
	private GoalSelector sel;
	
	protected BukkitAI(GoalSelector sel) {
		this.sel = sel;
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
		return ChipConversions.wrapGoal(goals.get(in));
	}

	@Override
	public Pathfinder put(Integer key, Pathfinder value) {
		goals.put(key, value.getHandle());
		updateAI();
		return value;
	}

	public void putNoAI(Integer key, Pathfinder value) {
		goals.put(key, value.getHandle());
	}

	@Override
	public Pathfinder remove(Object key) {
		Goal g = goals.remove(key);
		updateAI();
		return ChipConversions.wrapGoal(g);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Pathfinder> m) {
		for (Map.Entry<? extends Integer, ? extends Pathfinder> entry : m.entrySet()) {
			putNoAI(entry.getKey(), entry.getValue());
		}
		updateAI();
	}

	@Override
	public void clear() {
		goals.clear();
		updateAI();
	}

	@Override
	public Set<Integer> keySet() {
		return goals.keySet();
	}

	@Override
	public List<Pathfinder> values() {
		List<Pathfinder> values = new ArrayList<>();
		for (WrappedGoal g : sel.getAvailableGoals()) {
			values.add(ChipConversions.wrapGoal(g.getGoal()));	
		}

		return values;
	}

	@Override
	public Set<Entry<Integer, Pathfinder>> entrySet() {
		Set<Entry<Integer, Pathfinder>> entries = new HashSet<>();

		for (WrappedGoal g : sel.getAvailableGoals()) {
			entries.add(Map.entry(g.getPriority(), ChipConversions.wrapGoal(g.getGoal())));
		}

		return entries;
	}

}
