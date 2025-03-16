package dev.bopke.celestIslesSkyblock.island.share

import org.bukkit.entity.Player
import java.util.Optional
import java.util.UUID
import java.util.concurrent.CompletableFuture

interface IslandShareRepository {

    fun create(islandId: Int, player: Player): UnidentifiedIslandShare

    fun save(islandShare: UnidentifiedIslandShare): CompletableFuture<IslandShare>

    fun save(islandShare: IslandShare): CompletableFuture<Int>

    fun get(islandId: Int, playerUUID: UUID): CompletableFuture<Optional<IslandShare>>

    fun getAll(islandId: Int): CompletableFuture<List<IslandShare>>

}