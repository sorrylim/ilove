package com.ilove.ilove.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Adapter.ChatRoomAdapter
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatItem
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import org.json.JSONObject

class MessageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_message, container, false)
        var chatRoomRV =rootView.findViewById<RecyclerView>(R.id.rv_chatroom)
        var chatRoomList = ArrayList<ChatRoomItem>()
        var chatRoomAdapter = ChatRoomAdapter(activity!!, chatRoomList!!)


        VolleyService.getMyChatRoom(UserInfo.ID,activity!!,{success ->

            var array = success

            for(i in 0..array.length()-1){
                var json=array[i] as JSONObject

                chatRoomList.add(
                    ChatRoomItem(
                        json.getString("room_id"),
                        json.getString("room_maker"),
                        json.getString("room_partner"),
                        json.getString("room_title"),
                        json.getString("chat_content"),
                        json.getString("chat_time")
                    )
                )
            }

            chatRoomRV.setHasFixedSize(true)
            chatRoomRV.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
            chatRoomAdapter.sortByLastChat()
            chatRoomRV.adapter = chatRoomAdapter
        })

        return rootView
    }
}