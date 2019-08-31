package com.visttux

import com.visttux.btcchart.di.ApplicationComponent
import com.visttux.btcchart.di.ApplicationModule
import com.visttux.btcchart.di.DaggerApplicationComponent
import com.visttux.data.di.TestDataModule

/* This App class is used to inject TestDataModule */
class TestApp : App() {
    override fun getAppComponent(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .dataModule(TestDataModule())
            .build()
    }
}