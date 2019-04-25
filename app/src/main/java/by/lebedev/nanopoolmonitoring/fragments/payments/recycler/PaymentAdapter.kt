package by.lebedev.nanopoolmonitoring.fragments.payments.recycler

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.TabActivity
import by.lebedev.nanopoolmonitoring.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoring.fragments.retrofit.entity.payment.DataPayments
import by.lebedev.nanopoolmonitoring.fragments.retrofit.entity.payment.Payments
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PaymentAdapter(
    private val payments: ArrayList<DataPayments>,
    private val context: Context?
) : RecyclerView.Adapter<PaymentViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PaymentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_account, viewGroup, false)
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