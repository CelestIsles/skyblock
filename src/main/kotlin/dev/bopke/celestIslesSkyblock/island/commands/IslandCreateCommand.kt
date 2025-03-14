package dev.bopke.celestIslesSkyblock.island.commands

import dev.bopke.celestIslesSkyblock.config.PluginConfig
import dev.bopke.celestIslesSkyblock.island.IslandRepository
import dev.bopke.celestIslesSkyblock.island.UnidentifiedIsland
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.bopke.celestIslesSkyblock.worlds.WorldFactory
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player

@Command(name = "island", aliases = ["wyspa", "is"])
class IslandCreateCommand(
    private val worldFactory: WorldFactory,
    private val noticeService: NoticeService,
    private val pluginConfig: PluginConfig,
    private val islandRepository: IslandRepository
) {

    @Execute(name = "create")
    fun create(@Context player: Player) {
        val uniqueId = player.uniqueId
        val name = this.pluginConfig.defaultIslandName.replace("{player}", player.name)
        val world = this.worldFactory.create(this.pluginConfig.skyblockWorldNamePrefix + uniqueId)
        val island: UnidentifiedIsland = this.islandRepository.create(uniqueId, world, name)

        this.islandRepository.getByCreatorUuid(uniqueId).thenAccept {
            it.ifPresentOrElse(
                {
                    this.noticeService.create()
                        .notice { messages -> messages.islandMessagesSubConfig.alreadyHaveIsland }
                        .player(player.uniqueId)
                        .send()
                },
                { this.insert(island, player, name) }
            )
        }
    }

    private fun insert(island: UnidentifiedIsland, player: Player, name: String) {
        this.islandRepository.insert(island).whenComplete { _, error ->
            if (error != null) {
                println(error.message)
                error.printStackTrace()
                return@whenComplete
            }

            this.noticeService.create()
                .notice { messages -> messages.islandMessagesSubConfig.createdIsland }
                .player(player.uniqueId)
                .placeholder("{name}", name)
                .placeholder("{player}", player.name)
                .send()
        }
    }

}