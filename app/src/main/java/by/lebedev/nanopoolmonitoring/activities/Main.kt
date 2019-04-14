package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoring.R
import kotlinx.android.synthetic.main.main_layout.*

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.main_layout)


        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter


        tabs_main.setupWithViewPager(viewpager_main)
    }

    }
