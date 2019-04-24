package by.lebedev.nanopoolmonitoring.activities.recycler.accounts

import android.widget.ImageView
import by.lebedev.nanopoolmonitoring.R

object ImageSetterUtil {
    fun setImage(imageView: ImageView, coin: String) {

        when (coin) {
            "ETH" -> {
                imageView.setImageResource(R.drawable.eth)
            }
            "ETC" -> {
                imageView.setImageResource(R.drawable.etc)
            }
            "ZEC" -> {
                imageView.setImageResource(R.drawable.zec)
            }
            "XMR" -> {
                imageView.setImageResource(R.drawable.xmr)
            }
            "PASC" -> {
                imageView.setImageResource(R.drawable.pasc)
            }
            "ETN" -> {
                imageView.setImageResource(R.drawable.etn)
            }
            "RVN" -> {
                imageView.setImageResource(R.drawable.raven)
            }
            "GRIN" -> {
                imageView.setImageResource(R.drawable.grin)
            }
        }
    }
}
