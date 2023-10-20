package ru.berte.auth.infrastructure.datetime

import ru.berte.auth.presentation.constant.AuthServiceApiConstants
import java.time.format.DateTimeFormatter

object DateTimeFormatterHolder {
    val dtf = DateTimeFormatter.ofPattern(AuthServiceApiConstants.DATE_TIME_FORMAT)
}
