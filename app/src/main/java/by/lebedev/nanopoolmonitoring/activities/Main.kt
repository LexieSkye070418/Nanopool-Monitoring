package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoring.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.main_layout.*


class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.main_layout)


        val adRequest = AdRequest
            .Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()
        adView.loadAd(adRequest)

    }

//    override fun onResume() {
//        super.onResume()
//        adView.resume()
//    }
//
//    override fun onPause() {
//        adView.pause()
//        super.onPause()
//    }
//
//    override fun onDestroy() {
//        adView.destroy()
//        super.onDestroy()
//    }
}