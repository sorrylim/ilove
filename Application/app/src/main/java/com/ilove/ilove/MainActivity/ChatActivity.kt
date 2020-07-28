package com.ilove.ilove.MainActivity

import android.content.ClipData
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ReportFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.ilove.ilove.Adapter.ChatAdapter
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.IntroActivity.InquireActivity
import com.ilove.ilove.Item.ChatItem
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.MainActivity.VipActivity
import com.ilove.ilove.Object.VolleyService
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat_drawerlayout.*
import org.json.JSONObject
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.ilove.ilove.R

class ChatActivity : AppCompatActivity() {

    var chatAdapter=ChatAdapter(this)
    var room : ChatRoomItem? = null

    var ref : DatabaseReference? = null
    var query : Query? = null

    var chatPartnerImage : String? = null

    var roomId:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_drawerlayout)
        setSupportActionBar(toolbar_chat)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        navigation_view!!.setNavigationItemSelectedListener(navListener)

        var intent=intent
        room=intent.getSerializableExtra("room") as ChatRoomItem
        list_chat.adapter=chatAdapter

        roomId=room!!.roomId

        if(room!!.imageUrl==""){
            var partnerId=""
            if(UserInfo.ID==room!!.maker) partnerId = room!!.partner
            else partnerId = room!!.maker


            VolleyService.getProfileImageReq(partnerId,this,{success ->
                var json = success[0] as JSONObject

                chatPartnerImage=json.getString("image")
            })
        }
        else chatPartnerImage = room!!.imageUrl


        FirebaseMessaging.getInstance().subscribeToTopic(room!!.roomId!!)
            .addOnCompleteListener {
                var msg = "${room!!.roomId} subscribe success"
                if (!it.isSuccessful) msg = "${room!!.roomId} subscribe fail"
            }

        btn_send.setOnClickListener {

            if(edit_chat.text.toString()=="") return@setOnClickListener

            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            var chatPartner=""
            if(UserInfo.ID==room!!.maker) chatPartner=room!!.partner
            else chatPartner=room!!.maker

            VolleyService.insertChatReq(room!!.roomId,UserInfo.ID,UserInfo.NICKNAME,chatPartner,edit_chat.text.toString(),currentDate,this, {success ->
                writeFirebase(room!!.roomId,UserInfo.ID,UserInfo.NICKNAME,edit_chat.text.toString(),currentDate)
                edit_chat.setText("")
                list_chat.setSelection(chatAdapter.count - 1)
            })
        }

    }

    override fun onResume() {
        super.onResume()
        ref = FirebaseDatabase.getInstance().reference.child("chat").child(room!!.roomId)
        query = ref!!.orderByKey()

        query!!.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if(ref != null) chatConversation(snapshot,"change")
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(ref != null) chatConversation(snapshot,"add")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        finish()
        ref=null
        query=null
    }

    fun writeFirebase(roomId: String, chatSpeaker: String, chatSpeakerNickname: String, chatContent: String, chatTime: String){

        Log.d("test","WRITE")

        var map = HashMap<String, Any>()
        val key: String? = ref!!.push().key

        var root = ref!!.child(key!!)
        var objectMap = HashMap<String, Any>()

        objectMap.put("room_id", roomId)
        objectMap.put("chat_speaker", chatSpeaker)
        objectMap.put("chat_speaker_nickname", chatSpeakerNickname)
        objectMap.put("chat_content", chatContent)
        objectMap.put("chat_time", chatTime)
        objectMap.put("unread_count", 1)

        root.updateChildren(objectMap)
    }

    fun chatConversation(snapshot: DataSnapshot,updateType : String) {

        if(updateType=="add") {
            var key=snapshot.key
            var value=snapshot.value as HashMap<String,Any>

            if(UserInfo.ID!=value.get("chat_speaker").toString()){
                val hashMap=HashMap<String,Any>()
                hashMap.put("${key}/unread_count",0)
                ref!!.updateChildren(hashMap)
            }

            var i = snapshot.children.iterator()
            while (i.hasNext()) {

                var chatContent = ((i.next() as DataSnapshot).getValue()) as String
                var chatSpeaker = ((i.next() as DataSnapshot).getValue()) as String
                var chatSpeakerNickname = ((i.next() as DataSnapshot).getValue()) as String
                var chatTime = ((i.next() as DataSnapshot).getValue()) as String
                var roomId = ((i.next() as DataSnapshot).getValue()) as String
                var unreadCount = ((i.next() as DataSnapshot).getValue()) as Long

                while(chatPartnerImage==null){Log.d("test","이미지 수신 대기")}
                chatAdapter.addItem(
                    ChatItem(
                        roomId,
                        chatSpeaker,
                        chatSpeakerNickname,
                        chatContent,
                        chatTime,
                        null,
                        unreadCount.toString(),
                        chatPartnerImage
                    )
                )
            }

            chatAdapter.notifyDataSetChanged()
            list_chat.setSelection(chatAdapter.count - 1)

            var lastChat = chatAdapter.getItem(chatAdapter.count - 1) as ChatItem
            if (UserInfo.ID != lastChat.chatSpeaker) {
                VolleyService.readChatReq(lastChat.roomId!!, lastChat.chatTime, this, { success ->

                })
            }
        }
        else if(updateType=="change"){

            var i = snapshot.children.iterator()
            while (i.hasNext()) {

                var chatContent = ((i.next() as DataSnapshot).getValue()) as String
                var chatSpeaker = ((i.next() as DataSnapshot).getValue()) as String
                var chatSpeakerNickname = ((i.next() as DataSnapshot).getValue()) as String
                var chatTime = ((i.next() as DataSnapshot).getValue()) as String
                var roomId = ((i.next() as DataSnapshot).getValue()) as String
                var unreadCount = ((i.next() as DataSnapshot).getValue()) as Long

                chatAdapter.readChat(chatSpeaker,chatTime)
            }
            chatAdapter.notifyDataSetChanged()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_empty, menu)
        menu!!.add(0, 0, 0, "메뉴").setIcon(R.drawable.outline_list_24)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
           0-> { // 메뉴 버튼
                drawerLayout.openDrawer(GravityCompat.END)
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return false
    }
    private val navListener = NavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.report->{
                var intent = Intent(this, InquireActivity::class.java)
                startActivity(intent)
            }
            R.id.getout->{
                VolleyService.deleteReq("chatroom","room_id='"+roomId+"'",this,{success->
                    finish()
                })
            }
        }
        false
    }
}
