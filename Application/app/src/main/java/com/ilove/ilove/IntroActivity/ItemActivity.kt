package com.ilove.ilove.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.MainActivity.VipActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_item.*
import java.text.SimpleDateFormat
import java.util.*

class ItemActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        var psDialog = PSDialog(this)
        val gpsTracker = GpsTracker(this)

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var vipDate : Date = simpleDateFormat.parse(UserInfo.VIP)
        var leftDays = ""

        if(gpsTracker.timeDiffValue(vipDate.time) > 0) {
            leftDays = "0"
        }
        else {
            leftDays = ((gpsTracker.timeDiffValue(vipDate.time) * -1) / 86400 + 1).toString()
        }

        if(UserInfo.VIP == "0000-00-00 00:00:00") {
            leftDays = "0일"
        }
        else {
            var list = UserInfo.VIP.split(" ")
            leftDays = list.get(0) + "\n" + list.get(1) + " 까지"
        }


        toolbarCenterBinding(toolbar_item, "아이템 관리", true)

        text_candycount.text = "내사탕 ${UserInfo.CANDYCOUNT}개"
        text_vipticket.text = leftDays
        text_messageticket.text = "${UserInfo.MESSAGETICKET}일"
        text_liketicket.text = "${UserInfo.LIKECOUNT}개"

        btn_guidevip.setOnClickListener {
            var intent = Intent(this, VipActivity::class.java)
            startActivity(intent)
        }

        layout_message.setOnClickListener {
            psDialog.setMessageTicketDialog()
            psDialog.show()
        }

        btn_chargecandy.setOnClickListener {
            var intent = Intent(this, ChargeCandyActivity::class.java)
            startActivity(intent)
        }



    }
}