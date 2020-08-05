package com.ilove.ilove.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val psDialog = PSDialog(this)

        text_start.setOnClickListener {
            psDialog.setPermissionDialog()
            psDialog.show()
        }
    }
}