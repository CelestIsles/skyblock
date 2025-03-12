package dev.bopke.celestIslesSkyblock.commands

import dev.bopke.celestIslesSkyblock.islands.IslandRepository
import dev.bopke.celestIslesSkyblock.worlds.WorldFactory
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player

@Command(name = "island", aliases = ["wyspa", "is"])
class IslandCommand {

    @Execute(name = "home", aliases = ["dom"])
    fun executeHome(@Context player: Player) {
        val island = IslandRepository.getInstance().getForUUID(player.uniqueId.toString())
        if (island == null) {
            player.sendMessage("No island :(")
            return
        }
        val world = island.world
        player.teleport(world.spawnLocation)
    }

    @Execute(name = "new", aliases = ["nowa"])
    fun executeNew(@Context player: Player) {
        val existingIsland = IslandRepository.getInstance().getForUUID(player.uniqueId.toString())
        if (existingIsland != null) {
            player.sendMessage("You already have an island.")
            return
        }
        val island = IslandRepository.getInstance()
            .create(player.uniqueId.toString(), WorldFactory().create(player.uniqueId.toString()))
        player.teleport(island.world.spawnLocation)
    }

    @Execute(name = "sethome", aliases = ["ustawdom"])
    fun executeSethome(@Context player: Player) {
        val island = IslandRepository.getInstance().getForUUID(player.uniqueId.toString())
        if (island == null || player.world.name != island.uuid) {
            player.sendMessage("It's not your island!")
            return
        }
        island.world.spawnLocation = player.location
        player.sendMessage("Spawn set.")
    }
}
