package com.ilove.ilove.Fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.MainActivity.PartnerListActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.fragment_channel.*
import kotlinx.android.synthetic.main.fragment_channel.view.*
import kotlinx.android.synthetic.main.fragment_channel.view.layout_sendlike

class ChannelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_channel, container, false)
        var sendLikeCount = rootView.text_sendlikecount
        var receiveLikeCount = rootView.text_receivelikecount
        var eachLikeCount = rootView.text_eachlikecount
        var sendMeetCount = rootView.text_sendmeetcount
        var receiveMeetCount = rootView.text_receivemeetcount
        var eachMeetCount = rootView.text_eachmeetcount
        var sendLikeBtn = rootView.layout_sendlike

        VolleyService.getExpressionCountReq("ljs", activity!!, {success->
            var json = success
            sendLikeCount.text = json!!.getInt("send_like").toString() + "명"
            receiveLikeCount.text = json!!.getInt("receive_like").toString() + "명"
            eachLikeCount.text = json!!.getInt("each_like").toString() + "명"
            sendMeetCount.text = json!!.getInt("send_meet").toString() + "명"
            receiveMeetCount.text = json!!.getInt("receive_meet").toString() + "명"
            eachMeetCount.text = json!!.getInt("each_meet").toString() + "명"
        })

        sendLikeBtn.setOnClickListener {
            var intent = Intent(activity!!, PartnerListActivity::class.java)
            intent.putExtra("listType", "내가 좋아요를 보낸 이성")
            startActivity(intent)
        }

        /*var dialog = Dialog(activity!!)
        dialog.setContentView(R.layout.dialog_guidelike)
        dialog.show()*/


        return rootView
    }

}