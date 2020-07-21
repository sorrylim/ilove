package com.ilove.ilove.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.ilove.ilove.Fragment.InquireFragment
import com.ilove.ilove.Fragment.InquireListFragment
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.item_partnerprofile.view.*

class InquireAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    var fa = fa
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> InquireFragment(fa)
            else -> InquireListFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }


}