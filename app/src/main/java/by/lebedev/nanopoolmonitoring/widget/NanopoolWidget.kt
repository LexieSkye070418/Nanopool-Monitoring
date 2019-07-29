package by.lebedev.nanopoolmonitoring.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.retrofit.entity.chart.ChartData
import by.lebedev.nanopoolmonitoring.retrofit.entity.workers.DataWorkers
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.newThread
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [NanopoolWidgetConfigureActivity]
 */
class NanopoolWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        Log.e("AAA", "onUpdate")
        val views = RemoteViews(context.packageName, R.layout.nanopool_widget)

        for (appWidgetId in appWidgetIds) {

            val intentSync = Intent(context, NanopoolWidget::class.java)
            intentSync.action =
                AppWidgetManager.ACTION_APPWIDGET_UPDATE //You need to specify the action for the intent. Right now that intent is doing nothing for there is no action to be broadcasted.
            val pendingSync = PendingIntent.getBroadcast(
                context,
                0,
                intentSync,
                PendingIntent.FLAG_UPDATE_CURRENT
            ) //You need to specify a proper flag for the intent. Or else the intent will become deleted.


            views.setOnClickPendingIntent(R.id.updateButton, pendingSync)

            appWidgetManager.updateAppWidget(appWidgetId, views)

            setHashrate(
                context,
                appWidgetManager,
                appWidgetId
            )
            setCoinImageAndName(
                context,
                appWidgetManager,
                appWidgetId
            )

//            getChartInfo(
//                context,
//                appWidgetManager,
//                appWidgetId
//            )

            appWidgetManager.updateAppWidget(appWidgetId, views)

        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        Log.e("AAA", "onDelete")
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            NanopoolWidgetConfigureActivity.deleteSharedPref(
                context,
                appWidgetId
            )
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("AAA", "onReceive")
        super.onReceive(context, intent)


        val appWidgetManager = AppWidgetManager.getInstance(context)

        val thisAppWidget = ComponentName(context?.packageName, NanopoolWidget::class.java.getName())
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)

        if (context != null) {
            Log.e("AAA", "Call Update from onReceive")
            onUpdate(context, appWidgetManager, appWidgetIds)
        }

    }

    override fun onEnabled(context: Context) {
        Log.e("AAA", "onEnabled")

    }

    override fun onDisabled(context: Context) {
        Log.e("AAA", "onDisabled")
    }


    companion object {

        fun setCoinImageAndName(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            Log.e("AAA", "set coin image")


            val views = RemoteViews(context.packageName, R.layout.nanopool_widget)
            val coinId =
                NanopoolWidgetConfigureActivity.loadSharedPrefCoin(
                    context,
                    appWidgetId
                )
            views.setViewVisibility(R.id.progressBar, View.VISIBLE)


            when (coinId) {
                0 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.eth
                    )

                    views.setTextViewText(R.id.widgetCurrentCoin, "Ethereum")
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
                1 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.etc
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "Ethereum Classic")
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                2 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.zec
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "ZCash")
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                3 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.xmr
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "Monero")
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                4 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.pasc
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "Pascal")
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                5 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.etn
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "Electroneum")
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                6 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.raven
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "Raven")
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                7 -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.grin
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "Grin-29")
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                else -> {
                    views.setViewVisibility(R.id.progressBar, View.INVISIBLE)
                    views.setImageViewResource(
                        R.id.widgetCoinImage,
                        R.drawable.eth
                    )
                    views.setTextViewText(R.id.widgetCurrentCoin, "Ethereum")
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }


        fun setHashrate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            Log.e("AAA", "set hashrate")

            val nf = NumberFormat.getInstance()


            val wallet =
                NanopoolWidgetConfigureActivity.loadSharedPrefWallet(
                    context,
                    appWidgetId
                )

            val coinId =
                NanopoolWidgetConfigureActivity.loadSharedPrefCoin(
                    context,
                    appWidgetId
                )

            val coin = TabIntent.instance.shortNameFromSelector(coinId)
            val views = RemoteViews(context.packageName, R.layout.nanopool_widget)

            nf.maximumFractionDigits = 3

            views.setTextViewText(
                R.id.widgetCurrentBalance, "Updating..."
            )

            appWidgetManager.updateAppWidget(appWidgetId, views)
            views.setTextViewText(
                R.id.widgetCurrentHashrate, "Updating..."
            )
            appWidgetManager.updateAppWidget(appWidgetId, views)

            views.setTextViewText(
                R.id.widgetCurrentWorkers, "Updating..."
            )
            appWidgetManager.updateAppWidget(appWidgetId, views)

            val d = provideApi().getGeneralInfo(coin, wallet)
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->

                    if (result.status.equals(true)) {
                        Log.e("AAA", "balance: " + result.data.balance.toString())
                        Log.e("AAA", "hashrate: " + result.data.hashrate.toString())

                        views.setTextViewText(
                            R.id.widgetCurrentBalance,
                            (nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase())
                        )
                        appWidgetManager.updateAppWidget(appWidgetId, views)


                        if (result.data.hashrate > 1000) {

                            views.setTextViewText(
                                R.id.widgetCurrentHashrate,
                                nf.format
                                    (result.data.hashrate.div(1000)).toString().plus(" ").plus(
                                    TabIntent.instance.getWorkerHashTypeHigh(
                                        coin
                                    )
                                )
                            )
                            appWidgetManager.updateAppWidget(appWidgetId, views)

                        } else {
                            views.setTextViewText(
                                R.id.widgetCurrentHashrate,
                                nf.format
                                    (result.data.hashrate).toString().plus(" ").plus(
                                    TabIntent.instance.getWorkerHashType(
                                        coin
                                    )
                                )
                            )
                        }
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    } else {
                        views.setTextViewText(
                            R.id.widgetCurrentHashrate, "Account not found"
                        )
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }


                    , {
                        Log.e("AAA", it.message)
                    })

            val x = provideApi().getWorkers(coin, wallet)
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->

                    if (!result.data.isEmpty()) {
                        Log.e("AAA", "workers: " + result.data.size.toString())

                        views.setTextViewText(
                            R.id.widgetCurrentWorkers,
                            countAlive(result.data).toString()
                        )
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    } else {
                        views.setTextViewText(
                            R.id.widgetCurrentWorkers, "Workers not found"
                        )
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }, {
                    Log.e("err", it.message)
                })

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun countAlive(workerList: ArrayList<DataWorkers>): Int {
            var count = 0
            for (i in 0 until workerList.size) {
                if (workerList.get(i).hashrate != 0L) {
                    count++
                }
            }
            return count
        }


        fun getChartInfo(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            Log.e("AAA", "Get chart info")

            val wallet =
                NanopoolWidgetConfigureActivity.loadSharedPrefWallet(
                    context,
                    appWidgetId
                )

            val coinId =
                NanopoolWidgetConfigureActivity.loadSharedPrefCoin(
                    context,
                    appWidgetId
                )

            val coin = TabIntent.instance.shortNameFromSelector(coinId)

            Log.e("AAA", "appWidgetId= " + appWidgetId.toString())
            Log.e("AAA", "wallet= " + wallet)
            Log.e("AAA", "coin= " + coin)


            val c = provideApi().getChart(coin, wallet)
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->

                    Log.e("AAA", "inside val C")
                    if (!result.data.isEmpty() && result.data.get(0).hashrate.toInt() != 0) {
                        Log.e("AAA", result.data.get(0).hashrate.toString())
                        Log.e("AAA", result.data.get(0).date.toString())
                        Log.e("AAA", result.data.get(0).shares.toString())

                        result.data.sortBy { it.date }

                        val limitedArray = ArrayList<ChartData>()

                        if (result.data.size > 20) {
                            for (i in 0..20) {
                                limitedArray.add(result.data.get(i))

                            }
                        } else {
                            for (i in 0 until result.data.size) {
                                limitedArray.add(result.data.get(i))
                            }
                        }

                        setupLineChart(context, appWidgetId, appWidgetManager, coin, limitedArray)

                    }

                }, {
                    Log.e("err", "error getting chart data" + it.message)
                })

        }

        fun setupLineChart(
            context: Context,
            appWidgetId: Int,
            appWidgetManager: AppWidgetManager,
            coin: String,
            result: ArrayList<ChartData>
        ) {

            Log.e("AAA", "Setup line chart")

            val views = RemoteViews(context.packageName, R.layout.nanopool_widget)
            val lineChart = LineChart(context)

            lineChart.measure(
                View.MeasureSpec.makeMeasureSpec(90, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(40, View.MeasureSpec.EXACTLY)
            )
            lineChart.layout(0, 0, lineChart.getMeasuredWidth(), lineChart.getMeasuredHeight())


//            lineChart.setBackgroundColor(Color.WHITE);

            // disable description text
            lineChart.getDescription().setEnabled(false);

            // enable touch gestures
            lineChart.setTouchEnabled(false);
            lineChart.setDrawGridBackground(false);

            // enable scaling and dragging
            lineChart.setDragEnabled(false);
//            lineChart.setScaleEnabled(true);

            // force pinch zoom along both axis
//            lineChart.setPinchZoom(true);

            val xAxis = lineChart.getXAxis()
            xAxis.setDrawLabels(false)

//            xAxis.position = XAxis.XAxisPosition.BOTTOM
//            xAxis.textSize = 10f
//            xAxis.setDrawAxisLine(false)
//            xAxis.setDrawGridLines(true)
//            xAxis.textColor = Color.rgb(230, 133, 22)
//            xAxis.setCenterAxisLabels(true)
            xAxis.granularity = 1.5f

//            xAxis.setValueFormatter(object : IAxisValueFormatter {
//                private val mFormat = SimpleDateFormat("HH:mm", Locale.US)
//
//                override fun getFormattedValue(value: Float, axis: AxisBase?): String {
//                    val date = Date((value * 1000).minus(10800000).toLong())
//
//                    return mFormat.format(date)
//
//                }
//            })


            val leftAxis = lineChart.getAxisLeft()
            leftAxis.setEnabled(false);

//            leftAxis.setValueFormatter(object : IAxisValueFormatter {
//
//                override fun getFormattedValue(value: Float, axis: AxisBase?): String {
//
//                    return value.div(1000).toInt().toString().plus(" ").plus(
//                        TabIntent.instance.getWorkerHashType(
//                            coin
//                        )
//                    )
//
//                }
//            })

//            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//            leftAxis.setDrawGridLines(true);
//            leftAxis.setGranularityEnabled(true);
//            leftAxis.setYOffset(9f);
//            leftAxis.setTextColor(Color.rgb(230, 133, 22));

            val rightAxis = lineChart.getAxisRight();
            rightAxis.setEnabled(false);

            val values = ArrayList<Entry>()

            for (i in 0 until result.size) {
                values.add(Entry(result.get(i).date.toFloat(), result.get(i).hashrate.toFloat()))
            }

            // create a dataset and give it a type
            val set = LineDataSet(values, "")
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set.setCubicIntensity(0.2f)
            set.setDrawFilled(true);
            set.setDrawCircles(false);
            set.setLineWidth(1.5f);
            set.setFillColor(R.color.yellow)
            set.setDrawCircleHole(false)
            set.setColor(R.color.yellow, 100)

            // create a data object with the data sets
            val data = LineData(set);
            data.setDrawValues(false)

            // set data
            lineChart.setData(data)



            val chartBitmap = lineChart.chartBitmap


            android.os.Handler().postDelayed({
//              views.setImageViewResource(R.id.chartOnWidget, R.drawable.xmr)
//              views.setBitmap(R.id.chartOnWidget, "set bitmap", chartBitmap)
                views.setImageViewBitmap(R.id.chartOnWidget,chartBitmap)
                appWidgetManager.updateAppWidget(appWidgetId, views)

                Log.e("AAA", "SETTING BITMAP")
            }, 5000)

        }
    }
}