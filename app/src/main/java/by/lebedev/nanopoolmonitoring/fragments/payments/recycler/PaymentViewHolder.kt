package by.lebedev.nanopoolmonitoring.fragments.payments.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.recycler.accounts.ImageSetterUtil
import by.lebedev.nanopoolmonitoring.fragments.retrofit.entity.payment.DataPayments
import by.lebedev.nanopoolmonitoring.fragments.retrofit.entity.payment.Payments
import by.lebedev.nanopoolmonitoring.room.entity.Account


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
        paymentAmount.setText(payment.amount.toString())
        paymentTx.setText(payment.txHash)
    }
}