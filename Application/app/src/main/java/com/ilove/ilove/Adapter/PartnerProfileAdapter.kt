package com.ilove.ilove.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_partnerprofile.view.*

class PartnerProfileAdapter(val context: Context, val profileImageList:ArrayList<String>, val count:Int) : RecyclerView.Adapter<PartnerProfileAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerProfileAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_partnerprofile, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return count
    }

    override fun onBindViewHolder(holder: PartnerProfileAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(profileImageList.get(position)).into(holder.itemView.image_partnerprofile)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

}