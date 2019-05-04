package by.lebedev.nanopoolmonitoring.fragments.dashboard

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.retrofit.entity.dashboard.DataChart
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    @Inject
    lateinit var tabIntent: TabIntent
    val nf = NumberFormat.getInstance()
    var coin: String = ""
    var wallet: String = ""
    lateinit var chart: LineChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(by.lebedev.nanopoolmonitoring.R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart = chartView

        nf.maximumFractionDigits = 4

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        getGeneralInfo()
        getChart()
        chart.getDescription().setEnabled(false)

    }

    fun getGeneralInfo() {
        val d = provideApi().getGeneralInfo(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result.status && balance != null) {

                    balance.setText(nf.format(result.data.balance).toString().plus(" ETH"))
                    current_hashrate.setText(result.data.hashrate.toString().plus(" H/s"))
                    hours_6.setText(result.data.avgHashrate.h6.toString().plus(" H/s"))
                    hours_24.setText(result.data.avgHashrate.h24.toString().plus(" H/s"))

                    getProfitInfo(coin, result.data.avgHashrate.h6)
                }

            }, {
                Log.e("err", it.message)
            })
    }

    fun getProfitInfo(coin: String, hashrate: Double) {
        val d = provideApi().getProfit(coin, hashrate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (minute_coin != null && hour_coin != null && day_coin != null && week_coin != null && month_coin != null) {

                    minute_coin.setText(nf.format(result.data.minute.coins).toString())
                    minute_btc.setText(nf.format(result.data.minute.bitcoins).toString())
                    minute_usd.setText(Math.round(result.data.minute.dollars * 1000).div(1000).toString())

                    hour_coin.setText(nf.format(result.data.hour.coins).toString())
                    hour_btc.setText(nf.format(result.data.hour.bitcoins).toString())
                    hour_usd.setText(Math.round(result.data.hour.dollars * 1000.0).div(1000.0).toString())

                    day_coin.setText(nf.format(result.data.day.coins).toString())
                    day_btc.setText(nf.format(result.data.day.bitcoins).toString())
                    day_usd.setText(Math.round(result.data.day.dollars * 100.0).div(100.0).toString())

                    week_coin.setText(nf.format(result.data.week.coins).toString())
                    week_btc.setText(nf.format(result.data.week.bitcoins).toString())
                    week_usd.setText(Math.round(result.data.week.dollars * 100.0).div(100.0).toString())

                    month_coin.setText(nf.format(result.data.month.coins).toString())
                    month_btc.setText(nf.format(result.data.month.bitcoins).toString())
                    month_usd.setText(Math.round(result.data.month.dollars * 100.0).div(100.0).toString())
                }
            }, {
                Log.e("err", it.message)
            })
    }

    fun getChart() {
        val d = provideApi().getChart(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->

                val resultTen = ArrayList<DataChart>()

                for (i in 0..9) {
                    resultTen.add(i, result.data.get(i))
                }


                val entries = ArrayList<Entry>()

                for (data in result.data) {
                    entries.add(Entry((data.date * 1000).toFloat(), data.hashrate.toFloat()))
                }

                Collections.sort(entries, EntryXComparator())

                val xAxis = chart.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.textSize = 10f
                xAxis.textColor = Color.WHITE
                xAxis.setDrawAxisLine(false)
                xAxis.setDrawGridLines(true)
                xAxis.textColor = Color.rgb(255, 192, 56)
                xAxis.setCenterAxisLabels(true)
                xAxis.granularity = 1f // one hour
                xAxis.valueFormatter = object : ValueFormatter() {

                    val mFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

                    override fun getFormattedValue(value: Float): String {

                        val millis = value.toLong()
                        return mFormat.format(Date(millis))
                    }
                }


                val dataSet = LineDataSet(entries, "Hashrate")
                dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER)
                val lineData = LineData(dataSet)


                val leftAxis = chart.axisLeft
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
                leftAxis.textColor = ColorTemplate.getHoloBlue()
                leftAxis.setDrawGridLines(true)
                leftAxis.isGranularityEnabled = true
                leftAxis.axisMinimum = 0f
                leftAxis.axisMaximum = 170f
                leftAxis.yOffset = -9f
                leftAxis.textColor = Color.rgb(255, 192, 56)

                val rightAxis = chart.axisRight
                rightAxis.isEnabled = false




                chart.data = lineData
                chart.animateX(1000)
                chart.animateY(1000)

                chart.invalidate()
                Log.e("AAA", result.toString())
            }
    }

}