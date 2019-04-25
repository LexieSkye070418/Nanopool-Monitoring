package by.lebedev.nanopoolmonitoring.fragments.payments.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.retrofit.entity.payment.DataPayments


class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val paymentConfirmation: TextView
    private val paymentDate: TextView
    private val paymentAmount: TextView
    private val paymentTx: TextView


    init {
        paymentConfirmation = itemView.findViewById(R.id.confirmation)
        paymentDate = itemView.findViewById(R.id.date_time)
        paymentAmount = itemView.findViewById(R.id.amount_transaction)
        paymentTx = itemView.findViewById(R.id.tx_transaction)
    }

    fun bind(payment: DataPayments) {
        paymentConfirmation.setText(payment.transform(payment.confirmed))

        paymentDate.setText(payment.convertLongToTime(payment.date))
        paymentAmount.setText(payment.amount.toString().plus(TabIntent.instance.coin))
        paymentTx.setText(payment.txHash)
    }
}