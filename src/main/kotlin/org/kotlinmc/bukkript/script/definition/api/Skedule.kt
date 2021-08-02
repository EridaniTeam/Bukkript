package org.kotlinmc.bukkript.script.definition.api

import org.kotlinmc.bukkit.extensions.BukkitDispatchers
import org.kotlinmc.bukkit.skedule.BukkitSchedulerController
import org.kotlinmc.bukkit.skedule.SynchronizationContext
import org.kotlinmc.bukkit.skedule.schedule
import org.kotlinmc.bukkript.script.definition.BukkriptScript


fun BukkriptScript.schedule(
    initialContext: SynchronizationContext = SynchronizationContext.SYNC,
    co: suspend BukkitSchedulerController.() -> Unit,
) = plugin.schedule(initialContext, co).also {
    onDisable {
        it.cancel()
    }
}

val BukkriptScript.BukkitDispatchers get() = plugin.BukkitDispatchers