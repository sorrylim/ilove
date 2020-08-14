package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.MainActivity.ChatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_chatroom.view.*
import kotlinx.android.synthetic.main.item_newuser.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
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
            else {
                if(hour!=12)
                    timeStr = "오후 ${hour - 12}:${min}"
                else
                    timeStr = "오후 ${hour}:${min}"
            }
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

        Glide.with(holder.itemView)
            .load(chatRoom.imageUrl).apply(RequestOptions().fitCenter()).transition(
                DrawableTransitionOptions().crossFade())
            .apply(RequestOptions().override(640,640))
            .into(holder.itemView.image_chatroom)
        holder.itemView.image_chatroom.setClipToOutline(true)

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