package com.visttux.data.di

import com.visttux.data.ServiceProvider
import com.visttux.data.btc.BtcMarketPriceNetworkDataSource
import dagger.Module

@Module
class TestDataModule : DataModule() {
    override fun provideBtcMarketPriceNetworkDataSource(): BtcMarketPriceNetworkDataSource =
        ServiceProvider(LOCAL_URL).provideService(BtcMarketPriceNetworkDataSource::class.java)

    companion object {
        const val LOCAL_URL = "http://localhost:8080"
    }
}