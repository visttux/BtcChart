package com.visttux.data.btc.dto

import com.nhaarman.mockitokotlin2.eq
import com.visttux.btc.model.Currency
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertTrue

class BtcInfoDtoMappingTest {
    private lateinit var btcInfoDTO: BtcInfoDTO

    @Before
    fun setUp() {
        btcInfoDTO = BtcInfoDTO(
            "ok",
            "Market Price (USD)",
            CurrencyDTO.USD,
            "day",
            "Average USD market price across major bitcoin exchanges.",
            listOf(
                ValueDTO(
                    150_000L,
                    BigDecimal("11397.801666666666")
                )
            )
        )
    }

    @Test
    fun `assert mapping data - domain works properly`() {
        val btcInfo = btcInfoDTO.toDomainEntity()

        assertThat(btcInfo.status, `is`("ok"))
        assertThat(btcInfo.name, `is`("Market Price (USD)"))
        assertTrue { btcInfo.unit is Currency.USD }
        assertThat(btcInfo.period, `is`("day"))
        assertThat(btcInfo.description, `is`("Average USD market price across major bitcoin exchanges."))
        assertThat(btcInfo.values[0].date, `is`(eq(Date(150_000_000L))))
        assertThat(btcInfo.values[0].price, `is`(eq(BigDecimal("11397.801666666666"))))
    }
}