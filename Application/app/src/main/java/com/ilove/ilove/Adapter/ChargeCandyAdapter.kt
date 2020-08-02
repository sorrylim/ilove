package com.ilove.ilove.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_chargecandy.view.*

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

        holder.itemView.text_chargecandycount.text = candyCount.get(position).candyCount.toString() + "개"
        holder.itemView.text_chargecandyamount.text = candyCount.get(position).candyAmount

        if(candyCount.get(position).isSelected == true) {
            holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_color_layout_2dp)
            holder.itemView.image_chargecandy.setImageResource(R.drawable.fullcandy_icon)
        }
        else {
            holder.itemView.image_chargecandy.setImageResource(R.drawable.candy_icon)
            holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_layout)
        }

        holder.itemView.setOnClickListener {
            if(tempPosition == null) {
                candyCount.get(position).isSelected = !candyCount.get(position).isSelected

                tempPosition = position

                if(candyCount.get(position).isSelected == true) {
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_color_layout_2dp)
                    holder.itemView.image_chargecandy.setImageResource(R.drawable.fullcandy_icon)
                }
                else {
                    holder.itemView.image_chargecandy.setImageResource(R.drawable.candy_icon)
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_layout)
                }
            }
            else {
                candyCount.get(tempPosition!!).isSelected = !candyCount.get(tempPosition!!).isSelected
                candyCount.get(position).isSelected = !candyCount.get(position).isSelected

                tempPosition = position


                if(candyCount.get(position).isSelected == true) {
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_color_layout_2dp)
                    holder.itemView.image_chargecandy.setImageResource(R.drawable.fullcandy_icon)
                }
                else {
                    holder.itemView.image_chargecandy.setImageResource(R.drawable.candy_icon)
                    holder.itemView.layout_chargecandy.setBackgroundResource(R.drawable.rounded_layout)
                }

                notifyDataSetChanged()
            }

            var psDialog = PSDialog(context as Activity)
            psDialog.setUpdateCandy(candyCount.get(position).candyCount, "increase")
            psDialog.show()

        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}