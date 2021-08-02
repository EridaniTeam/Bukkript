package org.kotlinmc.bukkript.script.definition.configuration

import org.kotlinmc.bukkript.script.definition.*
import org.kotlinmc.bukkript.script.definition.resolver.resolveScriptAnnotation
import org.kotlinmc.bukkript.script.definition.resolver.resolveScriptStaticDependencies
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.script.experimental.dependencies.Repository
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm


class BukkriptScriptCompilationConfiguration : ScriptCompilationConfiguration({
    defaultImports(bukkitImports + kotlinBukkitAPICoreImports
            + kotlinImports + javaImports + kotlinCoroutinesImports + scriptingImports
    )
    jvm {
        dependenciesFromClassContext(BukkriptScriptCompilationConfiguration::class, wholeClasspath = true)
        compilerOptions(
            "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi,kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-jvm-target", "1.8",
        )
    }
    refineConfiguration {
        beforeCompiling(handler = ::resolveScriptStaticDependencies)
        onAnnotations(
            annotations = listOf(KotlinType(Script::class),
                KotlinType(DependPlugin::class),
                KotlinType(Import::class),
                KotlinType(DependsOn::class),
                KotlinType(Repository::class)),
            handler = ::resolveScriptAnnotation
        )
    }
    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
})
