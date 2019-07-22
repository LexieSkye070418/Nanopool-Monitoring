package by.lebedev.nanopoolmonitoring.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews




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



            NanopoolWidgetConfigureActivity.setWalletFromConfig(
                context,
                appWidgetManager,
                appWidgetId
            )
            NanopoolWidgetConfigureActivity.setCoinImage(
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
            NanopoolWidgetConfigureActivity.setWalletFromConfig(
                context,
                appWidgetManager,
                mAppWidgetId
            )
        }
        if (context != null) {
            NanopoolWidgetConfigureActivity.setCoinImage(
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
            views.setTextViewText(by.lebedev.nanopoolmonitoring.R.id.widgetCoin, walletText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }



    }
}

