package dev.bopke.celestIslesSkyblock.island

import com.dzikoysk.sqiffy.dsl.Column
import com.dzikoysk.sqiffy.dsl.Expression
import org.bukkit.World
import java.util.*
import java.util.concurrent.CompletableFuture

interface IslandRepository {

    fun create(creatorUuid: UUID, world: World, islandName: String): UnidentifiedIsland

    fun save(island: UnidentifiedIsland): CompletableFuture<Island>

    fun save(island: Island): CompletableFuture<Int>

    fun <T> update(expression: Expression<out Column<*>, Boolean>, column: Column<T>, value: T): CompletableFuture<Int>

    fun getByCreatorUuid(creatorUuid: UUID): CompletableFuture<Optional<Island>>

}