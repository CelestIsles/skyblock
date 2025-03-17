package dev.bopke.celestIslesSkyblock.config

import dev.bopke.celestIslesSkyblock.config.subconfig.IslandMessagesSubConfig
import dev.bopke.celestIslesSkyblock.config.subconfig.WrongUsageSubConfig
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment
import eu.okaeri.configs.annotation.Header

@Header("## CelestIsles (Messages-Config) ##")
class MessageConfig : OkaeriConfig() {

    @Comment
    var wrongUsageSubConfig: WrongUsageSubConfig = WrongUsageSubConfig()

    @Comment
    var islandMessagesSubConfig: IslandMessagesSubConfig = IslandMessagesSubConfig()

}