package com.ilove.ilove.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.IntroActivity.*
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_partnerlist.view.*
import org.json.JSONObject

class ProfileFragment(titleText: TextView) : Fragment() {
    var titleText : TextView = titleText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        val settingBtn : TextView = rootView.findViewById(R.id.text_mypagesetting)
        val editProfileBtn: Button = rootView.findViewById(R.id.btn_editprofile)
        val itemManagement : TextView = rootView.findViewById(R.id.text_mypageitem)
        val inquireBtn : TextView = rootView.findViewById(R.id.text_mypageinquire)
        var mainProfileImage : ImageView = rootView.findViewById(R.id.image_mypageprofile)
        var nickName: TextView = rootView.findViewById(R.id.text_mypagenickname)
        var mainProfile : String = ""


        inquireBtn.setOnClickListener {
            var intent = Intent(activity!!, InquireActivity::class.java)
            startActivity(intent)
        }

        VolleyService.getProfileImageReq(UserInfo.ID, activity!!, {success->
            var array = success
            for(i in 0..array.length()-1) {
                var json = array[i] as JSONObject
                if(json.getString("image_usage") == "mainprofile") {
                    mainProfile = json.getString("image")
                }
            }

            Glide.with(activity!!)
                .load(mainProfile).transition(DrawableTransitionOptions().crossFade()).apply(
                    RequestOptions().override(100, 100))
                .into(mainProfileImage)

            mainProfileImage.setClipToOutline(true)
        })

        nickName.text = UserInfo.NICKNAME

        editProfileBtn.setOnClickListener {
                 var intent = Intent(activity, EditProfileActivity::class.java)
                startActivity(intent)
        }

        settingBtn.setOnClickListener {
            var intent = Intent(activity!!, SettingActivity::class.java)
            startActivity(intent)
        }

        itemManagement.setOnClickListener {
            var intent = Intent(activity!!, ItemActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

}