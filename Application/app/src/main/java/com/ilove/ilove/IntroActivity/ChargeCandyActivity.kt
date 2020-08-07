package com.ilove.ilove.IntroActivity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.ilove.ilove.Adapter.ChargeCandyAdapter
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_charge_candy.*

class ChargeCandyActivity : PSAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_candy)


        var titleText = "내 사탕 ${UserInfo.CANDYCOUNT}개"

        var ssb : SpannableStringBuilder = SpannableStringBuilder(titleText)
        ssb.setSpan(ForegroundColorSpan(Color.parseColor("#FFA500")), 5, titleText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.setSpan(RelativeSizeSpan(0.7f), 0 ,4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        toolbarBinding(toolbar_chargecandy, "", true)
        text_maintoolbar.setText(ssb)

        var candyCount : ArrayList<UserItem.Candy> = arrayListOf(
            UserItem.Candy(10, "추후결정"),  UserItem.Candy(20, "추후결정"), UserItem.Candy(20, "추후결정"), UserItem.Candy(50, "추후결정"), UserItem.Candy(100, "추후결정"),UserItem.Candy(200, "추후결정"), UserItem.Candy(400, "추후결정"))

        rv_chargecandy.setHasFixedSize(true)
        rv_chargecandy.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_chargecandy.adapter = ChargeCandyAdapter(this, candyCount)


    }
}