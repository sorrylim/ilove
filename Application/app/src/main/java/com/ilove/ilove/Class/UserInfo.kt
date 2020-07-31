package com.ilove.ilove.Class

import android.app.Application

class UserInfo : Application() {
    companion object {
        var ID: String = "hyeha"
        var PW: String = ""
        var NICKNAME: String = "최혜지"
        var BIRTHDAY : String = ""
        var GENDER: String = "F"
        var AUTHORITY: String = "normal"
        var BLOCKING: Int = 0
        var PHONE: String = ""
        var ALARMLIKE: Int = 0
        var ALARMMEET: Int = 0
        var ALARMCHECKPROFILE: Int = 0
        var ALARMUPDATEPROFILE: Int = 0
        var ALARMMESSAGE: Int = 0
        var CANDYCOUNT: Int = 100
        var LIKECOUNT: Int = 0
        var MESSAGETICKET: Int = 0
        var VIP : Int = 0
        var TOKEN: String?=""
        var LATITUDE: String? = null
        var LONGITUDE : String? = null
    }
}