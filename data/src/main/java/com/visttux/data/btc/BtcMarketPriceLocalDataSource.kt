package com.visttux.data.btc

import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.BtcRequest
import com.visttux.data.TimeProvider
import io.reactivex.Maybe
import javax.inject.Inject

class BtcMarketPriceLocalDataSource @Inject constructor(private val timeProvider: TimeProvider) {
    private val cache: MutableMap<BtcRequest, Pair<Long, BtcInfo>> = mutableMapOf()

    fun get(request: BtcRequest): Maybe<BtcInfo> {
        return if (cache[request] == null || cacheHasExpired(request)) {
            Maybe.empty()
        } else {
            Maybe.just(cache[request]?.second)
        }
    }

    private fun cacheHasExpired(request: BtcRequest): Boolean =
        cache[request]?.first ?: 0L + EXPIRATION_TIME_OFFSET > System.currentTimeMillis()


    fun save(request: BtcRequest, btcInfo: BtcInfo) {
        cache[request] = Pair(timeProvider.currentTimeInMillis(), btcInfo)
    }

    companion object {
        const val EXPIRATION_TIME_OFFSET = 30_000_000
    }
}
