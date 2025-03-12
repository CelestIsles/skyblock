package dev.bopke.celestIslesSkyblock.islands

import org.bukkit.World


// TODO: Involve database, remember something, etc
public class IslandRepository {
    private val islands = HashMap<String, Island>()

    public fun getForUUID(uuid: String): Island? {
        synchronized(islands) {
            return islands[uuid]
        }
    }

    public fun create(uuid: String, island: Island) {
        synchronized(islands) {
            islands[uuid] = island
        }
    }

    public fun create(uuid: String, world: World): Island {
        val island = Island(uuid, world)
        create(uuid, island)
        return island
    }

    public fun delete(uuid: String) {
        synchronized(islands) {
            islands.remove(uuid)
        }
    }

    companion object {
        private val Instance: IslandRepository = IslandRepository()
        fun getInstance(): IslandRepository = Instance
    }
}