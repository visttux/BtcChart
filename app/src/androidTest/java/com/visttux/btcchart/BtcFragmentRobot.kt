package com.visttux.btcchart

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

class BtcFragmentRobot {
    fun clickOnSevenDayChip() {
        onView(withId(R.id.chip7d))
            .perform(ViewActions.click())
    }

    fun checkChartIsRendered() {
        onView(withId(R.id.chart))
            .check(
                matches(
                    withEffectiveVisibility(Visibility.VISIBLE)
                )
            )
    }
}