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
import com.ilove.ilove.Item.InquireItem
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.MainActivity.StoryActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_item.view.*
import kotlinx.android.synthetic.main.dialog_messageticket.view.*
import kotlinx.android.synthetic.main.item_chargecandy.view.*
import kotlinx.android.synthetic.main.item_inquire.view.*
import kotlinx.android.synthetic.main.item_messageticket.view.*
import kotlinx.android.synthetic.main.item_storylist.view.*
import kotlinx.android.synthetic.main.item_useroption.view.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class InquireListAdapter(val context: Context, val inquireList:ArrayList<InquireItem.Inquire>) : RecyclerView.Adapter<InquireListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return inquireList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InquireListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_inquire, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(inquireList.get(position).inquireType == "qna" && inquireList.get(position).check == 0) {
            holder.itemView.text_inquiretitle.text = "[문의] " + inquireList.get(position).inquireTitle
        }
        else if(inquireList.get(position).inquireType == "qna" && inquireList.get(position).check == 1) {
            holder.itemView.text_inquiretitle.text = "[문의답변] " + inquireList.get(position).inquireTitle
        }
        else if(inquireList.get(position).inquireType == "report" && inquireList.get(position).check == 0) {
            holder.itemView.text_inquiretitle.text = "[신고] " + inquireList.get(position).inquireTitle
        }
        else if(inquireList.get(position).inquireType == "report" && inquireList.get(position).check == 1) {
            holder.itemView.text_inquiretitle.text = "[신고] " + inquireList.get(position).inquireTitle
        }

        holder.itemView.text_inquiredate.text = inquireList.get(position).date
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}