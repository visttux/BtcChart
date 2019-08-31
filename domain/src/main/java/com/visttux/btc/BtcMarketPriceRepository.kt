package com.visttux.btc

import com.visttux.btc.model.BtcInfo
import com.visttux.btc.model.BtcRequest
import io.reactivex.Single

interface BtcMarketPriceRepository {
  fun get(request: BtcRequest): Single<BtcInfo>
}