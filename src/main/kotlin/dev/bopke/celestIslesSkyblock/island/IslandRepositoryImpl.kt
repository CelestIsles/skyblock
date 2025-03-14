package dev.bopke.celestIslesSkyblock.island

import com.dzikoysk.sqiffy.SqiffyDatabase
import com.dzikoysk.sqiffy.dsl.Column
import com.dzikoysk.sqiffy.dsl.eq
import org.bukkit.World
import java.util.*
import java.util.concurrent.CompletableFuture

class IslandRepositoryImpl(
    private val database: SqiffyDatabase
) : IslandRepository {

    override fun create(creatorUuid: UUID, world: World, islandName: String): UnidentifiedIsland {
        return UnidentifiedIsland(
            creator_uuid = creatorUuid,
            world = world.name,
            name = islandName
        )
    }

    override fun insert(island: UnidentifiedIsland): CompletableFuture<Island> {
        return CompletableFuture.supplyAsync {
            this.database.insert(IslandTable) {
                it[IslandTable.creator_uuid] = island.creator_uuid
                it[IslandTable.world] = island.world
                it[IslandTable.name] = island.name
            }.map { island.withId(id = it[IslandTable.id]) }.first()
        }
    }

    override fun <T> update(creatorUuid: UUID, column: Column<T>, value: T): CompletableFuture<Int> {
        return CompletableFuture.supplyAsync {
            this.database.update(IslandTable) { update -> update[column] = value }
                .where { IslandTable.creator_uuid eq creatorUuid }
                .execute()
        }
    }

    override fun getByCreatorUuid(creatorUuid: UUID): CompletableFuture<Optional<Island>> {
        return CompletableFuture.supplyAsync {
            Optional.ofNullable(
                this.database.select(IslandTable)
                    .where { IslandTable.creator_uuid eq creatorUuid }
                    .map { it.toIsland() }
                    .firstOrNull()
            )
        }
    }
}