package dev.bopke.celestIslesSkyblock.notice

import com.eternalcode.multification.adventure.AudienceConverter
import com.eternalcode.multification.bukkit.BukkitMultification
import com.eternalcode.multification.translation.TranslationProvider
import dev.bopke.celestIslesSkyblock.config.MessageConfig
import net.kyori.adventure.platform.AudienceProvider
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.ComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class NoticeService(
    val messageConfig: MessageConfig,
    val audienceProvider: AudienceProvider,
    val miniMessage: MiniMessage
) : BukkitMultification<MessageConfig>() {

    override fun translationProvider(): TranslationProvider<MessageConfig> {
        return TranslationProvider { this.messageConfig }
    }

    override fun serializer(): ComponentSerializer<Component, Component, String> {
        return this.miniMessage;
    }

    override fun audienceConverter(): AudienceConverter<CommandSender> {
        return AudienceConverter { commandSender ->
            when (commandSender) {
                is Player -> this.audienceProvider.player(commandSender.uniqueId)
                else -> this.audienceProvider.console()
            }
        }
    }


}