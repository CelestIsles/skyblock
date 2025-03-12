package dev.bopke.celestIslesSkyblock.worlds

import dev.bopke.celestIslesSkyblock.worlds.generator.BiomeProvider
import dev.bopke.celestIslesSkyblock.worlds.generator.ChunkGenerator
import org.bukkit.World
import org.bukkit.WorldCreator

class WorldFactory {
    fun create(name: String): World {
        val worldCreator = WorldCreator(name)
        worldCreator.generator(ChunkGenerator())
        worldCreator.biomeProvider(BiomeProvider())
        val world = worldCreator.createWorld()!!
        world.worldBorder.size = 1000.0
        return world
    }
}