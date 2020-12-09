package com.task.btc.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.task.btc.utils.Constants.Companion.DATE_FORMAT
import java.io.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

data class RecordDto(
        @JsonFormat(pattern = DATE_FORMAT)
        val datetime: ZonedDateTime,

        val amount: BigDecimal
) : Serializable

data class TimeDurationDto(
        @JsonFormat(pattern = DATE_FORMAT)
        val startDatetime: ZonedDateTime,

        @JsonFormat(pattern = DATE_FORMAT)
        val endDatetime: ZonedDateTime
) : Serializable