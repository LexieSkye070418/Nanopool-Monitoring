package by.lebedev.nanopoolmonitoringnoads.fragments.charts

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.os.ConfigurationCompat.getLocales
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoringnoads.R

import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.chart.ChartData
import by.lebedev.nanopoolmonitoringnoads.retrofit.provideApi
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.linechart_layout.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LineChartFragment : Fragment() {

    @Inject
    lateinit var tabIntent: TabIntent
    val nf = NumberFormat.getInstance()
    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.linechart_layout, container, false)
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

                if (!result.data.isEmpty() && lineChart != null && result.data.get(0).hashrate.toInt() != 0) {

                    result.data.sortBy { it.date }

                    val limitedArray = ArrayList<ChartData>()

                    for (i in 0..20) {
                        limitedArray.add(result.data.get(i))

                    }

                    setupLineChart(limitedArray)

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
            private val mFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("HH:mm", getResources().getConfiguration().getLocales().get(0))
            } else {
                SimpleDateFormat("HH:mm", getResources().getConfiguration().locale)
            }

            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val date = Date((value * 1000).minus(10800000).toLong())

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

}