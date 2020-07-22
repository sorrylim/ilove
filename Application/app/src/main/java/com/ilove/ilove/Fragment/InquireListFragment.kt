package com.ilove.ilove.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Adapter.InquireListAdapter
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.InquireItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import org.json.JSONObject

class InquireListFragment : Fragment() {

    var inquireList = ArrayList<InquireItem.Inquire>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_inquire_list, container, false)
        val inquireRV : RecyclerView = rootView.findViewById(R.id.rv_inquire)
        val emptyInquireText : TextView = rootView.findViewById(R.id.text_emptyinquire)


        VolleyService.getInquireListReq(UserInfo.ID, activity!!, {success->
            inquireList.clear()
            var array = success
            for(i in 0..array.length()-1) {
                var json = array[i] as JSONObject
                inquireList.add(
                    InquireItem.Inquire(
                        json.getString("date"),
                        json.getString("inquire_title"),
                        json.getString("inquire_content"),
                        json.getString("inquire_answer"),
                        json.getString("inquire_type"),
                        json.getInt("check"),
                        json.getString("answer_date")
                    )
                )
            }

            if(inquireList.size == 0) {
                emptyInquireText.visibility = View.VISIBLE
            }
            else {
                inquireRV.setHasFixedSize(true)
                inquireRV.layoutManager =
                    LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
                inquireRV.adapter = InquireListAdapter(activity!!, inquireList)
            }
        })

        return rootView
    }
}