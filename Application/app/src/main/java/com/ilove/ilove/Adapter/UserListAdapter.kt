package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.Partner
import com.ilove.ilove.Item.UserList
import com.ilove.ilove.MainActivity.PartnerActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_partner.view.*
import kotlinx.android.synthetic.main.item_partnerlist.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class UserListAdapter(val context: Context, val userList:ArrayList<UserList>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_partner, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var gpsTracker = GpsTracker(context as Activity)
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())
        var partnerDate : Date = simpleDateFormat.parse(userList.get(position).recentTime)
        var age = curDate.substring(0, 4).toInt() - userList.get(position).userAge.substring(0, 4).toInt() + 1

        Glide.with(holder.itemView)
            .load(userList.get(position).userImage)
            .into(holder.itemView.image_userlistprofile)
        holder.itemView.text_userlistinfo.text = userList.get(position).userNickname + " " + age
        holder.itemView.text_userlistrecent.text =  userList.get(position).recentGps + "km" + ", " + gpsTracker.timeDiff(partnerDate.getTime())
        holder.itemView.text_userlistintroduce.text = userList.get(position).userIntroduce
        holder.itemView.image_userlistprofile.setClipToOutline(true)

        if(userList.get(position).like == 1) {
            holder.itemView.btn_userlike.setLiked(true)
        }
        else if(userList.get(position).like == 0) {
            holder.itemView.btn_userlike.setLiked(false)
        }

        if(userList.get(position).meet == 1) {
            holder.itemView.btn_call.setLiked(true)
        }
        else if(userList.get(position).meet == 0) {
            holder.itemView.btn_call.setLiked(false)
        }

        holder.itemView.btn_userlike.setOnLikeListener(object: OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertExpressionReq(UserInfo.ID, userList.get(position).userId, "like", curDate, context, {success->
                    VolleyService.sendFCMReq(userList.get(position).userId,"like",context)
                    when(success) {
                        "success" -> likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_on, null))
                        "eachsuccess" -> {
                            likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_off, null))
                            var dialog = PSDialog(context as Activity)
                            dialog.setEachExpressionLikeDialog(userList.get(position).userId,userList.get(position).userNickname, userList.get(position).userAge + ", " + userList.get(position).userCity, userList.get(position).userImage)
                            dialog.show()
                        }
                        else -> Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun unLiked(likeButton: LikeButton?) {
                VolleyService.deleteExpressionReq(UserInfo.ID, userList.get(position).userId, "like", context, {success->
                    if(success=="success") {
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_off, null))
                    }
                    else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

        holder.itemView.btn_call.setOnLikeListener(object: OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertExpressionReq(UserInfo.ID, userList.get(position).userId, "meet", curDate, context, {success->
                    when(success) {
                        "success" -> likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_icon, null))
                        "eachsuccess" -> {
                            likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_icon, null))
                            var dialog = PSDialog(context as Activity)
                            dialog.setEachExpressionMeetDialog(userList.get(position).userNickname, userList.get(position).userAge + ", " + userList.get(position).userCity, userList.get(position).userPhone, userList.get(position).userImage)
                            dialog.show()
                        }
                        else -> Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun unLiked(likeButton: LikeButton?) {
                VolleyService.deleteExpressionReq(UserInfo.ID, userList.get(position).userId, "meet", context, {success->
                    if(success=="success") {
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_n_icon, null))
                    }
                    else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

        holder.itemView.setOnClickListener {
            var intent = Intent(context, PartnerActivity::class.java)
            intent.putExtra("userNickname", userList.get(position).userNickname)
            intent.putExtra("userId", userList.get(position).userId)
            intent.putExtra("userAge", age.toString())
            intent.putExtra("userCity", userList.get(position).userCity)

            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            VolleyService.insertHistoryReq(UserInfo.ID, userList.get(position).userId, "profile", currentDate, context, {success->
                if(success == "success")
                    context.startActivity(intent)
                else
                    Toast.makeText(context, "서버와의 통신 오류 입니다.", Toast.LENGTH_SHORT).show()
            })
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}