package com.ilove.ilove.Class

import android.app.Application

class UserInfo : Application() {
    companion object {
        var ID: String = "testtest"
        var PW: String = "test"
        var NICKNAME: String = "김세현"
        var BIRTHDAY : String = ""
        var GENDER: String = "M"
        var AUTHORITY: String = "manager"
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