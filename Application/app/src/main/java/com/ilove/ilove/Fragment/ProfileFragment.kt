package com.ilove.ilove.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.IntroActivity.ItemActivity
import com.ilove.ilove.IntroActivity.SettingActivity
import com.ilove.ilove.R

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        val settingBtn : TextView = rootView.findViewById(R.id.text_mypagesetting)
        val editProfileBtn: Button = rootView.findViewById(R.id.btn_editprofile)
        val itemManagement : TextView = rootView.findViewById(R.id.text_mypageitem)
        var nickName: TextView = rootView.findViewById(R.id.text_mypagenickname)

        nickName.text = UserInfo.NICKNAME

        editProfileBtn.setOnClickListener {
            
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