package by.lebedev.nanopoolmonitoring

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [NanopoolWidgetConfigureActivity]
 */
class NanopoolWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            NanopoolWidgetConfigureActivity.deleteSharedPref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val walletText = NanopoolWidgetConfigureActivity.loadSharedPrefWallet(context, appWidgetId)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.nanopool_widget)
            views.setTextViewText(R.id.widgetCoin, walletText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun setSelectedCoinImage(context: Context,
                                 appWidgetManager: AppWidgetManager,
                                 appWidgetId: Int,
                                 coinId: Int) {
            val views = RemoteViews(context.packageName, R.layout.nanopool_widget)

            when (coinId) {
                0 -> {
                    views.setImageViewResource(R.id.widgetCoin,)
                    addAccCoinLogo.setImageResource(R.drawable.eth)

                }
                1 -> {
                    addAccCoinLogo.setImageResource(R.drawable.etc)

                }
                2 -> {
                    addAccCoinLogo.setImageResource(R.drawable.zec)

                }
                3 -> {
                    addAccCoinLogo.setImageResource(R.drawable.xmr)

                }
                4 -> {
                    addAccCoinLogo.setImageResource(R.drawable.pasc)

                }
                5 -> {
                    addAccCoinLogo.setImageResource(R.drawable.etn)

                }
                6 -> {
                    addAccCoinLogo.setImageResource(R.drawable.raven)

                }
                7 -> {
                    addAccCoinLogo.setImageResource(R.drawable.grin)

                }
                else -> addAccCoinLogo.setImageResource(R.drawable.eth)
            }
        }


    }
}

