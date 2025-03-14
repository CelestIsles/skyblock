package dev.bopke.celestIslesSkyblock.notice

import com.eternalcode.multification.notice.Notice
import dev.rollczi.litecommands.handler.result.ResultHandler
import dev.rollczi.litecommands.handler.result.ResultHandlerChain
import dev.rollczi.litecommands.invocation.Invocation
import org.bukkit.command.CommandSender

class NoticeHandler(private val noticeService: NoticeService) : ResultHandler<CommandSender, Notice> {

    override fun handle(
        invocation: Invocation<CommandSender>?,
        result: Notice?,
        chain: ResultHandlerChain<CommandSender>?
    ) {
        this.noticeService.create()
            .viewer(invocation?.sender() ?: return)
            .notice(result ?: return)
            .send()
    }
}