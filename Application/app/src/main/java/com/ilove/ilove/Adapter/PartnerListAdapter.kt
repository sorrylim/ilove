package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.Item.Partner
import com.ilove.ilove.MainActivity.ChatActivity
import com.ilove.ilove.MainActivity.PartnerActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_partner.view.*
import kotlinx.android.synthetic.main.item_partnerlist.*
import kotlinx.android.synthetic.main.item_partnerlist.view.*
import kotlinx.android.synthetic.main.item_partnerlist.view.btn_each
import kotlinx.android.synthetic.main.item_partnerlist.view.btn_partnerlistcall
import kotlinx.android.synthetic.main.item_partnerlist.view.btn_partnerlistlike
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class PartnerListAdapter(val context: Context, val partnerList:ArrayList<Partner>, val listType: String) : RecyclerView.Adapter<PartnerListAdapter.ViewHolder>() {
    var dateHistory : String = ""

    override fun getItemCount(): Int {
        return partnerList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_partnerlist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(listType == "eachlike") {
            holder.itemView.btn_partnerlistcall.visibility = View.GONE
            holder.itemView.btn_partnerlistlike.visibility = View.GONE
            holder.itemView.btn_each.visibility = View.VISIBLE
            holder.itemView.btn_each.setText("대화하기")
            holder.itemView.btn_each.setOnClickListener {
                VolleyService.checkChatRoom(UserInfo.ID,partnerList[position].userId,context, {success ->
                    if(success!=null){
                        var json=success as JSONObject

                        var roomTitleArray=json.getString("room_title").split("&")

                        var roomTitle=""
                        if(UserInfo.NICKNAME==roomTitleArray[0]) roomTitle=roomTitleArray[1]
                        else roomTitle=roomTitleArray[0]

                        var room=ChatRoomItem(
                            json.getString("room_id"),
                            json.getString("room_maker"),
                            json.getString("room_partner"),
                            roomTitle,
                            "",
                            "",
                            ""
                        )

                        var intent=Intent(context,ChatActivity::class.java)
                        intent.putExtra("room",room)
                        context.startActivity(intent)
                    }
                    else{
                        VolleyService.createRoomReq(partnerList[position].userId,partnerList[position].userNickname,context,{success ->
                            var json=success
                            var room=ChatRoomItem(
                                json.getString("room_id"),
                                json.getString("room_maker"),
                                json.getString("room_partner"),
                                json.getString("room_title"),
                                "",
                                "",
                                ""
                            )

                            FirebaseMessaging.getInstance().subscribeToTopic(room.roomId)
                                .addOnCompleteListener {
                                    Log.d("test","success subscribe to topic")
                                }

                            var intent = Intent(context, ChatActivity::class.java)

                            intent.putExtra("room",room)
                            context.startActivity(intent)
                        })
                    }
                })

            }
        }
        else if(listType == "eachmeet") {
            holder.itemView.btn_partnerlistcall.visibility = View.GONE
            holder.itemView.btn_partnerlistlike.visibility = View.GONE
            holder.itemView.btn_each.visibility = View.VISIBLE
            holder.itemView.btn_each.setText("연락처 열람")
        }

        var gpsTracker = GpsTracker(context as Activity)
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())
        var partnerDate : Date = simpleDateFormat.parse(partnerList.get(position).recentTime)

        var age = curDate.substring(0, 4).toInt() - partnerList.get(position).userAge.substring(0,4).toInt() + 1


        if(partnerList.get(position).dateHistory.substring(0, 10) == dateHistory)
        {
            holder.itemView.text_historydate.visibility = View.GONE
        }
        else {
            holder.itemView.text_historydate.text = partnerList.get(position).dateHistory.substring(0, 10)
        }

        if(partnerList.get(position).like == 1) {
            holder.itemView.btn_partnerlistlike.setLiked(true)
        }
        else if(partnerList.get(position).like == 0) {
            holder.itemView.btn_partnerlistlike.setLiked(false)
        }

        if(partnerList.get(position).meet == 1) {
            holder.itemView.btn_partnerlistcall.setLiked(true)
        }
        else if(partnerList.get(position).meet == 0) {
            holder.itemView.btn_partnerlistcall.setLiked(false)
        }

        Glide.with(holder.itemView)
            .load(partnerList.get(position).userImage).apply(RequestOptions().circleCrop())
            .into(holder.itemView.image_partnerlistprofile)
        holder.itemView.text_partnerlistnickname.text = partnerList.get(position).userNickname + ", " + age.toString()
                holder.itemView.text_partnerlistrecenttime.text = gpsTracker.timeDiff(partnerDate.getTime())
        holder.itemView.image_partnerlistprofile.setClipToOutline(true)
        dateHistory = partnerList.get(position).dateHistory.substring(0, 10)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, PartnerActivity::class.java)
            intent.putExtra("userNickname", partnerList.get(position).userNickname)
            intent.putExtra("userId", partnerList.get(position).userId)
            intent.putExtra("userAge", age.toString())
            intent.putExtra("userCity", partnerList.get(position).userCity)



            VolleyService.insertHistoryReq(UserInfo.ID, partnerList.get(position).userId, "profile", curDate, context, {success->
                VolleyService.sendFCMReq(partnerList.get(position).userId, "visitprofile", context)
                if(success == "success")
                    context.startActivity(intent)
                else
                    Toast.makeText(context, "서버와의 통신 오류 입니다.", Toast.LENGTH_SHORT).show()
            })
        }


        holder.itemView.btn_partnerlistlike.setOnLikeListener(object: OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertExpressionReq(UserInfo.ID, partnerList.get(position).userId, "like", curDate, context, { success->
                    VolleyService.sendFCMReq(partnerList.get(position).userId,"llke",context)
                    when(success) {
                        "success" -> likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_on, null))
                        "eachsuccess" -> {
                            likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_on, null))
                            var dialog = PSDialog(context as Activity)
                            dialog.setEachExpressionLikeDialog(partnerList.get(position).userId,partnerList.get(position).userNickname, age.toString() + ", " + partnerList.get(position).userCity, partnerList.get(position).userImage)
                            dialog.show()
                        }
                        else -> Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun unLiked(likeButton: LikeButton?) {
                VolleyService.deleteExpressionReq(UserInfo.ID, partnerList.get(position).userId, "like", context, { success->
                    if(success=="success") {
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_off, null))
                    }
                    else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

        holder.itemView.btn_partnerlistcall.setOnLikeListener(object: OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertExpressionReq(UserInfo.ID, partnerList.get(position).userId, "meet", curDate, context, { success->
                    VolleyService.sendFCMReq(partnerList.get(position).userId,"meet",context)
                    when(success) {
                        "success" -> likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_icon, null))
                        "eachsuccess" -> {
                            likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_icon, null))
                            var dialog = PSDialog(context as Activity)
                            dialog.setEachExpressionMeetDialog(partnerList.get(position).userNickname, age.toString() + ", " + partnerList.get(position).userCity, partnerList.get(position).userPhone, partnerList.get(position).userImage)
                            dialog.show()
                        }
                        else -> Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun unLiked(likeButton: LikeButton?) {
                VolleyService.deleteExpressionReq(UserInfo.ID, partnerList.get(position).userId, "meet", context, { success->
                    if(success=="success") {
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_n_icon, null))
                    }
                    else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}