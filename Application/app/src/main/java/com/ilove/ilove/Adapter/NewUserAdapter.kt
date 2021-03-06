package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.NewUserList
import com.ilove.ilove.MainActivity.PartnerActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_newuser.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewUserAdapter(val context: Context, val userList:ArrayList<NewUserList>, val userListSize: Int) : RecyclerView.Adapter<NewUserAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userListSize
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewUserAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_newuser, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var gpsTracker = GpsTracker(context as Activity)
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())
        var partnerDate : Date = simpleDateFormat.parse(userList.get(position).recentTime)
        var age = curDate.substring(0, 4).toInt() - userList.get(position).userAge.substring(0, 4).toInt() + 1
        var location : List<String> = userList.get(position).recentGps.split(",")

        var distance = gpsTracker.getDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1))


        holder.itemView.text_newusernicknameage.text = userList.get(position).userNickname + ", " + age.toString()

        holder.itemView.text_newuserrecentdata.text = distance.get(0)+ distance.get(1) + ", " + gpsTracker.timeDiff(partnerDate.getTime())

        Log.d("test", "${userList.get(position).userNickname} , ${gpsTracker.timeDiff(partnerDate.getTime())}")
        Glide.with(holder.itemView)
            .load(userList.get(position).userImage).transition(DrawableTransitionOptions().crossFade()).apply(RequestOptions().fitCenter()).apply(RequestOptions().override(300,300))
            .into(holder.itemView.image_newuser)
        holder.itemView.image_newuser.setClipToOutline(true)


        holder.itemView.setOnClickListener {
            if(UserInfo.ENABLE == 1) {
                var intent = Intent(context, PartnerActivity::class.java)
                intent.putExtra("userId", userList.get(position).userId)
                intent.putExtra("userNickname", userList.get(position).userNickname)
                intent.putExtra("userAge", age.toString())
                intent.putExtra("userCity", userList.get(position).userCity)
                intent.putExtra("userPhone", userList.get(position).userPhone)
                context.startActivity(intent)
            }
            else {
                val psDialog = PSDialog(context!!)
                psDialog.setIncompleteProfile()
                psDialog.show()
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}