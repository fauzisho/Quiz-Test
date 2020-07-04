package com.uzi.myapplication.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.uzi.quiz.R
import com.uzi.quiz.ui.`try`.TryFragment
import com.uzi.quiz.ui.exit.ExitFragment
import com.uzi.quiz.ui.home.HomeFragment

private val TAB_TITLES = arrayOf(
    R.string.title_try,
    R.string.title_home,
    R.string.title_exit
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return TryFragment()
        } else if (position == 1) {
            return HomeFragment()
        } else {
            return ExitFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}