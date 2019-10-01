package by.lebedev.nanopoolmonitoring.fragments.payments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.fragments.payments.recycler.PaymentAdapter
import by.lebedev.nanopoolmonitoring.retrofit.entity.payment.DataPayments
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_payments.*
import javax.inject.Inject

class PaymentsFragment : Fragment() {

    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData

    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()?.setBackgroundDrawableResource(by.lebedev.nanopoolmonitoring.R.drawable.nanopool_background)


        val component = DaggerMagicBox.builder().build()
        coinWalletTempData = component.provideTabIntent()

        coin = coinWalletTempData.coin
        wallet = coinWalletTempData.wallet

        getPayments()
    }

    fun getPayments() {
        val d = provideApi().getPayments(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result!=null&&result.status&&progressPayments!=null&&payment_recycle!=null) {
                        progressPayments.visibility = View.INVISIBLE
                    setupRecycler(result.data)
                }else {
                    if (progressPayments != null && textForErrorPayments != null) {
                        progressPayments.visibility = View.INVISIBLE
                        textForErrorPayments.setText("Payments not found...")
                    }
                }
            }, {
                Log.e("err", it.message)
            })
    }

    fun setupRecycler(payments: ArrayList<DataPayments>) {
        payment_recycle.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(view?.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        payment_recycle.layoutManager = layoutManager
        payment_recycle.adapter = PaymentAdapter(payments)
    }

}
