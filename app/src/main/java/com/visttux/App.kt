package com.visttux

import com.visttux.data.di.DataModule
import com.visttux.btcchart.di.ApplicationComponent
import com.visttux.btcchart.di.ApplicationModule
import com.visttux.btcchart.di.DaggerApplicationComponent

open class App : android.app.Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeInjection()
    }

    open fun getAppComponent(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .dataModule(DataModule())
            .build()
    }

    private fun initializeInjection() {
        component = getAppComponent()
        component.inject(this)
    }

    companion object {
        lateinit var instance: App private set
    }
}