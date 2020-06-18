package com.ilove.ilove.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Item.Partner
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_partnerlist.view.*

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

        holder.itemView.text_partnerlistnickname.text = partnerList.get(position).nickname
        holder.itemView.text_partnerlistage.text = partnerList.get(position).age + "," + partnerList.get(position).city
        dateHistory = partnerList.get(position).dateHistory

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}