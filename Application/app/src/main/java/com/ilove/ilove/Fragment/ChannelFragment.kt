package com.ilove.ilove.Fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilove.ilove.Adapter.NewUserAdapter
import com.ilove.ilove.Adapter.UserListAdapter
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.NewUserList
import com.ilove.ilove.Item.UserList
import com.ilove.ilove.MainActivity.PartnerListActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.fragment_channel.*
import kotlinx.android.synthetic.main.fragment_channel.view.*
import kotlinx.android.synthetic.main.fragment_channel.view.layout_sendlike
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChannelFragment : Fragment() {

    var newUserList = ArrayList<NewUserList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var psDialog = PSDialog(activity!!)

        var rootView = inflater.inflate(R.layout.fragment_channel, container, false)
        var visitProfileCount = rootView.text_visitprofilecount
        var visitStoryCount = rootView.text_visitstorycount
        var sendLikeCount = rootView.text_sendlikecount
        var receiveLikeCount = rootView.text_receivelikecount
        var eachLikeCount = rootView.text_eachlikecount
        var sendMeetCount = rootView.text_sendmeetcount
        var receiveMeetCount = rootView.text_receivemeetcount
        var eachMeetCount = rootView.text_eachmeetcount
        var sendLikeBtn = rootView.layout_sendlike
        var receiveLikeBtn = rootView.layout_receivelike
        var eachLikeBtn = rootView.layout_eachlike
        var sendMeetBtn = rootView.layout_sendmeet
        var receiveMeetBtn = rootView.layout_receivemeet
        var eachMeetBtn = rootView.layout_eachmeet
        var visitProfileBtn = rootView.layout_visitprofile
        var visitStoryBtn = rootView.layout_visitstory
        var newUserRV = rootView.rv_newmember
        var swipeLayout: SwipeRefreshLayout = rootView.layout_swipe
        var nestedScrollView : NestedScrollView = rootView.scroll_channel
        var upProfileBtn : ConstraintLayout = rootView.findViewById(R.id.layout_upprofile)
        val newUserText : TextView = rootView.findViewById(R.id.textView100)

        val gpsTracker = GpsTracker(activity!!)

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var partnerDate : Date = simpleDateFormat.parse("2020-08-07 16:08:00")

        Log.d("test", "${gpsTracker.timeDiffValue(partnerDate.getTime())}")


        upProfileBtn.setOnClickListener {
            if(UserInfo.ENABLE == 1) {
                psDialog.setUpProfile()
                psDialog.show()
            }
            else {
                psDialog.setIncompleteProfile()
                psDialog.show()
            }
        }

        nestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER)

        swipeLayout.setOnRefreshListener {
            VolleyService.getExpressionCountReq(UserInfo.ID, activity!!, {success->
                var json = success

                if(json.getInt("send_like") > 0) {
                    sendLikeBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }
                else {
                    sendLikeBtn.setBackgroundResource(0)
                }

                if(json.getInt("receive_like") > 0) {
                    receiveLikeBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }
                else {
                    receiveLikeBtn.setBackgroundResource(0)
                }

                if(json.getInt("each_like") > 0) {
                    eachLikeBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }
                else {
                    eachLikeBtn.setBackgroundResource(0)
                }

                if(json.getInt("send_meet") > 0) {
                    sendMeetBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }
                else {
                    sendMeetBtn.setBackgroundResource(0)
                }

                if(json.getInt("receive_meet") > 0) {
                    receiveMeetBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }
                else {
                    receiveMeetBtn.setBackgroundResource(0)
                }

                if(json.getInt("each_meet") > 0) {
                    eachMeetBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }
                else {
                    eachMeetBtn.setBackgroundResource(0)
                }

                sendLikeCount.text = json!!.getInt("send_like").toString() + "명"
                receiveLikeCount.text = json!!.getInt("receive_like").toString() + "명"
                eachLikeCount.text = json!!.getInt("each_like").toString() + "명"
                sendMeetCount.text = json!!.getInt("send_meet").toString() + "명"
                receiveMeetCount.text = json!!.getInt("receive_meet").toString() + "명"
                eachMeetCount.text = json!!.getInt("each_meet").toString() + "명"

                VolleyService.getCountViewReq(UserInfo.ID, activity!!, {success->
                    var json = success
                    visitProfileCount.text = json!!.getInt("profile").toString() + "명"
                    visitStoryCount.text = json!!.getInt("story").toString() + "명"

                    if(UserInfo.GENDER == "M") {
                        VolleyService.getNewUserListReq("F", activity!!, {success->
                            newUserList.clear()
                            var array = success
                            for(i in 0..array.length()-1)
                            {
                                var json = array[i] as JSONObject
                                newUserList.add(NewUserList(json.getString("user_id"), json.getString("user_nickname"), json.getString("user_birthday"), json.getString("user_city"),
                                    json.getString("user_recentgps"), json.getString("user_recenttime"), json.getString("user_phone"), json.getString("image")))
                            }

                            VolleyService.getBlockingListReq(UserInfo.ID, activity!!, {success->
                                var blockingList = ArrayList<String>()
                                var array = success
                                for(i in 0..array.length()-1) {
                                    var json = array[i] as JSONObject
                                    blockingList.add(json.getString("blocking_user"))
                                }

                                if(UserInfo.BLOCKING == 1) {
                                    var iter = newUserList.iterator()
                                    while(iter.hasNext()){
                                        var userList = iter.next() as NewUserList

                                        if(UserInfo.BLOCKINGNUMBER.contains(userList.userPhone)){
                                            iter.remove()
                                        }
                                        else {
                                            for(i in 0..blockingList.size-1) {
                                                if(userList.userPhone == blockingList.get(i)) {
                                                    iter.remove()
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    var iter = newUserList.iterator()
                                    while(iter.hasNext()){
                                        var userList = iter.next() as NewUserList

                                        for(i in 0..blockingList.size-1) {
                                            if(userList.userPhone == blockingList.get(i)) {
                                                iter.remove()
                                            }
                                        }
                                    }
                                }


                                if(newUserList.size == 0) {
                                    newUserText.visibility = View.GONE
                                    newUserRV.visibility = View.GONE
                                    newUserRV.adapter = null
                                }
                                else {
                                    if(newUserList.size > 4) {
                                        newUserText.visibility = View.VISIBLE
                                        newUserRV.visibility = View.VISIBLE
                                        newUserRV.setHasFixedSize(true)
                                        newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                                        newUserRV.adapter = NewUserAdapter(activity!!, newUserList, 4)
                                    }
                                    else {
                                        newUserText.visibility = View.VISIBLE
                                        newUserRV.visibility = View.VISIBLE
                                        newUserRV.setHasFixedSize(true)
                                        newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                                        newUserRV.adapter = NewUserAdapter(activity!!, newUserList, newUserList.size)
                                    }
                                }
                            })
                        })
                    }
                    else {
                        VolleyService.getNewUserListReq("M", activity!!, {success->
                            newUserList.clear()
                            var array = success
                            for(i in 0..array.length()-1)
                            {
                                var json = array[i] as JSONObject
                                newUserList.add(NewUserList(json.getString("user_id"), json.getString("user_nickname"), json.getString("user_birthday"), json.getString("user_city"),
                                    json.getString("user_recentgps"), json.getString("user_recenttime"), json.getString("user_phone"), json.getString("image")))
                            }

                            VolleyService.getBlockingListReq(UserInfo.ID, activity!!, {success->
                                var blockingList = ArrayList<String>()
                                var array = success
                                for(i in 0..array.length()-1) {
                                    var json = array[i] as JSONObject
                                    blockingList.add(json.getString("blocking_user"))
                                }

                                if(UserInfo.BLOCKING == 1) {
                                    var iter = newUserList.iterator()
                                    while(iter.hasNext()){
                                        var userList = iter.next() as NewUserList

                                        if(UserInfo.BLOCKINGNUMBER.contains(userList.userPhone)){
                                            iter.remove()
                                        }
                                        else {
                                            for(i in 0..blockingList.size-1) {
                                                if(userList.userPhone == blockingList.get(i)) {
                                                    iter.remove()
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    var iter = newUserList.iterator()
                                    while(iter.hasNext()){
                                        var userList = iter.next() as NewUserList

                                        for(i in 0..blockingList.size-1) {
                                            if(userList.userPhone == blockingList.get(i)) {
                                                iter.remove()
                                            }
                                        }
                                    }
                                }


                                if(newUserList.size == 0) {
                                    newUserText.visibility = View.GONE
                                    newUserRV.visibility = View.GONE
                                    newUserRV.adapter = null
                                }
                                else {
                                    if(newUserList.size > 4) {
                                        newUserText.visibility = View.VISIBLE
                                        newUserRV.visibility = View.VISIBLE
                                        newUserRV.setHasFixedSize(true)
                                        newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                                        newUserRV.adapter = NewUserAdapter(activity!!, newUserList, 4)
                                    }
                                    else {
                                        newUserText.visibility = View.VISIBLE
                                        newUserRV.visibility = View.VISIBLE
                                        newUserRV.setHasFixedSize(true)
                                        newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                                        newUserRV.adapter = NewUserAdapter(activity!!, newUserList, newUserList.size)
                                    }
                                }
                            })
                        })
                    }
                })
                swipeLayout.setRefreshing(false)
            })
        }

        if(UserInfo.GENDER == "M") {
            VolleyService.getNewUserListReq("F", activity!!, {success->
                var loadingDialog = PSDialog(activity!!)
                loadingDialog.setLoadingDialog()
                loadingDialog.show()
                newUserList.clear()
                var array = success
                for(i in 0..array.length()-1)
                {
                    var json = array[i] as JSONObject
                    newUserList.add(NewUserList(json.getString("user_id"), json.getString("user_nickname"), json.getString("user_birthday"), json.getString("user_city"),
                    json.getString("user_recentgps"), json.getString("user_recenttime"), json.getString("user_phone"), json.getString("image")))
                }
                VolleyService.getBlockingListReq(UserInfo.ID, activity!!, {success->
                    var blockingList = ArrayList<String>()
                    var array = success
                    for(i in 0..array.length()-1) {
                        var json = array[i] as JSONObject
                        blockingList.add(json.getString("blocking_user"))
                    }

                    if(UserInfo.BLOCKING == 1) {
                        var iter = newUserList.iterator()
                        while(iter.hasNext()){
                            var userList = iter.next() as NewUserList

                            if(UserInfo.BLOCKINGNUMBER.contains(userList.userPhone)){
                                iter.remove()
                            }
                            else {
                                for(i in 0..blockingList.size-1) {
                                    if(userList.userPhone == blockingList.get(i)) {
                                        iter.remove()
                                    }
                                }
                            }
                        }
                    }
                    else {
                        var iter = newUserList.iterator()
                        while(iter.hasNext()){
                            var userList = iter.next() as NewUserList

                            for(i in 0..blockingList.size-1) {
                                if(userList.userPhone == blockingList.get(i)) {
                                    iter.remove()
                                }
                            }
                        }
                    }


                    if(newUserList.size == 0) {
                        newUserText.visibility = View.GONE
                        newUserRV.visibility = View.GONE
                        newUserRV.adapter = null
                        loadingDialog.dismiss()
                    }
                    else {
                        if(newUserList.size > 4) {
                            newUserText.visibility = View.VISIBLE
                            newUserRV.visibility = View.VISIBLE
                            newUserRV.setHasFixedSize(true)
                            newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                            newUserRV.adapter = NewUserAdapter(activity!!, newUserList, 4)
                        }
                        else {
                            newUserText.visibility = View.VISIBLE
                            newUserRV.visibility = View.VISIBLE
                            newUserRV.setHasFixedSize(true)
                            newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                            newUserRV.adapter = NewUserAdapter(activity!!, newUserList, newUserList.size)
                        }
                        loadingDialog.dismiss()
                    }
                })
            })
        }
        else {
            VolleyService.getNewUserListReq("M", activity!!, {success->
                var loadingDialog = PSDialog(activity!!)
                loadingDialog.setLoadingDialog()
                loadingDialog.show()
                newUserList.clear()
                var array = success
                for(i in 0..array.length()-1)
                {
                    var json = array[i] as JSONObject
                    newUserList.add(NewUserList(json.getString("user_id"), json.getString("user_nickname"), json.getString("user_birthday"), json.getString("user_city"),
                        json.getString("user_recentgps"), json.getString("user_recenttime"), json.getString("user_phone"), json.getString("image")))
                }
                VolleyService.getBlockingListReq(UserInfo.ID, activity!!, {success->
                    var blockingList = ArrayList<String>()
                    var array = success
                    for(i in 0..array.length()-1) {
                        var json = array[i] as JSONObject
                        blockingList.add(json.getString("blocking_user"))
                    }

                    if(UserInfo.BLOCKING == 1) {
                        var iter = newUserList.iterator()
                        while(iter.hasNext()){
                            var userList = iter.next() as NewUserList

                            if(UserInfo.BLOCKINGNUMBER.contains(userList.userPhone)){
                                iter.remove()
                            }
                            else {
                                for(i in 0..blockingList.size-1) {
                                    if(userList.userPhone == blockingList.get(i)) {
                                        iter.remove()
                                    }
                                }
                            }
                        }
                    }
                    else {
                        var iter = newUserList.iterator()
                        while(iter.hasNext()){
                            var userList = iter.next() as NewUserList

                            for(i in 0..blockingList.size-1) {
                                if(userList.userPhone == blockingList.get(i)) {
                                    iter.remove()
                                }
                            }
                        }
                    }


                    if(newUserList.size == 0) {
                        newUserText.visibility = View.GONE
                        newUserRV.visibility = View.GONE
                        newUserRV.adapter = null
                        loadingDialog.dismiss()
                    }
                    else {
                        if(newUserList.size > 4) {
                            newUserText.visibility = View.VISIBLE
                            newUserRV.visibility = View.VISIBLE
                            newUserRV.setHasFixedSize(true)
                            newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                            newUserRV.adapter = NewUserAdapter(activity!!, newUserList, 4)
                        }
                        else {
                            newUserText.visibility = View.VISIBLE
                            newUserRV.visibility = View.VISIBLE
                            newUserRV.setHasFixedSize(true)
                            newUserRV.layoutManager = GridLayoutManager(activity!!, 2)
                            newUserRV.adapter = NewUserAdapter(activity!!, newUserList, newUserList.size)
                        }
                        loadingDialog.dismiss()
                    }
                })
            })
        }

        VolleyService.getExpressionCountReq(UserInfo.ID, activity!!, {success->
            var json = success

            if(json.getInt("send_like") > 0) {
                sendLikeBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
            }
            else {
                sendLikeBtn.setBackgroundResource(0)
            }

            if(json.getInt("receive_like") > 0) {
                receiveLikeBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
            }
            else {
                receiveLikeBtn.setBackgroundResource(0)
            }

            if(json.getInt("each_like") > 0) {
                eachLikeBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
            }
            else {
                eachLikeBtn.setBackgroundResource(0)
            }

            if(json.getInt("send_meet") > 0) {
                sendMeetBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
            }
            else {
                sendMeetBtn.setBackgroundResource(0)
            }

            if(json.getInt("receive_meet") > 0) {
                receiveMeetBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
            }
            else {
                receiveMeetBtn.setBackgroundResource(0)
            }

            if(json.getInt("each_meet") > 0) {
                eachMeetBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
            }
            else {
                eachMeetBtn.setBackgroundResource(0)
            }

            sendLikeCount.text = json!!.getInt("send_like").toString() + "명"
            receiveLikeCount.text = json!!.getInt("receive_like").toString() + "명"
            eachLikeCount.text = json!!.getInt("each_like").toString() + "명"
            sendMeetCount.text = json!!.getInt("send_meet").toString() + "명"
            receiveMeetCount.text = json!!.getInt("receive_meet").toString() + "명"
            eachMeetCount.text = json!!.getInt("each_meet").toString() + "명"

            VolleyService.getCountViewReq(UserInfo.ID, activity!!, {success->
                var json = success

                if(json.getInt("profile") > 0) {
                    visitProfileBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }

                if(json.getInt("story") > 0) {
                    visitStoryBtn.setBackgroundResource(R.drawable.rounded_color_layout_1dp)
                }

                visitProfileCount.text = json!!.getInt("profile").toString() + "명"
                visitStoryCount.text = json!!.getInt("story").toString() + "명"
            })
        })

        sendLikeBtn.setOnClickListener {
            startActivity("내가 좋아요를 보낸 이성")
        }

        receiveLikeBtn.setOnClickListener {
            startActivity("나에게 좋아요를 보낸 이성")
        }

        eachLikeBtn.setOnClickListener {
            startActivity("서로 좋아요가 연결된 이성")
        }

        sendMeetBtn.setOnClickListener {
            startActivity("내가 만나고 싶은 그대")
        }

        receiveMeetBtn.setOnClickListener {
            startActivity("나를 만나고 싶어하는 그대")
        }

        eachMeetBtn.setOnClickListener {
            startActivity("서로 연락처를 주고받은 그대")
        }

        visitProfileBtn.setOnClickListener {
            startActivity("내 프로필을 확인한 사람")
        }

        visitStoryBtn.setOnClickListener {
            startActivity("내 스토리를 확인한 사람")
        }

        return rootView
    }

    fun startActivity(input: String) {
        var intent = Intent(activity!!, PartnerListActivity::class.java)
        intent.putExtra("listType", input)
        startActivity(intent)
    }

}