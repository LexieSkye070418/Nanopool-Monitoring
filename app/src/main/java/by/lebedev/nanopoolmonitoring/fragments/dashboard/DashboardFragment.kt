package by.lebedev.nanopoolmonitoring.fragments.dashboard

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.retrofit.entity.chart.ChartData
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import com.github.mikephil.charting.components.AxisBase
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import javax.inject.Inject
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.transform.Templates
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {
    lateinit var mAdView: AdView

    @Inject
    lateinit var tabIntent: TabIntent
    val nf = NumberFormat.getInstance()
    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()
            ?.setBackgroundDrawableResource(R.drawable.nanopool_background)


        MobileAds.initialize(this.context, "ca-app-pub-1501215034144631~3780667725")

        val adView = AdView(this.context)
        adView.adSize = AdSize.BANNER

        adView.adUnitId = "ca-app-pub-1501215034144631/8209904262"

        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        nf.maximumFractionDigits = 4

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        getGeneralInfo()

        getChartInfo()


    }

    fun getGeneralInfo() {
        val d = provideApi().getGeneralInfo(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result.status && balance != null) {

                    balance.setText(nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase())
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { balance.setTextColor(it) }

                    current_hashrate.setText(
                        result.data.hashrate.toString().plus(" ").plus(
                            tabIntent.getWorkerHashType(
                                coin
                            )
                        )
                    )
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { current_hashrate.setTextColor(it) }

                    hours_6.setText(
                        result.data.avgHashrate.h6.toString().plus(" ").plus(
                            tabIntent.getWorkerHashType(
                                coin
                            )
                        )
                    )
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { hours_6.setTextColor(it) }

                    hours_24.setText(
                        result.data.avgHashrate.h24.toString().plus(" ").plus(
                            tabIntent.getWorkerHashType(
                                coin
                            )
                        )
                    )
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { hours_24.setTextColor(it) }

                    if (result.data.avgHashrate.h6 > 1) {
                        getProfitInfo(coin, result.data.avgHashrate.h6)
                    }
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

                if (view != null && minute_coin != null && hour_coin != null && day_coin != null && week_coin != null && month_coin != null) {
                    minute_coin.setText(nf.format(result.data.minute.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { minute_coin.setTextColor(it) }
                    minute_btc.setText(nf.format(result.data.minute.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { minute_btc.setTextColor(it) }
                    minute_usd.setText(Math.round(result.data.minute.dollars * 1000).div(1000).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { minute_usd.setTextColor(it) }

                    hour_coin.setText(nf.format(result.data.hour.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { hour_coin.setTextColor(it) }
                    hour_btc.setText(nf.format(result.data.hour.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { hour_btc.setTextColor(it) }
                    hour_usd.setText(Math.round(result.data.hour.dollars * 1000.0).div(1000.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { hour_usd.setTextColor(it) }

                    day_coin.setText(nf.format(result.data.day.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { day_coin.setTextColor(it) }
                    day_btc.setText(nf.format(result.data.day.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { day_btc.setTextColor(it) }
                    day_usd.setText(Math.round(result.data.day.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { day_usd.setTextColor(it) }

                    week_coin.setText(nf.format(result.data.week.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { week_coin.setTextColor(it) }
                    week_btc.setText(nf.format(result.data.week.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { week_btc.setTextColor(it) }
                    week_usd.setText(Math.round(result.data.week.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { week_usd.setTextColor(it) }

                    month_coin.setText(nf.format(result.data.month.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { month_coin.setTextColor(it) }
                    month_btc.setText(nf.format(result.data.month.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { month_btc.setTextColor(it) }
                    month_usd.setText(Math.round(result.data.month.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { month_usd.setTextColor(it) }
                }
            }, {
                Log.e("err", it.message)
            })
    }

    fun getChartInfo() {
        val d = provideApi().getChart(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (!result.data.isEmpty() && lineChart != null && result.data.get(0).hashrate.toInt() != 0) {


                    result.data.sortWith(object : Comparator<ChartData> {
                        override fun compare(p1: ChartData, p2: ChartData): Int = when {
                            p1.date > p2.date -> 1
                            p1.date == p2.date -> 0
                            else -> -1
                        }
                    })
                    val limitedArray = ArrayList<ChartData>()

                    for (i in 0..20) {
                        limitedArray.add(result.data.get(i))

                    }

                    setupLineChart(limitedArray)
                    setupBarChart(limitedArray)

                }

            }, {
                Log.e("err", it.message)
            })

    }

    fun setupLineChart(result: ArrayList<ChartData>) {

        lineChart.setBackgroundColor(Color.WHITE);

        // disable description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(false);
        lineChart.setDrawGridBackground(false);

        // enable scaling and dragging
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(true);

        // force pinch zoom along both axis
        lineChart.setPinchZoom(true);

        val xAxis = lineChart.getXAxis();

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(true)
        xAxis.textColor = Color.rgb(230, 133, 22)
        xAxis.setCenterAxisLabels(true)
        xAxis.granularity = 5f

        xAxis.setValueFormatter(object : IAxisValueFormatter {
            private val mFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val date = Date((value * 1000).toLong())

                return mFormat.format(date)

            }
        })

        val leftAxis = lineChart.getAxisLeft()
        leftAxis.setValueFormatter(object : IAxisValueFormatter {

            override fun getFormattedValue(value: Float, axis: AxisBase?): String {

                return value.div(1000).toInt().toString().plus(" ").plus(
                    tabIntent.getWorkerHashType(
                        coin
                    )
                )

            }
        })

        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setYOffset(9f);
        leftAxis.setTextColor(Color.rgb(230, 133, 22));

        val rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        val values = ArrayList<Entry>()

        for (i in 0 until result.size) {
            values.add(Entry(result.get(i).date.toFloat(), result.get(i).hashrate.toFloat()))
        }

        // create a dataset and give it a type
        val set = LineDataSet(values, "Hashrate")
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f)
        set.setDrawFilled(true);
        set.setDrawCircles(false);
        set.setLineWidth(1.8f);
        set.setFillColor(R.color.yellow)
        set.setDrawCircleHole(false)
        set.setColor(R.color.yellow, 100)

        // create a data object with the data sets
        val data = LineData(set);
        data.setDrawValues(false)

        // set data
        lineChart.setData(data)
        lineChart.animateXY(1000, 1000)
        lineChart.invalidate()
    }

    fun setupBarChart(result: ArrayList<ChartData>) {

//        barChart.setBackgroundColor(Color.WHITE);

        // disable description text
        barChart.getDescription().setEnabled(false);

        // enable touch gestures
//        barChart.setTouchEnabled(false);
//        barChart.setDrawGridBackground(false);

        // enable scaling and dragging
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(true);

        // force pinch zoom along both axis
//        barChart.setPinchZoom(true);

        val xAxis = barChart.getXAxis();

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 10f
//        xAxis.setDrawAxisLine(false)
//        xAxis.setDrawGridLines(true)
//        xAxis.textColor = Color.rgb(230, 133, 22)
//        xAxis.setCenterAxisLabels(true)
//        xAxis.granularity = 5f

        xAxis.setValueFormatter(object : IAxisValueFormatter {
            private val mFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val date = Date((value * 1000).toLong())

                return mFormat.format(date)

            }
        })

        val leftAxis = barChart.getAxisLeft()


        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);
//        leftAxis.setYOffset(9f);
//        leftAxis.setTextColor(Color.rgb(230, 133, 22));

        val rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        val values = ArrayList<BarEntry>()

        for (i in 0 until result.size) {
            values.add(BarEntry(result.get(i).date.toFloat(), result.get(i).shares.toFloat()))
        }
        currentHashrateTextView.setText(result.get(0).shares.toString())


        // create a dataset and give it a type
        val set = BarDataSet(values, "Shares")
        set.barBorderColor = R.color.dark_red_error
        set.barShadowColor = R.color.dark_red_error
        set.valueTypeface = Typeface.DEFAULT
        set.setColor(R.color.dark_red_error)
        set.setColor(R.color.dark_red_error, 0)
        // create a data object with the data sets
        val data = BarData(set);
//        data.barWidth = 0.1f
//        data.setDrawValues(false)
//        data.setValueTextColor(R.color.dark_red_error)

        // set data
        barChart.setFitBars(true)
        barChart.setData(data)
        barChart.animateXY(1000, 1000)
        barChart.invalidate()
    }

}