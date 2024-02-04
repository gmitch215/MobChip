package me.gamercoder215.mobchip.bukkit

import me.gamercoder215.mobchip.DragonBrain
import me.gamercoder215.mobchip.EntityBrain
import me.gamercoder215.mobchip.VillagerBrain
import me.gamercoder215.mobchip.ai.EntityAI
import me.gamercoder215.mobchip.ai.behavior.CreatureBehavior
import me.gamercoder215.mobchip.ai.behavior.DragonBehavior
import me.gamercoder215.mobchip.ai.behavior.EntityBehavior
import me.gamercoder215.mobchip.ai.behavior.VillagerBehavior
import me.gamercoder215.mobchip.ai.controller.EntityController
import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager
import me.gamercoder215.mobchip.ai.sensing.EntitySenses
import me.gamercoder215.mobchip.combat.EntityCombatTracker
import me.gamercoder215.mobchip.nbt.EntityNBT
import org.bukkit.entity.*

/**
 * Fetches the [EntityBrain] of this [Entity].
 * @return Entity Brain, or null if not instance of [Mob]
 */
inline val Entity.brain: EntityBrain?
    get() {
        if (this !is Mob) return null
        return this.brain
    }

/**
 * Fetches the [EntityBrain] of this [Mob].
 * @return Entity Brain
 */
inline val Mob.brain: EntityBrain
    get() = BukkitBrain.getBrain(this)!!

/**
 * Fetches the [VillagerBrain] of this [Villager].
 * @return Villager Brain
 */
inline val Villager.brain: VillagerBrain
    get() = (this as Mob).brain as VillagerBrain

/**
 * Fetches the [DragonBrain] of this [EnderDragon].
 * @return Dragon Brain
 */
inline val EnderDragon.brain: DragonBrain
    get() = BukkitBrain.getBrain(this)!!

/**
 * Fetches the [EntityAI] of this [Mob] for Pathfinder Goals.
 * @return Entity AI
 */
inline val Mob.goalSelector: EntityAI
    get() = this.brain.goalAI

/**
 * Fetches the [EntityAI] of this [Mob] for Target Goals.
 * @return Entity AI
 */
inline val Mob.targetSelector: EntityAI
    get() = this.brain.targetAI

/**
 * Fetches the [EntityNBT] Editor of this [Mob].
 * @return Entity NBT
 */
inline val Mob.nbt: EntityNBT
    get() = this.brain.nbtEditor

/**
 * Fetches the [EntityController] of this [Mob].
 * @return Entity Controller
 */
inline val Mob.controller: EntityController
    get() = this.brain.controller

/**
 * Fetches the [EntityCombatTracker] of this [Mob].
 * @return Entity Combat Tracker
 */
inline val Mob.combatTracker: EntityCombatTracker
    get() = this.brain.combatTracker

/**
 * Fetches the [EntitySenses] of this [Mob].
 * @return Entity Senses
 */
inline val Mob.senses: EntitySenses
    get() = this.brain.senses

/**
 * Fetches the [EntityScheduleManager] for this [Mob].
 * @return Entity Schedule Manager
 */
inline val Mob.scheduleManager: EntityScheduleManager
    get() = this.brain.scheduleManager

// Behaviors

/**
 * Fetches the [EntityBehavior] of this [Mob].
 * @return Entity Behavior
 */
inline val Mob.behavior: EntityBehavior
    get() = this.brain.behaviors

/**
 * Fetches the [CreatureBehavior] of this [Creature].
 * @return Creature Behavior
 */
inline val Creature.behavior: CreatureBehavior
    get() = this.brain.behaviors as CreatureBehavior

/**
 * Fetches the [VillagerBehavior] of this [Villager].
 * @return Villager Behavior
 */
inline val Villager.behavior: VillagerBehavior
    get() = this.brain.behaviors as VillagerBehavior

/**
 * Fetches the [DragonBehavior] of this [EnderDragon].
 * @return Dragon Behavior
 */
inline val EnderDragon.behavior: DragonBehavior
    get() = this.brain.behaviors as DragonBehavior

// Other

/**
 * Fetches the [EntityGossipContainer] of this [Villager].
 * @return Villager Gossip Container
 */
inline val Villager.gossipContainer: EntityGossipContainer
    get() = this.brain.gossipContainer

