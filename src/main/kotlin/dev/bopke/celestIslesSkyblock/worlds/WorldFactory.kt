package dev.bopke.celestIslesSkyblock.worlds

import dev.bopke.celestIslesSkyblock.config.PluginConfig
import dev.bopke.celestIslesSkyblock.worlds.generator.BiomeProvider
import dev.bopke.celestIslesSkyblock.worlds.generator.ChunkGenerator
import org.bukkit.World
import org.bukkit.WorldCreator

class WorldFactory(val config: PluginConfig) {

    fun create(name: String): World {
        val worldCreator = WorldCreator(name)
        worldCreator.generator(ChunkGenerator())
        worldCreator.biomeProvider(BiomeProvider())

        val world = worldCreator.createWorld()!!
        world.worldBorder.size = this.config.islandBorderSize;
        return world
    }

}