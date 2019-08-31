package com.visttux.data.btc

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.BtcRequest.*
import com.visttux.btc.model.Currency
import com.visttux.btc.model.Value
import com.visttux.data.TimeProvider
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class BtcMarketPriceLocalDataSourceTest {
    private lateinit var btcMarketPriceLocalDataSource: BtcMarketPriceLocalDataSource

    private val timeProvider: TimeProvider = mock {
        on { currentTimeInMillis() } doReturn System.currentTimeMillis()
    }

    @Before
    fun setUp() {
        btcMarketPriceLocalDataSource = BtcMarketPriceLocalDataSource(timeProvider)
    }

    @Test
    fun `on empty cache should return empty stream`() {
        val stream = btcMarketPriceLocalDataSource.get(ONE_YEAR)

        stream.test()
            .assertNoValues()
    }

    @Test
    fun `on cache containing searched values should return filled stream`() {
        val btcInfo = provideBtcInfo()
        btcMarketPriceLocalDataSource.save(ONE_YEAR, btcInfo)

        val stream = btcMarketPriceLocalDataSource.get(ONE_YEAR)

        stream.test()
            .assertResult(btcInfo)
    }

    @Test
    fun `on cache non containing searched values should return empty stream`() {
        val btcInfo = provideBtcInfo()
        btcMarketPriceLocalDataSource.save(ONE_YEAR, btcInfo)

        val stream = btcMarketPriceLocalDataSource.get(SIX_MONTHS)

        stream.test()
            .assertNoValues()
    }

    @Test
    fun `on cache with an expired time containing searched values should return empty stream`() {
        whenever(timeProvider.currentTimeInMillis()) doReturn 1L
        val btcInfo = provideBtcInfo()
        btcMarketPriceLocalDataSource.save(ONE_YEAR, btcInfo)

        val stream = btcMarketPriceLocalDataSource.get(SIX_MONTHS)

        stream.test()
            .assertNoValues()
    }

    private fun provideBtcInfo(): BtcInfo {
        return BtcInfo(
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
}