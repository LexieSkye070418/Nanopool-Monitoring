package by.lebedev.nanopoolmonitoring.fragments.rates

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.fragments.rates.recycler.CoinAdapter
import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.ServiceGenerator.create1
import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCanInfo
import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCap
import by.lebedev.nanopoolmonitoring.retrofit.entity.chart.ChartData
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pool.*
import kotlinx.android.synthetic.main.fragment_rates.*
import kotlinx.android.synthetic.main.item_coin.*
import kotlinx.android.synthetic.main.linechart_layout.*

class RatesFragment : Fragment() {
    lateinit var progressBar: ProgressBar

    private lateinit var adapter: CoinAdapter
    var status: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()?.setBackgroundDrawableResource(by.lebedev.nanopoolmonitoring.R.drawable.nanopool_background)
        progressBar = view.findViewById<ProgressBar>(R.id.progressRates)

        getD()
    }

    fun getD() {
        val disposables = create1().loadData("587a787c-2a32-44f9-8a5a-40470f01ce6f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.data.map{
                    it.url = "https://s2.coinmarketcap.com/static/img/coins/32x32/${it.id}.png"
                    it.price = Math.round(it.price * 100.0) / 100.0
                    Log.e("AAA", "it.price")

                }
                progressBar.visibility = View.INVISIBLE
                setupRecycler(result.data)
            },
                { error -> Log.e("AAA", "не забрал!!!") })
    }


    fun setupRecycler(list: ArrayList<CoinCap>) {
        recycleView.layoutManager = LinearLayoutManager(context)
        adapter = CoinAdapter(list)
        recycleView.adapter = adapter
    }
}