package com.visttux.data.btc.dto

import com.visttux.btc.model.BtcRequest
import com.visttux.btc.model.BtcRequest.*
import com.visttux.data.btc.dto.BtcRequestDTO.*

sealed class BtcRequestDTO(val timeSpan: String) {
    object SevenDays : BtcRequestDTO("7days")
    object ThirtyDays : BtcRequestDTO("30days")
    object SixMonths : BtcRequestDTO("180days")
    object OneYear : BtcRequestDTO("1year")
}

fun BtcRequest.toDto() : BtcRequestDTO {
    return when(this) {
        SEVEN_DAYS -> SevenDays
        THIRTY_DAYS -> ThirtyDays
        SIX_MONTHS -> SixMonths
        ONE_YEAR -> OneYear
    }
}