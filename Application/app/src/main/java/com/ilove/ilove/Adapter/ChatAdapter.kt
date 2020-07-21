package com.ilove.ilove.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginLeft
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatItem
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_partnerlist.view.*
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
        else {
            if(hour!=12)
                timeStr = "오후 ${hour - 12}:${min}"
            else
                timeStr = "오후 ${hour}:${min}"
        }

        if (!item.isMyChat!!) {
            view = inflater.inflate(R.layout.item_chat, parent, false)

            var textSpeaker = view!!.findViewById(R.id.text_speaker) as TextView
            var textContent = view!!.findViewById(R.id.text_content) as TextView
            var textTime = view.findViewById(R.id.text_time) as TextView
            var textUnreadCount = view.findViewById(R.id.text_unread_count) as TextView
            var imageChatPartner = view.findViewById(R.id.image_chatpartner) as ImageView
            Glide.with(view)
                .load(item.chatPartnerImage)
                .apply(RequestOptions().circleCrop())
                .apply(RequestOptions().override(640,640))
                .into(imageChatPartner)

            if(chatList.count()>1 && position > 0) {
                var beforeItem = chatList[position - 1]
                if(beforeItem.isMyChat!!) textSpeaker!!.text = item.chatSpeakerNickname
                else {
                    textSpeaker.visibility=View.GONE
                    imageChatPartner.visibility=View.GONE

                    var constraintSet=ConstraintSet()
                    //constraintSet.clone(context,R.id.layout_chat)
                    constraintSet.clone(view.findViewById<ConstraintLayout>(R.id.layout_chat))
                    //constraintSet.setMargin(view.findViewById(R.id.text_content),ConstraintSet.TOP,8)
                    constraintSet.connect(R.id.text_content,ConstraintSet.TOP,R.id.layout_chat,ConstraintSet.TOP,12)
                    constraintSet.applyTo(view.findViewById(R.id.layout_chat))
                }

            }

            if(item.unreadCount!!.toInt()>0) textUnreadCount.text = item.unreadCount
            else textUnreadCount.text = ""
            textContent.text = item.chatContent
            textTime.text = timeStr
        } else {
            view = inflater.inflate(R.layout.item_my_chat, parent, false)

            var textContent = view!!.findViewById(R.id.text_content) as TextView
            var textTime = view.findViewById(R.id.text_time) as TextView
            var textUnreadCount = view.findViewById(R.id.text_unread_count) as TextView

            if(item.unreadCount!!.toInt()>0) textUnreadCount.text = item.unreadCount
            else textUnreadCount.text = ""
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

        chatList.add(chatItem)
    }

    fun clear() {
        chatList.clear()
    }

    fun readChat(chatSpeaker: String, chatTime: String) {
        chatList.forEach {
            if(it.chatSpeaker==chatSpeaker && it.chatTime==chatTime){
                it.unreadCount=0.toString()
            }
        }
    }
}
