package by.lebedev.nanopoolmonitoringnoads.fragments.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import javax.inject.Inject


class DashboardFragment : Fragment() {

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

                if (view != null && minute_coin != null && hour_coin != null && day_coin != null && week_coin != null && month_coin != null) {
                    val minute_coin = view!!.findViewById<TextView>(R.id.minute_coin)
                    val minute_btc = view!!.findViewById<TextView>(R.id.minute_btc)
                    val minute_usd = view!!.findViewById<TextView>(R.id.minute_usd)
                    minute_coin.setText(nf.format(result.data.minute.coins).toString())
                    minute_btc.setText(nf.format(result.data.minute.bitcoins).toString())
                    minute_usd.setText(Math.round(result.data.minute.dollars * 1000).div(1000).toString())

                    val hour_coin = view!!.findViewById<TextView>(R.id.hour_coin)
                    val hour_btc = view!!.findViewById<TextView>(R.id.hour_btc)
                    val hour_usd = view!!.findViewById<TextView>(R.id.hour_usd)
                    hour_coin.setText(nf.format(result.data.hour.coins).toString())
                    hour_btc.setText(nf.format(result.data.hour.bitcoins).toString())
                    hour_usd.setText(Math.round(result.data.hour.dollars * 1000.0).div(1000.0).toString())

                    val day_coin = view!!.findViewById<TextView>(R.id.day_coin)
                    val day_btc = view!!.findViewById<TextView>(R.id.day_btc)
                    val day_usd = view!!.findViewById<TextView>(R.id.day_usd)
                    day_coin.setText(nf.format(result.data.day.coins).toString())
                    day_btc.setText(nf.format(result.data.day.bitcoins).toString())
                    day_usd.setText(Math.round(result.data.day.dollars * 100.0).div(100.0).toString())

                    val week_coin = view!!.findViewById<TextView>(R.id.week_coin)
                    val week_btc = view!!.findViewById<TextView>(R.id.week_btc)
                    val week_usd = view!!.findViewById<TextView>(R.id.week_usd)
                    week_coin.setText(nf.format(result.data.week.coins).toString())
                    week_btc.setText(nf.format(result.data.week.bitcoins).toString())
                    week_usd.setText(Math.round(result.data.week.dollars * 100.0).div(100.0).toString())

                    val month_coin = view!!.findViewById<TextView>(R.id.month_coin)
                    val month_btc = view!!.findViewById<TextView>(R.id.month_btc)
                    val month_usd = view!!.findViewById<TextView>(R.id.month_usd)
                    month_coin.setText(nf.format(result.data.month.coins).toString())
                    month_btc.setText(nf.format(result.data.month.bitcoins).toString())
                    month_usd.setText(Math.round(result.data.month.dollars * 100.0).div(100.0).toString())
                }
            }, {
                Log.e("err", it.message)
            })
    }
}
