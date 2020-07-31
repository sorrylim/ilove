package com.ilove.ilove.Fragment

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilove.ilove.Adapter.UserListAdapter
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.UserList
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.fragment_list.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ListFragment(titleText: TextView) : Fragment() {
    var upProfileUserList = ArrayList<UserList>()
    var userList = ArrayList<UserList>()
    var rootView : View? = null
    var sortType : Int = 0
    var titleText: TextView = titleText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        var sortGps : TextView = rootView!!.findViewById(R.id.text_sortgps)
        var sortTime : TextView = rootView!!.findViewById(R.id.text_sorttime)
        var userListRV : RecyclerView = rootView!!.findViewById(R.id.rv_userlist)
        var swipeLayout : SwipeRefreshLayout = rootView!!.findViewById(R.id.layout_swipe)

        //글꼴변경
        //val typefaceb = Typeface.createFromAsset(context?.assets, "nanumbarunpenb.ttf")
        //val typefacer = Typeface.createFromAsset(context?.assets, "nanumbarunpenr.ttf")

        swipeLayout.setOnRefreshListener {
            if(sortType == 0) {
                sortGps()
                swipeLayout.setRefreshing(false)
            }
            else {
                sortTime()
                swipeLayout.setRefreshing(false)
            }
        }

        userListRV.setOverScrollMode(View.OVER_SCROLL_NEVER)


        var typeBold = Typeface.createFromAsset(context?.assets, "nanumbarunpenb.ttf")
        var typeNormal = Typeface.createFromAsset(context?.assets, "nanumbarunpenr.ttf")

        sortGps()
        sortGps.setTypeface(typeBold)
        sortGps.setTextColor(Color.parseColor("#212121"))

        sortGps.setOnClickListener {
            sortType = 0
            sortGps()
            sortGps.setTextColor(Color.parseColor("#212121"))
            sortTime.setTypeface(typeNormal)
            sortGps.setTypeface(typeBold)
            sortTime.setTextColor(Color.parseColor("#616161"))
        }

        sortTime.setOnClickListener {
            sortType = 1
            sortTime()
            sortTime.setTextColor(Color.parseColor("#212121"))
            sortTime.setTypeface(typeBold)
            sortGps.setTypeface(typeNormal)
            sortGps.setTextColor(Color.parseColor("#616161"))
        }

        return rootView
    }

    fun sortTime() {
        var gpsTracker = GpsTracker(activity!!)
        val partnerListRV : RecyclerView = rootView!!.findViewById(R.id.rv_userlist)
        if(UserInfo.GENDER == "M") {
            VolleyService.getUserListReq("F", UserInfo.ID, activity!!, {success->
                userList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var location : List<String> = json.getString("user_recentgps").split(",")
                    var partner = UserList(json.getString("user_id"), json.getString("user_nickname"),
                        json.getString("user_birthday"), json.getString("user_city"), gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)), json.getString("user_recenttime"),
                        json.getString("user_previewintroduce"), json.getString("user_phone"), json.getString("image"), json.getString("user_purpose"),json.getInt("like"), json.getInt("meet"))
                    userList.add(partner)
                }

                Collections.sort(userList, object : Comparator<UserList> {
                    override fun compare(p0: UserList?, p1: UserList?): Int {
                        if(p0!!.recentTime < p1!!.recentTime) {
                            return 1
                        }
                        else if(p0!!.recentTime > p1!!.recentTime) {
                            return -1
                        }
                        return 0
                    }
                })

                VolleyService.getUpProfileUserListReq("F", UserInfo.ID, activity!!, { success ->
                    upProfileUserList.clear()
                    var array = success
                    for (i in 0..array!!.length() - 1) {
                        var json = array[i] as JSONObject
                        var location : List<String> = json.getString("user_recentgps").split(",")
                        var partner = UserList(
                            json.getString("user_id"),
                            json.getString("user_nickname"),
                            json.getString("user_birthday"),
                            json.getString("user_city"),
                            gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)),
                            json.getString("user_recenttime"),
                            json.getString("user_previewintroduce"),
                            json.getString("user_phone"),
                            json.getString("image"),
                            json.getString("user_purpose"),
                            json.getInt("like"),
                            json.getInt("meet")
                        )
                        upProfileUserList.add(partner)
                    }

                    upProfileUserList.addAll(userList)
                    partnerListRV.setHasFixedSize(true)
                    partnerListRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    partnerListRV.adapter = UserListAdapter(activity!!, upProfileUserList)
                })
            })
        }
        else if(UserInfo.GENDER == "F") {
            VolleyService.getUserListReq("M", UserInfo.ID, activity!!, {success->
                userList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var location : List<String> = json.getString("user_recentgps").split(",")
                    var partner = UserList(json.getString("user_id"), json.getString("user_nickname"),
                        json.getString("user_birthday"), json.getString("user_city"), gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)), json.getString("user_recenttime"),
                        json.getString("user_previewintroduce"), json.getString("user_phone"), json.getString("image"),json.getString("user_purpose"),
                        json.getInt("like"), json.getInt("meet"))
                    userList.add(partner)
                }

                Collections.sort(userList, object : Comparator<UserList> {
                    override fun compare(p0: UserList?, p1: UserList?): Int {
                        if(p0!!.recentTime < p1!!.recentTime) {
                            return 1
                        }
                        else if(p0!!.recentTime > p1!!.recentTime) {
                            return -1
                        }
                        return 0
                    }
                })

                VolleyService.getUpProfileUserListReq("M", UserInfo.ID, activity!!, { success ->
                    upProfileUserList.clear()
                    var array = success
                    for (i in 0..array!!.length() - 1) {
                        var json = array[i] as JSONObject
                        var location: List<String> = json.getString("user_recentgps").split(",")
                        var partner = UserList(
                            json.getString("user_id"),
                            json.getString("user_nickname"),
                            json.getString("user_birthday"),
                            json.getString("user_city"),
                            gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)),
                            json.getString("user_recenttime"),
                            json.getString("user_previewintroduce"),
                            json.getString("user_phone"),
                            json.getString("image"),
                            json.getString("user_purpose"),
                            json.getInt("like"),
                            json.getInt("meet")
                        )
                        upProfileUserList.add(partner)
                    }

                    upProfileUserList.addAll(userList)
                    partnerListRV.setHasFixedSize(true)
                    partnerListRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    partnerListRV.adapter = UserListAdapter(activity!!, upProfileUserList)
                })


            })
        }
    }

    fun sortGps() {
        var gpsTracker = GpsTracker(activity!!)
        val partnerListRV : RecyclerView = rootView!!.findViewById(R.id.rv_userlist)
        if(UserInfo.GENDER == "M") {
            VolleyService.getUserListReq("F", UserInfo.ID, activity!!, {success->
                userList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var location : List<String> = json.getString("user_recentgps").split(",")
                    var partner = UserList(json.getString("user_id"), json.getString("user_nickname"),
                        json.getString("user_birthday"), json.getString("user_city"), gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)), json.getString("user_recenttime"),
                        json.getString("user_previewintroduce"), json.getString("user_phone"), json.getString("image"), json.getString("user_purpose"),json.getInt("like"), json.getInt("meet"))
                    userList.add(partner)
                }

                Collections.sort(userList, object : Comparator<UserList> {
                    override fun compare(p0: UserList?, p1: UserList?): Int {
                        if(p0!!.recentGps < p1!!.recentGps) {
                            return -1
                        }
                        else if(p0!!.recentGps > p1!!.recentGps) {
                            return 1
                        }
                        return 0
                    }
                })

                VolleyService.getUpProfileUserListReq("F", UserInfo.ID, activity!!, { success ->
                    upProfileUserList.clear()
                    var array = success
                    for (i in 0..array!!.length() - 1) {
                        var json = array[i] as JSONObject
                        var location : List<String> = json.getString("user_recentgps").split(",")
                        var partner = UserList(
                            json.getString("user_id"),
                            json.getString("user_nickname"),
                            json.getString("user_birthday"),
                            json.getString("user_city"),
                            gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)),
                            json.getString("user_recenttime"),
                            json.getString("user_previewintroduce"),
                            json.getString("user_phone"),
                            json.getString("image"),
                            json.getString("user_purpose"),
                            json.getInt("like"),
                            json.getInt("meet")
                        )
                        upProfileUserList.add(partner)
                    }

                    upProfileUserList.addAll(userList)
                    partnerListRV.setHasFixedSize(true)
                    partnerListRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    partnerListRV.adapter = UserListAdapter(activity!!, upProfileUserList)
                })
            })
        }
        else if(UserInfo.GENDER == "F") {
            VolleyService.getUserListReq("M", UserInfo.ID, activity!!, {success->
                userList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var location : List<String> = json.getString("user_recentgps").split(",")
                    var partner = UserList(json.getString("user_id"), json.getString("user_nickname"),
                        json.getString("user_birthday"), json.getString("user_city"), gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)), json.getString("user_recenttime"),
                        json.getString("user_previewintroduce"), json.getString("user_phone"), json.getString("image"),json.getString("user_purpose"),
                        json.getInt("like"), json.getInt("meet"))
                    userList.add(partner)
                }

                Collections.sort(userList, object : Comparator<UserList> {
                    override fun compare(p0: UserList?, p1: UserList?): Int {
                        if(p0!!.recentGps < p1!!.recentGps) {
                            return -1
                        }
                        else if(p0!!.recentGps > p1!!.recentGps) {
                            return 1
                        }
                        return 0
                    }
                })

                VolleyService.getUpProfileUserListReq("M", UserInfo.ID, activity!!, { success ->
                    upProfileUserList.clear()
                    var array = success
                    for (i in 0..array!!.length() - 1) {
                        var json = array[i] as JSONObject
                        var location: List<String> = json.getString("user_recentgps").split(",")
                        var partner = UserList(
                            json.getString("user_id"),
                            json.getString("user_nickname"),
                            json.getString("user_birthday"),
                            json.getString("user_city"),
                            gpsTracker.getSortDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1)),
                            json.getString("user_recenttime"),
                            json.getString("user_previewintroduce"),
                            json.getString("user_phone"),
                            json.getString("image"),
                            json.getString("user_purpose"),
                            json.getInt("like"),
                            json.getInt("meet")
                        )
                        upProfileUserList.add(partner)
                    }

                    upProfileUserList.addAll(userList)
                    partnerListRV.setHasFixedSize(true)
                    partnerListRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    partnerListRV.adapter = UserListAdapter(activity!!, upProfileUserList)
                })
            })
        }
    }
}