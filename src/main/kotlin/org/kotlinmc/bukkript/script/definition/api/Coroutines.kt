package org.kotlinmc.bukkript.script.definition.api


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.bukkit.entity.Player
import org.kotlinmc.bukkit.architecture.KotlinPlugin
import org.kotlinmc.bukkit.architecture.lifecycle.LifecycleListener
import org.kotlinmc.bukkit.architecture.lifecycle.getOrInsertGenericLifecycle
import org.kotlinmc.bukkit.collections.onlinePlayerMapOf
import org.kotlinmc.bukkit.extensions.BukkitDispatchers
import org.kotlinmc.bukkript.script.definition.BukkriptScript

/**
 * A CoroutineScope that trigger in the Main Thread of Bukkit by default.
 *
 * This scope ensures that your task will be canceled when the script disable
 * removing the possibility of Job leaks.
 */
val BukkriptScript.scriptCoroutineScope: CoroutineScope
    get() = plugin.getOrInsertBukkriptCoroutineLifecycle().getScriptCoroutineScope(this)

/**
 * A CoroutineScope that trigger in the Main Thread of Bukkit by default.
 *
 * This scope ensures that your task will be canceled when the script disable
 * and when the [player] disconnect the server removing the possibility of Job leaks.
 */
fun BukkriptScript.playerCoroutineScope(player: Player): CoroutineScope {
    return plugin.getOrInsertBukkriptCoroutineLifecycle().getPlayerCoroutineScope(this, player)
}


internal fun KotlinPlugin.getOrInsertBukkriptCoroutineLifecycle(): BukkriptCoroutineLifecycle {
    return getOrInsertGenericLifecycle(Int.MIN_VALUE) {
        BukkriptCoroutineLifecycle(this)
    }
}


internal class BukkriptCoroutineLifecycle(
    override val plugin: KotlinPlugin,
) : LifecycleListener<KotlinPlugin> {

    inner class ScriptCoroutineScope(
        val job: Job,
        val coroutineScope: CoroutineScope,
    ) {
        fun cancelJobs() = job.cancel()
    }

    val coroutineScopes = hashMapOf<BukkriptScript, ScriptCoroutineScope>()

    private val playersCoroutineScope by lazy {
        onlinePlayerMapOf<HashMap<BukkriptScript, ScriptCoroutineScope>>()
    }

    override fun onPluginEnable() {}

    override fun onPluginDisable() {
        for (scope in coroutineScopes.values) {
            scope.cancelJobs()
        }
        for (scope in playersCoroutineScope.values) {
            scope.values.forEach { it.cancelJobs() }
        }
    }

    fun getScriptCoroutineScope(script: BukkriptScript): CoroutineScope {
        return coroutineScopes[script]?.coroutineScope
            ?: newCoroutineScope().also {
                coroutineScopes[script] = it
            }.also {
                script.onDisable {
                    coroutineScopes.remove(script)?.cancelJobs()
                }

            }.coroutineScope
    }

    fun getPlayerCoroutineScope(script: BukkriptScript, player: Player): CoroutineScope {
        return playersCoroutineScope[player]?.get(script)?.coroutineScope
            ?: newCoroutineScope().also {
                playersCoroutineScope.put(player, hashMapOf(script to it)) { map ->
                    map.values.forEach { it.cancelJobs() }
                }
            }.also {
                script.onDisable {
                    for ((_, map) in playersCoroutineScope) {
                        map.remove(script)?.cancelJobs()
                    }
                }
            }.coroutineScope
    }

    private fun newCoroutineScope(): ScriptCoroutineScope {
        val job = Job()
        return ScriptCoroutineScope(
            job,
            CoroutineScope(BukkitDispatchers.SYNC + job)
        )
    }
}