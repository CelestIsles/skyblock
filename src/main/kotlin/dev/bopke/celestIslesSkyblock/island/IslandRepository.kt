package dev.bopke.celestIslesSkyblock.island

import com.dzikoysk.sqiffy.dsl.Column
import org.bukkit.World
import java.util.*
import java.util.concurrent.CompletableFuture

interface IslandRepository {

    fun create(creatorUuid: UUID, world: World, islandName: String): UnidentifiedIsland

    fun insert(island: UnidentifiedIsland): CompletableFuture<Island>

    fun <T> update(creatorUuid: UUID, column: Column<T>, value: T): CompletableFuture<Int>

    fun getByCreatorUuid(creatorUuid: UUID): CompletableFuture<Optional<Island>>

}