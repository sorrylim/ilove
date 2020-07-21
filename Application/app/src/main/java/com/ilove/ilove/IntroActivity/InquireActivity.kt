package com.ilove.ilove.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ilove.ilove.Adapter.InquireAdapter
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_inquire.*

class InquireActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquire)

        toolbarBinding(toolbar_inquire, "1:1문의", true)

        val tabLayoutTextArray = arrayOf("문의하기","문의내역")

        viewpager_inquire.adapter = InquireAdapter(this)

        TabLayoutMediator(tabLayout_inquire, viewpager_inquire) {tab, position ->
            tab.text = tabLayoutTextArray[position]
            viewpager_inquire.setCurrentItem(tab.position, true)
        }.attach()
    }
}