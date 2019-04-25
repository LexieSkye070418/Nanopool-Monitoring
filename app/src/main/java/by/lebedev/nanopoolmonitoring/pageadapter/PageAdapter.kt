package by.lebedev.nanopoolmonitoring.pageadapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import by.lebedev.nanopoolmonitoring.fragments.rates.FourthFragment
import by.lebedev.nanopoolmonitoring.fragments.dashboard.DashboardFragment
import by.lebedev.nanopoolmonitoring.fragments.NewsFragment
import by.lebedev.nanopoolmonitoring.fragments.payments.PaymentsFragment
import by.lebedev.nanopoolmonitoring.fragments.pool.PoolFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {

            0 -> DashboardFragment()
            1 -> PoolFragment()
            2 -> PaymentsFragment()
            3 -> FourthFragment()
            else -> {
                return NewsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Dashboard"
            1 -> "Pool"
            2 -> "Payments"
            3 -> "Rates"
            else -> {
                return "News"
            }
        }
    }
}