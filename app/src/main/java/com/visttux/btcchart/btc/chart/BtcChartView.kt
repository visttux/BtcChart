package com.visttux.btcchart.btc.chart

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.visttux.btcchart.R
import com.visttux.btcchart.btc.model.BtcInfoUiModel
import com.visttux.getColor
import java.text.SimpleDateFormat
import java.util.*

class BtcChartView(context: Context, attributeSet: AttributeSet?) : LineChart(context, attributeSet) {

    fun render(btcInfo: BtcInfoUiModel) {
        val list = buildEntryList(btcInfo)
        formatAxisLegendUpponListSize(list.size)
        if (graphHasData()) {
            drawRecycling(list)
        } else {
            drawNew(list)
        }
    }

    private fun formatAxisLegendUpponListSize(size: Int) {
        xAxis.setValueFormatter { value, _ ->
            val format = when (size) {
                7 -> DAY_FORMAT
                30 -> DAY_FORMAT
                90 -> DAY_FORMAT
                180 -> MONTH_FORMAT
                else -> MONTH_FORMAT
            }
            val formatter = SimpleDateFormat(format, Locale.US)
            formatter.format(Date(value.toLong()))
        }
    }

    private fun drawNew(list: List<Entry>) {
        val lineDataSet = LineDataSet(list, context.getString(R.string.chart_title))
        lineDataSet.color = getColor(R.color.light_blue)
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawCircles(false)
        data = LineData(lineDataSet)
        invalidate()
    }

    private fun drawRecycling(list: List<Entry>) {
        val set = data.getDataSetByIndex(0) as LineDataSet
        set.values = list
        set.notifyDataSetChanged()
        data.notifyDataChanged()
        notifyDataSetChanged()
        invalidate()
    }

    private fun graphHasData() = data != null && data.dataSetCount > 0

    private fun buildEntryList(btcInfo: BtcInfoUiModel): List<Entry> {
        return btcInfo.values.map { Entry(it.date, it.price) }
    }

    fun setUp() {
        setUpGeneralParams()
        setUpAxisParams()
        setUpLegend()
    }

    private fun setUpGeneralParams() {
        setBackgroundColor(getColor(R.color.dark_background))
        description.isEnabled = false
        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(true)
        setPinchZoom(true)
    }

    private fun setUpLegend() {
        legend.textColor = ContextCompat.getColor(context, R.color.white)
        legend.form = Legend.LegendForm.LINE
    }

    private fun setUpAxisParams() {
        axisLeft.textColor = getColor(R.color.white)
        axisRight.isEnabled = false
        xAxis.textColor = getColor(R.color.white)
    }

    companion object {
        const val DAY_FORMAT = "dd. MMM"
        const val MONTH_FORMAT = "MMM ''YY"
    }
}