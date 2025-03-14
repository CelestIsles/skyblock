package dev.bopke.celestIslesSkyblock.island

import org.bukkit.World
import java.util.*
import java.util.concurrent.CompletableFuture

interface IslandRepository {

    fun create(creatorUuid: UUID, world: World, islandName: String): UnidentifiedIsland

    fun insert(island: UnidentifiedIsland): CompletableFuture<Island>

}