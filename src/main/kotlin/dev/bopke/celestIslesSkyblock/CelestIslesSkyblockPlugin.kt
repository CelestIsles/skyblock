package dev.bopke.celestIslesSkyblock

import com.eternalcode.multification.notice.Notice
import dev.bopke.celestIslesSkyblock.commands.IslandCommand
import dev.bopke.celestIslesSkyblock.config.ConfigManager
import dev.bopke.celestIslesSkyblock.config.MessageConfig
import dev.bopke.celestIslesSkyblock.config.PluginConfig
import dev.bopke.celestIslesSkyblock.notice.NoticeHandler
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.bopke.celestIslesSkyblock.notice.adventure.LegacyColorProcessor
import dev.bopke.celestIslesSkyblock.worlds.WorldFactory
import dev.bopke.celestIslesSkyblock.worlds.generator.BiomeProvider
import dev.bopke.celestIslesSkyblock.worlds.generator.ChunkGenerator
import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import net.kyori.adventure.platform.AudienceProvider
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin


class CelestIslesSkyblockPlugin : JavaPlugin() {

    private lateinit var audienceProvider: AudienceProvider
    private lateinit var miniMessage: MiniMessage
    private lateinit var noticeService: NoticeService

    private lateinit var configManager: ConfigManager
    private lateinit var pluginConfig: PluginConfig
    private lateinit var messageConfig: MessageConfig

    private lateinit  var worldFactory: WorldFactory

    private var liteCommands: LiteCommands<CommandSender>? = null

    override fun onEnable() {
        this.messageConfig = MessageConfig()

        this.audienceProvider = BukkitAudiences.create(this)
        this.miniMessage = MiniMessage.builder()
            .postProcessor(LegacyColorProcessor())
            .build()
        this.noticeService = NoticeService(this.messageConfig, this.audienceProvider, this.miniMessage)

        this.configManager = ConfigManager(this.noticeService.noticeRegistry)
        this.pluginConfig  = this.configManager.loadConfig(PluginConfig::class, this.dataFolder, "config.yml") as PluginConfig
        this.messageConfig = this.configManager.loadConfig(MessageConfig::class, this.dataFolder, "messages.yml") as MessageConfig

        this.worldFactory = WorldFactory(this.pluginConfig)

        this.liteCommands = LiteBukkitFactory.builder("skyblock", this)
            .commands(
                IslandCommand(this.worldFactory, this.noticeService, this.pluginConfig)
            )
            .result(Notice::class.java, NoticeHandler(this.noticeService))
            .build()
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