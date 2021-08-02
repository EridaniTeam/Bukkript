package org.kotlinmc.bukkript.script.definition

val bukkitImports = listOf(
    "org.bukkit.*",
    "org.bukkit.block.*",
    "org.bukkit.block.banner.*",
    "org.bukkit.command.*",
    "org.bukkit.configuration.*",
    "org.bukkit.configuration.file.*",
    "org.bukkit.configuration.serialization.*",
    "org.bukkit.enchantments.*",
    "org.bukkit.entity.*",
    "org.bukkit.entity.minecart.*",
    "org.bukkit.event.*",
    "org.bukkit.event.block.*",
    "org.bukkit.event.enchantment.*",
    "org.bukkit.event.entity.*",
    "org.bukkit.event.hanging.*",
    "org.bukkit.event.inventory.*",
    "org.bukkit.event.painting.*",
    "org.bukkit.event.player.*",
    "org.bukkit.event.server.*",
    "org.bukkit.event.weather.*",
    "org.bukkit.event.world.*",
    "org.bukkit.generator.*",
    "org.bukkit.help.*",
    "org.bukkit.inventory.*",
    "org.bukkit.inventory.meta.*",
    "org.bukkit.map.*",
    "org.bukkit.material.*",
    "org.bukkit.metadata.*",
    "org.bukkit.permissions.*",
    "org.bukkit.plugin.*",
    "org.bukkit.plugin.messaging.*",
    "org.bukkit.potion.*",
    "org.bukkit.projectiles.*",
    "org.bukkit.scheduler.*",
    "org.bukkit.scoreboard.*",
    "org.bukkit.util.*",
    "org.bukkit.util.io.*",
    "org.bukkit.util.noise.*",
    "org.bukkit.util.permissions.*",
)
val kotlinBukkitAPICoreImports = listOf(
    /* Architecture package not needed */
    "org.kotlinmc.bukkit.collections.*",
    /* Controller package not needed */
    "org.kotlinmc.bukkit.dsl.command.*",
    "org.kotlinmc.bukkit.dsl.command.arguments.*",
    "org.kotlinmc.bukkit.dsl.command.KCommand",
    "org.kotlinmc.bukkit.dsl.command.ExecutorBlock",
    "org.kotlinmc.bukkit.dsl.command.Executor",
    "org.kotlinmc.bukkit.dsl.command.CommandException",
    "org.kotlinmc.bukkit.dsl.command.TabCompleter",
    "org.kotlinmc.bukkit.dsl.command.TabCompleterBlock",
    "org.kotlinmc.bukkit.dsl.command.ExecutorPlayerBlock",

    "org.kotlinmc.bukkit.dsl.menu.*",
    "org.kotlinmc.bukkit.dsl.menu.pagination.*",
    "org.kotlinmc.bukkit.dsl.menu.pagination.slot.*",
    "org.kotlinmc.bukkit.dsl.menu.slot.*",

    "org.kotlinmc.bukkit.dsl.scoreboard.*",

    "org.kotlinmc.bukkit.extensions.*",

    /* Flow Event ignored because already have a implementation at Bukkript */

    "org.kotlinmc.bukkit.menu.*",
    "org.kotlinmc.bukkit.menu.slot.*",

    "org.kotlinmc.bukkit.utils.*",
    "org.kotlinmc.bukkit.utils.player.*",
    "org.kotlinmc.bukkit.utils.time.*",
)


val bukkriptImports = listOf(
    "org.kotlinmc.bukkript.script.definition.api.*"
)

val kotlinImports = listOf(
    "kotlin.time.*",
    "kotlin.math.*",
)

val javaImports = listOf(
    "java.util.*",
    "java.util.concurrent.*",
    "java.io.*",
    //"java.lang.*",
)

val kotlinCoroutinesImports = listOf(
    "kotlinx.coroutines.*",
    "kotlinx.coroutines.flow.*",
    "kotlinx.coroutines.channels.*",
    "kotlinx.coroutines.selects.*",
)

val scriptingImports = listOf(
    "kotlin.script.experimental.dependencies.DependsOn",
    "kotlin.script.experimental.dependencies.Repository",
)
