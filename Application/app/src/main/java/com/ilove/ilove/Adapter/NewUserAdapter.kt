package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ImageItem
import com.ilove.ilove.Item.NewUserList
import com.ilove.ilove.MainActivity.PartnerActivity
import com.ilove.ilove.MainActivity.StoryActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_partner.*
import kotlinx.android.synthetic.main.item_newuser.view.*
import kotlinx.android.synthetic.main.item_partnerlist.view.*
import kotlinx.android.synthetic.main.item_storylist.view.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class NewUserAdapter(val context: Context, val userList:ArrayList<NewUserList>) : RecyclerView.Adapter<NewUserAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewUserAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_newuser, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy"))

        var age = currentDate.toInt() - userList.get(position).userAge.substring(0, 4).toInt() + 1

        holder.itemView.text_newusernicknameage.text = userList.get(position).userNickname + ", " + age.toString()
        holder.itemView.text_newuserrecentdata.text = userList.get(position).recentGps + ", " + userList.get(position).recentTime
        Glide.with(holder.itemView)
            .load(userList.get(position).userImage)
            .into(holder.itemView.image_newuser)
        holder.itemView.image_newuser.setClipToOutline(true)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, PartnerActivity::class.java)
            intent.putExtra("userId", userList.get(position).userId)
            intent.putExtra("userNickname", userList.get(position).userNickname)
            intent.putExtra("userAge", age.toString())
            intent.putExtra("userCity", userList.get(position).userCity)
            context.startActivity(intent)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}