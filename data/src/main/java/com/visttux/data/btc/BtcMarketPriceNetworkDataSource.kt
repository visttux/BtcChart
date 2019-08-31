package com.visttux.data.btc

import com.visttux.data.btc.dto.BtcInfoDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BtcMarketPriceNetworkDataSource {
    @GET("/charts/market-price?format=json")
    @Headers("Accept: application/json")
    fun get(@Query("timespan") timespan: String): Single<BtcInfoDTO>
}