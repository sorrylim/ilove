package com.ilove.ilove.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbarBinding(toolbar_setting, "설정", true)

        text_settingalarm.setOnClickListener {
            var intent = Intent(this, AlarmSettingActivity::class.java)
            startActivity(intent)
        }
        text_settinglogout.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }



    }
}