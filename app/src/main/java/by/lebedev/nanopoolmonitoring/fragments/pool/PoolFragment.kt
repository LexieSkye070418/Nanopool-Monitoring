package by.lebedev.nanopoolmonitoring.fragments.pool

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.webview.WebActivity
import by.lebedev.nanopoolmonitoring.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pool.*
import java.text.NumberFormat
import javax.inject.Inject

class PoolFragment : Fragment() {
    lateinit var mAdView: AdView
    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData

    val nf = NumberFormat.getInstance()

    var hashType: String = ""
    var coin: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pool, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()
            ?.setBackgroundDrawableResource(R.drawable.nanopool_background)
        MobileAds.initialize(view.context, "ca-app-pub-1501215034144631~3780667725")

        val adView = AdView(view.context)
        adView.adSize = AdSize.BANNER

        adView.adUnitId = "ca-app-pub-1501215034144631/3339997521"


        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)

        nf.maximumFractionDigits = 8

        val component = DaggerMagicBox.builder().build()
        coinWalletTempData = component.provideTabIntent()


        coin = coinWalletTempData.coin
        hashType = coinWalletTempData.getHashType(coin)
        coin_name.setText(coinWalletTempData.fullName(coin))

        setPrice()
        setHashrate()
        setMiners()
        setGeneralInfo()
        setMinorPoolData()

        swipeRefreshPool.setColorSchemeResources(
            R.color.blue,
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.orange
        )
        swipeRefreshPool.setOnRefreshListener {
            poolLayoutForUpdate.visibility = View.INVISIBLE
            hashrate.text = getString(R.string.updatingPoolInfo)
            miners.text = getString(R.string.updatingPoolInfo)
            price.text = getString(R.string.updatingPoolInfo)
            poolFee.text = getString(R.string.updatingPoolInfo)
            payoutScheme.text = getString(R.string.updatingPoolInfo)
            blockValidation.text = getString(R.string.updatingPoolInfo)
            payoutLimit.text = getString(R.string.updatingPoolInfo)

            setPrice()
            setHashrate()
            setMiners()
            setGeneralInfo()
            setMinorPoolData()
        }


        website.setOnClickListener {
            val intent = Intent(this.context, WebActivity::class.java)
            intent.putExtra("url", "https://" + website.text.toString())
            startActivity(intent)
        }
    }

    fun setPrice() {
        val d = provideApi().getPrice(coin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result != null && result.status && price != null) {
                    swipeRefreshPool.setRefreshing(false)
                    poolLayoutForUpdate.visibility = View.VISIBLE
                    nf.maximumFractionDigits = 2
                    price.setText(nf.format(result.data.price_usd).toString().plus('$'))
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { price.setTextColor(it) }
                }

            }, {
                Log.e("err", it.message)
            })
    }

    fun setHashrate() {
        val d = provideApi().getHashrate(coin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result != null && result.status && hashrate != null) {
                    hashrate.setText(nf.format(result.data).toString().plus(' ').plus(hashType))
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { hashrate.setTextColor(it) }
                }
            }, {
                Log.e("err", it.message)
            })
    }

    fun setMiners() {
        val d = provideApi().getMiners(coin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result != null && result.status && miners != null) {
                    miners.setText(result.data.toString())
                    view?.context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                        ?.let { miners.setTextColor(it) }
                }
            }, {
                Log.e("err", it.message)
            })
    }

    fun setGeneralInfo() {
        if (authors != null) authors.text = coinWalletTempData.getAuthors(coin)
        if (release != null) release.text = coinWalletTempData.getRelease(coin)
        if (writtenIn != null) writtenIn.text = coinWalletTempData.getWrittenIn(coin)
        if (website != null) website.text = coinWalletTempData.getWebsite(coin)
    }

    fun setMinorPoolData() {
        if (poolFee != null) poolFee.text = coinWalletTempData.getPoolFee(coin)
        if (payoutScheme != null) payoutScheme.text = coinWalletTempData.getPayoutScheme(coin)
        if (blockValidation != null) blockValidation.text = coinWalletTempData.getBlockValidation(coin)
        if (payoutLimit != null) payoutLimit.text = coinWalletTempData.getPayoutLimit(coin)
    }

}