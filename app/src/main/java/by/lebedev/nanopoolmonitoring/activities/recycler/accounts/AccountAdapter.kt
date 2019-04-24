package by.lebedev.nanopoolmonitoring.activities.recycler.accounts

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AccountAdapter(
    private val accountList: List<Account>,
    private val context: Context?
) : RecyclerView.Adapter<AccountViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AccountViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_account, viewGroup, false)
        val holder = AccountViewHolder(view)
        view.setOnClickListener { v ->
//                        onItemClickListener!!.onItemClick(holder.adapterPosition)
        }

        val trashImage = holder.itemView.findViewById<ImageView>(R.id.trash_image)

        trashImage.setOnClickListener {

            Completable.fromAction {
                DataBase.getInstance(it.context).db.accountDao()
                    .delete(AccountLocalList.instance.list.get(holder.adapterPosition))
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        AccountLocalList.instance.list.removeAt(holder.adapterPosition)
                        notifyDataSetChanged()
                    },
                    {
                        Toast.makeText(view.context, "Error deleting account...", Toast.LENGTH_SHORT).show()
                    }
                )
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