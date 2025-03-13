package dev.bopke.celestIslesSkyblock.notice.adventure

import net.kyori.adventure.text.Component
import java.util.function.UnaryOperator
import java.util.regex.Pattern

class LegacyColorProcessor : UnaryOperator<Component> {
    override fun apply(component: Component): Component {
        return component.replaceText { builder ->
            builder
                .match(Pattern.compile(".*"))
                .replacement { matchResult, _ -> Legacy.component(matchResult.group()) }
        }
    }
}