package com.visttux.btcchart.di

import com.visttux.data.di.DataModule
import com.visttux.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ActivityModule::class, DataModule::class])
open class ApplicationModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app
}