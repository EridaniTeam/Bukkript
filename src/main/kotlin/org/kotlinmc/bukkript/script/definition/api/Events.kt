package org.kotlinmc.bukkript.script.definition.api

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.kotlinmc.bukkit.extensions.event
import org.kotlinmc.bukkit.extensions.events
import org.kotlinmc.bukkit.extensions.unregisterListener
import org.kotlinmc.bukkit.flow.eventFlow
import org.kotlinmc.bukkript.script.definition.BukkriptScript
import kotlin.reflect.KClass

inline fun <reified T : Event> BukkriptScript.event(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline block: T.() -> Unit,
) = event(T::class, priority, ignoreCancelled, block)

fun <T : Event> BukkriptScript.event(
    type: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    block: T.() -> Unit,
) = plugin.events { event(plugin, type, priority, ignoreCancelled, block) }.also {
    onDisable {
        it.unregisterListener()
    }
}

// event flow

inline fun <reified T : Event> BukkriptScript.eventFlow(
    assign: Player? = null,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
): Flow<T> = eventFlow<T>(T::class, assign, priority, ignoreCancelled)

fun <T : Event> BukkriptScript.eventFlow(
    type: KClass<T>,
    assign: Player? = null,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
): Flow<T> {
    val channel = Channel<T>(Channel.CONFLATED)
    val listener = plugin.events {}
    val assignListener = plugin.events {}

    val unregister = onDisable {
        listener.unregisterListener()
        assignListener.unregisterListener()
        channel.close()
    }

    val flow = eventFlow(
        type,
        plugin,
        assign,
        priority,
        ignoreCancelled,
        channel,
        listener,
        assignListener
    ).onCompletion {
        listener.unregisterListener()
        assignListener.unregisterListener()
        unregister()
    }

    return flow
}

