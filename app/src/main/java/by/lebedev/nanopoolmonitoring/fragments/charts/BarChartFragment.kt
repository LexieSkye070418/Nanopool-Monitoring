package by.lebedev.nanopoolmonitoring.fragments.charts

import android.graphics.Color
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
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.barchart_layout.*
import java.text.NumberFormat
import javax.inject.Inject

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
        nf.maximumFractionDigits = 1

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

                if (result != null && result.status && barChart != null && !result.data.isEmpty()) {


                    result.data.sortBy { it.date }

                    val limitedArray = ArrayList<ChartData>()


                    if (result.data.size > 10) {
                        for (i in 0..10) {
                            limitedArray.add(result.data.get(i))

                        }
                    } else {

                        for (i in 0 until result.data.size) {
                            limitedArray.add(result.data.get(i))
                        }
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

        set.setColor(R.color.green_confirm)
        set.setValueFormatter(object : IValueFormatter {
            override fun getFormattedValue(
                value: Float,
                entry: Entry?,
                dataSetIndex: Int,
                viewPortHandler: ViewPortHandler?
            ): String {
                if (value > 1000) {
                    return nf.format(value.div(1000)).toString().plus(" k")
                } else return value.toInt().toString()
            }

        })
        val leftAxis = barChart.getAxisLeft()

        val rightAxis = barChart.getAxisRight()
        rightAxis.isEnabled = false

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawLabels(false)
        xAxis.textColor = R.color.white

        val data = BarData(set)
        data.setValueTextSize(8f)
        data.barWidth = 0.5f // set custom bar width


        barChart.getDescription().setEnabled(false)

        barChart.setData(data)
        barChart.setFitBars(true) // make the x-axis fit exactly all bars
        barChart.invalidate() // refresh
        barChart.animateXY(1500, 1500)


//        barChart.setBackgroundColor(Color.WHITE);

        // disable description text
        barChart.getDescription().setEnabled(false);

        // enable touch gestures
        barChart.setTouchEnabled(false);
        barChart.setDrawGridBackground(false);

        // enable scaling and dragging
//        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(true);

        // force pinch zoom along both axis
//        barChart.setPinchZoom(true);

//        val xAxis = barChart.getXAxis();
//
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 0f
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
//        xAxis.textColor = Color.rgb(230, 133, 22)
//        xAxis.setCenterAxisLabels(true)
//        xAxis.granularity = 5f

        leftAxis.setValueFormatter(object : IAxisValueFormatter {

            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                if (value > 1000) {
                    return nf.format(value.div(1000)).toString().plus(" k")
                } else return value.toInt().toString()
            }
        })
//
//        val leftAxis = barChart.getAxisLeft()


        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);
//        leftAxis.setYOffset(9f);
        leftAxis.setTextColor(Color.rgb(230, 133, 22));

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
        barChart.invalidate()
    }

}