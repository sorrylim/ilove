package com.ilove.ilove.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Object.Blur
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatItem
import com.ilove.ilove.Object.ImageManager
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_chatroom.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter(private val activity: Activity) : BaseAdapter() {
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

    @SuppressLint("ResourceAsColor")
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
            if (hour != 12)
                timeStr = "오후 ${hour - 12}:${min}"
            else
                timeStr = "오후 ${hour}:${min}"
        }

        if (!item.isMyChat!!) {

            view = inflater.inflate(R.layout.item_chat, parent, false)

            var textContent = view!!.findViewById(R.id.text_content) as TextView
            var textTime = view.findViewById(R.id.text_time) as TextView

            var textSpeaker = view!!.findViewById(R.id.text_speaker) as TextView
            var imageChatPartner = view.findViewById(R.id.image_chatpartner) as ImageView
            Glide.with(view)
                .load(item.chatPartnerImage).transition(DrawableTransitionOptions().crossFade())
                .apply(RequestOptions().circleCrop())
                .apply(RequestOptions().override(640, 640))
                .into(imageChatPartner)

            textContent.text = item.chatContent
            textTime.text = timeStr

            if (chatList.count() > 1 && position > 0) {
                var beforeItem = chatList[position - 1]
                if (beforeItem.isMyChat!!) textSpeaker!!.text = item.chatSpeakerNickname
                else {
                    textSpeaker.visibility = View.GONE
                    imageChatPartner.visibility = View.GONE

                    var constraintSet = ConstraintSet()
                    constraintSet.clone(view.findViewById<ConstraintLayout>(R.id.layout_chat))
                    constraintSet.connect(
                        R.id.text_content,
                        ConstraintSet.TOP,
                        R.id.layout_chat,
                        ConstraintSet.TOP,
                        12
                    )
                    constraintSet.applyTo(view.findViewById(R.id.layout_chat))
                }
            }

            if (UserInfo.MESSAGETICKET == 0) {
                if (Build.VERSION.SDK_INT >= 11) {
                    textContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                }
                var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var gpsTracker = GpsTracker(context as Activity)
                if(UserInfo.VIP!="0000-00-00 00:00:00") {
                    if (gpsTracker.timeDiffValue(
                            simpleDateFormat.parse(UserInfo.VIP).getTime()
                        ) < 0
                    ) {
                        var filter = BlurMaskFilter(20F, BlurMaskFilter.Blur.NORMAL)
                        textContent.paint.setMaskFilter(filter)
                        var btnBuyCandy = view.findViewById<Button>(R.id.btn_buycandy)
                        btnBuyCandy.visibility = View.VISIBLE
                        btnBuyCandy.setOnClickListener {
                            var psDialog = PSDialog(activity)
                            psDialog.setMessageTicketDialog()
                            psDialog.show()
                        }
                    }
                }
                else {
                    var filter = BlurMaskFilter(20F, BlurMaskFilter.Blur.NORMAL)
                    textContent.paint.setMaskFilter(filter)
                    var btnBuyCandy = view.findViewById<Button>(R.id.btn_buycandy)
                    btnBuyCandy.visibility = View.VISIBLE
                    btnBuyCandy.setOnClickListener {
                        var psDialog = PSDialog(activity)
                        psDialog.setMessageTicketDialog()
                        psDialog.show()
                    }
                }
            }
        } else {
            view = inflater.inflate(R.layout.item_my_chat, parent, false)

            var textContent = view!!.findViewById(R.id.text_content) as TextView
            var textTime = view.findViewById(R.id.text_time) as TextView
            var textUnreadCount = view.findViewById(R.id.text_unread_count) as TextView

            if (item.unreadCount!!.toInt() > 0) textUnreadCount.text = item.unreadCount
            else textUnreadCount.text = ""
            textContent.text = item.chatContent
            textTime.text = timeStr

            if (item.unreadCount!!.toInt() > 0) {
                textUnreadCount.text = item.unreadCount
                textUnreadCount.setTextColor(Color.rgb(255, 200, 0))
            } else {
                textUnreadCount.text = "읽음"
                textUnreadCount.setTextColor(R.color.colorMdGrey_500)
            }
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
            if (it.chatSpeaker == chatSpeaker && it.chatTime == chatTime) {
                it.unreadCount = 0.toString()
            }
        }
    }
}
