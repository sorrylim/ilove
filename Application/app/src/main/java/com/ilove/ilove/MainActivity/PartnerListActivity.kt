package com.ilove.ilove.MainActivity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Adapter.PartnerListAdapter
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.Partner
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_partner_list.*
import org.json.JSONObject

class PartnerListActivity : PSAppCompatActivity() {

    var partnerList = ArrayList<Partner>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_list)

        var intent = intent
        var listType:String = intent.getStringExtra("listType")

        toolbarBinding(toolbar_partnerlist, listType, true)

        if(listType == "내가 좋아요를 보낸 이성")
        {
            VolleyService.getSendLikeUserReq("ljs", this, {success->
                partnerList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var partner = Partner(json.getString("user_id"), json.getString("user_nickname"),
                    json.getString("user_birthday"), json.getString("user_city"), json.getString("like_date"),
                    0, 0, ArrayList<Bitmap>())
                    partnerList.add(partner)
                }
                Log.d("test", partnerList.size.toString())
                rv_partnerlist.setHasFixedSize(true)
                rv_partnerlist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                rv_partnerlist.adapter = PartnerListAdapter(this, partnerList)
            })
        }
        else if(listType == "나에게 좋아요를 보낸 이성")
        {
            VolleyService.getReceiveLikeUserReq(UserInfo.ID, this, {success->

            })
        }
        else if(listType == "서로 좋아요가 연결된 이성")
        {
            VolleyService.getEachLikeUser1Req(UserInfo.ID, this, {success->
                VolleyService.getEachLikeUser2Req(UserInfo.ID, this, {success->

                })
            })
        }
    }
}