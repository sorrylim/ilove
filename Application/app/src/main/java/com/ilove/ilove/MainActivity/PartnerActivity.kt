package com.ilove.ilove.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_partner.*
import kotlinx.android.synthetic.main.item_partner.view.*
import org.json.JSONObject

class PartnerActivity : PSAppCompatActivity() {

    var profileImageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner)

        var intent = intent
        var userNickname = intent.getStringExtra("userNickname")
        var userId = intent.getStringExtra("userId")

        image_partnerprofile.setClipToOutline(true)
        toolbarCenterBinding(toolbar_partner, userNickname, true)

        VolleyService.getProfileImageReq(userId, this, {success->
            profileImageList.clear()
            var array = success
            for(i in 0..array.length()-1) {
                var json = array[i] as JSONObject
                profileImageList.add(json.getString("image"))
            }
        })
    }
}