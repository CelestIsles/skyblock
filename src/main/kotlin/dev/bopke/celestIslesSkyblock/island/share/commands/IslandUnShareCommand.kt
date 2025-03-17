package dev.bopke.celestIslesSkyblock.island.share.commands

import dev.bopke.celestIslesSkyblock.island.Island
import dev.bopke.celestIslesSkyblock.island.IslandRepository
import dev.bopke.celestIslesSkyblock.island.share.IslandShareRepository
import dev.bopke.celestIslesSkyblock.island.share.commands.argument.IslandShareArgument
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

@Command(name = "island", aliases = ["wyspa", "is"])
class IslandUnShareCommand(
    private val noticeService: NoticeService,
    private val islandRepository: IslandRepository,
    private val islandShareRepository: IslandShareRepository
) {

    @Execute(name = "unshare")
    fun unShare(@Context player: Player, @Arg("player-to-unshare") unsharePlayer: IslandShareArgument) {
        val uniqueId = player.uniqueId

        this.islandRepository.getByCreatorUuid(uniqueId)
            .thenCompose { optionalIsland -> this.processUnShareIsland(optionalIsland, player, unsharePlayer) }
            .exceptionally { error ->
                println(error.message)
                error.printStackTrace()
                null
            }
    }

    private fun processUnShareIsland(
        optionalIsland: Optional<Island>,
        player: Player,
        playerToUnShare: IslandShareArgument
    ): CompletableFuture<Void> {
        if (!optionalIsland.isPresent) {
            this.noticeService.create()
                .notice { messages -> messages.islandMessagesSubConfig.dontHaveIsland }
                .player(player.uniqueId)
                .send()
            return CompletableFuture.completedFuture(null)
        }

        val island = optionalIsland.get()
        return this.checkAndUnShareIsland(island.id, player, playerToUnShare)
    }

    private fun checkAndUnShareIsland(islandId: Int, player: Player, playerToUnShare: IslandShareArgument): CompletableFuture<Void> {
        return this.isIslandAlreadyShared(islandId, playerToUnShare.getPlayerUuid()).thenCompose { isAlreadyShared ->
            if (!isAlreadyShared) {
                this.noticeService.create()
                    .notice { messages -> messages.islandMessagesSubConfig.islandIsNotSharedWithPlayer }
                    .player(player.uniqueId)
                    .placeholder("{player}", playerToUnShare.getPlayerName())
                    .send()
                return@thenCompose CompletableFuture.completedFuture<Void>(null)
            }

            this.unShareIslandWithPlayer(islandId, player, playerToUnShare)
        }
    }

    private fun unShareIslandWithPlayer(
        islandId: Int,
        player: Player,
        playerToUnShare: IslandShareArgument
    ): CompletableFuture<Void> {
        return this.islandShareRepository.get(islandId, playerToUnShare.getPlayerUuid()).thenCompose { optionalIslandShare ->
            if (optionalIslandShare.isEmpty) {
                return@thenCompose CompletableFuture.completedFuture(null)
            }

            this.islandShareRepository.delete(optionalIslandShare.get()).thenAccept { _ ->
                this.sendUnShareSuccessNotices(player, playerToUnShare)
            }
        }
    }

    private fun sendUnShareSuccessNotices(player: Player, playerToUnShare: IslandShareArgument) {
        this.noticeService.create()
            .notice { messages -> messages.islandMessagesSubConfig.unShareIsland }
            .player(player.uniqueId)
            .placeholder("{player}", playerToUnShare.getPlayerName())
            .send()

        this.noticeService.create()
            .notice { messages -> messages.islandMessagesSubConfig.playerUnSharedIslandWithYou }
            .player(playerToUnShare.getPlayerUuid())
            .placeholder("{player}", player.name)
            .send()
    }

    private fun isIslandAlreadyShared(islandId: Int, playerUUID: UUID): CompletableFuture<Boolean> {
        return this.islandShareRepository.get(islandId, playerUUID).thenApply { optionalIslandShare ->
            optionalIslandShare.isPresent
        }
    }

}