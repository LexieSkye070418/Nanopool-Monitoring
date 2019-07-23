package by.lebedev.nanopoolmonitoring.widget

import android.appwidget.AppWidgetManager
import android.os.AsyncTask
import android.util.Log
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.NumberFormat

class WidgetDataLoader(

    val appWidgetManager: AppWidgetManager,
    val appWidgetId: Int,
    val coin: String,
    val wallet: String

) : AsyncTask<String, String, String>() {

    override fun doInBackground(vararg params: String?): String {

        Log.e("AAA", "Execute in background")

//        val views = RemoteViews(context.packageName, R.layout.nanopool_widget)

        val nf = NumberFormat.getInstance()
        nf.maximumFractionDigits = 3

        var balance ="N/A"

        val d = provideApi().getGeneralInfo(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result.status.equals(true)) {
                    Log.e("AAA", result.data.balance.toString())
                    Log.e("AAA", result.data.hashrate.toString())

                    balance = nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase()


//            views.setTextViewText(
//                R.id.widgetCurrentBalance,
//                (nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase())
//            )
//            appWidgetManager.updateAppWidget(appWidgetId, views)


//            if (result.data.hashrate > 1000) {
//
//                views.setTextViewText(
//                    R.id.widgetCurrentHashrate,
//                    nf.format
//                        (result.data.hashrate.div(1000)).toString().plus(" ").plus(
//                        TabIntent.instance.getWorkerHashTypeHigh(
//                            coin
//                        )
//                    )
//                )
//                appWidgetManager.updateAppWidget(appWidgetId, views)
//
//            } else {
//                views.setTextViewText(
//                    R.id.widgetCurrentHashrate,
//                    nf.format
//                        (result.data.hashrate).toString().plus(" ").plus(
//                        TabIntent.instance.getWorkerHashType(
//                            coin
//                        )
//                    )
//                )
//            }
//            appWidgetManager.updateAppWidget(appWidgetId, views)

                }
            }


                , {

                    Log.e("AAA", it.message)
                })
        return balance

    }

}

