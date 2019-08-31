package com.visttux.data.di

import com.visttux.data.btc.BtcMarketPriceNetworkDataSource
import com.visttux.btc.BtcMarketPriceRepository

interface DataComponent {
  fun provideBtcMarketPriceNetworkDataSource(): BtcMarketPriceNetworkDataSource
  fun provideBtcMarketPriceRepository(): BtcMarketPriceRepository
}