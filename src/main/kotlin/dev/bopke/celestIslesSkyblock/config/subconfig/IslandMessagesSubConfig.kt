package dev.bopke.celestIslesSkyblock.config.subconfig

import com.eternalcode.multification.bukkit.notice.BukkitNotice
import com.eternalcode.multification.notice.Notice
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Header

@Header("## CelestIsles (Island-Messages-Section) ##")
class IslandMessagesSubConfig : OkaeriConfig() {

    // Schemat wiadomosci:
    //  "<color:#22DD22>✔</color> <color:#00FF7F>tresc <color:#FFFF55>{name}</color>!</color>" wiadomosc pomyslna (kolorki)
    //  "<color:#FF2222>❌</color> <color:#FF4444>tresc <color:#FFAA00>{player}</color>!</color>" wiadomosc bledu (kolorki)
    //  "<color:#2299FF>ℹ</color> <color:#00AAFF>tresc. <color:##FFD700>{player}</color>.</color>" wiadomosc informacyjna (kolorki)

    var alreadyHaveIsland: Notice = BukkitNotice.builder()
        .chat("<color:#FF2222>❌</color> <color:#FF4444>You already have island!</color>")
        .build()

    var createdIsland: Notice = BukkitNotice.builder()
        .chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>Successfully created island with name <color:#FFFF55>{name}</color>!</color>",
            "  <color:#00FF7F>If you want to change default name, use this command <color:#FFFF55>/island setname <nazwa></color></color>",
            "  <color:#00FF7F>If you want to see all command, use this command <color:#FFFF55>/island</color></color>",
        )
        .build()

    var changedIslandName: Notice = Notice.chat(
        "<color:#22DD22>✔</color> <color:#00FF7F>Successfully changed island name to <color:#FFFF55>{name}</color>!</color>"
    )

}