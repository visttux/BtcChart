package com.visttux.data.btc.dto

import com.google.gson.annotations.SerializedName
import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.Currency.*
import com.visttux.btc.model.Value
import java.math.BigDecimal
import java.util.*

data class BtcInfoDTO(
    val status: String,
    val name: String,
    val unit: CurrencyDTO,
    val period: String,
    val description: String,
    val values: List<ValueDTO>
)

data class ValueDTO(
    @SerializedName("x") val date: Long,
    @SerializedName("y") val price: BigDecimal
)

enum class CurrencyDTO {
    EUR, USD
}

fun BtcInfoDTO.toDomainEntity(): BtcInfo {
    return BtcInfo(
        this.status,
        this.name,
        when (this.unit) {
            CurrencyDTO.USD -> USD
            CurrencyDTO.EUR -> EUR
        },
        this.period,
        this.description,
        this.values.map {
            Value(
                Date(it.date * MILLIS_IN_SECOND),
                it.price
            )
        }
    )
}

const val MILLIS_IN_SECOND = 1000L