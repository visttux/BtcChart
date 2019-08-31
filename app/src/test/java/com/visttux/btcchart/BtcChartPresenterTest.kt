package com.visttux.btcchart

import com.nhaarman.mockitokotlin2.*
import com.visttux.btcchart.btc.chart.BtcChartPresenter
import com.visttux.btcchart.btc.model.toUiModel
import com.visttux.btc.*
import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.BtcRequest
import com.visttux.btc.model.Currency
import com.visttux.btc.model.Value
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.math.BigDecimal
import java.util.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BtcChartPresenterTest {
    private lateinit var presenter: BtcChartPresenter

    private val view: BtcChartPresenter.View = mock()
    private val useCase: GetBtcMarketPriceUseCase = mock()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpSchedulers() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                Schedulers.trampoline()
            }
        }
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.reset()
        setUpSchedulers()
        presenter = BtcChartPresenter(useCase, view)
    }

    @Test
    fun `1 on error on stream, presenter should call to render error`() {
        val request = BtcRequest.ONE_YEAR
        val error = Exception("test exception")
        whenever(useCase.execute(request)) doReturn Single.error(error)

        presenter.onCreate()

        verify(view).showError(error)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `2 on success stream, presenter should call to render`() {
        val request = BtcRequest.ONE_YEAR
        val btcInfo = provideBtcInfo()
        whenever(useCase.execute(request)) doReturn Single.just(btcInfo)

        presenter.onCreate()

        verify(view).render(btcInfo.toUiModel())
        verifyNoMoreInteractions(view)
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