package by.lebedev.nanopoolmonitoring.fragments.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

class DashboardFragment : Fragment() {

    @Inject
    lateinit var tabIntent: TabIntent

    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                if (result.status) {
                    balance.setText(result.data.balance.toString().plus('$'))
                    current_hashrate.setText(result.data.hashrate.toString().plus(" H/s"))
                    hours_6.setText(result.data.avgHashrate.h6.toString().plus(" H/s"))
                    hours_24.setText(result.data.avgHashrate.h24.toString().plus(" H/s"))


                    getProfitInfo(coin, result.data.avgHashrate.h6)
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

                minute_coin.setText(Math.round(result.data.minute.coins * 10000.0).div(10000.0).toString())
                minute_btc.setText(Math.round(result.data.minute.bitcoins * 100000.0).div(100000.0).toString())
                minute_usd.setText(Math.round(result.data.minute.dollars * 1000.0).div(1000.0).toString())

                hour_coin.setText(Math.round(result.data.hour.coins * 10000.0).div(10000.0).toString())
                hour_btc.setText(Math.round(result.data.hour.bitcoins * 100000.0).div(100000.0).toString())
                hour_usd.setText(Math.round(result.data.hour.dollars * 1000.0).div(1000.0).toString())

                day_coin.setText(Math.round(result.data.day.coins * 10000.0).div(10000.0).toString())
                day_btc.setText(Math.round(result.data.day.bitcoins * 100000.0).div(100000.0).toString())
                day_usd.setText(Math.round(result.data.day.dollars * 1000.0).div(1000.0).toString())

                week_coin.setText(Math.round(result.data.week.coins * 10000.0).div(10000.0).toString())
                week_btc.setText(Math.round(result.data.week.bitcoins * 100000.0).div(100000.0).toString())
                week_usd.setText(Math.round(result.data.week.dollars * 1000.0).div(1000.0).toString())

                month_coin.setText(Math.round(result.data.month.coins * 10000.0).div(10000.0).toString())
                month_btc.setText(Math.round(result.data.month.bitcoins * 100000.0).div(100000.0).toString())
                month_usd.setText(Math.round(result.data.month.dollars * 1000.0).div(1000.0).toString())

            }, {
                Log.e("err", it.message)
            })
    }
}
