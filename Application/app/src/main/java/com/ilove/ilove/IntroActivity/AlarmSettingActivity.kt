package com.ilove.ilove.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_alarm_setting.*

class AlarmSettingActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)

        toolbarBinding(toolbar_alarmsetting, "알림설정", true)

        settingSwitch(switch_alarmupprofile,UserInfo.ALARMUPDATEPROFILE)
        settingSwitch(switch_alarmcheckprofile,UserInfo.ALARMCHECKPROFILE)
        settingSwitch(switch_alarmlike,UserInfo.ALARMLIKE)
        settingSwitch(switch_alarmmessage,UserInfo.ALARMMESSAGE)
        settingSwitch(switch_alarmmeet,UserInfo.ALARMMEET)

        switch_alarmupprofile.setOnCheckedChangeListener { compoundButton, b ->

        }

        switch_alarmcheckprofile.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("visit",b)
        }

        switch_alarmlike.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("like",b)
        }

        switch_alarmmessage.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("chat",b)
        }

        switch_alarmmeet.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("meet",b)
        }
    }

    fun settingSwitch(switch: Switch, state: Int){
        if(state==1) switch.isChecked=true
        else switch.isChecked=false
    }


    fun updateAlarm(alarmType:String,alarmState:Boolean){
        VolleyService.updateAlarm(UserInfo.ID,alarmType,alarmState,this)
    }
}