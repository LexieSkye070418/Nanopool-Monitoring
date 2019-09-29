package by.lebedev.nanopoolmonitoringnoads.fragments.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.fragments.charts.BarChartFragment
import by.lebedev.nanopoolmonitoringnoads.fragments.charts.LineChartFragment
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
        getActivity()?.getWindow()
            ?.setBackgroundDrawableResource(R.drawable.nanopool_background)


        nf.maximumFractionDigits = 4

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        //setting data
//        setGeneralInfo()
        setCurrHashrateBalance()
        setAverageHashrateAndCalcProfit()



        if (layoutLineChart != null) {

            val lineChartFragment = LineChartFragment()
            val ft = childFragmentManager.beginTransaction()
            ft.replace(R.id.layoutLineChart, lineChartFragment)
            ft.commit()
        }
        if (layoutBarChart != null) {
            val barChartFragment = BarChartFragment()
            val ft1 = childFragmentManager.beginTransaction()
            ft1.replace(R.id.layoutBarChart, barChartFragment)
            ft1.commit()
        }


    }

    fun setCurrHashrateBalance() {
        nf.maximumFractionDigits = 2
        val d = provideApi().getHashrateBalance(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result->
                if (result!=null&&result.status&&balance!=null&&current_hashrate!=null){

                    balance.setText(nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase())
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { balance.setTextColor(it) }

                    if (result.data.hashrate > 1000) {
                        current_hashrate.setText(
                            nf.format
                                (result.data.hashrate.div(1000)).toString().plus(" ").plus(
                                tabIntent.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        current_hashrate.setText(
                            nf.format
                                (result.data.hashrate).toString().plus(" ").plus(
                                tabIntent.getWorkerHashType(
                                    coin
                                )
                            )
                        )
                    }

                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { current_hashrate.setTextColor(it) }
                }

            },{
                Log.e("err", it.message)
            })
    }

    fun setAverageHashrateAndCalcProfit() {
        nf.maximumFractionDigits = 2
        val d = provideApi().getAverageHashrate(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result->
                if (result!=null&&result.status&&hours_6!=null&&hours_24!=null){

                    if (result.data.h6 > 1000) {

                        hours_6.setText(
                            nf.format
                                (result.data.h6.div(1000)).toString().plus(" ").plus(
                                tabIntent.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        hours_6.setText(
                            nf.format
                                (result.data.h6).toString().plus(" ").plus(
                                tabIntent.getWorkerHashType(
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
                                tabIntent.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        hours_24.setText(
                            nf.format
                                (result.data.h24).toString().plus(" ").plus(
                                tabIntent.getWorkerHashType(
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

            },{
                Log.e("err", it.message)
            })
    }




    fun setGeneralInfo() {
        nf.maximumFractionDigits = 2
        val d = provideApi().getGeneralInfo(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result!=null&&result.status && balance != null) {

                    balance.setText(nf.format(Math.abs(result.data.balance)).toString().plus(" ").plus(coin).toUpperCase())
                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { balance.setTextColor(it) }


                    if (result.data.hashrate > 1000) {
                        current_hashrate.setText(
                            nf.format
                                (result.data.hashrate.div(1000)).toString().plus(" ").plus(
                                tabIntent.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        current_hashrate.setText(
                            nf.format
                                (result.data.hashrate).toString().plus(" ").plus(
                                tabIntent.getWorkerHashType(
                                    coin
                                )
                            )
                        )
                    }


                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { current_hashrate.setTextColor(it) }


                    if (result.data.avgHashrate.h6 > 1000) {

                        hours_6.setText(
                            nf.format
                                (result.data.avgHashrate.h6.div(1000)).toString().plus(" ").plus(
                                tabIntent.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        hours_6.setText(
                            nf.format
                                (result.data.avgHashrate.h6).toString().plus(" ").plus(
                                tabIntent.getWorkerHashType(
                                    coin
                                )
                            )
                        )
                    }

                    view?.context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
                        ?.let { hours_6.setTextColor(it) }


                    if (result.data.avgHashrate.h24 > 1000) {

                        hours_24.setText(
                            nf.format
                                (result.data.avgHashrate.h24.div(1000)).toString().plus(" ").plus(
                                tabIntent.getWorkerHashTypeHigh(
                                    coin
                                )
                            )
                        )
                    } else {
                        hours_24.setText(
                            nf.format
                                (result.data.avgHashrate.h24).toString().plus(" ").plus(
                                tabIntent.getWorkerHashType(
                                    coin
                                )
                            )
                        )
                    }

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
        nf.maximumFractionDigits = 4
        val d = provideApi().getProfit(coin, hashrate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result != null&& result.status&& view != null && minute_coin != null && hour_coin != null && day_coin != null && week_coin != null && month_coin != null) {
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

}