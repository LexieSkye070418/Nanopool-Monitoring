package by.lebedev.nanopoolmonitoringnoads.activities.recycler.accounts

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.activities.TabActivity
import by.lebedev.nanopoolmonitoringnoads.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.room.DataBase
import by.lebedev.nanopoolmonitoringnoads.room.entity.Account
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountAdapter(
    private val accountList: List<Account>,
    private val context: Context?
) : RecyclerView.Adapter<AccountViewHolder>() {

    @Inject
    lateinit var accountLocalList: AccountLocalList
    init {
        val component = DaggerMagicBox.builder().build()
        accountLocalList = component.provideAccountLocalList()

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AccountViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_account, viewGroup, false)
        val holder = AccountViewHolder(view)
        view.setOnClickListener { v ->

            val intent = Intent(view.context,TabActivity::class.java)
            intent.putExtra("COIN",accountLocalList.list.get(holder.adapterPosition).coin)
            intent.putExtra("WALLET",accountLocalList.list.get(holder.adapterPosition).wallet)
            if (context != null) {
                context.startActivity(intent)
            }
        }

        val trashImage = holder.itemView.findViewById<ImageView>(R.id.trash_image)

        trashImage.setOnClickListener {

            Completable.fromAction {
                DataBase.getInstance(it.context).db.accountDao()
                    .delete(accountLocalList.list.get(holder.adapterPosition))
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        accountLocalList.list.removeAt(holder.adapterPosition)
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