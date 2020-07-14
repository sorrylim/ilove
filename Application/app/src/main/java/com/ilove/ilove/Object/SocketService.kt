package com.ilove.ilove.Object

import android.util.Log
import com.ilove.ilove.Fragment.MessageFragment
import com.ilove.ilove.MainActivity.ChatActivity
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

object SocketService {
    var ip="http://18.217.130.157:3001"
    var socket: Socket = IO.socket(ip)

    val onConnect: Emitter.Listener= Emitter.Listener {
    }

    val onMsg: Emitter.Listener= Emitter.Listener {
        Log.d("test",it[0].toString())

        var handler=ChatActivity.handler
        var msg=handler!!.obtainMessage()

        msg.what=0
        msg.obj=it[0]
        handler.sendMessage(msg)

        var handler2=MessageFragment.handler
        var msg2=handler2!!.obtainMessage()

        msg.what=0
        handler2.sendMessage(msg2)
    }

    fun connectSocket(){
        socket.connect()
    }

    fun disconnectSocket(){
        socket.disconnect()
    }

    fun init(){
        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on("msg",onMsg)
    }

    fun emitMsg(json: JSONObject){
        socket.emit("msg",json)

        var handler=MessageFragment.handler
        var msg=handler!!.obtainMessage()

        msg.what=0
        handler.sendMessage(msg)
    }

    fun emitJoin(roomId : String, roomPartner : String) {
        var json=JSONObject()
            .put("room_id",roomId)
            .put("room_partner",roomPartner)

        socket.emit("join",json)
    }
}