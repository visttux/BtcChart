package com.visttux.btcchart.btc.chart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.visttux.App
import com.visttux.btcchart.R
import com.visttux.btcchart.btc.model.BtcInfoUiModel
import com.visttux.btcchart.di.ActivityModule
import com.visttux.btcchart.di.DaggerActivityComponent
import kotlinx.android.synthetic.main.chart_fragment_view.*
import kotlinx.android.synthetic.main.chart_range_selector.*
import javax.inject.Inject


class BtcChartFragment : Fragment(), BtcChartPresenter.View {
    @Inject
    lateinit var presenter: BtcChartPresenter
    private var isLoading: Boolean = false
    private var previousCheckedChip = R.id.chip1y

    init {
        DaggerActivityComponent
            .builder()
            .applicationComponent(App.instance.component)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.chart_fragment_view, container, false)

    private fun showLoading() {
        chart.visibility = GONE
        loading.visibility = VISIBLE
    }

    private fun hideLoading() {
        isLoading = false
        chart.visibility = VISIBLE
        loading.visibility = GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
        isLoading = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //TODO serialize data
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        //TODO deserialize data
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpChips()
        if (isLoading || savedInstanceState != null) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    private fun setUpChips() {
        chipGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
            if (checkedId == View.NO_ID) {
                chipGroup.check(previousCheckedChip)
            } else {
                previousCheckedChip = checkedId
                when (checkedId) {
                    R.id.chip7d -> presenter.onSevenDayGraphRequested()
                    R.id.chip30d -> presenter.onThirtyDayGraphRequested()
                    R.id.chip6m -> presenter.onSixMonthGraphRequested()
                    R.id.chip1y -> presenter.onOneYearGraphRequested()
                }
                showLoading()
                isLoading = true
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpToolbar()
        setUpChart()
    }

    private fun setUpChart() {
        chart.setUp()
    }

    private fun setUpToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_bitcoin)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun render(btcInfo: BtcInfoUiModel) {
        hideLoading()
        chart.render(btcInfo)
    }

    override fun showError(error: Throwable?) {
        Log.e("error on BtcChart", error.toString())
        view?.let { view ->
            error?.toString()?.let { Snackbar.make(view, it, Snackbar.LENGTH_LONG).show() }
        }
    }

}