package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.pageadapter.PagerAdapter
import kotlinx.android.synthetic.main.main_layout.*


class TabActivity : AppCompatActivity() {
//    lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

//        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)

        val fragmentAdapter = PagerAdapter(supportFragmentManager)

        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
        TabIntent.instance.coin = intent.getStringExtra("COIN")
        TabIntent.instance.wallet = intent.getStringExtra("WALLET")
    }

}
