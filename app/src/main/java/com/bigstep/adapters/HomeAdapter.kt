package com.bigstep.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bigstep.ui.fragments.history.HistoryFragment
import com.bigstep.ui.fragments.video.VideoFragment
import com.bigstep.utils.AppConstants
class HomeAdapter(fm: FragmentManager?, private val tabsCount: Int) : FragmentStatePagerAdapter(fm!!) {


    override fun getItem(position: Int): Fragment {
        var frag: Fragment? = null
        if (position == AppConstants.TAB_VIDEO) {
            frag = VideoFragment.newInstance();
        } else if (position == AppConstants.TAB_HISTORY) {
            frag = HistoryFragment.newInstance();
        }
        return frag!!
    }

    override fun getCount(): Int {
        return tabsCount
    }
}