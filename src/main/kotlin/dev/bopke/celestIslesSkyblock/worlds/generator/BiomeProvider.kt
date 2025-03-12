package dev.bopke.celestIslesSkyblock.worlds.generator

import org.bukkit.block.Biome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.WorldInfo

class BiomeProvider : BiomeProvider() {
    override fun getBiome(p0: WorldInfo, p1: Int, p2: Int, p3: Int): Biome {
        return Biome.PLAINS
    }

    override fun getBiomes(p0: WorldInfo): MutableList<Biome> {
        return mutableListOf(Biome.PLAINS)
    }
}
