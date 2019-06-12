package by.lebedev.nanopoolmonitoring.pageadapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import by.lebedev.nanopoolmonitoring.fragments.dashboard.DashboardFragment
import by.lebedev.nanopoolmonitoring.fragments.news.NewsFragment
import by.lebedev.nanopoolmonitoring.fragments.payments.PaymentsFragment
import by.lebedev.nanopoolmonitoring.fragments.pool.PoolFragment
import by.lebedev.nanopoolmonitoring.fragments.rates.RatesFragment
import by.lebedev.nanopoolmonitoring.fragments.workers.WorkersFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {



    override fun getItem(position: Int): Fragment {
        return when (position) {

            0 -> DashboardFragment()
            1 -> WorkersFragment()
            2 -> PoolFragment()
            3 -> PaymentsFragment()
            4 -> RatesFragment()
            else -> {
                return NewsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 6
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Dashboard"
            1 -> "Workers"
            2 -> "Pool"
            3 -> "Payments"
            4 -> "Rates"
            else -> {
                return "News"
            }
        }
    }
}