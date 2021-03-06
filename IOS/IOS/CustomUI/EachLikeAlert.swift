//
//  EachMeetAlert.swift
//  IOS
//
//  Created by 김세현 on 2020/08/07.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EachLikeAlert: View {
    
    var userId:String
    var userNickname:String
    var userAge:Int
    var userCity:String
    var uiImage:UIImage
    
    @Binding var showing:Bool
    
    var body: some View {
        VStack(spacing: 10){
            Text("좋아요가 연결되었어요!\n대화를 시작해보세요")
                .font(.system(size:15))
                .multilineTextAlignment(.center)
            
            Image(uiImage: uiImage)
                .resizable()
                .frame(width: 100, height: 100)
                .clipShape(Circle())
            
            Text("\(userNickname)")
                .font(.system(size:15))
            Text("\(userAge), \(userCity)")
                .font(.system(size:10))
            
            NavigationLink(destination: ChatView(roomImage: self.uiImage, userId: userId, userNickname: userNickname,isNew: true))
            {
                Text("대화시작하기")
                    .font(.system(size: 15))
                    .foregroundColor(Color.white)
                    .padding(15)
                    .frame(width: 180, height: 40)
                    .background(Color.orange)
                    .cornerRadius(20)
            }
            
            Text("닫기")
                .font(.system(size: 13))
                .foregroundColor(Color.gray)
                .onTapGesture {
                    self.showing=false
            }
        }
        .frame(width:230, height: 300)
        .background(Color.white)
        .cornerRadius(20)
        .animation(.spring())
        .onDisappear(){
            self.showing=false
        }
    }
}

