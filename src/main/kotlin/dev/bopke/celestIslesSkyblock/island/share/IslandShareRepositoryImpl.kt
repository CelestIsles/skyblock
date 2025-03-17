package dev.bopke.celestIslesSkyblock.island.share

import com.dzikoysk.sqiffy.SqiffyDatabase
import com.dzikoysk.sqiffy.dsl.and
import com.dzikoysk.sqiffy.dsl.eq
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.CompletableFuture

class IslandShareRepositoryImpl(
    private val database: SqiffyDatabase
) : IslandShareRepository {

    override fun create(islandId: Int, player: Player): UnidentifiedIslandShare {
        return UnidentifiedIslandShare(
            island_id = islandId,
            player_uuid = player.uniqueId,
            player_name = player.name,
            shared_since = LocalDateTime.now()
        )
    }

    override fun save(islandShare: UnidentifiedIslandShare): CompletableFuture<IslandShare> {
        return CompletableFuture.supplyAsync {
            this.database.insert(IslandShareTable) {
                it[IslandShareTable.island_id] = islandShare.island_id
                it[IslandShareTable.player_uuid] = islandShare.player_uuid
                it[IslandShareTable.player_name] = islandShare.player_name
                it[IslandShareTable.shared_since] = islandShare.shared_since
            }.map { islandShare.withId(id = it[IslandShareTable.id]) }.first()
        }
    }

    override fun save(islandShare: IslandShare): CompletableFuture<Int> {
        return CompletableFuture.supplyAsync {
            this.database.update(IslandShareTable) {
                it[IslandShareTable.island_id] = islandShare.island_id
                it[IslandShareTable.player_uuid] = islandShare.player_uuid
                it[IslandShareTable.player_name] = islandShare.player_name
                it[IslandShareTable.shared_since] = islandShare.shared_since
            }
                .where { IslandShareTable.id eq islandShare.id }
                .execute()
        }
    }

    override fun delete(islandShare: IslandShare): CompletableFuture<Int> {
        return CompletableFuture.supplyAsync {
            this.database.delete(IslandShareTable).where { IslandShareTable.id eq islandShare.id }.execute()
        }
    }

    override fun get(islandId: Int, playerUUID: UUID): CompletableFuture<Optional<IslandShare>> {
        return CompletableFuture.supplyAsync {
            Optional.ofNullable(
                this.database.select(IslandShareTable)
                    .where {
                        and(
                            IslandShareTable.island_id eq islandId,
                            IslandShareTable.player_uuid eq playerUUID
                        )
                    }
                    .map { it.toIslandShare() }
                    .firstOrNull()
            )
        }
    }

    override fun get(islandId: Int, playerName: String): CompletableFuture<Optional<IslandShare>> {
        return CompletableFuture.supplyAsync {
            Optional.ofNullable(
                this.database.select(IslandShareTable)
                    .where {
                        and(
                            IslandShareTable.island_id eq islandId,
                            IslandShareTable.player_name eq playerName
                        )
                    }
                    .map { it.toIslandShare() }
                    .firstOrNull()
            )
        }
    }

    override fun getAll(islandId: Int): CompletableFuture<List<IslandShare>> {
        return CompletableFuture.supplyAsync {
            this.database.select(IslandShareTable)
                .where { IslandShareTable.island_id eq islandId }
                .map { it.toIslandShare() }
                .toList()
        }
    }

}