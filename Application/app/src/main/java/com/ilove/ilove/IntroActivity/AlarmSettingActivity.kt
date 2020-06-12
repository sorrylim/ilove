package com.ilove.ilove.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_alarm_setting.*

class AlarmSettingActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)

        toolbarBinding(toolbar_alarmsetting, "알림설정", true)
    }
}