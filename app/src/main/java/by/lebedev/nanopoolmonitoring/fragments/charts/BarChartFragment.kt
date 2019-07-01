package by.lebedev.nanopoolmonitoring.fragments.charts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.retrofit.entity.chart.ChartData
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.barchart_layout.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class BarChartFragment : Fragment() {

    @Inject
    lateinit var tabIntent: TabIntent
    val nf = NumberFormat.getInstance()
    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.barchart_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        getChartInfo()

    }

    fun getChartInfo() {
        val d = provideApi().getChart(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (!result.data.isEmpty() && barChart != null && result.data.get(0).hashrate.toInt() != 0) {


                    result.data.sortWith(object : Comparator<ChartData> {
                        override fun compare(p1: ChartData, p2: ChartData): Int = when {
                            p1.date > p2.date -> 1
                            p1.date == p2.date -> 0
                            else -> -1
                        }
                    })
                    val limitedArray = ArrayList<ChartData>()

                    for (i in 0..3) {
                        limitedArray.add(result.data.get(i))

                    }

                    setupBarChart(limitedArray)

                }

            }, {
                Log.e("err", it.message)
            })

    }

    fun setupBarChart(result: ArrayList<ChartData>) {


        val entries = ArrayList<BarEntry>()

        for (i in 0 until result.size) {
            entries.add(BarEntry(i.toFloat(), result.get(i).shares.toFloat()))
        }

        val set = BarDataSet(entries, "Shares")

        val leftAxis = barChart.getAxisLeft()

        val rightAxis = barChart.getAxisRight()
        rightAxis.isEnabled = false

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
//        xAxis.setValueFormatter(object : IAxisValueFormatter {
//            private val mFormat = SimpleDateFormat("HH", Locale.getDefault())
//            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
//                val date = Date((value * 1000).toLong())
//                return mFormat.format(date)
//            }
//        })

        val data = BarData(set)
        data.setValueTextSize(8f)
        data.barWidth = 1f // set custom bar width



        barChart.getDescription().setEnabled(false)

        barChart.setData(data)
        barChart.setFitBars(true) // make the x-axis fit exactly all bars
        barChart.invalidate() // refresh
        barChart.animateXY(1500, 1500)


//        barChart.setBackgroundColor(Color.WHITE);

        // disable description text
//        barChart.getDescription().setEnabled(false);

        // enable touch gestures
//        barChart.setTouchEnabled(false);
//        barChart.setDrawGridBackground(false);

        // enable scaling and dragging
//        barChart.setDragEnabled(false);
//        barChart.setScaleEnabled(true);

        // force pinch zoom along both axis
//        barChart.setPinchZoom(true);

//        val xAxis = barChart.getXAxis();
//
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.textSize = 10f
//        xAxis.setDrawAxisLine(false)
//        xAxis.setDrawGridLines(true)
//        xAxis.textColor = Color.rgb(230, 133, 22)
//        xAxis.setCenterAxisLabels(true)
//        xAxis.granularity = 5f

//        xAxis.setValueFormatter(object : IAxisValueFormatter {
//            private val mFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
//
//            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
//                val date = Date((value * 1000).toLong())
//
//                return mFormat.format(date)

//            }
//        })
//
//        val leftAxis = barChart.getAxisLeft()


//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);
//        leftAxis.setYOffset(9f);
//        leftAxis.setTextColor(Color.rgb(230, 133, 22));

//        val rightAxis = barChart.getAxisRight();
//        rightAxis.setEnabled(false);
//
//        val values = ArrayList<BarEntry>()
//
//        for (i in 0 until result.size) {
//            values.add(BarEntry(result.get(i).date.toFloat(), result.get(i).shares.toFloat()))
//        }
//
//
//         create a dataset and give it a type
//        val set = BarDataSet(values, "Shares")
//
//        val proposedColor = ContextCompat.getColor(view.context, R.color.dark_red_error)
//        val currentColor = ContextCompat.getColor(view.context, R.color.hyperlink_text)


//        set.setColors(currentColor,proposedColor)
//
//        set.barBorderColor = R.color.dark_red_error
//        set.barShadowColor = R.color.dark_red_error
//        set.valueTypeface = Typeface.DEFAULT
//        set.setColor(R.color.dark_red_error)
//        set.setColor(R.color.dark_red_error, 0)
        // create a data object with the data sets
//        val data = BarData(set);
//        data.barWidth = 0.1f
//        data.setDrawValues(false)
//        data.setValueTextColor(R.color.dark_red_error)

        // set data
//        barChart.setFitBars(true)
//        barChart.setData(data)
//        barChart.animateXY(1000, 1000)
//        barChart.invalidate()
    }

}