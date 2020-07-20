package com.ilove.ilove.Item

import java.io.Serializable

data class ChatRoomItem (
    var roomId:String,
    var maker:String,
    var partner:String,
    var roomTitle:String,
    var lastChat:String?,
    var lastChatTime:String?,
    var imageUrl:String?
) : Serializable