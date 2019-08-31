package com.visttux.data.di

import com.visttux.data.ServiceProvider
import com.visttux.data.btc.BtcMarketPriceLocalDataSource
import com.visttux.data.btc.BtcMarketPriceNetworkDataSource
import com.visttux.data.btc.BtcMarketPriceRepositoryImpl
import com.visttux.btc.BtcMarketPriceRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
open class DataModule {
    @Provides
    @Reusable
    fun provideBtcMarketPriceRepository(
        btcMarketPriceNetworkDataSource: BtcMarketPriceNetworkDataSource,
        btcMarketPriceLocalDataSource: BtcMarketPriceLocalDataSource
    ): BtcMarketPriceRepository =
        BtcMarketPriceRepositoryImpl(btcMarketPriceNetworkDataSource, btcMarketPriceLocalDataSource)

    @Provides
    @Reusable
    open fun provideBtcMarketPriceNetworkDataSource() =
        ServiceProvider(BASE_URL).provideService(BtcMarketPriceNetworkDataSource::class.java)

    companion object {
        private const val BASE_URL = "https://api.blockchain.info"
    }
}

