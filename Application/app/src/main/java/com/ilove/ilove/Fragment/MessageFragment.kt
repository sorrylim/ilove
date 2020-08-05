package com.ilove.ilove.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Adapter.ChatRoomAdapter
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import org.json.JSONArray
import org.json.JSONObject

class MessageFragment(titleText: TextView) : Fragment() {

    companion object{
        var handler: Handler? = null
    }

    var titleText : TextView = titleText
    var chatRoomRV : RecyclerView? = null
    var chatRoomList = ArrayList<ChatRoomItem>()
    var chatRoomAdapter: ChatRoomAdapter? = null
    var nothingMessageText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_message, container, false)
        chatRoomRV = rootView.findViewById<RecyclerView>(R.id.rv_chatroom)
        chatRoomAdapter = ChatRoomAdapter(activity!!, chatRoomList!!)
        nothingMessageText = rootView.findViewById(R.id.text_messagenothing)

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    0 -> {
                        refreshList(chatRoomRV!!, chatRoomList, chatRoomAdapter!!)
                    }
                }
            }
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        Log.d("test","resume refreshList")
        refreshList(chatRoomRV!!, chatRoomList, chatRoomAdapter!!)
    }

    private fun refreshList(
        chatRoomRV: RecyclerView,
        chatRoomList: java.util.ArrayList<ChatRoomItem>,
        chatRoomAdapter: ChatRoomAdapter
    ) {

        chatRoomAdapter.clear()

        VolleyService.getMyChatRoom(UserInfo.ID, activity!!, { success ->
            var array = success

            if(array.length()==0){
                nothingMessageText!!.visibility = View.VISIBLE
            }
            else {
                nothingMessageText!!.visibility = View.GONE
                for (i in 0..array.length() - 1) {
                    var json = array[i] as JSONObject

                    var partnerId = ""

                    if (UserInfo.ID == json.getString("room_maker")) partnerId =
                        json.getString("room_partner")
                    else partnerId = json.getString("room_maker")

                    VolleyService.getProfileImageReq(
                        partnerId,
                        activity!!,
                        { success ->
                            var imageJson = success[0] as JSONObject
                            addChatRoom(json, imageJson.getString("image"))
                            chatRoomAdapter.sortByLastChat()
                            chatRoomRV.setHasFixedSize(true)
                            chatRoomRV.layoutManager =
                                LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
                            chatRoomRV.adapter = chatRoomAdapter
                        })
                }
            }
        })
    }

    fun addChatRoom(json: JSONObject, imageUrl: String) {

        chatRoomList.add(
            ChatRoomItem(
                json.getString("room_id"),
                json.getString("room_maker"),
                json.getString("room_partner"),
                json.getString("room_title"),
                json.getString("chat_content").split("|")[0],
                json.getString("chat_time"),
                imageUrl
            )
        )
    }
}
