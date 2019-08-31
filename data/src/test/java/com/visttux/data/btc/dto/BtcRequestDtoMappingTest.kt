package com.visttux.data.btc.dto

import com.visttux.btc.model.BtcRequest
import com.visttux.btc.model.BtcRequest.ONE_YEAR
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class BtcRequestDtoMappingTest {
    private lateinit var btcRequest: BtcRequest

    @Before
    fun setUp() {
        btcRequest = ONE_YEAR
    }

    @Test
    fun `assert mapping domain - data works properly`() {
        val dto = btcRequest.toDto()

        assertThat(dto.timeSpan, `is`("1year"))
    }
}