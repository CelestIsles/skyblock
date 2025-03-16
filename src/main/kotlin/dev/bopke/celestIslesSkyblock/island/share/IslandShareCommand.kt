package dev.bopke.celestIslesSkyblock.island.share

import dev.bopke.celestIslesSkyblock.island.Island
import dev.bopke.celestIslesSkyblock.island.IslandRepository
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

@Command(name = "island", aliases = ["wyspa", "is"])
class IslandShareCommand(
    private val noticeService: NoticeService,
    private val islandRepository: IslandRepository,
    private val islandShareRepository: IslandShareRepository
) {

    @Execute(name = "share")
    fun share(@Context player: Player, @Arg("player-to-share") playerToShare: Player) {
        val uniqueId = player.uniqueId

        if (player == playerToShare) {
            this.noticeService.create()
                .notice { messages -> messages.islandMessagesSubConfig.cannotShareWithYourself }
                .player(player.uniqueId)
                .send()
            return
        }

        this.islandRepository.getByCreatorUuid(uniqueId)
            .thenCompose { optionalIsland -> processIsland(optionalIsland, player, playerToShare) }
            .exceptionally { error ->
                println(error.message)
                error.printStackTrace()
                null
            }
    }

    private fun processIsland(
        optionalIsland: Optional<Island>,
        player: Player,
        playerToShare: Player
    ): CompletableFuture<Void> {
        if (!optionalIsland.isPresent) {
            this.noticeService.create()
                .notice { messages -> messages.islandMessagesSubConfig.dontHaveIsland }
                .player(player.uniqueId)
                .send()
            return CompletableFuture.completedFuture(null)
        }

        val island = optionalIsland.get()
        return this.checkAndShareIsland(island.id, player, playerToShare)
    }

    private fun checkAndShareIsland(islandId: Int, player: Player, playerToShare: Player): CompletableFuture<Void> {
        return isIslandAlreadyShared(islandId, playerToShare.uniqueId).thenCompose { isAlreadyShared ->
            if (isAlreadyShared) {
                this.noticeService.create()
                    .notice { messages -> messages.islandMessagesSubConfig.islandIsAlreadySharedWithPlayer }
                    .player(player.uniqueId)
                    .placeholder("{player}", playerToShare.name)
                    .send()
                return@thenCompose CompletableFuture.completedFuture<Void>(null)
            }

            this.shareIslandWithPlayer(islandId, player, playerToShare)
        }
    }

    private fun shareIslandWithPlayer(islandId: Int, player: Player, playerToShare: Player): CompletableFuture<Void> {
        val islandShare = this.islandShareRepository.create(islandId, playerToShare.uniqueId)
        return this.islandShareRepository.save(islandShare).thenAccept { _ ->
            this.sendShareSuccessNotices(player, playerToShare)
        }
    }

    private fun sendShareSuccessNotices(player: Player, playerToShare: Player) {
        this.noticeService.create()
            .notice { messages -> messages.islandMessagesSubConfig.shareIsland }
            .player(player.uniqueId)
            .placeholder("{player}", playerToShare.name)
            .send()

        this.noticeService.create()
            .notice { messages -> messages.islandMessagesSubConfig.playerSharedIslandWithYou }
            .player(playerToShare.uniqueId)
            .placeholder("{player}", player.name)
            .send()
    }

    private fun isIslandAlreadyShared(islandId: Int, playerUUID: UUID): CompletableFuture<Boolean> {
        return this.islandShareRepository.get(islandId, playerUUID).thenApply {
            optionalIslandShare -> optionalIslandShare.isPresent
        }
    }

}