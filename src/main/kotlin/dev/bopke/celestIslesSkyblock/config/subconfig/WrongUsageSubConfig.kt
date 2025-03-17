package dev.bopke.celestIslesSkyblock.config.subconfig

import com.eternalcode.multification.notice.Notice
import eu.okaeri.configs.OkaeriConfig

class WrongUsageSubConfig : OkaeriConfig() {

    var invalidUsage: Notice = Notice.chat(
        "<color:#FF2222>❌</color> <color:#FF4444>Wrong command usage <color:#FFAA00>{COMMAND}</color>."
    )

    var invalidUsageHeader: Notice = Notice.chat(
        "<color:#FF2222>❌</color> <color:#FF4444>Wrong command usage!"
    )

    var invalidUsageEntry: Notice = Notice.chat("&8 » <color:#FFAA00>{SCHEME}</color>")

    var noPermission: Notice = Notice.chat(
        "<color:#FF2222>❌</color> <color:#FF4444>You do not have permission to use this command!"
    )

    var cantFindPlayer: Notice = Notice.chat(
        "<color:#FF2222>❌</color> <color:#FF4444>Can't find that player!</color>"
    )

    var onlyForPlayer: String = "&4Command only for players!"

}