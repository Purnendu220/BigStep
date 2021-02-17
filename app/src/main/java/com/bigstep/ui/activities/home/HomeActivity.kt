package com.bigstep.ui.activities.home

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.bigstep.R
import com.bigstep.adapters.HomeAdapter
import com.bigstep.custom.BottomTabLayout
import com.bigstep.utils.AppConstants
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity(),BottomTabLayout.TabListener,
    ViewPager.OnPageChangeListener {
    lateinit var mContext: Context;
    lateinit var bottomTabLayout :BottomTabLayout;
    lateinit var homeAdapter:HomeAdapter;
    val TOTAL_TABS = 2;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mContext = this@HomeActivity
        setupBottomTabs()
        initViewPager()
    }
    private fun initViewPager(){
        homeAdapter = HomeAdapter(supportFragmentManager, TOTAL_TABS);
        viewPager.adapter = homeAdapter
        viewPager.addOnPageChangeListener(this)
        viewPager.offscreenPageLimit = TOTAL_TABS
    }
    private fun setupBottomTabs() {
        val tabList: MutableList<BottomTabLayout.Tab> = ArrayList<BottomTabLayout.Tab>()
        tabList.add(
            BottomTabLayout.Tab(
                AppConstants.TAB_VIDEO,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                ContextCompat.getColor(mContext, R.color.purple_500),
                ContextCompat.getColor(mContext, R.color.theme_light_text),
                mContext.getString(R.string.title_video),
                mContext.getString(R.string.title_video)
            )
        )
        tabList.add(
            BottomTabLayout.Tab(
                AppConstants.TAB_HISTORY,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                ContextCompat.getColor(mContext, R.color.purple_500),
                ContextCompat.getColor(mContext, R.color.theme_light_text),
                mContext.getString(R.string.title_history),
                mContext.getString(R.string.title_history)
            )
        )



        bottomTabLayout = BottomTabLayout()
        bottomTabLayout.setup(mContext, layoutBottomTabs, tabList, this)
        bottomTabLayout.setTab(AppConstants.TAB_VIDEO)
    }





    override fun onPositionChange(
        currentPosition: Int,
        tab: BottomTabLayout.Tab?,
        tabList: List<BottomTabLayout.Tab?>?
    ) {
        viewPager.currentItem = tab!!.id
    }

    override fun onReselection(
        currentPosition: Int,
        tab: BottomTabLayout.Tab?,
        tabList: List<BottomTabLayout.Tab?>?
    ) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        bottomTabLayout.setTab(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }


}