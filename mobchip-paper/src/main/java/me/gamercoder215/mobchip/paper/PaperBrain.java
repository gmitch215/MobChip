package me.gamercoder215.mobchip.paper;

import java.util.NoSuchElementException;

import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftMob;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.ai.behavior.Behavior;
import me.gamercoder215.mobchip.ai.controller.EntityController;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.util.ChipGetter;

/**
 * Represents Paper/Purpur Implementation of the MobChip API
 */
public class PaperBrain implements EntityBrain {

	private final Mob m;
	private final net.minecraft.world.entity.Mob nmsMob;

	private PaperBrain(@NotNull Mob m) {
		this.m = m;
		this.nmsMob = ((CraftMob) m).getHandle();
	}

	/**
	 * Get the EntityBrain of this Mob.
	 * @param m Mob to get
	 * @return EntityBrain
	 */
	public static EntityBrain getBrain(@NotNull Mob m) {
		return new PaperBrain(m);
	}

    @Override
    public EntityAI getGoalAI() {
        return new PaperAI(nmsMob.goalSelector);
    }

    @Override
    public EntityAI getTargetAI() {
        return new PaperAI(nmsMob.targetSelector);
    }

	@SuppressWarnings("unchecked")
	@Override
	public boolean addBehavior(@NotNull Behavior b) {
		return b.getHandle().tryStart(ChipGetter.getLevel(m.getWorld()), nmsMob, 0L);
	}

	@Override
	public EntityNavigation getNavigation() {
		return new PaperNavigation(nmsMob.getNavigation());
	}

	@Override
	public EntityController getController() {
		return new PaperController(m.getWorld(), nmsMob.getJumpControl(), nmsMob.getLookControl(), nmsMob.getMoveControl());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setMemory(EntityMemory memory, Object value) {
		if (value == null) {
			nmsMob.getBrain().eraseMemory(memory.getHandle());
			return;
		}

		if (memory.convert(value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		nmsMob.getBrain().setMemory(memory.getHandle(), memory.convert(value));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setMemory(EntityMemory memory, Object value, long expire) {
		if (expire < 0) throw new IllegalArgumentException("Invalid ticks number " + expire);

		nmsMob.getBrain().setMemoryWithExpiry(memory.getHandle(), memory.convert(value), expire);
	}

	@Override
	@SuppressWarnings("unchecked")
	public @Nullable Object getMemory(EntityMemory memory) {
		try {
			return nmsMob.getBrain().getMemory(memory.getHandle()).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	@Nullable
	@SuppressWarnings("unchecked")
	public <T> T getMemory(EntityMemory memory, Class<T> clazz) {
		try {
			Object obj = nmsMob.getBrain().getMemory(memory.getHandle()).get();
			if (!(clazz.isInstance(obj))) return null;

			return (T) obj;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public long getExpiration(EntityMemory memory) {
		return nmsMob.getBrain().getTimeUntilExpiry(memory.getHandle());
	}

	@Override
	public boolean containsMemory(EntityMemory memory) {
		return nmsMob.getBrain().hasMemoryValue(memory.getHandle());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addSensor(@NotNull Sensor s) {
		s.getHandle().create().tick(((CraftWorld) m.getWorld()).getHandle(), nmsMob);	
	}
    
}
