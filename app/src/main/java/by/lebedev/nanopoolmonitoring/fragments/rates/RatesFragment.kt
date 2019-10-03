package by.lebedev.nanopoolmonitoring.fragments.rates

import android.annotation.SuppressLint
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
import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.ServiceGenerator.provideApi
import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rates.*

class RatesFragment : Fragment() {
    lateinit var progressBar: ProgressBar
    private lateinit var adapter: CoinAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()?.setBackgroundDrawableResource(R.drawable.nanopool_background)
        progressBar = view.findViewById(R.id.progressRates)

        swipeRefreshRates.setColorSchemeResources(
            R.color.blue,
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.orange
        )
        swipeRefreshRates.setOnRefreshListener {
            recycleView.visibility= View.GONE
            setRates()
        }

        setRates()
    }

    @SuppressLint("CheckResult")
    fun setRates() {
        provideApi().loadData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (result != null) {
                    result.data.map {

                        it.url = "https://s2.coinmarketcap.com/static/img/coins/32x32/${it.id}.png"
                        it.quote.USD.price = Math.round(it.quote.USD.price * 100.0) / 100.0
                        it.quote.USD.percent_change_24h = Math.round(it.quote.USD.percent_change_24h * 100.0) / 100.0
                    }
                    if (recycleView != null) {
                        swipeRefreshRates.setRefreshing(false)

                        progressBar.visibility = View.INVISIBLE
                        setupRecycler(result.data)
                    }
                }
            },
                { error -> Log.e("AAA", error.message) })
    }


    fun setupRecycler(list: ArrayList<CoinCap>) {
        recycleView.layoutManager = LinearLayoutManager(context)
        adapter = CoinAdapter(list)
        recycleView.adapter = adapter
        recycleView.visibility= View.VISIBLE
    }
}