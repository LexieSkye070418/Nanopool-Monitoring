package by.lebedev.nanopoolmonitoring.fragments.rates

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.fragments.rates.ServiceGenerator.create1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_four.*

class FourthFragment : Fragment() {

    private lateinit var adapter: CoinAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_four, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getD()
    }

     fun getD() {
        val disposables = create1().loadData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.map {
                    it
                    it.url = "http://coincap.io/images/coins/${it.id}.png"
                    it.price_usd = Math.round(it.price_usd *100.0)/100.0
                }
                setupRecycler(result)
                Log.e("AAA", "забрал лист")
            },
                { error -> Log.e("AAA", "не забрал!!!") })
    }


     fun setupRecycler(list: List<CoinCap>) {

        recycleView.layoutManager = LinearLayoutManager(context)

        adapter = CoinAdapter(list)

        recycleView.adapter = adapter
    }
}