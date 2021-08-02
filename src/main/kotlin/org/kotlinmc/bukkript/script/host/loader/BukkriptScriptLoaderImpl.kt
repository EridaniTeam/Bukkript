package org.kotlinmc.bukkript.script.host.loader

import org.bukkit.plugin.Plugin
import org.kotlinmc.bukkript.script.definition.BukkriptScript
import org.kotlinmc.bukkript.script.definition.api.LogLevel
import org.kotlinmc.bukkript.script.definition.bukkritNameRelative
import org.kotlinmc.bukkript.script.host.compiler.BukkriptCompiledScript
import java.io.File
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.constructorArgs
import kotlin.script.experimental.jvm.BasicJvmScriptEvaluator
import kotlin.script.experimental.jvm.baseClassLoader
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.loadDependencies

class BukkriptScriptLoaderImpl(
    val plugin: Plugin,
    val scriptDir: File,
    val parentClassloader: ClassLoader,
    val classProvider: ClassProvider,
    val logger: (script: String, LogLevel, message: String) -> Unit,
) : BukkriptScriptLoader {

    override suspend fun load(compiledScript: BukkriptCompiledScript): BukkriptLoadedScript {

        val dataFolder = File(scriptDir, compiledScript.source.bukkritNameRelative(scriptDir))

        val classLoader = ScriptClassloader(
            classProvider,
            parentClassloader,
            compiledScript.description.dependenciesFiles.map { File(it) }.toSet()
        )

        fun scriptLog(level: LogLevel, message: String) {
            logger(compiledScript.scriptName, level, message)
        }

        val evalConfig = ScriptEvaluationConfiguration {
            constructorArgs(
                plugin,
                compiledScript.description,
                dataFolder,
                compiledScript.scriptName,
                ::scriptLog
            )
            jvm {
                baseClassLoader(classLoader)
                loadDependencies(false)
            }
        }

        val evaluator = BasicJvmScriptEvaluator()

        val result = evaluator(compiledScript.compiled, evalConfig)

        return when (result) {
            is ResultWithDiagnostics.Success -> {
                when (val it = result.value.returnValue) {
                    is ResultValue.Error -> throw it.error
                    ResultValue.NotEvaluated -> TODO() // TODO: throw error
                    else -> {
                        // VALUE and UNIT
                        if (it.scriptClass != null && it.scriptInstance !== null) {
                            BukkriptLoadedScript(
                                it.scriptInstance as BukkriptScript,
                                it.scriptClass!!,
                                classLoader,
                                compiledScript,
                                dataFolder
                            )
                        } else {
                            TODO() // TODO: throw error
                        }
                    }
                }
            }
            is ResultWithDiagnostics.Failure -> TODO() // TODO: throw error
        }
    }
}