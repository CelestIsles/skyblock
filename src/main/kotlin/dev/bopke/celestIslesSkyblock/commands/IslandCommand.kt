package dev.bopke.celestIslesSkyblock.commands

import dev.bopke.celestIslesSkyblock.config.PluginConfig
import dev.bopke.celestIslesSkyblock.island.IslandRepository
import dev.bopke.celestIslesSkyblock.island.UnidentifiedIsland
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.bopke.celestIslesSkyblock.worlds.WorldFactory
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player

@Command(name = "island", aliases = ["wyspa", "is"])
class IslandCommand(
    private val worldFactory: WorldFactory,
    private val noticeService: NoticeService,
    private val pluginConfig: PluginConfig,
    private val islandRepository: IslandRepository
) {

    @Execute(name = "create")
    fun create(@Context player: Player, @Arg("island-name") islandName: String) {
        val world = this.worldFactory.create(this.pluginConfig.skyblockWorldNamePrefix + player.uniqueId)
        val island: UnidentifiedIsland = this.islandRepository.create(player.uniqueId, world, islandName)

        this.islandRepository.insert(island).whenComplete { definedIsland, error ->
            if (error != null) {
                println(error.message)
                error.printStackTrace()
                return@whenComplete
            }

            player.sendMessage("Wyspa została stworzona!")
            player.sendMessage("Id wyspy: ${definedIsland.id}")
        }
    }

}