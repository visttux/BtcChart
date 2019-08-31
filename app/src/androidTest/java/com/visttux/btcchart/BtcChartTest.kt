package com.visttux.btcchart

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.visttux.BaseActivity
import okhttp3.mockwebserver.Dispatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


@RunWith(AndroidJUnit4::class)
@LargeTest
class BtcChartTest {

    @get:Rule
    var activityRule: ActivityTestRule<BaseActivity> = ActivityTestRule(BaseActivity::class.java)

    private val server: MockWebServer = MockWebServer()
    private val robot: BtcFragmentRobot = BtcFragmentRobot()

    @Before
    @Throws(Exception::class)
    fun setup() {
        startLocalServerWithMockedResponse()
    }

    private fun startLocalServerWithMockedResponse() {
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    "/charts/market-price?format=json&timespan=7days" -> return MockResponse()
                        .setResponseCode(200)
                        .setBody("{\"status\":\"ok\",\"name\":\"Market Price (USD)\",\"unit\":\"USD\",\"period\":\"day\",\"description\":\"Average USD market price across major bitcoin exchanges.\",\"values\":[{\"x\":1565481600,\"y\":11377.804166666669},{\"x\":1565568000,\"y\":11397.801666666666},{\"x\":1565654400,\"y\":11144.38916666667},{\"x\":1565740800,\"y\":10450.813333333334},{\"x\":1565827200,\"y\":9988.947499999998},{\"x\":1565913600,\"y\":10230.733333333332},{\"x\":1566000000,\"y\":10292.383333333333}]}")
                }
                return MockResponse()
                    .setResponseCode(404)
            }
        }
        server.setDispatcher(dispatcher)
        server.start(8080)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun test() {
        givenChartScreen()
        whenUserSelectsSevenDays()
        thenChartShouldBeRendered()
    }

    private fun givenChartScreen() {}

    private fun whenUserSelectsSevenDays() {
        robot.clickOnSevenDayChip()
    }

    private fun thenChartShouldBeRendered() {
        robot.checkChartIsRendered()
    }

}