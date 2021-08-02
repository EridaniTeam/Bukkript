package org.kotlinmc.bukkript.plugin

val kotlinVersion = "1.5.21"
val kotlinScriptVersion = "0.0.7"

val gradleFile = "plugins {\n" +
        "    kotlin(\"jvm\") version \"$kotlinVersion\"\n" +
        "}\n" +
        "\n" +
        "val bukkitVersion = \"1.8.8-R0.1-SNAPSHOT\"\n" +
        "\n" +
        "repositories {\n" +
        "    mavenCentral()\n" +
        "    maven(\"https://maven.eridani.club/\")\n" +
        "    maven(\"https://papermc.io/repo/repository/maven-public/\")\n" +
        "}\n" +
        "\n" +
        "dependencies {\n" +
        "    implementation(\"club.eridani.bukkit:kotlin-script:$kotlinScriptVersion\")\n" +
        "    implementation(\"org.spigotmc:spigot-api:\$bukkitVersion\")\n" +
        "}"