package com.ilove.ilove.Adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ImageItem
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.MainActivity.StoryActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_item.view.*
import kotlinx.android.synthetic.main.dialog_messageticket.view.*
import kotlinx.android.synthetic.main.item_messageticket.view.*
import kotlinx.android.synthetic.main.item_storylist.view.*
import kotlinx.android.synthetic.main.item_useroption.view.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MessageCandyAdapter(val context: Context, val messageCandyCount:ArrayList<UserItem.MessageTicket>, var negativeText: TextView) : RecyclerView.Adapter<MessageCandyAdapter.ViewHolder>() {

    var tempPosition : Int? = null

    override fun getItemCount(): Int {
        return messageCandyCount.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageCandyAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_messageticket, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        holder.itemView.text_day.text = messageCandyCount.get(position).day+ "일"
        holder.itemView.text_daycandy.text = messageCandyCount.get(position).candyCount + "개"

        if(messageCandyCount.get(position).isSelected == true) {
            holder.itemView.text_day.setTextColor(Color.parseColor("#FFA500"))
            holder.itemView.text_daycandy.setTextColor(Color.parseColor("#FFA500"))
            holder.itemView.view31.setBackgroundColor(Color.parseColor("#FFA500"))
        }
        else {
            holder.itemView.text_day.setTextColor(Color.parseColor("#9E9E9E"))
            holder.itemView.text_daycandy.setTextColor(Color.parseColor("#9E9E9E"))
            holder.itemView.view31.setBackgroundColor(Color.parseColor("#9E9E9E"))
        }

        holder.itemView.setOnClickListener {
            if(tempPosition == null) {
                messageCandyCount.get(position).isSelected = !messageCandyCount.get(position).isSelected

                tempPosition = position

                if(messageCandyCount.get(position).isSelected == true) {
                    holder.itemView.text_day.setTextColor(Color.parseColor("#FFA500"))
                    holder.itemView.text_daycandy.setTextColor(Color.parseColor("#FFA500"))
                    holder.itemView.view31.setBackgroundColor(Color.parseColor("#FFA500"))
                }
                else {
                    holder.itemView.text_day.setTextColor(Color.parseColor("#9E9E9E"))
                    holder.itemView.text_daycandy.setTextColor(Color.parseColor("#9E9E9E"))
                    holder.itemView.view31.setBackgroundColor(Color.parseColor("#9E9E9E"))
                }
            }
            else {
                messageCandyCount.get(tempPosition!!).isSelected = !messageCandyCount.get(tempPosition!!).isSelected
                messageCandyCount.get(position).isSelected = !messageCandyCount.get(position).isSelected

                tempPosition = position


                if(messageCandyCount.get(position).isSelected == true) {
                    holder.itemView.text_day.setTextColor(Color.parseColor("#212121"))
                    holder.itemView.text_daycandy.setTextColor(Color.parseColor("#FFA500"))
                    holder.itemView.view31.setBackgroundColor(Color.parseColor("#FFA500"))
                }
                else {
                    holder.itemView.text_day.setTextColor(Color.parseColor("#9E9E9E"))
                    holder.itemView.text_daycandy.setTextColor(Color.parseColor("#9E9E9E"))
                    holder.itemView.view31.setBackgroundColor(Color.parseColor("#9E9E9E"))
                }

                notifyDataSetChanged()
            }

            if(UserInfo.CANDYCOUNT < messageCandyCount.get(position).candyCount.toInt()) {
                negativeText.visibility = View.VISIBLE
            }
            else {
                negativeText.visibility = View.INVISIBLE
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}