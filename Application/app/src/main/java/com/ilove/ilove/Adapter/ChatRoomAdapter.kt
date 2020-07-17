package com.ilove.ilove.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.MainActivity.ChatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_chatroom.view.*
import kotlin.concurrent.timer

class ChatRoomAdapter(val context: Context, val chatRoomList: ArrayList<ChatRoomItem>) :
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return chatRoomList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chatroom, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatRoomAdapter.ViewHolder, position: Int) {
        var chatRoom = chatRoomList[position]

        var roomTitle = chatRoom.roomTitle.split("&")

        if(UserInfo.NICKNAME == roomTitle[0])
            holder.itemView.text_title.text=roomTitle[1]
        else
            holder.itemView.text_title.text=roomTitle[0]

        if(chatRoom.lastChat!="null" && chatRoom.lastChatTime!="null") {
            val time = chatRoom.lastChatTime!!.split(" ")[1].split(":")
            val hour = time[0].toInt()
            val min = time[1]
            var timeStr = ""
            if (hour < 12)
                timeStr = "오전 ${hour}:${min}"
            else
                timeStr = "오후 ${hour - 12}:${min}"
            holder.itemView.text_last_chat_time.text = timeStr

            holder.itemView.text_last_chat.text = "${chatRoom.lastChat}"
        }
        else {
            holder.itemView.text_last_chat_time.text=""
            holder.itemView.text_last_chat.text=""
        }
        holder.view.setOnClickListener {
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("room", chatRoom)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: String) {

        }
    }

    fun insertLastChat(roomId: String, content: String, time: String) {
        for (i in 0..chatRoomList.size - 1) {
            if (chatRoomList[i].roomId.toString() == roomId) {
                chatRoomList[i].lastChat = content
                chatRoomList[i].lastChatTime = time
                break
            }
        }
    }


    fun sortByLastChat() {
        chatRoomList.sortByDescending { selector(it) }
    }

    fun selector(room: ChatRoomItem): String = room.lastChatTime!!

    fun clear(){
        chatRoomList.clear()
    }
}