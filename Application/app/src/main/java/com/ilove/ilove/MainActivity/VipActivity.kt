package com.ilove.ilove.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_vip.*

class VipActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip)

        toolbarBinding(toolbar_guidevip, "정회원 안내", true)

        var dialog = PSDialog(this)

        btn_requestvip.setOnClickListener{
            dialog.setBuyVipDialog()
            dialog.show()
        }
    }
}