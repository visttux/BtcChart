package com.visttux.btc

import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.BtcRequest
import io.reactivex.Single
import javax.inject.Inject

class GetBtcMarketPriceUseCase @Inject constructor(
    private val btcMarketPriceRepository: BtcMarketPriceRepository
) {
    fun execute(request: BtcRequest): Single<BtcInfo> {
        return btcMarketPriceRepository.get(request)
    }
}