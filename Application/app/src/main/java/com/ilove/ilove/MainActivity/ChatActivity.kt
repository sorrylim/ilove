package com.ilove.ilove.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.ilove.ilove.Adapter.ChatAdapter
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Fragment.MessageFragment
import com.ilove.ilove.Item.ChatItem
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {

    var chatAdapter=ChatAdapter()
    var room : ChatRoomItem? = null

    var ref : DatabaseReference? = null
    var query : Query? = null

    var chatPartnerImage : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var intent=intent
        room=intent.getSerializableExtra("room") as ChatRoomItem
        Log.d("test",room!!.toString())
        list_chat.adapter=chatAdapter

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

        ref = FirebaseDatabase.getInstance().reference.child("chat").child(room!!.roomId)
        query = ref!!.orderByKey()
        query!!.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                chatConversation(snapshot,"change")
            }
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                chatConversation(snapshot,"add")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })

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


            var lastChat=chatAdapter.getItem(chatAdapter.count-1) as ChatItem
            if(UserInfo.ID!=lastChat.chatSpeaker) {
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
}