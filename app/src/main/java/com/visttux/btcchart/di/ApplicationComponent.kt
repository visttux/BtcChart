package com.visttux.btcchart.di

import com.visttux.data.di.DataComponent
import com.visttux.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent : DataComponent {
    fun inject(app: App)
}