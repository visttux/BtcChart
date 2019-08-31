package com.visttux.btcchart.di

import com.visttux.BaseActivity
import com.visttux.btcchart.di.scope.PerActivity
import com.visttux.btcchart.btc.chart.BtcChartPresenter
import com.visttux.btcchart.btc.chart.BtcChartFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.properties.Delegates

@Module
class ActivityModule {
    private var activity: BaseActivity by Delegates.notNull()
    private var chartFragment: BtcChartFragment by Delegates.notNull()

    constructor(activity: BaseActivity) {
        this.activity = activity
    }

    constructor(fragment: BtcChartFragment) {
        this.chartFragment = fragment
    }

    @Provides
    @Singleton
    fun provideBaseActivity() = activity

    @Provides
    @PerActivity
    fun provideChartView() = chartFragment

    @Provides
    @PerActivity
    fun provideChartCallback() = chartFragment as BtcChartPresenter.View
}