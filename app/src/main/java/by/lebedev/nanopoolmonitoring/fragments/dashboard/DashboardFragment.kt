package by.lebedev.nanopoolmonitoring.fragments.dashboard

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.fragments.charts.BarChartFragment
import by.lebedev.nanopoolmonitoring.fragments.charts.LineChartFragment
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import com.google.android.gms.ads.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.internal.operators.completable.CompletableFromAction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import javax.inject.Inject


class DashboardFragment : Fragment() {
    lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd
    val APP_PREFERENCES = "settings"
    val APP_PREFERENCES_CHECK = "check"
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData
    val nf = NumberFormat.getInstance()
    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()
            ?.setBackgroundDrawableResource(R.drawable.nanopool_background)

        pref = view.context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        if (pref.getBoolean(APP_PREFERENCES_CHECK, false)) {
            layoutForCalculator.visibility = View.GONE
        }

        MobileAds.initialize(this.context, "ca-app-pub-1501215034144631~3780667725")

        mInterstitialAd = InterstitialAd(view.context)
        mInterstitialAd.adUnitId = "ca-app-pub-1501215034144631/2506262157"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        val adView = AdView(this.context)
        adView.adSize = AdSize.BANNER

        adView.adUnitId = "ca-app-pub-1501215034144631/8209904262"

        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        nf.maximumFractionDigits = 4

        val component = DaggerMagicBox.builder().build()
        coinWalletTempData = component.provideTabIntent()

        coin = coinWalletTempData.coin
        wallet = coinWalletTempData.wallet

        showInterstitial(coinWalletTempData.firstTimeAds)

        setCurrHashrateBalance()
        setAverageHashrateAndCalcProfit()
        inflateLineChart()
        inflateBarChart()

        swipeRefreshDashboard.setColorSchemeResources(
            R.color.blue,
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.orange
        )
        swipeRefreshDashboard.setOnRefreshListener {
            scrollViewForRefresh.visibility = View.INVISIBLE
            inflateLineChart()
            inflateBarChart()
            setCurrHashrateBalance()
            setAverageHashrateAndCalcProfit()
            mAdView.loadAd(adRequest)
        }

        layoutForCalculator.setOnClickListener {
            val editor = pref.edit()
            editor.putBoolean(APP_PREFERENCES_CHECK, true)
            editor.apply()

            layoutForCalculator.visibility = View.GONE

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=by.lebedev.miningcalculator")
            startActivity(intent)
        }

    }

    fun setCurrHashrateBalance() {
        nf.maximumFractionDigits = 2
        val d = provideApi().getHashrateBalance(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result != null && result.status && balance != null && current_hashrate != null) {

                    swipeRefreshDashboard.setRefreshing(false)

                    scrollViewForRefresh.visibility = View.VISIBLE
                    balance.setText(nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase())
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { balance.setTextColor(it) }

                    if (result.data.hashrate > 1000) {
                        current_hashrate.setText(
                            nf.format
                                (result.data.hashrate.div(1000)).toString().plus(" ").plus(
                                coinWalletTempData.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        current_hashrate.setText(
                            nf.format
                                (result.data.hashrate).toString().plus(" ").plus(
                                coinWalletTempData.getWorkerHashType(
                                    coin
                                )
                            )
                        )
                    }

                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { current_hashrate.setTextColor(it) }
                }

            }, {
                Log.e("err", it.message)
            })
    }

    fun setAverageHashrateAndCalcProfit() {
        nf.maximumFractionDigits = 2
        val d = provideApi().getAverageHashrate(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result != null && result.status && hours_6 != null && hours_24 != null) {

                    if (result.data.h6 > 1000) {

                        hours_6.setText(
                            nf.format
                                (result.data.h6.div(1000)).toString().plus(" ").plus(
                                coinWalletTempData.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        hours_6.setText(
                            nf.format
                                (result.data.h6).toString().plus(" ").plus(
                                coinWalletTempData.getWorkerHashType(
                                    coin
                                )
                            )
                        )
                    }

                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { hours_6.setTextColor(it) }


                    if (result.data.h24 > 1000) {

                        hours_24.setText(
                            nf.format
                                (result.data.h24.div(1000)).toString().plus(" ").plus(
                                coinWalletTempData.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        hours_24.setText(
                            nf.format
                                (result.data.h24).toString().plus(" ").plus(
                                coinWalletTempData.getWorkerHashType(
                                    coin
                                )
                            )
                        )
                    }

                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { hours_24.setTextColor(it) }

                    if (result.data.h6 > 1) {
                        getProfitInfo(coin, result.data.h6)
                    }

                }

            }, {
                Log.e("err", it.message)
            })
    }

    fun getProfitInfo(coin: String, hashrate: Double) {
        nf.maximumFractionDigits = 4
        val d = provideApi().getProfit(coin, hashrate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result != null && result.status && view != null && minute_coin != null && hour_coin != null && day_coin != null && week_coin != null && month_coin != null) {
                    minute_coin.setText(nf.format(result.data.minute.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { minute_coin.setTextColor(it) }
                    minute_btc.setText(nf.format(result.data.minute.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { minute_btc.setTextColor(it) }
                    minute_usd.setText(Math.round(result.data.minute.dollars * 1000).div(1000).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { minute_usd.setTextColor(it) }

                    hour_coin.setText(nf.format(result.data.hour.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { hour_coin.setTextColor(it) }
                    hour_btc.setText(nf.format(result.data.hour.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { hour_btc.setTextColor(it) }
                    hour_usd.setText(Math.round(result.data.hour.dollars * 1000.0).div(1000.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { hour_usd.setTextColor(it) }

                    day_coin.setText(nf.format(result.data.day.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { day_coin.setTextColor(it) }
                    day_btc.setText(nf.format(result.data.day.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { day_btc.setTextColor(it) }
                    day_usd.setText(Math.round(result.data.day.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { day_usd.setTextColor(it) }

                    week_coin.setText(nf.format(result.data.week.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { week_coin.setTextColor(it) }
                    week_btc.setText(nf.format(result.data.week.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { week_btc.setTextColor(it) }
                    week_usd.setText(Math.round(result.data.week.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { week_usd.setTextColor(it) }

                    month_coin.setText(nf.format(result.data.month.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { month_coin.setTextColor(it) }
                    month_btc.setText(nf.format(result.data.month.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { month_btc.setTextColor(it) }
                    month_usd.setText(Math.round(result.data.month.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { month_usd.setTextColor(it) }
                }
            }, {
                Log.e("err", it.message)
            })
    }

    fun inflateLineChart() {
        if (layoutLineChart != null) {
            val lineChartFragment = LineChartFragment()
            val ft = childFragmentManager.beginTransaction()
            ft.replace(R.id.layoutLineChart, lineChartFragment)
            ft.commit()

        }

    }

    fun inflateBarChart() {
        if (layoutBarChart != null) {
            val barChartFragment = BarChartFragment()
            val ft1 = childFragmentManager.beginTransaction()
            ft1.replace(R.id.layoutBarChart, barChartFragment)
            ft1.commit()
        }
    }

    fun sleepMinute(): Action {
        return Action { Thread.sleep(60000) }
    }

    fun showInterstitial(firstTimeAds:Boolean) {
        val d = CompletableFromAction(sleepMinute())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val isDashboard = view?.findViewById<ScrollView>(R.id.scrollViewForRefresh)
                if (mInterstitialAd.isLoaded&&firstTimeAds&&isDashboard!=null) {
                    coinWalletTempData.firstTimeAds = false
                    mInterstitialAd.show()
                }
            }
    }

}