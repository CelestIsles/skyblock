package dev.bopke.celestIslesSkyblock.command.handler

import com.eternalcode.multification.shared.Formatter
import dev.bopke.celestIslesSkyblock.notice.NoticeService
import dev.rollczi.litecommands.handler.result.ResultHandlerChain
import dev.rollczi.litecommands.invalidusage.InvalidUsage
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler
import dev.rollczi.litecommands.invocation.Invocation
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class InvalidUsageHandler(
    private val noticeService: NoticeService
) : InvalidUsageHandler<CommandSender> {

    override fun handle(
        invocation: Invocation<CommandSender>?,
        result: InvalidUsage<CommandSender>?,
        chain: ResultHandlerChain<CommandSender>?
    ) {
        val commandSender = invocation!!.sender()
        val schematic = result!!.schematic

        if (commandSender !is Player) {
            return
        }

        val player: Player = commandSender
        val uniqueId = player.uniqueId

        val formatter = Formatter()
        formatter.register("{COMMAND}", schematic.first())

        if (schematic.isOnlyFirst) {
            this.noticeService.create()
                .notice { messages -> messages.wrongUsageSubConfig.invalidUsage }
                .formatter(formatter)
                .player(uniqueId)
                .send()
            return
        }

        this.noticeService.create()
            .notice { messages -> messages.wrongUsageSubConfig.invalidUsageHeader }
            .player(uniqueId)
            .send()

        for (scheme in schematic.all()) {
            formatter.register("{SCHEME}", scheme)
            this.noticeService.create()
                .notice { messages -> messages.wrongUsageSubConfig.invalidUsageEntry }
                .formatter(formatter)
                .player(uniqueId)
                .send()
        }
    }
}