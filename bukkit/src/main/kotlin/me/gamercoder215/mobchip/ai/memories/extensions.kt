package me.gamercoder215.mobchip.ai.memories

/**
 * Registers this [Memory] to the Memory Registry.
 * @throws IllegalStateException if this [Memory] is already registered.
 */
@Throws(IllegalStateException::class)
fun Memory<*>.register() = EntityMemories.registerMemory(this)