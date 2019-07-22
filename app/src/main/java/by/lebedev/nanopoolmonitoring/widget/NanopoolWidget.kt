package by.lebedev.nanopoolmonitoring.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.NumberFormat


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [NanopoolWidgetConfigureActivity]
 */
class NanopoolWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        Log.e("AAA", "onUpdate")
        val views = RemoteViews(context.packageName, by.lebedev.nanopoolmonitoring.R.layout.nanopool_widget)


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
            views.setOnClickPendingIntent(by.lebedev.nanopoolmonitoring.R.id.updateButton, pendingSync)



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
        var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
        val extras = intent?.getExtras()
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        if (context != null) {
            setHashrate(
                context,
                appWidgetManager,
                mAppWidgetId
            )
        }
        if (context != null) {
            setCoinImageAndName(
                context,
                appWidgetManager,
                mAppWidgetId
            )
        }



    }

    override fun onEnabled(context: Context) {
        Log.e("AAA", "onEnabled")

    }

    override fun onDisabled(context: Context) {
        Log.e("AAA", "onDisabled")

    }


    companion object {

        internal fun loadWallet(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            Log.e("AAA", "loadWallet")

            val walletText =
                NanopoolWidgetConfigureActivity.loadSharedPrefWallet(
                    context,
                    appWidgetId
                )
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, by.lebedev.nanopoolmonitoring.R.layout.nanopool_widget)
            views.setTextViewText(by.lebedev.nanopoolmonitoring.R.id.widgetCurrentCoin, walletText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }


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
            val nf = NumberFormat.getInstance()
            Log.e("AAA", "set hashrate")


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

            nf.maximumFractionDigits = 2
            val d = provideApi().getGeneralInfo(coin, wallet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->

                    if (result.status != null) {
                        val views = RemoteViews(context.packageName, R.layout.nanopool_widget)
                        views.setTextViewText(
                            R.id.widgetCurrentBalance,
                            nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase()
                        )


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
                    }
                }


                    , {
                        Log.e("AAA", it.message)
                    })

            val views = RemoteViews(context.packageName, R.layout.nanopool_widget)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }



    }
}

