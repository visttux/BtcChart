package com.visttux.data.btc

import com.nhaarman.mockitokotlin2.*
import com.visttux.btc.*
import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.BtcRequest.*
import com.visttux.btc.model.Currency
import com.visttux.btc.model.Value
import com.visttux.data.btc.dto.*
import com.visttux.data.btc.dto.BtcRequestDTO.*
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class BtcMarketPriceRepositoryTest {
    private lateinit var btcMarketPriceRepository: BtcMarketPriceRepository

    private val btcMarketPriceLocalDataSource: BtcMarketPriceLocalDataSource = mock()
    private val btcMarketPriceNetworkDataSource: BtcMarketPriceNetworkDataSource = mock()

    @Before
    fun setUp() {
        btcMarketPriceRepository =
            BtcMarketPriceRepositoryImpl(btcMarketPriceNetworkDataSource, btcMarketPriceLocalDataSource)
    }

    @Test
    fun `given local data source has data, verify repository returns from local stream`() {
        val infoDTO = provideBtcInfoDto()
        val info = provideBtcInfo()
        val localStream = Maybe.just(info)
        val networkStream = Single.just(infoDTO)
        whenever(btcMarketPriceLocalDataSource.get(SEVEN_DAYS)) doReturn localStream
        whenever(btcMarketPriceNetworkDataSource.get(SevenDays.timeSpan)) doReturn networkStream

        val stream = btcMarketPriceRepository.get(SEVEN_DAYS)

        stream
            .test()
            .assertResult(info)
    }


    @Test
    fun `given local data source has no data, verify repository returns from network stream`() {
        val btcInfoDTO = provideBtcInfoDto()
        val networkStream = Single.just(btcInfoDTO)
        whenever(btcMarketPriceLocalDataSource.get(SEVEN_DAYS)) doReturn Maybe.empty()
        whenever(btcMarketPriceNetworkDataSource.get(SevenDays.timeSpan)) doReturn networkStream

        val stream = btcMarketPriceRepository.get(SEVEN_DAYS)

        stream
            .test()
            .assertResult(btcInfoDTO.toDomainEntity())
    }

    private fun provideBtcInfoDto(): BtcInfoDTO {
        return BtcInfoDTO(
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