package by.lebedev.nanopoolmonitoring.activities

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import by.lebedev.nanopoolmonitoring.activities.news.FiveFragment
import by.lebedev.nanopoolmonitoring.fragments.dashboard.DashboardFragment
import by.lebedev.nanopoolmonitoring.fragments.payments.PaymentsFragment
import by.lebedev.nanopoolmonitoring.fragments.pool.PoolFragment
import by.lebedev.nanopoolmonitoring.fragments.rates.FourthFragment

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {

            0 -> DashboardFragment()
            1 -> PoolFragment()
            2 -> PaymentsFragment()
            3 -> FourthFragment()
            else -> {
                return FiveFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "First Tab"
            1 -> "Second Tab"
            2 -> "Third Tab"
            3 -> "Four  Tab"
            else -> {
                return "Five  Tab"
            }
        }
    }
}