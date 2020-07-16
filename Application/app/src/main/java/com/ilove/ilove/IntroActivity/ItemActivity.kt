package com.ilove.ilove.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.MainActivity.VipActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        var psDialog = PSDialog(this)

        toolbarCenterBinding(toolbar_item, "아이템 관리", true)

        text_candycount.text = "내사탕 ${UserInfo.CANDYCOUNT}개"
        text_vipticket.text = "${UserInfo.VIP}일"
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


    }
}