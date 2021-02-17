package com.bigstep.custom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bigstep.R
import java.util.*



class BottomTabLayout : View.OnClickListener {
    class Tab(
        id: Int,
        imageResActive: Int,
        imageResInactive: Int,
        textColorActive: Int,
        textColorInactive: Int,
        textActive: String,
        textInactive: String
    ) {
        var imageResActive: Int
        var imageResInactive: Int
        var textColorActive: Int
        var textColorInactive: Int
        var textActive: String
        var textInactive: String
        var isActive = false
        var id = 0

        init {
            this.id = id
            this.imageResActive = imageResActive
            this.imageResInactive = imageResInactive
            this.textColorActive = textColorActive
            this.textColorInactive = textColorInactive
            this.textActive = textActive
            this.textInactive = textInactive
        }
    }

    private inner class TabView(
        tab: Tab,
        linearLayout: LinearLayout,
        textView: TextView,
        imageView: ImageView,
        textViewNotificationCounter: TextView
    ) {
        var tab: Tab
        var linearLayout: LinearLayout
        var textView: TextView
        var imageView: ImageView
        var textViewNotificationCounter: TextView

        init {
            this.tab = tab
            this.linearLayout = linearLayout
            this.textView = textView
            this.imageView = imageView
            this.textViewNotificationCounter = textViewNotificationCounter
        }
    }

    interface TabListener {
        fun onPositionChange(
            currentPosition: Int,
            tab: Tab?,
            tabList: List<Tab?>?
        )

        fun onReselection(
            currentPosition: Int,
            tab: Tab?,
            tabList: List<Tab?>?
        )
    }

    private var tabList: List<Tab>? = null
    private var tabViewList: MutableList<TabView>? =
        null
    var currentlySelectedId = -1
        private set
    private var tabListener: TabListener? = null
    private var context: Context? = null
    fun setup(
        context: Context,
        viewGroup: ViewGroup,
        tabList: List<Tab>,
        tabListener: TabListener?
    ) {
        this.tabList = tabList
        this.context = context
        this.tabListener = tabListener
        tabViewList =
            ArrayList<TabView>(tabList.size)
        val dp =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                1f,
                context.resources.displayMetrics
            )
                .toInt()
        val iconSize =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                28f,
                context.resources.displayMetrics
            )
                .toInt()
        val tabSize = context.resources.getDimension(R.dimen.bottom_tab_bar_height).toInt()
        val linearLayoutMain = LinearLayout(context)
        linearLayoutMain.orientation = LinearLayout.HORIZONTAL
        linearLayoutMain.setBackgroundColor(Color.WHITE)
        linearLayoutMain.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tabSize)

//        TypedValue outValue = new TypedValue();
//        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        for (count in tabList.indices) {
            val tab: Tab = tabList[count]
            val linearLayout = LinearLayout(context)
            linearLayout.id = tab.id
            linearLayout.gravity = Gravity.CENTER
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.isClickable = true
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1F
            )
            //            linearLayout.setBackgroundResource(outValue.resourceId);
            val imageView = ImageView(context)
            imageView.layoutParams = FrameLayout.LayoutParams(iconSize, iconSize, Gravity.CENTER)
            imageView.setPadding(dp, dp * 4, dp, dp * 2)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            val textViewNotificationCounter = TextView(context)
            textViewNotificationCounter.layoutParams =
                FrameLayout.LayoutParams(13 * dp, 13 * dp, Gravity.RIGHT)
            textViewNotificationCounter.setBackgroundResource(R.drawable.notification_counter)
            textViewNotificationCounter.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8f)
            textViewNotificationCounter.setTextColor(Color.WHITE)
            textViewNotificationCounter.gravity = Gravity.CENTER
            textViewNotificationCounter.visibility = View.INVISIBLE
            textViewNotificationCounter.text = ""
            val frameLayout = FrameLayout(context)
            frameLayout.layoutParams = FrameLayout.LayoutParams(iconSize + dp * 8, iconSize)
            frameLayout.addView(imageView)
            frameLayout.addView(textViewNotificationCounter)
            val textView = TextView(context)
            textView.gravity = Gravity.CENTER
            textView.setTextColor(ContextCompat.getColor(context, R.color.theme_light_text))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11f)
            textView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL))
            linearLayout.addView(frameLayout)
            linearLayout.addView(textView)
            linearLayoutMain.addView(linearLayout)
            (tabViewList as ArrayList<TabView>).add(
                TabView(
                    tab,
                    linearLayout,
                    textView,
                    imageView,
                    textViewNotificationCounter
                )
            )
            linearLayout.setOnClickListener(this)
        }
        viewGroup.addView(linearLayoutMain)
    }

    fun setTab(id: Int) {
        for (tabView in tabViewList!!) {
            if (tabView.tab.id == id) {
                tabView.linearLayout.performClick()
            }
        }
    }

    override fun onClick(view: View) {
        for (tab in tabList!!) {
            if (tab.id == view.id) {
                if (currentlySelectedId != view.id) {
                    currentlySelectedId = view.id
                    setTabView(currentlySelectedId)
                    tabListener?.onPositionChange(currentlySelectedId, tab, tabList)
                } else {
                    tabListener?.onReselection(currentlySelectedId, tab, tabList)
                }
            }
        }
    }




    private fun setTabView(id: Int) {
        for (index in tabViewList!!.indices) {
            val tabView: TabView =
                tabViewList!![index]
            val tab: Tab = tabList!![index]
            if (tabView.tab.id == id) {
                tabView.imageView.setImageResource(tab.imageResActive)
                tabView.imageView.setColorFilter(tab.textColorActive)
                tabView.textView.text = tab.textActive
                tabView.textView.setTextColor(tab.textColorActive)
                tab.isActive = true
                tabView.linearLayout.animate().scaleXBy(.12f).scaleYBy(.12f).setDuration(100)
                    .setInterpolator(LinearInterpolator()).start()
            } else {
                tabView.imageView.setImageResource(tab.imageResInactive)
                tabView.imageView.setColorFilter(tab.textColorInactive)
                tabView.textView.text = tab.textInactive
                tabView.textView.setTextColor(tab.textColorInactive)
                tab.isActive = false
                tabView.linearLayout.animate().scaleX(1f).scaleY(1f).setDuration(200)
                    .setInterpolator(LinearInterpolator()).start()
            }
        }
    }
}