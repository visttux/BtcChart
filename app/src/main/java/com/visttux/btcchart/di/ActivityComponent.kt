package com.visttux.btcchart.di

import com.visttux.BaseActivity
import com.visttux.btcchart.di.scope.PerActivity
import com.visttux.btcchart.btc.chart.BtcChartFragment
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: BaseActivity)
    fun inject(fragment: BtcChartFragment)
}