package com.ilove.ilove.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
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
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class UserListAdapter(val context: Context, val userList:ArrayList<UserList>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_partner, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_userlistinfo.text = userList.get(position).userNickname + ", " + userList.get(position).userAge + ", " + userList.get(position).userCity + ", " + userList.get(position).recentGps
        holder.itemView.text_userlistintroduce.text = userList.get(position).userIntroduce
        holder.itemView.text_userlistcertification.text = userList.get(position).userCertification

        if(userList.get(position).like == 1) {
            holder.itemView.btn_userlike.setLiked(true)
        }
        else if(userList.get(position).like == 0) {
            holder.itemView.btn_userlike.setLiked(false)
        }

        holder.itemView.btn_userlike.setOnLikeListener(object: OnLikeListener {
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertExpressionReq(UserInfo.ID, userList.get(position).userId, "like", currentDate, context, {success->
                    if(success=="success") {
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_on, null))
                    }
                    else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
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

        holder.itemView.setOnClickListener {
            var intent = Intent(context, PartnerActivity::class.java)
            intent.putExtra("userNickname", userList.get(position).userNickname)
            intent.putExtra("userId", userList.get(position).userId)

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