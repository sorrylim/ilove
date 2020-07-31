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
    //var user_city: String
    var user_recenttime: String
    var expression_date: String
    var user_phone: String
    var like: Int
    var meet: Int
    var image: String
}

struct VisitPartnerModel : Codable {
    var user_id: String
    var user_nickname: String
    var user_birthday: String
    //var user_city: String
    var user_recenttime: String
    var visit_date: String
    var user_phone: String
    var like: Int
    var meet: Int
    var image: String
}

struct NewUserModel : Codable {
    var user_id : String
    var user_nickname : String
    var user_birthday : String
    var user_city : String
    var user_recentgps : String
    var user_recenttime : String
    var user_phone : String
    var image : String
}

struct UserModel : Codable {
    var user_id : String
    var user_nickname : String
    var user_birthday : String
    var user_city : String
    var user_recentgps : String
    var user_recenttime : String
    var user_previewintroduce : String?
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

struct MyStoryImageModel : Codable {
    var image_id : Int
    var image : String
}

struct ChatRoomModel : Codable {
    var room_id : String
    var room_maker : String
    var room_partner : String
    var room_title : String
    var chat_content : String
    var chat_time : String
}

struct ProfileImageModel : Codable {
    var image_id : Int
    var user_id : String
    var image : String?
    var image_usage : String
    var image_content : String?
    var viewcount : Int
    var image_date : String
    var likecount : Int
}

struct UserOptionModel : Codable{
    var user_id : String
    var user_height : String?
    var user_bodytype : String?
    var user_bloodtype : String?
    var user_residence : String?
    var user_job : String?
    var user_education : String?
    var user_holiday : String?
    var user_cigarette : String?
    var user_alcohol : String?
    var user_religion : String?
    var user_brother : String?
    var user_country : String?
    var user_salary : String?
    var user_asset : String?
    var user_marriagehistory : String?
    var user_children : String?
    var user_marriageplan : String?
    var user_childrenplan : String?
    var user_parenting : String?
    var user_wishdate : String?
    var user_datecost : String?
    var user_roommate : String?
    var user_language : String?
    var user_interest : String?
    var user_personality : String?
    var user_favoriteperson : String?
    var user_city : String?
    var user_introduce : String?
    var user_previewintroduce : String?
    var user_gender : String?
}
