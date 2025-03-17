package dev.bopke.celestIslesSkyblock.island.share.commands

import dev.bopke.celestIslesSkyblock.island.IslandRepository
import dev.bopke.celestIslesSkyblock.island.share.IslandShare
import dev.bopke.celestIslesSkyblock.island.share.IslandShareRepository
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.bopke.celestIslesSkyblock.util.TimeUtil
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

@Command(name = "island", aliases = ["wyspa", "is"])
class IslandShareListCommand(
    private val noticeService: NoticeService,
    private val islandRepository: IslandRepository,
    private val islandShareRepository: IslandShareRepository
) {

    @Execute(name = "share list")
    fun list(@Context player: Player) {
        val uniqueId = player.uniqueId

        this.islandRepository.getByCreatorUuid(uniqueId).thenCompose { islandOptional ->
            if (islandOptional.isEmpty) {
                this.noticeService.create()
                    .notice { messages -> messages.islandMessagesSubConfig.dontHaveIsland }
                    .player(uniqueId)
                    .send()
                return@thenCompose CompletableFuture.completedFuture<List<IslandShare>>(emptyList())
            }

            val island = islandOptional.get()
            this.islandShareRepository.getAll(island.id)
        }.thenAccept { shareList ->
            this.displayShareList(player.uniqueId, shareList)
        }
    }

    private fun displayShareList(playerUuid: UUID, shareList: List<IslandShare>) {
        if (shareList.isEmpty()) {
            this.noticeService.create()
                .notice { messages -> messages.islandMessagesSubConfig.noShares }
                .player(playerUuid)
                .send()
            return
        }

        this.noticeService.create()
            .notice { messages -> messages.islandMessagesSubConfig.shareListHeader }
            .player(playerUuid)
            .send()

        shareList.forEach { islandShare ->
            val offlinePlayer = Bukkit.getOfflinePlayer(islandShare.player_uuid)

            this.noticeService.create()
                .notice { messages -> messages.islandMessagesSubConfig.shareListEntry }
                .player(playerUuid)
                .placeholder("{player}", offlinePlayer.name ?: "Unknown")
                .placeholder("{date}", TimeUtil.format(islandShare.shared_since))
                .send()
        }
    }

}