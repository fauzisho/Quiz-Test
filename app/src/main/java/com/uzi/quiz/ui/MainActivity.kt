package com.uzi.quiz.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.uzi.myapplication.ui.main.SectionsPagerAdapter
import com.uzi.quiz.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)?.setIcon(R.drawable.ic_coffe_icon)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_home_black_24dp)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_exit_to_app_black_24dp)

        tabs.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(Color.parseColor("#3700B3"), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(Color.parseColor("#959595"), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        tabs.getTabAt(1)?.select()
    }
}
