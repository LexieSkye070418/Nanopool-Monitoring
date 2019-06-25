package by.lebedev.nanopoolmonitoringnoads.fragments.payments.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.payment.DataPayments

class PaymentAdapter(
    private val payments: ArrayList<DataPayments>) : RecyclerView.Adapter<PaymentViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PaymentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_payment, viewGroup, false)
        val holder = PaymentViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(accountViewHolder: PaymentViewHolder, id: Int) {

        accountViewHolder.bind(payments.get(id))
    }

    override fun getItemCount(): Int {
        return payments.size
    }
}