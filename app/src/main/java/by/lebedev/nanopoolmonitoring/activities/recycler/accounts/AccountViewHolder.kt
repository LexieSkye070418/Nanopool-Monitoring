package by.lebedev.nanopoolmonitoring.activities.recycler.accounts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val coinImage: ImageView
    private val trashImage: ImageView
    private val coinName: TextView
    private val walletName: TextView


    init {
        coinImage = itemView.findViewById(R.id.coin_image)
        trashImage = itemView.findViewById(R.id.trash_image)
        coinName = itemView.findViewById(R.id.coin_name)
        walletName = itemView.findViewById(R.id.wallet_name)
    }

    fun bind(account: Account) {
        coinName.setText(account.coin)

        walletName.setText(account.wallet)

        ImageSetterUtil.setImage(coinImage, account.coin)

//        trashImage.setOnClickListener {
//            Completable.fromAction {
//                DataBase.getInstance(it.context).db.accountDao()
//                    .delete(account)
//            }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {}
//        }
    }
}