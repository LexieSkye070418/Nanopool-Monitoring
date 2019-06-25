package by.lebedev.nanopoolmonitoringnoads.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.pageadapter.PagerAdapter
import kotlinx.android.synthetic.main.main_layout.*
import javax.inject.Inject


class TabActivity : AppCompatActivity() {

    @Inject
    lateinit var tabIntent: TabIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()


        val fragmentAdapter = PagerAdapter(supportFragmentManager)

        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
        tabIntent.coin = intent.getStringExtra("COIN")
        tabIntent.wallet = intent.getStringExtra("WALLET")

    }
}