package com.ilove.ilove.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.ilove.ilove.Adapter.ChatAdapter
import com.ilove.ilove.Item.ChatItem
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.Object.SocketService
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        var intent=intent
        var room=intent.getSerializableExtra("room") as ChatRoomItem
        list_chat.adapter=chatAdapter

        FirebaseMessaging.getInstance().subscribeToTopic(room.roomId!!)
            .addOnCompleteListener {
                var msg = "${room.roomId} subscribe success"
                if (!it.isSuccessful) msg = "${room.roomId} subscribe fail"
            }

        SocketService.connectSocket()
        SocketService.init()

        SocketService.emitJoin(room.roomId)
        VolleyService.chatInitReq(room.roomId,this,{success ->
            if(success!!.length()!=0) {
                var array = success!!
                for (i in 0..array.length() - 1) {
                    var json=array[i] as JSONObject
                    addChatItem(json)
                }
            }
        })

        btn_send.setOnClickListener {

            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            var json=JSONObject()
                .put("room_id","test_room")
                .put("chat_speaker", "ksh")
                .put("chat_speaker_nickname","ksh")
                .put("chat_content", "${edit_chat.text}")
                .put("chat_time", currentDate)

            addChatItem(json)

            SocketService.emitMsg(json)

            edit_chat.setText("")
        }

        handler=object : Handler(){
            override fun handleMessage(msg: Message) {
                when(msg!!.what) {
                    0 -> {
                        var json=msg.obj as JSONObject
                        if(json.getString("chat_speaker")!="ksh")
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
}