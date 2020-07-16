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

    var connecting: Boolean = false

    fun connectSocket(){
        if(!connecting) {
            socket.connect()
            socket.on("connect", onConnect)
            socket.on("msg", onMsg)
            connecting=true
        }
    }

    fun disconnectSocket(){
        socket.disconnect()
    }

    val onConnect: Emitter.Listener= Emitter.Listener {
        Log.d("test","Socket Connect")
    }

    val onMsg: Emitter.Listener= Emitter.Listener {
        Log.d("test","onMsg")

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

    fun emitMsg(json: JSONObject){
        socket.emit("msg",json)
    }

    fun emitJoin(roomId : String) {
        var json=JSONObject()
            .put("room_id",roomId)

        socket.emit("join",json)
    }
}