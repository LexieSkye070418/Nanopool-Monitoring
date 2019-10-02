package by.lebedev.nanopoolmonitoringnoads.fragments.payments

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoringnoads.dagger.CoinWalletTempData

import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.fragments.payments.recycler.PaymentAdapter
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.payment.DataPayments
import by.lebedev.nanopoolmonitoringnoads.retrofit.provideApi

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_payments.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject


class PaymentsFragment : Fragment() {

    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData
    var localPaymentsArray: ArrayList<DataPayments>? = null
    val nf = NumberFormat.getInstance()
    var selectedPeriod = -1
    val periodArray = arrayOf("24H", "1W", "1M", "1Y", "ALL")
    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getActivity()?.getWindow()
            ?.setBackgroundDrawableResource(R.drawable.nanopool_background)


        val component = DaggerMagicBox.builder().build()
        coinWalletTempData = component.provideCoinWalletTempData()

        coin = coinWalletTempData.coin
        wallet = coinWalletTempData.wallet

        selectPayoutPeriod.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
                .setTitle("Select payout period")
                .setIcon(R.drawable.bitcoinicon)
                .setCancelable(true)

                .setSingleChoiceItems(
                    periodArray, -1, { dialog: DialogInterface, item: Int ->
                        selectedPeriod = item
                    }
                )
                .setPositiveButton("OK") { dialog: DialogInterface, item: Int ->
                    selectPayoutPeriod.text = periodArray.get(selectedPeriod)
                    payoutCount.text = localPaymentsArray?.let { it1 ->
                        countEarning(
                            it1,
                            getTimeshiftMillis(periodArray.get(selectedPeriod))
                        ).plus(" ").plus(coin.toUpperCase())
                    }
                    dialog.cancel()
                }
                .setNegativeButton("Cancel") { dialog: DialogInterface, item: Int -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        getPayments()
    }

    fun getPayments() {
        val d = provideApi().getPayments(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result != null && result.status && progressPayments != null && payment_recycle != null) {
                    progressPayments.visibility = View.INVISIBLE
                    setupRecycler(result.data)
                    localPaymentsArray = result.data
                    payoutCount.text = countEarning(result.data, 86400000).plus(" ").plus(coin.toUpperCase())


                } else {
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

    fun countEarning(payments: ArrayList<DataPayments>, millisShift: Long): String {
        nf.maximumFractionDigits = 2
        var count = 0.0
        val calendar = Calendar.getInstance()
        val todayMillis = calendar.timeInMillis
        val checkoutMillis = todayMillis - millisShift
        for (i in 0 until payments.size) {
            if ((payments.get(i).date * 1000) >= checkoutMillis) {
                count += payments.get(i).amount
            }
        }
        return nf.format(count)
    }

    fun getTimeshiftMillis(period: String): Long {
        return when (period) {
            "24H" -> {
                return 86400000L
            }
            "1W" -> {
                return 604800000L
            }
            "1M" -> {
                return 2592000000L
            }
            "1Y" -> {
                return 31536000000L
            }
            "ALL" -> {
                return Calendar.getInstance().timeInMillis
            }
            else -> 0L
        }
    }


}