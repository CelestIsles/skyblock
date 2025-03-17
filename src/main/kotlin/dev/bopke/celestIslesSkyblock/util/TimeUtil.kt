package dev.bopke.celestIslesSkyblock.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {

    // in the future, we can add here more formatters like short one with only time
    private val defaultFormatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")

    fun format(dateTime: LocalDateTime): String {
        return dateTime.format(defaultFormatter)
    }

}