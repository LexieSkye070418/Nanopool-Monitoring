package by.lebedev.nanopoolmonitoring.fragments.pool

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pool.*

class PoolFragment : Fragment() {
    var hashType: String = ""
    var coin: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pool, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        coin = TabIntent.instance.coin
        hashType = TabIntent.instance.getHashType(coin)
        coin_name.setText(TabIntent.instance.fullName(coin))
        getPrice()
        getHashrate()
        getMiners()


    }


    fun getPrice() {
        val d = provideApi().getPrice(coin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                price.setText(result.data.price_usd.toString().plus('$'))
            }, {
                Log.e("err",it.message)
            })
    }

    fun getHashrate() {
        val d = provideApi().getHashrate(coin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hashrate.setText(result.data.toString().plus(' ').plus(hashType))
            }, {
                Log.e("err",it.message)
            })
    }

    fun getMiners() {
        val d = provideApi().getMiners(coin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                miners.setText(result.data.toString())
            }, {
                Log.e("err",it.message)
            })
    }


}
