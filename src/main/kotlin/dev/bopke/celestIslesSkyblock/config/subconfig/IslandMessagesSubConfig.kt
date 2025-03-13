package dev.bopke.celestIslesSkyblock.config.subconfig

import com.eternalcode.multification.bukkit.notice.BukkitNotice
import com.eternalcode.multification.notice.Notice
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Header

@Header("## CelestIsles (Island-Messages-Section) ##")
class IslandMessagesSubConfig : OkaeriConfig() {

    var alreadyHaveIsland: Notice = BukkitNotice.builder()
        .chat("&cYou already have an island.")
        .build()

    var createdIsland: Notice = BukkitNotice.builder()
        .chat("&aYou have created an island!")
        .build()

}