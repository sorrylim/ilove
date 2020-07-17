package com.ilove.ilove.Object

import android.util.Log
import com.ilove.ilove.MainActivity.ChatActivity
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

object SocketService {
    var ip="http://18.217.130.157:3001"
    var socket: Socket = IO.socket(ip)

    var connecting: Boolean = false

    fun connectSocket(roomId: String){
        if(!connecting) {
            socket.connect()
            socket.on("join", onJoin)
            socket.on("msg", onMsg)
            emitJoin(roomId)
            connecting=true
        }
    }

    fun disconnectSocket(){
        socket.disconnect()
    }

    val onJoin: Emitter.Listener= Emitter.Listener {
        Log.d("test","Socket Connect")
    }

    val onMsg: Emitter.Listener= Emitter.Listener {
        Log.d("test","onMsg")

        var handler=ChatActivity.handler
        var msg=handler!!.obtainMessage()

        msg.what=0
        msg.obj=it[0]
        handler.sendMessage(msg)
    }

    fun emitMsg(json: JSONObject){
        Log.d("test","emit Msg ")
        socket.emit("msg",json)
    }

    fun emitJoin(roomId : String) {
        var json=JSONObject()
            .put("room_id",roomId)

        socket.emit("join",json)
    }

    fun emitLeave(roomId : String){
        var json=JSONObject()
            .put("room_id",roomId)

        socket.emit("leave",json)
    }
}