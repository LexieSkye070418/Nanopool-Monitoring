package by.lebedev.nanopoolmonitoring.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.widget.RemoteViews
import by.lebedev.nanopoolmonitoring.R


class AlarmManagerBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("AAA", "Alarm broadcast received!!")
        val powerManager = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "nanopool:myTag")
        wakeLock.acquire()

        val remoteViews = RemoteViews(context.getPackageName(), R.layout.nanopool_widget_layout)
        val thisWidget = ComponentName(context, NanopoolWidget::class.java)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
        wakeLock.release()
    }
}