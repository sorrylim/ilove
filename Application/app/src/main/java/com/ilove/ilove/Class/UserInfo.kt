package com.ilove.ilove.Class

import android.app.Application
import android.location.Location

class UserInfo : Application() {
    companion object {
        var ID: String = "ksh"
        var PW: String = "F"
        var NICKNAME: String = ""
        var BIRTHDAY : String = ""
        var GENDER: String = "M"
        var AUTHORITY: String = ""
        var BLOCKING: Int = 0
        var PHONE: String = ""
        var ALARMLIKE: Int = 0
        var ALARMMEET: Int = 0
        var ALARMCHECKPROFILE: Int = 0
        var ALARMUPDATEPROFILE: Int = 0
        var ALARMMESSAGE: Int = 0
        var CANDYCOUNT: Int = 0
        var LIKECOUNT: Int = 0
        var MESSAGETICKET: Int = 0
        var TOKEN: String?=""
        var LATITUDE: String? = null
        var LONGITUDE : String? = null
    }
}