package dev.bopke.celestIslesSkyblock.notice.adventure

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object Legacy {

    val AMPERSAND_SERIALIZER: LegacyComponentSerializer = LegacyComponentSerializer.builder()
        .character('&')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build()

    val RESET_ITALIC: Component = Component.text()
        .decoration(TextDecoration.ITALIC, false)
        .build()

    fun title(text: String): Component {
        return RESET_ITALIC.append(AMPERSAND_SERIALIZER.deserialize(text))
    }

    fun component(text: String): Component {
        return AMPERSAND_SERIALIZER.deserialize(text)
    }

    fun component(texts: List<String>): List<Component> {
        return texts.map { component(it) }
    }

}