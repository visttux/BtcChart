package com.visttux.data

import javax.inject.Inject

class TimeProvider @Inject constructor() {
    fun currentTimeInMillis() = System.currentTimeMillis()
}
