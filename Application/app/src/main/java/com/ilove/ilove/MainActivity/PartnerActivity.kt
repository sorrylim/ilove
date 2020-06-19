package com.ilove.ilove.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_partner.*

class PartnerActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner)

        var intent = intent
        var userNickname = intent.getStringExtra("userNickname")

        toolbarCenterBinding(toolbar_partner, userNickname, true)
    }
}