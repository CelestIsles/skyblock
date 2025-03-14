package dev.bopke.celestIslesSkyblock.islands

import org.bukkit.World
import java.util.concurrent.ConcurrentHashMap


// TODO: Involve database, remember something, etc
class IslandRepository {
    private val islands = ConcurrentHashMap<String, Island>()

    fun getForUUID(uuid: String): Island? {
        synchronized(islands) {
            return islands[uuid]
        }
    }

    private fun create(uuid: String, island: Island) {
        synchronized(islands) {
            islands[uuid] = island
        }
    }

    fun create(uuid: String, world: World): Island {
        val island = Island(uuid, world)
        create(uuid, island)
        return island
    }

    fun delete(uuid: String) {
        synchronized(islands) {
            islands.remove(uuid)
        }
    }

    companion object {
        private val Instance: IslandRepository = IslandRepository()
        fun getInstance(): IslandRepository = Instance
    }
}