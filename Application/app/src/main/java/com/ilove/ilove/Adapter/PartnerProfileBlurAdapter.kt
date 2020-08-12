package com.ilove.ilove.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.item_partnerprofile.view.*

class PartnerProfileBlurAdapter(val context: Context, val profileImageList:ArrayList<String>, val count:Int) : RecyclerView.Adapter<PartnerProfileBlurAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerProfileBlurAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_partnerprofile, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return count
    }

    override fun onBindViewHolder(holder: PartnerProfileBlurAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(profileImageList.get(position)).apply(RequestOptions.bitmapTransform(BlurTransformation(25, 10)))
            .apply(RequestOptions().override(1080, 1920)).into(holder.itemView.image_partnerprofile)
        holder.itemView.image_partnerprofile.setClipToOutline(true)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

}