package dev.bopke.celestIslesSkyblock.island.commands

import dev.bopke.celestIslesSkyblock.island.IslandRepository
import dev.bopke.celestIslesSkyblock.island.IslandTable
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.cooldown.Cooldown
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player
import java.time.temporal.ChronoUnit

@Command(name = "island", aliases = ["wyspa", "is"])
class IslandSetNameCommand(
    private val noticeService: NoticeService,
    private val islandRepository: IslandRepository
) {

    @Execute(name = "setname")
    @Cooldown(key = "island-setname", count = 5L, unit = ChronoUnit.SECONDS)
    fun setName(@Context player: Player, @Arg("island-name") name: String) {
        this.islandRepository.update(player.uniqueId, IslandTable.name, name).thenAccept {
            this.noticeService.create()
                .notice { messages -> messages.islandMessagesSubConfig.changedIslandName }
                .player(player.uniqueId)
                .placeholder("{name}", name)
                .send()
        }
    }

}