package by.lebedev.nanopoolmonitoring

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import android.widget.RemoteViews
import by.lebedev.nanopoolmonitoring.dagger.PoolCoins
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import kotlinx.android.synthetic.main.nanopool_widget_configure.*
import javax.inject.Inject

/**
 * The configuration screen for the [NanopoolWidget] AppWidget.
 */
class NanopoolWidgetConfigureActivity : Activity() {
    var coinId: Int = 0
    @Inject
    lateinit var poolCoins: PoolCoins
    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    internal lateinit var wallet: EditText
    internal var mOnClickListener: View.OnClickListener = View.OnClickListener {
        val context = this@NanopoolWidgetConfigureActivity

        // When the button is clicked, store the string locally
        val walletText = wallet.text.toString()
        saveSharedPref(context, mAppWidgetId, walletText,coinId)

        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(context)
        NanopoolWidget.loadWallet(context, appWidgetManager, mAppWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        val component = DaggerMagicBox.builder().build()
        poolCoins = component.providePoolCoins()
        val arrayOfCoins = arrayOfNulls<String>(poolCoins.list.size)
        poolCoins.list.toArray(arrayOfCoins)


        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(Activity.RESULT_CANCELED)

        setContentView(R.layout.nanopool_widget_configure)

        widgetCoinSelect.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
                .setTitle("Select your coin")
                .setIcon(R.drawable.bitcoinicon)
                .setCancelable(true)

                .setSingleChoiceItems(arrayOfCoins, -1,
                    { dialog, item ->

                        coinId = item
                        coinNametextView.setText(poolCoins.fullName(coinId))
                    })


                .setPositiveButton("OK", { dialog, which -> dialog.cancel() })
                .setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })
            val alert = builder.create()
            alert.show()
        }


        wallet = findViewById<View>(R.id.walletWidgetEditText) as EditText
        findViewById<View>(R.id.add_button).setOnClickListener(mOnClickListener)

        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        wallet.setText(loadSharedPrefWallet(this@NanopoolWidgetConfigureActivity, mAppWidgetId))
    }

    companion object {

        private val PREFS_NAME = "by.lebedev.nanopoolmonitoring.NanopoolWidget"
        private val PREF_PREFIX_KEY_WALLET = "appwidgetwallet_"
        private val PREF_PREFIX_KEY_COINID = "appwidgetcoinid_"

        // Write the prefix to the SharedPreferences object for this widget
        internal fun saveSharedPref(context: Context, appWidgetId: Int, walletText: String, coin:Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putString(PREF_PREFIX_KEY_WALLET + appWidgetId, walletText)
            prefs.putInt(PREF_PREFIX_KEY_COINID + appWidgetId, coin)
            prefs.apply()
        }

        // Read the prefix from the SharedPreferences object for this widget.
        // If there is no preference saved, get the default from a resource
        internal fun loadSharedPrefWallet(context: Context, appWidgetId: Int): String {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val walletValue = prefs.getString(PREF_PREFIX_KEY_WALLET + appWidgetId, null)
            return walletValue ?: context.getString(R.string.default_wallet_text)
        }

        internal fun loadSharedPrefCoin(context: Context, appWidgetId: Int): Int {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val coinValue = prefs.getInt(PREF_PREFIX_KEY_COINID + appWidgetId, 0)
            return coinValue ?: 0
        }

        internal fun deleteSharedPref(context: Context, appWidgetId: Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.remove(PREF_PREFIX_KEY_WALLET + appWidgetId)
            prefs.remove(PREF_PREFIX_KEY_COINID + appWidgetId)
            prefs.apply()
        }

        internal fun setCoinImage(context: Context,
                                  appWidgetManager: AppWidgetManager,
                                  appWidgetId: Int) {

            val views = RemoteViews(context.packageName, R.layout.nanopool_widget)
            val coinId = loadSharedPrefCoin(context, appWidgetId)


            when (coinId) {
                0 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.eth)
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
                1 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.etc)
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                2 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.zec)
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                3 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.xmr)
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                4 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.pasc)
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                5 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.etn)
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                6 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.raven)
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                7 -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.grin)
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
                else -> {
                    views.setViewVisibility(R.id.progressBar,View.INVISIBLE)
                    views.setImageViewResource(R.id.widgetCoinImage,R.drawable.eth)
                    appWidgetManager.updateAppWidget(appWidgetId, views)}
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)


             }

    }
}

