package com.ilove.ilove.Fragment

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Adapter.UserListAdapter
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.UserList
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import org.json.JSONObject

class ListFragment : Fragment() {
    var userList = ArrayList<UserList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        val partnerListRV : RecyclerView = rootView.findViewById(R.id.rv_userlist)

        if(UserInfo.GENDER == "M") {
            VolleyService.getUserListReq("F", UserInfo.ID, activity!!, {success->
                userList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var partner = UserList(json.getString("user_id"), json.getString("user_nickname"),
                    json.getString("user_birthday"), json.getString("user_city"), json.getString("user_recentgps"),
                    json.getString("user_introduce"), json.getString("user_certification"), json.getInt("like"), json.getInt("meet"),
                        ArrayList<Bitmap>())
                    userList.add(partner)
                }
                partnerListRV.setHasFixedSize(true)
                partnerListRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                partnerListRV.adapter = UserListAdapter(activity!!, userList)
            })
        }
        else if(UserInfo.GENDER == "F") {
            VolleyService.getUserListReq("M", UserInfo.ID, activity!!, {success->
                userList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var partner = UserList(json.getString("user_id"), json.getString("user_nickname"),
                        json.getString("user_birthday"), json.getString("user_city"), json.getString("user_recentgps"),
                        json.getString("user_introduce"), json.getString("user_certification"),
                        json.getInt("like"), json.getInt("meet"), ArrayList<Bitmap>())
                    userList.add(partner)
                }
                partnerListRV.setHasFixedSize(true)
                partnerListRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                partnerListRV.adapter = UserListAdapter(activity!!, userList)
            })
        }



        return rootView
    }

}