plugins {
    kotlin("jvm") version "2.1.0"
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "dev.bopke"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.eternalcode.pl/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-core:7.2.0-SNAPSHOT")

    implementation("dev.rollczi:litecommands-bukkit:3.9.7")

    // -- adventure --
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.16.0")
    implementation("net.kyori:adventure-api:4.18.0")

    // -- notifications --
    implementation("com.eternalcode:multification-bukkit:1.1.4")
    implementation("com.eternalcode:multification-okaeri:1.1.4")

    // -- configs --
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:5.0.6")

    // -- tasker --
    implementation("eu.okaeri:okaeri-tasker-bukkit:3.0.2-beta.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // -- database --
    val sqiffy = "1.0.0-alpha.68"
    ksp("com.dzikoysk.sqiffy:sqiffy-symbol-processor:$sqiffy")
    implementation("com.dzikoysk.sqiffy:sqiffy:$sqiffy")
    implementation("org.postgresql:postgresql:42.5.0")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks.runServer {
    version("1.21.4")
}