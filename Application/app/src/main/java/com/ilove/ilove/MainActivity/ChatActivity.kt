package com.ilove.ilove.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
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

    companion object{
        var handler:Handler? = null
    }

    var chatAdapter=ChatAdapter()
    var room : ChatRoomItem? = null

    var ref : DatabaseReference? = null
    var query : Query? = null

    var init=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var intent=intent
        room=intent.getSerializableExtra("room") as ChatRoomItem
        list_chat.adapter=chatAdapter

        VolleyService.chatInitReq(room!!.roomId,this,{success ->
            if(success!!.length()!=0) {
                var array = success!!
                for (i in 0..array.length() - 1) {
                    var json=array[i] as JSONObject
                    addChatItem(json)
                }

                list_chat.setSelection(list_chat.adapter.getCount() - 1);
                init=1
            }
        })

        FirebaseMessaging.getInstance().subscribeToTopic(room!!.roomId!!)
            .addOnCompleteListener {
                var msg = "${room!!.roomId} subscribe success"
                if (!it.isSuccessful) msg = "${room!!.roomId} subscribe fail"
            }

        ref = FirebaseDatabase.getInstance().reference.child("chat").child(room!!.roomId)
        query = ref!!.orderByChild("chat_time")
        query!!.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                chatConversation(snapshot)
            }
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                chatConversation(snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })

        /*
        VolleyService.chatInitReq(room!!.roomId,this,{success ->
            if(success!!.length()!=0) {
                var array = success!!
                for (i in 0..array.length() - 1) {
                    var json=array[i] as JSONObject
                    //addChatItem(json)
                }

                list_chat.setSelection(list_chat.adapter.getCount() - 1);
            }
        })*/

        btn_send.setOnClickListener {

            if(edit_chat.text.toString()=="") return@setOnClickListener

            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            var chatPartner=""
            if(UserInfo.ID==room!!.maker) chatPartner=room!!.partner
            else chatPartner=room!!.maker

            VolleyService.insertChatReq(room!!.roomId,UserInfo.ID,UserInfo.NICKNAME,chatPartner,edit_chat.text.toString(),currentDate,this, {success ->
                firebaseWrite(room!!.roomId,UserInfo.ID,UserInfo.NICKNAME,edit_chat.text.toString(),currentDate)
                edit_chat.setText("")
                list_chat.setSelection(chatAdapter.count - 1)
            })
        }

        handler=object : Handler(){
            override fun handleMessage(msg: Message) {
                when(msg!!.what) {
                    0 -> {
                        var json=msg.obj as JSONObject
                        addChatItem(json)
                    }
                }
            }
        }
    }

    fun addChatItem(json : JSONObject){
        chatAdapter.addItem(
            ChatItem(
                json.getString("room_id"),
                json.getString("chat_speaker"),
                json.getString("chat_speaker_nickname"),
                json.getString("chat_content"),
                json.getString("chat_time"),
                null
            )
        )
        chatAdapter.notifyDataSetChanged()
    }

    fun firebaseWrite(roomId: String, chatSpeaker: String, chatSpeakerNickname: String, chatContent: String, chatTime: String){

        var map = HashMap<String, Any>()
        val key: String? = ref!!.push().key

        ref!!.updateChildren(map)

        var root = ref!!.child(key!!)
        var objectMap = HashMap<String, Any>()

        objectMap.put("room_id", roomId)
        objectMap.put("chat_speaker", chatSpeaker)
        objectMap.put("chat_speaker_nickname", chatSpeakerNickname)
        objectMap.put("chat_content", chatContent)
        objectMap.put("chat_time", chatTime)

        root.updateChildren(objectMap)
    }

    fun chatConversation(snapshot: DataSnapshot) {
        var msseageFragmentHandler = MessageFragment.handler
        var msg=msseageFragmentHandler!!.obtainMessage()
        msg.what=0
        msseageFragmentHandler.sendMessage(msg)

        if(init==0) return
        var i = snapshot.children.iterator()
        while (i.hasNext()) {

            var chatContent = ((i.next() as DataSnapshot).getValue()) as String
            var chatSpeaker = ((i.next() as DataSnapshot).getValue()) as String
            var chatSpeakerNickname = ((i.next() as DataSnapshot).getValue()) as String
            var chatTime = ((i.next() as DataSnapshot).getValue()) as String
            var roomId = ((i.next() as DataSnapshot).getValue()) as String

            chatAdapter.addItem(ChatItem(roomId,chatSpeaker,chatSpeakerNickname,chatContent,chatTime,null))
        }
        chatAdapter.notifyDataSetChanged()
    }
}