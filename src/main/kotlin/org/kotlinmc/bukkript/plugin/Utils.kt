package org.kotlinmc.bukkript.plugin

import org.kotlinmc.bukkript.script.definition.BukkriptScript
import org.kotlinmc.bukkript.script.host.loader.BukkriptLoadedScript


internal fun BukkriptLoadedScript.disable() {
    script.disable()
}


internal fun BukkriptScript.disable() {
    onDisableListeners.forEach { it() }
}
