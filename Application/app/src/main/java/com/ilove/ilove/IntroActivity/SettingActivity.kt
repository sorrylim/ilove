package com.ilove.ilove.IntroActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.UserInfo
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
        /*text_settinglogout.setOnClickListener {
            var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            var editor=pref.edit()
            editor.putString("ID", "")
                    .putString("PW", "")
                    .putString("NICKNAME", "")
                    .putString("BIRTHDAY", "")
                    .putString("PHONE", "")
                    .putString("GENDER", "")
                    .putString("AUTHORITY", "")
                    .putInt("BLOCKING", 0)
                    .putInt("ALARMLIKE", 0)
                    .putInt("ALARMMEET", 0)
                    .putInt("ALARMCHECKPROFILE",0)
                    .putInt("ALARMUPDATEPROFILE", 0)
                    .putInt("ALARMMESSAGE",0)
                    .putInt("CANDYCOUNT", 0)
                    .putInt("LIKECOUNT",0)
                    .putInt("MESSAGETICKET", 0)
                    .putInt("VIP",0)
                    .apply()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }*/



    }
}