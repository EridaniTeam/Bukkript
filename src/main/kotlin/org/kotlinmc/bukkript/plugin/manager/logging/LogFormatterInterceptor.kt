package org.kotlinmc.bukkript.plugin.manager.logging

import org.kotlinmc.bukkript.script.definition.api.LogLevel

fun logFormatterInterceptor(scriptName: String, level: LogLevel, message: String): String? {
    return "$scriptName [$level]: $message"
}