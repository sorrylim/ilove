package com.ilove.ilove.IntroActivity

import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity: PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        toolbarBinding(toolbar_review, "회원가입", true)
    }
}