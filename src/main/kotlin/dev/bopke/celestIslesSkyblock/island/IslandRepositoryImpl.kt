package dev.bopke.celestIslesSkyblock.island

import com.dzikoysk.sqiffy.SqiffyDatabase
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

    override fun insert(island: UnidentifiedIsland): CompletableFuture<Island>{
        return CompletableFuture.supplyAsync {
            println("Inserting island")
            this.database.insert(IslandTable) {
                it[IslandTable.creator_uuid] = island.creator_uuid
                it[IslandTable.world] = island.world
                it[IslandTable.name] = island.name
            }.map { island.withId(id = it[IslandTable.id]) }.first()
        }
    }
}