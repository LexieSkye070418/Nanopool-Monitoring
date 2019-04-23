package by.lebedev.nanopoolmonitoring.activities.recycler.accounts

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.room.entity.Account

class AccountAdapter(
        private val accountList: List<Account>,
        private val context: Context?) : RecyclerView.Adapter<AccountViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AccountViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_account, viewGroup, false)
        val holder = AccountViewHolder(view)
        view.setOnClickListener { v ->
    //            onItemClickListener!!.onItemClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(accountViewHolder: AccountViewHolder, id: Int) {

        accountViewHolder.bind(accountList.get(id))
    }

    override fun getItemCount(): Int {
        return accountList.size
    }
}