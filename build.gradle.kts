plugins {
    kotlin("jvm") version "1.5.21"
    id("net.minecrell.plugin-yml.bukkit") version "0.4.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "club.eridani"
version = "0.0.1"

val bukkitVersion = "1.8.8-R0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.eridani.club/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("club.eridani.bukkit:kotlin-api:0.0.3")
    compileOnly("org.spigotmc:spigot-api:$bukkitVersion")


    implementation(kotlin("scripting-common"))
    implementation(kotlin("scripting-jvm-host"))
    implementation(kotlin("scripting-compiler-embeddable"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("scripting-dependencies"))
    implementation(kotlin("scripting-dependencies-maven"))
}


bukkit {
    name = "Bukkript"
    main = "org.kotlinmc.bukkript.plugin.BukkriptPlugin"
    depend = listOf("KotlinMinecraftBukkit")

    description = "Bukkript Scripting."
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        dependencies {
            exclude(dependency(":kotlin-stdlib:"))
            exclude(dependency(":kotlin-stdlib-common:"))
            exclude(dependency(":kotlin-stdlib-jdk7:"))
            exclude(dependency(":kotlin-stdlib-jdk8:"))
            exclude(dependency(":kotlinx-coroutines-core:"))
        }
    }
}

tasks.create<Copy>("downloadLibs") {
    from(configurations.runtimeClasspath.get())
    into("libs")
}