package by.lebedev.nanopoolmonitoring.activities

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import by.lebedev.nanopoolmonitoring.activities.rates.FourthFragment

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