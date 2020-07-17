package com.ilove.ilove.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatItem
import com.ilove.ilove.R
import kotlin.collections.ArrayList


class ChatAdapter : BaseAdapter() {
    private var chatList = ArrayList<ChatItem>()
    override fun getCount(): Int {
        return chatList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return chatList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context: Context? = parent?.context

        var item = chatList[position]

        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var timeStr = ""
        val time = item.chatTime!!.split(" ")[1].split(":")
        val hour = time[0].toInt()
        val min = time[1]
        if (hour < 12)
            timeStr = "오전 ${hour}:${min}"
        else
            timeStr = "오후 ${hour - 12}:${min}"

        if (!item.isMyChat!!) {
            view = inflater.inflate(R.layout.item_chat, parent, false)
            var textSpeaker = view!!.findViewById(R.id.text_speaker) as TextView
            textSpeaker!!.text = item.chatSpeakerNickname
            var textContent = view!!.findViewById(R.id.text_content) as TextView
            var textTime = view.findViewById(R.id.text_time) as TextView

            textContent.text = item.chatContent
            textTime.text = timeStr
        } else {
            view = inflater.inflate(R.layout.item_my_chat, parent, false)

            var textContent = view!!.findViewById(R.id.text_content) as TextView
            var textTime = view.findViewById(R.id.text_time) as TextView

            textContent.text = item.chatContent
            textTime.text = timeStr
        }

        return view
    }

    fun addItem(
        chatItem: ChatItem
    ) {
        if (chatItem.chatSpeaker == UserInfo.ID)
            chatItem.isMyChat = true
        else
            chatItem.isMyChat = false

        Log.d("test",chatItem.toString())

        chatList.add(chatItem)
    }

    fun clear() {
        chatList.clear()
    }
}
