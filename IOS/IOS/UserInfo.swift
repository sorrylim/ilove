//
//  UserInfo.swift
//  IOS
//
//  Created by 김세현 on 2020/07/16.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation

public class UserInfo : ObservableObject{
    static let shared=UserInfo()
    
    var ID: String = ""
    var PW: String = ""
    var NICKNAME: String = ""
    var BIRTHDAY : String = ""
    var GENDER: String = ""
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
    var LATITUDE: Double? = nil
    var LONGITUDE : Double? = nil
    //var LOCATION: Location? = nil
}
