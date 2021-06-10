package com.inyongtisto.tokoonline.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterHistory
import com.inyongtisto.tokoonline.adapter.AdapterHistoryPrimary
import com.inyongtisto.tokoonline.helper.Helper
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_history.*

class HistoryActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        Helper().setToolbar(this, toolbar_history, "Riwayat")

        tabLayout = findViewById(R.id.tabLayout)

        viewPager = findViewById(R.id.viewPager)

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_process)))

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_completed)))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = AdapterHistoryPrimary(this, supportFragmentManager,
            tabLayout.tabCount)

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

