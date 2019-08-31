package com.visttux.btc.model

import java.math.BigDecimal
import java.util.*


data class BtcInfo(
    val status: String,
    val name: String,
    val unit: Currency,
    val period: String,
    val description: String,
    val values: List<Value>
)

data class Value(
    val date: Date,
    val price: BigDecimal
)

sealed class Currency(val symbol: String, val code: String) {
    object USD : Currency("$", "USD")
    object EUR : Currency("€", "EUR")
    object UNKNOWN : Currency("?", "?")
}