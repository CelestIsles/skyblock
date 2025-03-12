package dev.bopke.celestIslesSkyblock.worlds.generator

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*


class ChunkGenerator : ChunkGenerator() {
    override fun generateSurface(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
    }

    override fun generateBedrock(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
    }

    override fun generateCaves(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
    }

    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
    }

    override fun getFixedSpawnLocation(world: World, random: Random): Location {
        if (!world.isChunkLoaded(0, 0)) {
            world.loadChunk(0, 0)
        }

        val highestBlock = world.getHighestBlockYAt(0, 0)

        // No block at (0,0)
        if ((highestBlock <= -64) && (world.getBlockAt(0, -64, 0).type === Material.AIR)) {
            // TODO: find nearest safe block?
            return Location(
                world,
                0.0,
                320.0,
                0.0
            )
        }

        return Location(world, 0.0, highestBlock.toDouble(), 0.0)
    }
}