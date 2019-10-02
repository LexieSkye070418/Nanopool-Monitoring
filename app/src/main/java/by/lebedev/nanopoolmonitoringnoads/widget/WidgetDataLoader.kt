package by.lebedev.nanopoolmonitoringnoads.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.workers.DataWorkers
import by.lebedev.nanopoolmonitoringnoads.retrofit.provideApi

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.NumberFormat

class DataLoaderService() : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("AAA", "set hashrate from service")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        Log.e("AAA", "set hashrate from service")

        super.onCreate()

        val nf = NumberFormat.getInstance()

        val appWidgetId = 1

        val appWidgetManager = AppWidgetManager.getInstance(applicationContext)

        val wallet =
            NanopoolWidgetConfigureActivity.loadSharedPrefWallet(
                applicationContext,
                appWidgetId
            )

        val coinId =
            NanopoolWidgetConfigureActivity.loadSharedPrefCoin(
                applicationContext,
                appWidgetId
            )

        val coin = CoinWalletTempData.INSTANCE.shortNameFromSelector(coinId)
        val views = RemoteViews(applicationContext.packageName, R.layout.nanopool_widget_layout)

        nf.maximumFractionDigits = 3


        val d = provideApi().getGeneralInfo(coin, wallet)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result.status.equals(true)) {
                    Log.e("AAA", result.data.balance.toString())
                    Log.e("AAA", result.data.hashrate.toString())

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
                                CoinWalletTempData.INSTANCE.getWorkerHashTypeHigh(
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
                                CoinWalletTempData.INSTANCE.getWorkerHashType(
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
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (!result.data.isEmpty()) {

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


    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
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
}