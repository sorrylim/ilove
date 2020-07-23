package com.ilove.ilove.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ilove.ilove.Adapter.InquireAdapter
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Fragment.InquireListFragment
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_inquire.*

class InquireActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquire)

        toolbarBinding(toolbar_inquire, "1:1문의", true)

        val tabLayoutTextArray = arrayOf("문의하기","문의내역")

        var vadapter = InquireAdapter(this)

        viewpager_inquire.adapter = vadapter

        viewpager_inquire.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(position == 1) {
                }
            }
        })

        TabLayoutMediator(tabLayout_inquire, viewpager_inquire) {tab, position ->
            tab.text = tabLayoutTextArray[position]
            viewpager_inquire.setCurrentItem(tab.position, true)
        }.attach()
    }
}