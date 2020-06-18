package com.ilove.ilove.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Item.Partner
import com.ilove.ilove.Item.UserList
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_partner.view.*
import kotlinx.android.synthetic.main.item_partnerlist.view.*

class UserListAdapter(val context: Context, val userList:ArrayList<UserList>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_partner, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_userlistinfo.text = userList.get(position).nickname + ", " + userList.get(position).age + ", " + userList.get(position).city + ", " + userList.get(position).recentGps
        holder.itemView.text_userlistintroduce.text = userList.get(position).introduce
        holder.itemView.text_userlistcertification.text = userList.get(position).certification
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}