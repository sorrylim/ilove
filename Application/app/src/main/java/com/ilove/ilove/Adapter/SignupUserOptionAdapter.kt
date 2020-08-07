package com.ilove.ilove.Adapter

import android.app.Dialog
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

class SignupUserOptionAdapter(val context: Context, val userOptionList:ArrayList<UserItem.UserOption>, val dialog: Dialog) : RecyclerView.Adapter<SignupUserOptionAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return userOptionList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignupUserOptionAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_useroption, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_useroption.text = userOptionList.get(position).title


        holder.itemView.setOnClickListener {
            PSDialog.userOptionData = holder.itemView.text_useroption.text.toString()
            dialog.dismiss()
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}