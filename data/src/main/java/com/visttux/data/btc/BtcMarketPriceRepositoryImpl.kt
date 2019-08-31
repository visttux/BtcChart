package com.visttux.data.btc

import com.visttux.btc.model.BtcInfo
import com.visttux.data.btc.dto.toDomainEntity
import com.visttux.btc.BtcMarketPriceRepository
import com.visttux.btc.model.BtcRequest
import com.visttux.data.btc.dto.toDto
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class BtcMarketPriceRepositoryImpl @Inject constructor(
    private val btcMarketPriceNetworkDataSource: BtcMarketPriceNetworkDataSource,
    private val btcMarketPriceLocalDataSource: BtcMarketPriceLocalDataSource
) : BtcMarketPriceRepository {

    override fun get(request: BtcRequest): Single<BtcInfo> {
        val requestTimeSpanParam = request.toDto().timeSpan
        val local = btcMarketPriceLocalDataSource.get(request)
        val network = btcMarketPriceNetworkDataSource.get(requestTimeSpanParam)
            .map { it.toDomainEntity() }
            .doOnSuccess { btcMarketPriceLocalDataSource.save(request, it) }
        return Maybe.concat(local, network.toMaybe()).firstElement().toSingle()
    }
}