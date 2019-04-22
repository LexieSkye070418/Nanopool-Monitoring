package by.lebedev.nanopoolmonitoring.activities

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import by.lebedev.nanopoolmonitoring.activities.course.FourFragment
import by.lebedev.nanopoolmonitoring.activities.news.FiveFragment

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {

            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            3 -> FourFragment()
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