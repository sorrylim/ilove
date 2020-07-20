package com.ilove.ilove.Item

data class ChatItem (
    var roomId : String?,
    var chatSpeaker : String?,
    var chatSpeakerNickname : String?,
    var chatContent : String?,
    var chatTime : String?,
    var isMyChat:Boolean?,
    var unreadCount : Int?
)
