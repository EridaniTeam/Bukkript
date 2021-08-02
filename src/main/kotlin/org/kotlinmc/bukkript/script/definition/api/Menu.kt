package org.kotlinmc.bukkript.script.definition.api

import org.kotlinmc.bukkit.dsl.menu.MenuDSL
import org.kotlinmc.bukkit.dsl.menu.menu
import org.kotlinmc.bukkript.script.definition.BukkriptScript


inline fun BukkriptScript.menu(
    displayName: String,
    lines: Int,
    cancelOnClick: Boolean = true,
    block: MenuDSL.() -> Unit
): MenuDSL = plugin.menu(displayName, lines, cancelOnClick, block).also {
    onDisable {
        for (player in it.viewers.keys)
            it.close(player, closeInventory = true)
    }
}