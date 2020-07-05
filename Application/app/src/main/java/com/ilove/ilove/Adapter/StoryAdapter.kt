package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.ilove.ilove.MainActivity.StoryActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_storylist.view.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class StoryAdapter(val context: Context, val storyList:ArrayList<ImageItem.StoryImage>) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return storyList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_storylist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("test", "${storyList.get(position).image}")
        Glide.with(holder.itemView)
            .load(storyList.get(position).image)
            .into(holder.itemView.image_storylist)

        var displayMetrics: DisplayMetrics = DisplayMetrics()
        (holder.itemView.getContext() as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics) // 화면의 가로길이를 구함
        var width = displayMetrics.widthPixels / 3
        holder.itemView.getLayoutParams().width = width
        holder.itemView.getLayoutParams().height = width
        holder.itemView.requestLayout()

        holder.itemView.setOnClickListener {
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var intent = Intent(context , StoryActivity::class.java)
            intent.putExtra("image_id", storyList.get(position).imageId)
            intent.putExtra("image", storyList.get(position).image)

            VolleyService.insertHistoryReq(UserInfo.ID, storyList.get(position).userId, "story", currentDate, context, { success ->
                if(success == "success") {
                    context.startActivity(intent)
                }
                else {
                    Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}