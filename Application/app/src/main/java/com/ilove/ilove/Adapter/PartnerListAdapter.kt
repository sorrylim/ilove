package com.ilove.ilove.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.Partner
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
        if(partnerList.get(position).dateHistory == dateHistory)
        {
            holder.itemView.text_historydate.visibility = View.GONE
        }
        else {
            holder.itemView.text_historydate.text = partnerList.get(position).dateHistory
        }

        if(partnerList.get(position).like == 1) {
            holder.itemView.btn_partnerlistlike.setLiked(true)
        }
        else if(partnerList.get(position).like == 0) {
            holder.itemView.btn_partnerlistlike.setLiked(false)
        }


        holder.itemView.text_partnerlistnickname.text = partnerList.get(position).userNickname
        holder.itemView.text_partnerlistage.text = partnerList.get(position).userAge + "," + partnerList.get(position).userCity
        dateHistory = partnerList.get(position).dateHistory


        holder.itemView.btn_partnerlistlike.setOnLikeListener(object: OnLikeListener {
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertExpressionReq(UserInfo.ID, partnerList.get(position).userId, "like", currentDate, context, { success->
                    if(success=="success") {
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_on, null))
                    }
                    else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
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

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}