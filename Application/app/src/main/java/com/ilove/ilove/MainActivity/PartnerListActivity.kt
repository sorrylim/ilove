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

        when(listType) {
            "내가 좋아요를 보낸 이성" -> sendUserList("like")
            "나에게 좋아요를 보낸 이성" -> receiveUserList("like")
            "서로 좋아요가 연결된 이성" -> eachUserList("like")
            "내가 만나고 싶은 그대" -> sendUserList("meet")
            "나를 만나고 싶어하는 그대" -> receiveUserList("meet")
            "서로 연락처를 주고받은 그대" -> eachUserList("meet")
            "내 프로필을 확인한 사람" -> visitUserList("profile")
            "내 스토리를 확인한 사람" -> visitUserList("story")
        }

    }

    fun sendUserList(expressionType: String) {
        VolleyService.getSendUserReq(UserInfo.ID, expressionType, this, {success->
            partnerList.clear()
            var array = success
            for(i in 0..array!!.length()-1) {
                var json = array[i] as JSONObject
                var partner = Partner(json.getString("user_id"), json.getString("user_nickname"),
                    json.getString("user_birthday"), json.getString("user_city"), json.getString("expression_date"),
                    json.getInt("like"), json.getInt("meet"), ArrayList<Bitmap>())
                partnerList.add(partner)
            }
            rv_partnerlist.setHasFixedSize(true)
            rv_partnerlist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rv_partnerlist.adapter = PartnerListAdapter(this, partnerList)
        })
    }

    fun receiveUserList(expressionType: String) {
        VolleyService.getReceiveUserReq(UserInfo.ID, expressionType, this, {success->
            partnerList.clear()
            var array = success
            for(i in 0..array!!.length()-1) {
                var json = array[i] as JSONObject
                var partner = Partner(json.getString("user_id"), json.getString("user_nickname"),
                    json.getString("user_birthday"), json.getString("user_city"), json.getString("expression_date"),
                    json.getInt("like"), json.getInt("meet"), ArrayList<Bitmap>())
                partnerList.add(partner)
            }
            rv_partnerlist.setHasFixedSize(true)
            rv_partnerlist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rv_partnerlist.adapter = PartnerListAdapter(this, partnerList)
        })
    }

    fun eachUserList(expressionType: String) {
        VolleyService.getEach1UserReq(UserInfo.ID, expressionType, this, {success->
            partnerList.clear()
            var array = success
            for(i in 0..array!!.length()-1) {
                var json = array[i] as JSONObject
                var partner = Partner(json.getString("user_id"), json.getString("user_nickname"),
                    json.getString("user_birthday"), json.getString("user_city"), json.getString("expression_date"),
                    json.getInt("like"), json.getInt("meet"), ArrayList<Bitmap>())
                partnerList.add(partner)
            }
            VolleyService.getEach2UserReq("ljs", expressionType, this, {success->
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var partner = Partner(json.getString("user_id"), json.getString("user_nickname"),
                        json.getString("user_birthday"), json.getString("user_city"), json.getString("expression_date"),
                        json.getInt("like"), json.getInt("meet"), ArrayList<Bitmap>())
                    partnerList.add(partner)
                }
                rv_partnerlist.setHasFixedSize(true)
                rv_partnerlist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                rv_partnerlist.adapter = PartnerListAdapter(this, partnerList)
            })
        })
    }

    fun visitUserList(visitType:String) {
        VolleyService.getVisitUserReq(UserInfo.ID, visitType, this, {success->
            partnerList.clear()
            var array = success
            for(i in 0..array!!.length()-1) {
                var json = array[i] as JSONObject
                var partner = Partner(json.getString("user_id"), json.getString("user_nickname"),
                    json.getString("user_birthday"), json.getString("user_city"), json.getString("visit_date"),
                    json.getInt("like"), json.getInt("meet"), ArrayList<Bitmap>())
                partnerList.add(partner)
            }
            rv_partnerlist.setHasFixedSize(true)
            rv_partnerlist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rv_partnerlist.adapter = PartnerListAdapter(this, partnerList)
        })
    }
}