package by.lebedev.nanopoolmonitoringnoads.fragments.payments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.fragments.payments.recycler.PaymentAdapter
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.payment.DataPayments
import by.lebedev.nanopoolmonitoringnoads.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_payments.*
import javax.inject.Inject

class PaymentsFragment : Fragment() {

    @Inject
    lateinit var tabIntent: TabIntent

    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()?.setBackgroundDrawableResource(R.drawable.nanopool_background)


        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        getPayments()
    }

    fun getPayments() {
        val d = provideApi().getPayments(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result!=null&&!result.data.isEmpty()&&progressPayments!=null&&payment_recycle!=null) {
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