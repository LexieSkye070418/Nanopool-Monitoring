package by.lebedev.nanopoolmonitoringnoads.activities.recycler.accounts

import android.widget.ImageView
import by.lebedev.nanopoolmonitoringnoads.R

object ImageSetterUtil {
    fun setImage(imageView: ImageView, coin: String) {

        when (coin) {
            "eth" -> {
                imageView.setImageResource(R.drawable.eth)
            }
            "etc" -> {
                imageView.setImageResource(R.drawable.etc)
            }
            "zec" -> {
                imageView.setImageResource(R.drawable.zec)
            }
            "xmr" -> {
                imageView.setImageResource(R.drawable.xmr)
            }
            "pasc" -> {
                imageView.setImageResource(R.drawable.pasc)
            }
            "etn" -> {
                imageView.setImageResource(R.drawable.etn)
            }
            "rvn" -> {
                imageView.setImageResource(R.drawable.raven)
            }
            "grin29" -> {
                imageView.setImageResource(R.drawable.grin)
            }
        }
    }
}
