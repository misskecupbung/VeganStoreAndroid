package com.inyongtisto.tokoonline.adapter
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.inyongtisto.tokoonline.fragment.CompletedFragment
import com.inyongtisto.tokoonline.fragment.ProgressFragment

@Suppress("DEPRECATION")
internal class AdapterHistoryPrimary(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ProgressFragment()
            }
            1 -> {
                CompletedFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}

