package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.Partner
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

class PartnerListAdapter(val context: Context, val partnerList:ArrayList<Partner>) : RecyclerView.Adapter<PartnerListAdapter.ViewHolder>() {
    var dateHistory : String = ""

    override fun getItemCount(): Int {
        return partnerList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_partnerlist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        var age = currentDate.substring(0, 4).toInt() - partnerList.get(position).userAge.substring(0,4).toInt() + 1


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
        holder.itemView.text_partnerlistnickname.text = partnerList.get(position).userNickname
        holder.itemView.text_partnerlistage.text = age.toString() + ", " + partnerList.get(position).userCity
        holder.itemView.image_partnerlistprofile.setClipToOutline(true)
        dateHistory = partnerList.get(position).dateHistory.substring(0, 10)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, PartnerActivity::class.java)
            intent.putExtra("userNickname", partnerList.get(position).userNickname)
            intent.putExtra("userId", partnerList.get(position).userId)
            intent.putExtra("userAge", age.toString())
            intent.putExtra("userCity", partnerList.get(position).userCity)


            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            VolleyService.insertHistoryReq(UserInfo.ID, partnerList.get(position).userId, "profile", currentDate, context, {success->
                if(success == "success")
                    context.startActivity(intent)
                else
                    Toast.makeText(context, "서버와의 통신 오류 입니다.", Toast.LENGTH_SHORT).show()
            })
        }


        holder.itemView.btn_partnerlistlike.setOnLikeListener(object: OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertExpressionReq(UserInfo.ID, partnerList.get(position).userId, "like", currentDate, context, { success->
                    when(success) {
                        "success" -> likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_on, null))
                        "eachsuccess" -> {
                            likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_on, null))
                            var dialog = PSDialog(context as Activity)
                            dialog.setEachExpressionLikeDialog(partnerList.get(position).userId,partnerList.get(position).userNickname, partnerList.get(position).userAge + ", " + partnerList.get(position).userCity, partnerList.get(position).userImage)
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
                VolleyService.insertExpressionReq(UserInfo.ID, partnerList.get(position).userId, "meet", currentDate, context, { success->
                    when(success) {
                        "success" -> likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_icon, null))
                        "eachsuccess" -> {
                            likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.call_icon, null))
                            var dialog = PSDialog(context as Activity)
                            dialog.setEachExpressionMeetDialog(partnerList.get(position).userNickname, partnerList.get(position).userAge + ", " + partnerList.get(position).userCity, partnerList.get(position).userPhone, partnerList.get(position).userImage)
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