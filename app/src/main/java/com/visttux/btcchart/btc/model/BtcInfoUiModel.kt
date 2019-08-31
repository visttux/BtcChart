package com.visttux.btcchart.btc.model

import com.visttux.btc.model.BtcInfo

data class BtcInfoUiModel(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<ValueUiModel>

)

data class ValueUiModel(
    val date: Float,
    val price: Float
)

fun BtcInfo.toUiModel(): BtcInfoUiModel {
    return BtcInfoUiModel(
        this.status,
        this.name,
        this.unit.code,
        this.period,
        this.description,
        this.values.map {
            ValueUiModel(
                it.date.time.toFloat(),
                it.price.toFloat()
            )
        }
    )
}



