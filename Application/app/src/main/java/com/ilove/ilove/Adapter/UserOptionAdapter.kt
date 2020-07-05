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

class UserOptionAdapter(val context: Context, val userOptionList:ArrayList<UserItem.UserOption>) : RecyclerView.Adapter<UserOptionAdapter.ViewHolder>() {

    var tempPosition : Int? = null

    override fun getItemCount(): Int {
        return userOptionList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOptionAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_useroption, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_useroption.text = userOptionList.get(position).title

        if(userOptionList.get(position).isSelected == true) {
            holder.itemView.text_useroption.setTextColor(Color.parseColor("#212121"))
            PSDialog.userOptionData = holder.itemView.text_useroption.text.toString()
        }
        else {
            holder.itemView.text_useroption.setTextColor(Color.parseColor("#9E9E9E"))
        }

        holder.itemView.setOnClickListener {
            if(tempPosition == null) {
                userOptionList.get(position).isSelected = !userOptionList.get(position).isSelected
                tempPosition = position

                if(userOptionList.get(position).isSelected == true) {
                    holder.itemView.text_useroption.setTextColor(Color.parseColor("#212121"))
                    PSDialog.userOptionData = holder.itemView.text_useroption.text.toString()
                }
                else {
                    holder.itemView.text_useroption.setTextColor(Color.parseColor("#9E9E9E"))
                }
            }
            else {
                userOptionList.get(tempPosition!!).isSelected = !userOptionList.get(tempPosition!!).isSelected
                userOptionList.get(position).isSelected = !userOptionList.get(position).isSelected

                tempPosition = position


                if(userOptionList.get(position).isSelected == true) {
                    holder.itemView.text_useroption.setTextColor(Color.parseColor("#212121"))
                    PSDialog.userOptionData = holder.itemView.text_useroption.text.toString()
                }
                else {
                    holder.itemView.text_useroption.setTextColor(Color.parseColor("#9E9E9E"))
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