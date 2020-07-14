//
//  EachAlert.swift
//  IOS
//
//  Created by 김세현 on 2020/07/14.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EachAlert: View {
    
    var userId:String
    var userNickname:String
    var userAge:String
    var userCity:String
    
    var body: some View {
        VStack(spacing:10){
            Text("좋아요가 연결되었어요!\n대화를 시작해보세요")
                .font(.system(size:20,weight:.bold))
            
            Image("default_profile")
            
            Text("\(userNickname)")
                .font(.system(size:15))
            Text("\(userAge), \(userCity)")
                .font(.system(size:10))
            
            NavigationLink(destination: ChatListView()){
                Text("대화시작하기")
                    .font(.system(size: 10,weight: .bold))
                    .foregroundColor(Color.white)
                    .padding()
                    .background(Color.orange)
                    .cornerRadius(25)
                    .overlay(RoundedRectangle(cornerRadius: 15).stroke(Color.orange, lineWidth: 0))
            }
        }
        .frame(width:200, height: 250)
        .cornerRadius(20)
    }
}

struct EachAlert_Previews: PreviewProvider {
    static var previews: some View {
        EachAlert(userId: "", userNickname: "", userAge: "", userCity: "")
    }
}
