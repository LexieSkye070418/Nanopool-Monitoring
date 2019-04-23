package by.lebedev.nanopoolmonitoring.activities.recycler.accounts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.room.entity.Account


class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val coinImage: ImageView
    private val coinName: TextView
    private val walletName: TextView


    init {
        coinImage = itemView.findViewById(R.id.coin_image)
        coinName = itemView.findViewById(R.id.coin_name)
        walletName = itemView.findViewById(R.id.wallet_name)
    }

    fun bind(account: Account) {
        coinName.setText(account.coin)

        walletName.setText(account.wallet)

//        ImageLoaderUtil.loadImage(photoImageView, student.PHOTO_URL)
    }
}