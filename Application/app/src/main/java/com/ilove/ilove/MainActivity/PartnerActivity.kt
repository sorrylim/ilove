package com.ilove.ilove.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_partner.*
import kotlinx.android.synthetic.main.item_partner.view.*

class PartnerActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner)

        var intent = intent
        var userNickname = intent.getStringExtra("userNickname")
        var userId = intent.getStringExtra("userId")

        image_partnerprofile.setClipToOutline(true)

        //Glide.with(this).load(userImage).apply(RequestOptions().centerCrop()).into(image_partnerprofile)
        toolbarCenterBinding(toolbar_partner, userNickname, true)
    }
}