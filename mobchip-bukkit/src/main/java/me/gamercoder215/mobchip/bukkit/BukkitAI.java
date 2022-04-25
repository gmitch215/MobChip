package me.gamercoder215.mobchip.bukkit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftMob;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.goal.Pathfinder;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

public class BukkitAI implements EntityAI {
	
	private Map<Integer, Goal> goals = new HashMap<>();
	private GoalSelector sel;
	
	private BukkitAI(GoalSelector sel) {
		this.sel = sel;
		updateMap();
	}
	
	private void updateMap() {
		goals.clear();
		for (WrappedGoal g : sel.getAvailableGoals()) goals.put(g.getPriority(), g.getGoal());
	}
	
	public static EntityAI getAI(@NotNull Mob m) {
		return new BukkitAI(((CraftMob) m).getHandle().goalSelector);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pathfinder get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pathfinder put(Integer key, Pathfinder value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pathfinder remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Pathfinder> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Integer> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Pathfinder> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<Integer, Pathfinder>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
