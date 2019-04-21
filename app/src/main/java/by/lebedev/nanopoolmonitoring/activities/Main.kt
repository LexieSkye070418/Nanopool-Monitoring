package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.course.FourFragment
import kotlinx.android.synthetic.main.main_layout.*


class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)



        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)

        viewpager_main.adapter = fragmentAdapter


        tabs_main.setupWithViewPager(viewpager_main)
    }

    }