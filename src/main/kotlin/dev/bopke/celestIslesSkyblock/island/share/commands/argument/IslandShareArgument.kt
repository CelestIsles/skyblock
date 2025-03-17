package dev.bopke.celestIslesSkyblock.island.share.commands.argument

import java.util.UUID

class IslandShareArgument(
    private val playerName: String,
    private val playerUuid: UUID
) {

    fun getPlayerName(): String {
        return this.playerName
    }

    fun getPlayerUuid(): UUID {
        return this.playerUuid
    }

}