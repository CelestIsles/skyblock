package dev.bopke.celestIslesSkyblock.config.subconfig

import com.eternalcode.multification.bukkit.notice.BukkitNotice
import com.eternalcode.multification.notice.Notice
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Header

@Header("## CelestIsles (Island-Messages-Section) ##")
class IslandMessagesSubConfig : OkaeriConfig() {

    // Message schema:
    //  "<color:#22DD22>✔</color> <color:#00FF7F>content <color:#FFFF55>{name}</color>!</color>"  success message (colors)
    //  "<color:#FF2222>❌</color> <color:#FF4444>content <color:#FFAA00>{player}</color>!</color>" error message (colors)
    //  "<color:#2299FF>ℹ</color> <color:#00AAFF>content <color:##FFD700>{player}</color>.</color>" info message (colors)

    var alreadyHaveIsland: Notice = BukkitNotice.builder()
        .chat("<color:#FF2222>❌</color> <color:#FF4444>You already have island!</color>")
        .build()

    var createdIsland: Notice = BukkitNotice.builder()
        .chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>Successfully created island with name <color:#FFFF55>{name}</color>!</color>",
            "  <color:#00FF7F>If you want to change default name, use this command <color:#FFFF55>/island setname <name></color></color>",
            "  <color:#00FF7F>If you want to see all command, use this command <color:#FFFF55>/island</color></color>",
        )
        .build()

    var changedIslandName: Notice = Notice.chat(
        "<color:#22DD22>✔</color> <color:#00FF7F>Successfully changed island name to <color:#FFFF55>{name}</color>!</color>"
    )

}