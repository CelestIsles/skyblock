package dev.bopke.celestIslesSkyblock

import dev.bopke.celestIslesSkyblock.commands.IslandCommand
import dev.bopke.celestIslesSkyblock.worlds.generator.BiomeProvider
import dev.bopke.celestIslesSkyblock.worlds.generator.ChunkGenerator
import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin


class CelestIslesSkyblockPlugin : JavaPlugin() {
    private var liteCommands: LiteCommands<CommandSender>? = null

    override fun onEnable() {
        saveDefaultConfig()

        LiteBukkitFactory.builder("skyblock", this)
            .commands(
                IslandCommand()
            ).build()
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): org.bukkit.generator.ChunkGenerator {
        return ChunkGenerator()
    }

    override fun getDefaultBiomeProvider(worldName: String, id: String?): org.bukkit.generator.BiomeProvider {
        return BiomeProvider()
    }

    override fun onDisable() {
        this.liteCommands?.unregister();
    }
}