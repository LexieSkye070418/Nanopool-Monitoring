package by.lebedev.nanopoolmonitoring.fragments.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import javax.inject.Inject


class DashboardFragment : Fragment() {
    lateinit var mAdView: AdView

    @Inject
    lateinit var tabIntent: TabIntent
    val nf = NumberFormat.getInstance()
    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()?.setBackgroundDrawableResource(by.lebedev.nanopoolmonitoring.R.drawable.nanopool_background)


        MobileAds.initialize(this.context, "ca-app-pub-1501215034144631~3780667725")

        val adView = AdView(this.context)
        adView.adSize = AdSize.BANNER

        adView.adUnitId = "ca-app-pub-1501215034144631/8209904262"

        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        nf.maximumFractionDigits = 4

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        getGeneralInfo()


    }

    fun getGeneralInfo() {
        val d = provideApi().getGeneralInfo(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result.status && balance != null) {

                    balance.setText(nf.format(result.data.balance).toString().plus(" ETH"))
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { balance.setTextColor(it) }

                    current_hashrate.setText(result.data.hashrate.toString().plus(" H/s"))
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { current_hashrate.setTextColor(it) }

                    hours_6.setText(result.data.avgHashrate.h6.toString().plus(" H/s"))
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { hours_6.setTextColor(it) }

                    hours_24.setText(result.data.avgHashrate.h24.toString().plus(" H/s"))
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { hours_24.setTextColor(it) }

                    if (result.data.avgHashrate.h6 > 1) {
                        getProfitInfo(coin, result.data.avgHashrate.h6)
                    }
                }

            }, {
                Log.e("err", it.message)
            })
    }

    fun getProfitInfo(coin: String, hashrate: Double) {
        val d = provideApi().getProfit(coin, hashrate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (view != null && minute_coin != null && hour_coin != null && day_coin != null && week_coin != null && month_coin != null) {
                    val minute_coin = view!!.findViewById<TextView>(R.id.minute_coin)
                    val minute_btc = view!!.findViewById<TextView>(R.id.minute_btc)
                    val minute_usd = view!!.findViewById<TextView>(R.id.minute_usd)
                    minute_coin.setText(nf.format(result.data.minute.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { minute_coin.setTextColor(it) }
                    minute_btc.setText(nf.format(result.data.minute.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { minute_btc.setTextColor(it) }
                    minute_usd.setText(Math.round(result.data.minute.dollars * 1000).div(1000).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { minute_usd.setTextColor(it) }

                    val hour_coin = view!!.findViewById<TextView>(R.id.hour_coin)
                    val hour_btc = view!!.findViewById<TextView>(R.id.hour_btc)
                    val hour_usd = view!!.findViewById<TextView>(R.id.hour_usd)
                    hour_coin.setText(nf.format(result.data.hour.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { hour_coin.setTextColor(it) }
                    hour_btc.setText(nf.format(result.data.hour.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { hour_btc.setTextColor(it) }
                    hour_usd.setText(Math.round(result.data.hour.dollars * 1000.0).div(1000.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { hour_usd.setTextColor(it) }

                    val day_coin = view!!.findViewById<TextView>(R.id.day_coin)
                    val day_btc = view!!.findViewById<TextView>(R.id.day_btc)
                    val day_usd = view!!.findViewById<TextView>(R.id.day_usd)
                    day_coin.setText(nf.format(result.data.day.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { day_coin.setTextColor(it) }
                    day_btc.setText(nf.format(result.data.day.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { day_btc.setTextColor(it) }
                    day_usd.setText(Math.round(result.data.day.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { day_usd.setTextColor(it) }

                    val week_coin = view!!.findViewById<TextView>(R.id.week_coin)
                    val week_btc = view!!.findViewById<TextView>(R.id.week_btc)
                    val week_usd = view!!.findViewById<TextView>(R.id.week_usd)
                    week_coin.setText(nf.format(result.data.week.coins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.black) }
                        ?.let { week_coin.setTextColor(it) }
                    week_btc.setText(nf.format(result.data.week.bitcoins).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.btcpurple) }
                        ?.let { week_btc.setTextColor(it) }
                    week_usd.setText(Math.round(result.data.week.dollars * 100.0).div(100.0).toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { week_usd.setTextColor(it) }

                    val month_coin = view!!.findViewById<TextView>(R.id.month_coin)
                    val month_btc = view!!.findViewById<TextView>(R.id.month_btc)
                    val month_usd = view!!.findViewById<TextView>(R.id.month_usd)
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

    fun getChartInfo(){
        val d = provideApi().getChart(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (!result.data.isEmpty() && balance != null) {

                    currentHashrateTextView.setText(result.data.get(0).toString())
                }

            }, {
                Log.e("err", it.message)
            })

    }



}
