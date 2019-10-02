package by.lebedev.nanopoolmonitoringnoads.fragments.payments.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.payment.DataPayments

import javax.inject.Inject


class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val paymentConfirmation: TextView
    private val paymentDate: TextView
    private val paymentAmount: TextView
    private val paymentTx: TextView

    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData



    init {
        paymentConfirmation = itemView.findViewById(R.id.confirmation)
        paymentDate = itemView.findViewById(R.id.date_time)
        paymentAmount = itemView.findViewById(R.id.amount_transaction)
        paymentTx = itemView.findViewById(R.id.tx_transaction)

        val component = DaggerMagicBox.builder().build()
        coinWalletTempData = component.provideCoinWalletTempData()
    }

    fun bind(payment: DataPayments) {
        paymentConfirmation.setText(payment.transform(payment.confirmed))

        paymentDate.setText(payment.convertLongToTime(payment.date))
        paymentAmount.setText(payment.amount.toString().plus(coinWalletTempData.coin))
        paymentTx.setText(payment.txHash)
    }
}