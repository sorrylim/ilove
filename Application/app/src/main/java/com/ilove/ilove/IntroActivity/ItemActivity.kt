package com.ilove.ilove.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.MainActivity.VipActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        toolbarCenterBinding(toolbar_item, "아이템 관리", true)

        btn_guidevip.setOnClickListener {
            var intent = Intent(this, VipActivity::class.java)
            startActivity(intent)
        }

    }
}