package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.pageadapter.PagerAdapter
import kotlinx.android.synthetic.main.main_layout.*
import javax.inject.Inject


class TabActivity : AppCompatActivity() {

    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val component = DaggerMagicBox.builder().build()
        coinWalletTempData = component.provideTabIntent()


        val fragmentAdapter = PagerAdapter(supportFragmentManager)

        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
        coinWalletTempData.coin = intent.getStringExtra("COIN")
        coinWalletTempData.wallet = intent.getStringExtra("WALLET")

    }
}