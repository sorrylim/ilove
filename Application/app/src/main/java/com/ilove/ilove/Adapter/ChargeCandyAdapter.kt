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
import kotlinx.android.synthetic.main.item_chargecandy.view.*
import kotlinx.android.synthetic.main.item_messageticket.view.*
import kotlinx.android.synthetic.main.item_storylist.view.*
import kotlinx.android.synthetic.main.item_useroption.view.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ChargeCandyAdapter(val context: Context, val candyCount:ArrayList<UserItem.Candy>) : RecyclerView.Adapter<ChargeCandyAdapter.ViewHolder>() {

    var tempPosition : Int? = null

    override fun getItemCount(): Int {
        return candyCount.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargeCandyAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chargecandy, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.text_chargecandycount.text = candyCount.get(position).candyCount+ "개"
        holder.itemView.text_chargecandyamount.text = candyCount.get(position).candyAmount

        if(candyCount.get(position).isSelected == true) {
            holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_color_layout)
        }
        else {
            holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_layout)
        }

        holder.itemView.setOnClickListener {
            if(tempPosition == null) {
                candyCount.get(position).isSelected = !candyCount.get(position).isSelected

                tempPosition = position

                if(candyCount.get(position).isSelected == true) {
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_color_layout)
                }
                else {
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_layout)
                }
            }
            else {
                candyCount.get(tempPosition!!).isSelected = !candyCount.get(tempPosition!!).isSelected
                candyCount.get(position).isSelected = !candyCount.get(position).isSelected

                tempPosition = position


                if(candyCount.get(position).isSelected == true) {
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_color_layout)
                }
                else {
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_layout)
                }

                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}