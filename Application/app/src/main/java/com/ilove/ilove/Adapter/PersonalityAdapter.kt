package com.ilove.ilove.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_useroption.view.*

class PersonalityAdapter(val context: Context, val userOptionList:ArrayList<UserItem.UserOption>) : RecyclerView.Adapter<PersonalityAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userOptionList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalityAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_useroption, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_useroption.text = userOptionList.get(position).title

        if(userOptionList.get(position).isSelected == true) {
            holder.itemView.text_useroption.setTextColor(Color.parseColor("#FF8C00"))
        }
        else {
            holder.itemView.text_useroption.setTextColor(Color.parseColor("#9E9E9E"))
        }

        holder.itemView.setOnClickListener {
            PSDialog.userOptionData = ""
            userOptionList.get(position).isSelected = !userOptionList.get(position).isSelected

            if(userOptionList.get(position).isSelected == true) {
                holder.itemView.text_useroption.setTextColor(Color.parseColor("#FF8C00"))
            }
            else {
                holder.itemView.text_useroption.setTextColor(Color.parseColor("#9E9E9E"))
            }

            for(i in 0..userOptionList.size-1) {
                if(userOptionList.get(i).isSelected == true) {
                    PSDialog.userOptionData += userOptionList.get(i).title + ","
                }
            }
        }



    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}