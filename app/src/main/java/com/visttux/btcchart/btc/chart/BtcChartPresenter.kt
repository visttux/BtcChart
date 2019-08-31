package com.visttux.btcchart.btc.chart

import com.visttux.btc.model.BtcRequest
import com.visttux.btc.model.BtcRequest.*
import com.visttux.btc.GetBtcMarketPriceUseCase
import com.visttux.btcchart.btc.model.BtcInfoUiModel
import com.visttux.btcchart.btc.model.toUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BtcChartPresenter @Inject constructor(
    private val getBtcMarketPriceUseCase: GetBtcMarketPriceUseCase,
    private val view: View
) {
    private var disposable: Disposable? = null

    fun onCreate() {
        getBtcData(ONE_YEAR)
    }

    private fun getBtcData(request: BtcRequest) {
        disposable = getBtcMarketPriceUseCase.execute(request)
            .subscribeOn(Schedulers.io())
            .map { it.toUiModel() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.render(it) },
                { view.showError(it) }
            )
    }

    fun onDestroy() {
        disposable?.dispose()
    }

    fun onSevenDayGraphRequested() {
        getBtcData(SEVEN_DAYS)
    }

    fun onThirtyDayGraphRequested() {
        getBtcData(THIRTY_DAYS)
    }

    fun onSixMonthGraphRequested() {
        getBtcData(SIX_MONTHS)
    }

    fun onOneYearGraphRequested() {
        getBtcData(ONE_YEAR)
    }

    interface View {
        fun render(btcInfo: BtcInfoUiModel)
        fun showError(error: Throwable?)
    }
}
