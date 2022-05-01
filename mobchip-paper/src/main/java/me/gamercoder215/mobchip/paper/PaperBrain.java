package me.gamercoder215.mobchip.paper;

import java.util.NoSuchElementException;

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
import me.gamercoder215.mobchip.attributes.Attribute;
import me.gamercoder215.mobchip.attributes.ChipAttributeInstance;
import me.gamercoder215.mobchip.util.ChipConversions;
import net.minecraft.core.Registry;

/**
 * Represents Paper/Purpur Implementation of the MobChip API
 */
@SuppressWarnings({"unchecked", "deprecation"})
public final class PaperBrain implements EntityBrain {

	private final Mob m;
	private final net.minecraft.world.entity.Mob nmsMob;

	private PaperBrain(@NotNull Mob m) {
		this.m = m;
		this.nmsMob = ChipConversions.convertType(m);
	}

	/**
	 * Get the EntityBrain of this Mob.
	 * @param m Mob to get
	 * @return EntityBrain
	 */
	public static EntityBrain getBrain(@NotNull Mob m) {
		return new PaperBrain(m);
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public EntityAI getGoalAI() {
        return new PaperAI(nmsMob.goalSelector);
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public EntityAI getTargetAI() {
        return new PaperAI(nmsMob.targetSelector);
    }
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addBehavior(@NotNull Behavior b) {
		return b.getHandle().tryStart(ChipConversions.convertType(m.getWorld()), nmsMob, 0L);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityNavigation getNavigation() {
		return new PaperNavigation(nmsMob.getNavigation());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityController getController() {
		return new PaperController(m.getWorld(), nmsMob.getJumpControl(), nmsMob.getLookControl(), nmsMob.getMoveControl());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMemory(EntityMemory memory, Object value) {
		if (value == null) {
			nmsMob.getBrain().eraseMemory(memory.getHandle());
			return;
		}

		if (memory.convert(value) == null) throw new IllegalArgumentException("Invalid argument: " + value.getClass().getName());

		nmsMob.getBrain().setMemory(memory.getHandle(), memory.convert(value));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMemory(EntityMemory memory, Object value, long expire) {
		if (expire < 0) throw new IllegalArgumentException("Invalid ticks number " + expire);

		nmsMob.getBrain().setMemoryWithExpiry(memory.getHandle(), memory.convert(value), expire);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public @Nullable Object getMemory(EntityMemory memory) {
		try {
			return nmsMob.getBrain().getMemory(memory.getHandle()).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public <T> T getMemory(EntityMemory memory, Class<T> clazz) {
		try {
			Object obj = nmsMob.getBrain().getMemory(memory.getHandle()).get();
			if (!(clazz.isInstance(obj))) return null;

			return (T) obj;
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getExpiration(EntityMemory memory) {
		return nmsMob.getBrain().getTimeUntilExpiry(memory.getHandle());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsMemory(EntityMemory memory) {
		return nmsMob.getBrain().hasMemoryValue(memory.getHandle());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSensor(@NotNull Sensor s) {
		s.getHandle().create().tick(ChipConversions.convertType(m.getWorld()), nmsMob);	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChipAttributeInstance getAttribute(@NotNull Attribute a) {
		try {
			return new PaperAttributeInstance(a, nmsMob.getAttribute(Registry.ATTRIBUTE.getOptional(a.getId().toResourceLocation()).get()));
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
    
}
