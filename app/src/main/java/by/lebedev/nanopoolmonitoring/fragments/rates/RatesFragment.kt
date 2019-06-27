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
        getActivity()?.getWindow()?.setBackgroundDrawableResource(by.lebedev.nanopoolmonitoring.R.drawable.nanopool_background)
        progressBar = view.findViewById<ProgressBar>(R.id.progressRates)

        getD()
    }

    fun getD() {
        val disposables = create1().loadData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.map {

                    it.url = "http://coincap.io/images/coins/${it.id}.png"
                    it.price_usd = Math.round(it.price_usd * 100.0) / 100.0
                }
                progressBar.visibility = View.INVISIBLE
                setupRecycler(result)
            },
                { error -> Log.e("AAA", "не забрал!!!") })
    }


    fun setupRecycler(list: List<CoinCap>) {
        recycleView.layoutManager = LinearLayoutManager(context)
        adapter = CoinAdapter(list)
        recycleView.adapter = adapter
    }
}