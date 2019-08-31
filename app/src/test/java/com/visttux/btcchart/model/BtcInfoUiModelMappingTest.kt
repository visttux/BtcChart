package com.visttux.btcchart.model

import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.Currency
import com.visttux.btc.model.Value
import com.visttux.btcchart.btc.model.toUiModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class BtcInfoUiModelMappingTest {
    private lateinit var btcInfo: BtcInfo

    @Before
    fun setUp() {
        btcInfo = BtcInfo(
            "ok",
            "Market Price (USD)",
            Currency.USD,
            "day",
            "Average USD market price across major bitcoin exchanges.",
            listOf(
                Value(
                    Date(150_000L),
                    BigDecimal("11397.801666666666")
                )
            )
        )
    }

    @Test
    fun `assert mapping domain - ui works properly`() {
        val uiModel = btcInfo.toUiModel()

        assertThat(uiModel.status, `is`("ok"))
        assertThat(uiModel.name, `is`("Market Price (USD)"))
        assertThat(uiModel.unit, `is`("usd"))
        assertThat(uiModel.period, `is`("day"))
        assertThat(uiModel.description, `is`("Average USD market price across major bitcoin exchanges."))
        assertThat(uiModel.values[0].date, `is`(150_000_000f))
        assertThat(uiModel.values[0].price, `is`(11397.801666666666f))
    }
}