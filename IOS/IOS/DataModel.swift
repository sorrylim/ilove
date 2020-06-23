//
//  DataModel.swift
//  IOS
//
//  Created by 김세현 on 2020/06/23.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation

//각 요청에 대하여 서버에서 수신하는 데이터들의 모델
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

struct partnerModel : Codable {
    var userId: String
    var userNickname: String
    var userAge: String
    var userCity: String
    
}
