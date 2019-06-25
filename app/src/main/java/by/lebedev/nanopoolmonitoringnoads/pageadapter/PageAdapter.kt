package by.lebedev.nanopoolmonitoringnoads.pageadapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import by.lebedev.nanopoolmonitoringnoads.fragments.dashboard.DashboardFragment
import by.lebedev.nanopoolmonitoringnoads.fragments.news.NewsFragment
import by.lebedev.nanopoolmonitoringnoads.fragments.payments.PaymentsFragment
import by.lebedev.nanopoolmonitoringnoads.fragments.pool.PoolFragment
import by.lebedev.nanopoolmonitoringnoads.fragments.rates.RatesFragment
import by.lebedev.nanopoolmonitoringnoads.fragments.workers.WorkersFragment

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