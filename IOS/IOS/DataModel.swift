//
//  DataModel.swift
//  IOS
//
//  Created by 김세현 on 2020/06/23.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation

//각 요청에 대하여 서버에서 수신하는 데이터들의 모델
struct ResultModel : Codable {
    var result : String
}

struct ViewCountModel : Codable{
    var profile : Int
    var story : Int
}

struct ExpressionCountModel : Codable {
    var send_like : Int
    var receive_like : Int
    var each_like : Int
    var send_meet : Int
    var receive_meet : Int
    var each_meet : Int
}

struct PartnerModel : Codable {
    var user_id: String
    var user_nickname: String
    var user_birthday: String
    var user_city: String
    var expression_date: String
    var user_phone: String
    var like: Int
    var meet: Int
    var image: String
    //var userImage: String
}

struct UserModel : Codable {
    var user_id : String
    var user_nickname : String
    var user_birthday : String
    var user_city : String
    var user_recentgps : String
    var user_introduce : String
    var user_certification : String?
    var user_phone : String
    var like : Int
    var meet : Int
    var image: String
}

struct StoryModel : Codable {
    var image_id : Int
    var image : String
    var user_id : String
}

struct StoryUserModel : Codable {
    var user_id: String
    var user_nickname: String
    var user_birthday: String
    var user_recentgps: String
    var image_content: String
    var likecount: Int
    var viewcount: Int
    var like: Int
}
